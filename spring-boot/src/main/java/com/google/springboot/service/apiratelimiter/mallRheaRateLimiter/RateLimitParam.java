package com.google.springboot.service.apiratelimiter.mallRheaRateLimiter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitParam {
    /**
     * 參數標識，用於構造限流key
     */
    String value();
}