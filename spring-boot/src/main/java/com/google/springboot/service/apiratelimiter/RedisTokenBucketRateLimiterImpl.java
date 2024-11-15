package com.google.springboot.service.apiratelimiter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Collections;
/**
 * @Author kris
 * @Create 2024-10-28 21:11
 * @Description 令牌桶算法實現限流器
 *
 *  假设 rate = 5，burst = 10：
 *          * 在正常情况下，每秒最多处理 5 个请求。
 *          * 如果短时间内（例如 1 秒内）有 10 个请求到达，由于桶的容量为 10，前 10 个请求都可能会通过，但在下一秒钟将无法再处理请求，直到生成新的令牌。
 *          * 总结
 */


@Component
public class RedisTokenBucketRateLimiterImpl implements BucketRateRateLimiter {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> rateLimiterScript;
    private final String keyPrefix;
    private final double rate;        // 每秒生成的令牌数
    private final int burstCapacity;  // 令牌桶的最大容量

    public RedisTokenBucketRateLimiterImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = "api_limit";
        this.rate = 5.0;                // 每秒生成5个令牌
        this.burstCapacity = 10;        // 令牌桶容量为10

        /**
         local key = KEYS[1]
         local rate = tonumber(ARGV[1])         -- 每秒生成的令牌数
         local burst = tonumber(ARGV[2])        -- 令牌桶的最大容量
         local now = tonumber(ARGV[3])          -- 当前时间
         local requested = tonumber(ARGV[4])    -- 请求消耗的令牌数

         -- 获取当前令牌数和上次刷新时间
         local token_count = tonumber(redis.call('get', key .. ':tokens') or burst)
         local last_refreshed = tonumber(redis.call('get', key .. ':timestamp') or now)

         -- 计算时间差
         local delta = math.max(0, now - last_refreshed)

         -- 计算新生成的令牌数
         local new_tokens = math.min(burst, token_count + (delta * rate))

         if new_tokens >= requested then
         -- 如果足够，允许请求并更新状态
         redis.call('set', key .. ':tokens', new_tokens - requested)
         redis.call('set', key .. ':timestamp', now)
         return 1
         else
         -- 否则拒绝请求
         return 0
         end

         */

        /**
         * 令牌桶限流器的逻辑基于令牌桶算法，用于控制在给定时间内允许处理的请求数。下面是对 Lua 脚本逻辑的逐步解释：
         *
         * 令牌桶限流逻辑解析
         * 参数定义：
         *
         * key: 唯一标识符，通常用于标识特定客户端的令牌桶。
         * rate: 每秒生成的令牌数。比如，设定为 5，则每秒最多可生成 5 个令牌。
         * burst: 令牌桶的最大容量。比如，设定为 10，表示最多可以存储 10 个令牌。
         * now: 当前时间，通常以秒为单位。
         * requested: 本次请求消耗的令牌数，通常为 1，表示请求一个令牌。
         * 获取当前状态：
         *
         * token_count: 从 Redis 中获取当前可用的令牌数，如果未找到，则设为桶的最大容量（burst）。
         * last_refreshed: 从 Redis 中获取最后更新时间，如果未找到，则设为当前时间（now）。
         * 计算时间差：
         *
         * delta: 计算从上次刷新以来经过的时间差（now - last_refreshed），确保至少为 0。
         * 计算新生成的令牌数：
         *
         * new_tokens: 计算新生成的令牌数，使用公式 math.min(burst, token_count + (delta * rate))，这意味着新的令牌数不能超过桶的最大容量（burst）。
         * 请求处理：
         *
         * if new_tokens >= requested then: 检查是否有足够的令牌以满足请求。如果有，则允许请求，并更新 Redis 中的令牌数和时间戳。
         * redis.call('set', key .. ':tokens', new_tokens - requested): 更新令牌数，减去消耗的令牌数。
         * redis.call('set', key .. ':timestamp', now): 更新最后请求时间为当前时间。
         * 如果没有足够的令牌，则返回 0，表示请求被拒绝。
         * QPS（每秒请求数）计算
         * 在这个实现中，QPS（Queries Per Second，每秒请求数）由以下因素决定：
         *
         * rate: 每秒生成的令牌数。在这个示例中，rate 设置为 5，意味着每秒最多可以处理 5 个请求。
         * burst: 最大令牌桶容量，允许在短时间内突发多个请求。如果当前有 10 个令牌可用，则可以在短时间内处理 10 个请求，但在随后的请求中，只有在每秒生成 5 个新令牌后才能继续处理。
         * 例子
         * 假设 rate = 5，burst = 10：
         * 在正常情况下，每秒最多处理 5 个请求。
         * 如果短时间内（例如 1 秒内）有 10 个请求到达，由于桶的容量为 10，前 10 个请求都可能会通过，但在下一秒钟将无法再处理请求，直到生成新的令牌。
         * 总结
         * 此实现允许高频率的请求（最大 QPS 为 rate），并且通过桶的容量允许突发流量。
         * 这种策略适用于需要短时间内处理突发请求，同时又要控制请求速率的场景。
         */

        // Lua 脚本实现令牌桶算法
        this.rateLimiterScript = new DefaultRedisScript<>();
        this.rateLimiterScript.setScriptText(
                "local key = KEYS[1] " +
                        "local rate = tonumber(ARGV[1]) " +
                        "local burst = tonumber(ARGV[2]) " +
                        "local now = tonumber(ARGV[3]) " +
                        "local requested = tonumber(ARGV[4]) " +
                        "local token_count = tonumber(redis.call('get', key .. ':tokens') or burst) " +
                        "local last_refreshed = tonumber(redis.call('get', key .. ':timestamp') or now) " +
                        "local delta = math.max(0, now - last_refreshed) " +
                        "local new_tokens = math.min(burst, token_count + (delta * rate)) " +
                        "if new_tokens >= requested then " +
                        "    redis.call('set', key .. ':tokens', new_tokens - requested) " +
                        "    redis.call('set', key .. ':timestamp', now) " +
                        "    return 1 " +
                        "else " +
                        "    return 0 " +
                        "end"
        );
        this.rateLimiterScript.setResultType(Long.class);
    }

    @Override
    public boolean isAllowed(String clientId, int tokensRequested) {
        String key = keyPrefix + ":" + clientId;
        long currentTime = Instant.now().getEpochSecond();

        Long result = redisTemplate.execute(
                rateLimiterScript,
                Collections.singletonList(key),
                String.valueOf(rate),
                String.valueOf(burstCapacity),
                String.valueOf(currentTime),
                String.valueOf(tokensRequested)
        );

        return result != null && result == 1;
    }

    @Override
    public boolean isAllowed(String clientId) {
        return true;
    }
}

