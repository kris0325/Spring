package com.google.springboot.service.apiratelimiter.mallRheaRateLimiter;

/**
 * 限流異常
 */

public class RateLimitException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public RateLimitException() {
        super();
    }
    
    public RateLimitException(String message) {
        super(message);
    }
    
    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RateLimitException(Throwable cause) {
        super(cause);
    }
}