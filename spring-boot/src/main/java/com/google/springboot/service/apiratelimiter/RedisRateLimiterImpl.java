package com.google.springboot.service.apiratelimiter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;

/**
 * @Author kris
 * @Create 2024-10-28 20:13
 * @Description ：滑动窗口算法實現限流器
 * 在前面提到的基于 Redis 的限流器中，使用了滑动窗口算法（Sliding Window Log Algorithm）来限制请求。该算法基于 Redis 的 ZSET（有序集合） 来记录每个请求的时间戳，然后根据一个固定的时间窗口（如 10 秒）来计算窗口内的请求数量，从而判断是否超过限流阈值。
 *
 * 滑动窗口算法的工作原理
 * 记录请求时间：每次请求到来时，将当前时间戳记录到 Redis 的 ZSET 中。ZSET 的结构允许按时间戳排序，从而方便在一定范围内进行计数。
 *
 * 移除过期请求：根据当前时间和时间窗口大小，移除 ZSET 中窗口外的过期请求。即：将窗口外的请求记录清除，保留时间窗口内的有效请求。
 *
 * 计算请求数量：在当前时间窗口内，计算 ZSET 中的请求数量，并与最大允许请求数进行对比。若请求数低于限流阈值，则允许请求；否则拒绝请求。
 *
 * 算法示例
 * 假设限流策略为10 秒内最多允许 5 次请求：
 *
 * 当请求到来时，先移除 ZSET 中 10 秒前的记录，再统计剩余的请求数。如果请求数小于 5，则允许请求；否则限制请求。
 * 滑动窗口算法优缺点
 * 优点：比固定窗口算法更灵活，可以更精细地控制突发流量。
 * 缺点：在高并发下，频繁的 ZSET 操作可能带来一定的性能开销。
 * 令牌桶与滑动窗口的区别
 * 在滑动窗口算法中，限流器通过计算时间窗口内的请求数来决定是否允许新请求；而在令牌桶算法中，通过生成和消耗令牌来控制请求的速率。滑动窗口更适合统计在一定时间内的请求总量，而令牌桶更适合限制请求速率并允许一定的突发请求量。
 *
在生产环境中，确实可能会出现多个请求在同一时间几乎同时到达的情况。当前的限流实现依赖 Redis 的 ZSET（有序集合） 来存储请求时间戳，并根据时间窗口计算请求次数。不过，这种方案在短时间内快速并发请求时可能无法准确达到期望的限流效果。主要原因如下：

时间窗口更新不及时：由于所有请求同时到达，Redis 的 ZSET 尚未刷新到新的时间窗口，因此 Lua 脚本会在当前时间窗口内允许超过 maxRequests 的请求量。

限流窗口的粒度：当前实现的限流窗口是秒级别的，因此短时间（例如同一秒内）瞬间的高并发请求没有细化处理。

 * local key = KEYS[1]                   -- Redis 中的限流键，通常以 "api_limit:<clientId>" 命名
 * local maxRequests = tonumber(ARGV[1])  -- 最大请求数（由 Java 参数传入）
 * local timeWindow = tonumber(ARGV[2])   -- 时间窗口大小，单位为秒（由 Java 参数传入）
 * local currentTime = tonumber(ARGV[3])  -- 当前时间，单位为秒（由 Java 参数传入）
 *
 * -- 1. 移除当前窗口外的旧请求记录（过期的请求）
 * redis.call('ZREMRANGEBYSCORE', key, 0, currentTime - timeWindow)
 *
 * -- 2. 计算当前时间窗口内的请求数量
 * local requestCount = redis.call('ZCARD', key)
 *
 * -- 3. 判断当前请求数是否小于允许的最大请求数
 * if requestCount < maxRequests then
 *     -- 如果请求数未超过最大值，将当前请求计入限流窗口
 *     redis.call('ZADD', key, currentTime, currentTime)
 *     -- 设置过期时间（让 key 在窗口到期后自动删除）
 *     redis.call('EXPIRE', key, timeWindow)
 *     return 1  -- 表示请求被允许
 * else
 *     return 0  -- 表示请求被限流
 * end
 */
@Component
public class RedisRateLimiterImpl implements RateLimiter{
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> rateLimiterScript;
    private final String keyPrefix;
    private final int maxRequests;
    private final int timeWindowInSeconds;

    public RedisRateLimiterImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = "api_limit";
        this.maxRequests = 5;
        this.timeWindowInSeconds = 10;

        // Lua 脚本用于限流
        this.rateLimiterScript = new DefaultRedisScript<>();
        this.rateLimiterScript.setScriptText(
                "local key = KEYS[1] " +
                        "local maxRequests = tonumber(ARGV[1]) " +
                        "local timeWindow = tonumber(ARGV[2]) " +
                        "local currentTime = tonumber(ARGV[3]) " +

                        "redis.call('ZREMRANGEBYSCORE', key, 0, currentTime - timeWindow) " +
                        "local requestCount = redis.call('ZCARD', key) " +
                        "if requestCount < maxRequests then " +
                        "redis.call('ZADD', key, currentTime, currentTime) " +
                        "redis.call('EXPIRE', key, timeWindow) " +
                        "return 1 " +
                        "else " +
                        "return 0 " +
                        "end"
        );
        this.rateLimiterScript.setResultType(Long.class);
    }

    @Override
    public boolean isAllowed(String clientId) {
        String key = keyPrefix + ":" + clientId;
        long currentTime = Instant.now().getEpochSecond();

        // 使用 Lua 脚本进行限流检查
        Long result = redisTemplate.execute(
                rateLimiterScript,
                Collections.singletonList(key),
                String.valueOf(maxRequests),
                String.valueOf(timeWindowInSeconds),
                String.valueOf(currentTime)
        );

        return result != null && result == 1;
    }

    @Override
    public boolean isAllowed(String clientId,String id) {
        return true;
    }


}
