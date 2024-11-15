package com.google.springboot.service.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.time.Duration;

/**
 * @Author kris
 * @Create 2024-10-31 18:43
 * @Description: 这个优化后的SimpleCache实现具有以下特点:
 * 线程安全: 使用ConcurrentHashMap确保多线程环境下的安全性。
 * 灵活的过期策略: 支持默认过期时间和每次获取时指定的过期时间。
 * 最大大小限制: 通过maxSize参数限制缓存的最大条目数。
 * LRU清理: 当缓存超过最大大小时,可以清理最近最少使用的条目。
 * 惰性删除: 在获取时检查过期,而不是主动删除过期条目,提高性能。
 * 高精度时间: 使用纳秒级时间戳提高时间精度。
 * 功能分离: 将缓存条目封装为独立的内部类,提高代码的可读性和可维护性。
 * 这个实现在保持简单易用的同时,增加了更多实用的功能,使其更适合在生产环境中使用。
 */
public class LocalCacheMechanismV2 {
    static class SimpleCache<K, V> {
        // 使用ConcurrentHashMap保证线程安全
        private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
        private final Function<K, V> computeFunction; // 计算函数,用于生成缓存值
        private final long defaultExpirationNanos; // 默认过期时间(纳秒)
        private final int maxSize; // 缓存的最大条目数

        // 构造函数
        public SimpleCache(Function<K, V> computeFunction, Duration defaultExpiration, int maxSize) {
            this.computeFunction = computeFunction;
            this.defaultExpirationNanos = defaultExpiration.toNanos();
            this.maxSize = maxSize;
        }

        // 使用默认过期时间获取缓存值
        public V get(K key) {
            return get(key, defaultExpirationNanos);
        }

        // 使用指定过期时间获取缓存值
        public V get(K key, Duration expiration) {
            return get(key, expiration.toNanos());
        }

        // 核心获取方法
        private V get(K key, long expirationNanos) {
            long now = System.nanoTime();
            return cache.compute(key, (k, existingEntry) -> {
                if (existingEntry != null && !existingEntry.isExpired(now)) {
                    // 如果缓存存在且未过期,更新最后访问时间并返回
                    existingEntry.lastAccessTime = now;
                    return existingEntry;
                }
                // 缓存不存在或已过期,重新计算值
                V value = computeFunction.apply(k);
                return new CacheEntry<>(value, now + expirationNanos, now);
            }).value;
        }

        // 清除过期的缓存条目
        public void evictExpired() {
            long now = System.nanoTime();
            cache.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
        }

        // 清除最近最少使用的条目,当缓存大小超过最大限制时调用
        public void evictLeastRecentlyUsed() {
            if (cache.size() > maxSize) {
                List<Map.Entry<K, CacheEntry<V>>> entries = new ArrayList<>(cache.entrySet());
                entries.sort(Comparator.comparingLong(e -> e.getValue().lastAccessTime));
                int toRemove = cache.size() - maxSize;
                for (int i = 0; i < toRemove; i++) {
                    cache.remove(entries.get(i).getKey());
                }
            }
        }

        // 内部类,表示缓存条目
        private static class CacheEntry<V> {
            final V value; // 缓存的值
            final long expirationTime; // 过期时间
            long lastAccessTime; // 最后访问时间

            CacheEntry(V value, long expirationTime, long lastAccessTime) {
                this.value = value;
                this.expirationTime = expirationTime;
                this.lastAccessTime = lastAccessTime;
            }

            // 检查是否过期
            boolean isExpired(long now) {
                return now > expirationTime;
            }
        }
    }

    private static LocalCacheMechanismV2.ApiResponse fetchFromApi(String key) {
        System.out.println(" fetch Data by call fetchFromApi " + " : " + key + " : " + UUID.randomUUID());
        return new LocalCacheMechanismV2.ApiResponse("Data for " + key);
    }

    public static void main(String[] args) {
        fetchData();
    }

    public static void fetchData() {
        SimpleCache<String, ApiResponse> apiCache = new SimpleCache<>(
                LocalCacheMechanismV2::fetchFromApi,
                Duration.ofMinutes(5),
                1000
        );

// 使用默认过期时间
        ApiResponse response1 = apiCache.get("key1");
        ApiResponse response2 = apiCache.get("key1");


// 使用自定义过期时间
        ApiResponse response3 = apiCache.get("key2", Duration.ofSeconds(30));
        ApiResponse response4 = apiCache.get("key2");

// 定期清理过期条目
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(apiCache::evictExpired, 1, 1, TimeUnit.MINUTES);

// 在添加新条目前检查并清理LRU
        apiCache.evictLeastRecentlyUsed();
    }

    static class ApiResponse {
        private String data;

        public ApiResponse(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}
