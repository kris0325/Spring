package com.google.springboot.service.apiratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final Map<String, ApiRateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimiter)")
    public Object handleRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimiter) throws Throwable {
        String key = getKey(joinPoint, rateLimiter);
        ApiRateLimiter limiter = limiters.computeIfAbsent(key,
            k -> new ApiRateLimiter(rateLimiter.maxRequests(), rateLimiter.timeWindowSeconds()));

        if (!limiter.tryAcquire()) {
            log.warn("Rate limit exceeded for key: {}", key);
            throw new RateLimitExceededException(
                String.format("Rate limit exceeded. Maximum %d requests per %d seconds allowed",
                    rateLimiter.maxRequests(), rateLimiter.timeWindowSeconds())
            );
        }
        return joinPoint.proceed();
    }


    private String getKey(ProceedingJoinPoint joinPoint, RateLimit rateLimiter) {
        String key = rateLimiter.key();
        if (key.isEmpty()) {
            // 如果沒有指定key，使用方法簽名作為key
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            key = signature.getDeclaringTypeName() + "." + signature.getName();
        }
        return key;
    }
}