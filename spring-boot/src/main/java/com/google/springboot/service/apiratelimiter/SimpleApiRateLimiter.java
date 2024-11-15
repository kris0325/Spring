package com.google.springboot.service.apiratelimiter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleApiRateLimiter {
    // 定义Semaphore，允许的最大并发数（最大请求数）
    private final Semaphore semaphore;
    // 时间窗口长度（秒）
    private final long timeWindowInSeconds;
    // 允许的最大请求数
    private final int maxRequests;

    public SimpleApiRateLimiter(int maxRequests, long timeWindowInSeconds) {
        this.maxRequests = maxRequests;
        this.timeWindowInSeconds = timeWindowInSeconds;
        // 初始化信号量，最大并发数为允许的最大请求数
        this.semaphore = new Semaphore(maxRequests);
    }

    // 模拟处理API请求的方法
    public void handleRequest(String requestName) {
        boolean acquired = false;
        try {
            // 尝试获取信号量，如果获取失败说明超过了最大请求数
            acquired = semaphore.tryAcquire();
            if (acquired) {
                // 成功获取信号量，处理请求
                System.out.println(requestName + " is being processed.");
                // 模拟请求处理的耗时
                TimeUnit.MILLISECONDS.sleep(500);
            } else {
                // 没有获取到信号量，拒绝请求
                System.out.println(requestName + " is rejected due to rate limiting.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (acquired) {
                // 释放信号量，表示请求处理完成
                semaphore.release();
            }
        }
    }

    // 启动速率限制器，在固定时间窗口后重置信号量
    public void start() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            // 重置信号量的许可数
            semaphore.drainPermits();
            semaphore.release(maxRequests);
            System.out.println("Rate limit reset.");
        }, timeWindowInSeconds, timeWindowInSeconds, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        // 初始化API速率限制器，时间窗口为10秒，最多允许3个请求
        SimpleApiRateLimiter rateLimiter = new SimpleApiRateLimiter(3, 10);
        rateLimiter.start(); // 启动速率限制器

        // 使用线程池模拟多个并发请求
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 模拟10个API请求
        for (int i = 1; i <= 20; i++) {
            final String requestName = "Request-" + i;
            executor.submit(() -> rateLimiter.handleRequest(requestName));
        }

        // 关闭线程池
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }
}
