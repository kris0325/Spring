package com.google.springboot.service.apiratelimiter;

import java.util.concurrent.Semaphore;

/**
 *  TODO : 基於semaphore實現限流器
 */
public class ApiRateLimiter {
    private final Semaphore semaphore;
    private final int maxRequests;
    private final int timeWindowSeconds;
    private volatile long lastResetTime;

    public ApiRateLimiter(int maxRequests, int timeWindowSeconds) {
        this.maxRequests = maxRequests;
        this.timeWindowSeconds = timeWindowSeconds;
        this.semaphore = new Semaphore(maxRequests);
        this.lastResetTime = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
        tryReset(now);

        return semaphore.tryAcquire();
    }

    private synchronized void tryReset(long now) {
        if (now - lastResetTime >= timeWindowSeconds * 1000) {
            semaphore.drainPermits();
            semaphore.release(maxRequests);
            lastResetTime = now;
        }
    }
}