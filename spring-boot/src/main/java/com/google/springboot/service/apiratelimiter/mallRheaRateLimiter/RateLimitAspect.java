package com.google.springboot.service.apiratelimiter.mallRheaRateLimiter;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;

@Aspect
@Component
public class RateLimitAspect {
    Logger log = LoggerFactory.getLogger(RateLimitAspect.class);
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Around("@annotation(rateLimitResource)")
    public Object around(ProceedingJoinPoint point, RateLimitResource rateLimitResource) throws Throwable {
        // 1. 構造限流key
        String baseKey = "rateLimit:" + rateLimitResource.value();
        String limitKey = baseKey + ":" + getLimitParam(point);
        
        // 2. 執行限流檢查的Lua腳本
        String script = 
            "local key = KEYS[1] " +
            "local limit = tonumber(ARGV[1]) " +
            "local current = redis.call('incr', key) " +
            "if current == 1 then " +
            "  redis.call('expire', key, 1) " + // 設置1秒過期
            "end " +
            "return current";
        
        // 3. 執行腳本並獲取當前訪問次數
        Long current = redisTemplate.execute(
            new DefaultRedisScript<>(script, Long.class),
            Collections.singletonList(limitKey),
            String.valueOf(rateLimitResource.limit())
        );
        
        // 4. 檢查是否超過限流閾值
        if (current > rateLimitResource.limit()) {
            log.warn("Rate limit exceeded for key: {}, current: {}, limit: {}", 
                    limitKey, current, rateLimitResource.limit());
            throw new RateLimitException("Rate limit exceeded for " + limitKey);
        }
        
        // 5. 未超過限流閾值，執行原方法
        return point.proceed();
    }
    
    /**
     * c
     */
    private String getLimitParam(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = point.getArgs();
        
        for (int i = 0; i < parameters.length; i++) {
            RateLimitParam param = parameters[i].getAnnotation(RateLimitParam.class);
            if (param != null) {
                return String.valueOf(args[i]);
            }
        }
        return "default";
    }
}
