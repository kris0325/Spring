package com.google.springboot.service.apiratelimiter.mallRheaRateLimiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitResource {
    /**
     * 限流資源標識
     */
    String value();
    
    /**
     * 限流閾值
     */
    int limit() default 100;
    
    /**
     * 時間單位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}