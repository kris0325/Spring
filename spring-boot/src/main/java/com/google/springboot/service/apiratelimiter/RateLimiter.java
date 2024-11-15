package com.google.springboot.service.apiratelimiter;

/**
 * @Author kris
 * @Create 2024-10-28 20:12
 * @Description
 */
public interface RateLimiter {
    /**
     * 检查给定的客户端请求是否被允许
     *
     * @param clientId 客户端标识符
     * @return true 如果请求被允许，false 否则
     */
    boolean isAllowed(String clientId);

    boolean isAllowed(String clientId, String requested);
}
