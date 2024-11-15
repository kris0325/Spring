//package com.google.springboot.dao;
//
//import com.google.springboot.commom.OrderConstants;
//import org.apache.commons.lang3.RandomUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.BoundHashOperations;
//import org.springframework.data.redis.core.DefaultTypedTuple;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//import redis.clients.jedis.Jedis;
////import redis.clients.jedis.JedisCommands;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//import redis.clients.jedis.JedisCluster;
//
//
//@Repository
//public class RedisCacheDao<T> {
//    private final static Logger LOGGER = LoggerFactory.getLogger(RedisCacheDao.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private static final String UNLOCK_LUA =
//            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
//                    "then\n" +
//                    "    return redis.call('del',KEYS[1])\n" +
//                    "else\n" +
//                    "    return 0\n" +
//                    "end";
//
//
//    public boolean expireAt(String key, Date date) {
//        boolean result = false;
//        try {
//            result = redisTemplate.expireAt(key, date);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public T getFromValue(String key) {
//        T result = null;
//        try {
//            result = (T) redisTemplate.opsForValue().get(key);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForValue Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//    public T getFromValueException(String key) {
//        return (T) redisTemplate.opsForValue().get(key);
//    }
//
//
//    public void putToValue(String key, T value, Long time, TimeUnit timeUnit) {
//        try {
//            redisTemplate.opsForValue().set(key, value, time, timeUnit);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForObject Error! %s", e.getMessage()), e);
//        }
//
//    }
//
//
//    public void putToValue(String key, T value) {
//        try {
//            redisTemplate.opsForValue().set(key, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForObject Error! %s", e.getMessage()), e);
//        }
//
//    }
//
//    public Long increment(String key, Long value, Long timeout, TimeUnit timeUnit) {
//        Long result = null;
//        try {
//            result = redisTemplate.opsForValue().increment(key, value);
//            redisTemplate.expire(key, timeout, timeUnit);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public Long increment(String key, Long value) {
//        Long result = null;
//        try {
//            result = redisTemplate.opsForValue().increment(key, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public Double incrementDouble(String key, double value) {
//        Double result = null;
//        try {
//            result = redisTemplate.opsForValue().increment(key, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForValue increment Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public long putToList(String key, Object value) {
//        long result = 0;
//        try {
//            result = redisTemplate.opsForList().rightPush(key, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public boolean putToSortSet(String key, Object value, double score) {
//        boolean result = false;
//        try {
//            result = redisTemplate.opsForZSet().add(key, value, score);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public void putToHash(String key, String hashKey, Object value) {
//        boolean result = false;
//        try {
//            redisTemplate.opsForHash().put(key, hashKey, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForHash put Error! %s", e.getMessage()), e);
//        }
//    }
//
//
//    public List multiGetHash(String key, Set hashKey) {
//        List result = null;
//        try {
//            result = redisTemplate.opsForHash().multiGet(key, hashKey);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public Set getFromSortSet(String key, long length) {
//        Set result = null;
//        try {
//            result = redisTemplate.opsForZSet().range(key, 0, length - 1);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public long putToSortSet(String key, Set<DefaultTypedTuple> defaultTypedTuples) {
//        long result = 0;
//        try {
//            result = redisTemplate.opsForZSet().add(key, defaultTypedTuples);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public long getZsetRank(String key, Object member) {
//        long result = 0;
//        try {
//            result = redisTemplate.opsForZSet().rank(key, member);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForZSet rank Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public List<Object> leftGetAllList(String key) {
//        List<Object> result = null;
//        try {
//            result = redisTemplate.opsForList().range(key, 0L, -1L);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public Long incrementHash(String key, String hashKey, Long value) {
//        Long result = null;
//        try {
//            result = redisTemplate.opsForHash().increment(key, hashKey, value);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForHash increment Error! %s", e.getMessage()), e);
//        }
//        return result;
//    }
//
//
//    public void removeCache(String key) {
//        redisTemplate.delete(key);
//    }
//
//
//    public void putBoundHashWithExpire(String key, Map<String, String> dataMap, long time, TimeUnit timeUnit) {
//        try {
//            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
//
//            boundHashOperations.putAll(dataMap);
//            boundHashOperations.expire(time, timeUnit);
//        } catch (Exception e) {
//            LOGGER.error(String.format("Redis Cache putBoundHashWithExpire Error! %s", e.getMessage()), e);
//        }
//    }
//
//    public Map<String, String> getBoundHashEntries(String key) {
//        try {
//            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
//            return boundHashOperations.entries();
//        } catch (Exception e) {
//            LOGGER.error(String.format("Redis Cache getBoundHashEntries Error! %s", e.getMessage()), e);
//            return new HashMap<>();
//        }
//    }
//
//
//    public T getFromHash(String key, String hashKey) {
//        try {
//            return (T) redisTemplate.opsForHash().get(key, hashKey);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForList rightPush Error! %s", e.getMessage()), e);
//        }
//        return null;
//    }
//
//
//    public void putToHash(String key, String hashKey, T value, Long time, TimeUnit timeUnit) {
//        try {
//            redisTemplate.opsForHash().put(key, hashKey, value);
//            redisTemplate.expire(key, time, timeUnit);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache opsForHash put Error! %s", e.getMessage()), e);
//        }
//    }
//
//    public long getExpire(String key, TimeUnit timeUnit) {
//        long remaining = 0L;
//        try {
//            remaining = redisTemplate.getExpire(key, timeUnit);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache getExpire Error! %s", e.getMessage()), e);
//        }
//        return remaining;
//    }
//
//    public Set<T> getKeysByPattern(T pattern) {
//        Set<T> keySet = null;
//        try {
//            keySet = redisTemplate.keys(pattern);
//        } catch (Exception e) {
//            LOGGER.error(String.format("ab Redis Cache getKeysByPattern() Error! %s", e.getMessage()), e);
//        }
//        return keySet;
//    }
//
//
////    public String tryLock(String key, long expire) {
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = (connection) -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", expire);
////            };
////            String result = (String) redisTemplate.execute(callback);
////            if (StringUtils.isNotEmpty(result)) {
////                return requestId;
////            } else {
////                //再次检查
////                RedisCallback<String> query = (connection) -> {
////                    JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                    return commands.get(key);
////                };
////                String reqId = (String) redisTemplate.execute(query);
////                if (requestId.equals(reqId)) {
////                    return requestId;
////                }
////                return null;
////            }
////        } catch (Exception e) {
////            LOGGER.error("set redis occured an exception", e);
////        }
////        //redis出问题 默认放过
////        return requestId;
////    }
//
//
//    public boolean releaseLock(String key, String requestId) {
//        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
//        try {
//            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
//            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
//            RedisCallback<Long> callback = (connection) -> {
//                Object nativeConnection = connection.getNativeConnection();
//                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
//                // 集群模式
//                if (nativeConnection instanceof JedisCluster) {
//                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, Arrays.asList(key), Arrays.asList(requestId));
//                }
//
//                // 单机模式
//                else if (nativeConnection instanceof Jedis) {
//                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, Arrays.asList(key), Arrays.asList(requestId));
//                }
//                return 0L;
//            };
//            Long result = (Long) redisTemplate.execute(callback);
//
//            return result != null && result > 0;
//        } catch (Exception e) {
//            LOGGER.error("release lock occured an exception", e);
//        }
//        return false;
//    }
//
////    public boolean isDuplicate(Long orderId) {
////        boolean result = false;
////        String key = OrderConstants.ORDER_REPEAT_LOCK_KEY + orderId;
////        String requestId = UUID.randomUUID().toString();
////        //随机过期时间
////        long expire = RandomUtils.nextLong(OrderConstants.ORDER_REPEAT_LOCK_EXPIRE_TIME_START, OrderConstants.ORDER_REPEAT_LOCK_EXPIRE_TIME_END);
////        try {
////            RedisCallback<String> callback = (connection) -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", expire);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                //再次检查
////                result = doubleCheck(key, requestId);
////            }
////        } catch (Exception e) {
////            LOGGER.error("set redis occured an exception", e);
////        }
////        //redis出问题 默认放过
////        return result;
////    }
////
////
////    public boolean refundIsDuplicate(Long orderId) {
////        boolean result = false;
////        String key = OrderConstants.ORDER_REFUND_LOCK_KEY + orderId;
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = connection -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", OrderConstants.ORDER_REFUND_LOCK_EXPIRE_TIME);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                //再次检查
////                result = doubleCheck(key, requestId);
////            }
////        } catch (Exception e) {
////            LOGGER.error("refundIsDuplicate occur an exception e={}", e);
////        }//redis出问题 默认放过
////        return result;
////    }
//
////    public boolean itemsDelayNotifyIsDuplicate(Long itemsId, Integer notifyType) {
////        boolean result = false;
////        String key = OrderConstants.ITEMS_DELAY_NOTIFY_LOCK_KEY + itemsId + "_" + notifyType;
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = connection -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", OrderConstants.ITEMS_DELAY_NOTIFY_EXPIRE_TIME);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                //再次检查
////                result = doubleCheck(key, requestId);
////            }
////        } catch (Exception e) {
////            LOGGER.error("itemsDelayNotifyIsDuplicate occur an exception e={}", e);
////        }//redis出问题 默认放过
////        return result;
////    }
////
////
////    public boolean syncOrderCenterIsDuplicate(Long itemsId, Byte notifyType) {
////        boolean result = false;
////        String key = OrderConstants.SYNC_ORDER_LOCK_KEY + itemsId + "_" + notifyType;
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = connection -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", OrderConstants.SYNC_ORDER_EXPIRE_TIME);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                result = doubleCheck(key, requestId);
////
////            }
////        } catch (Exception e) {
////            LOGGER.error("syncOrderCenterIsDuplicate occur an exception e={}", e);
////        }//redis出问题 默认放过
////        return result;
////    }
////
////    public boolean insertCartLock(String openSource, Long skuId, String uid) {
////
////        boolean result = false;
////        String key = OrderConstants.INSERT_CART_LOCK_KEY + openSource + "_" + uid + "_" + skuId;
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = connection -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", OrderConstants.INSERT_CAR_EXPIRE_TIME);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                result = doubleCheck(key, requestId);
////
////            }
////        } catch (Exception e) {
////            LOGGER.error("insertCartLock occur an exception e={}", e);
////        }//redis出问题 默认放过
////        return result;
////    }
////
////    public boolean batchInsertCartLock(String openSource, String uid) {
////
////        boolean result = false;
////        String key = OrderConstants.BATCH_INSERT_CART_LOCK_KEY + "_" + openSource + "_" + uid;
////        String requestId = UUID.randomUUID().toString();
////        try {
////            RedisCallback<String> callback = connection -> {
////                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////                return commands.set(key, requestId, "NX", "PX", OrderConstants.INSERT_CAR_EXPIRE_TIME);
////            };
////            String res = (String) redisTemplate.execute(callback);
////            if (StringUtils.isEmpty(res)) {
////                result = doubleCheck(key, requestId);
////
////            }
////        } catch (Exception e) {
////            LOGGER.error("batchInsertCartLock occur an exception e={}", e);
////        }//redis出问题 默认放过
////        return result;
////    }
////
////    private boolean doubleCheck(String key, String requestId) {
////        //再次检查
////        RedisCallback<String> query = (connection) -> {
////            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
////            return commands.get(key);
////        };
////        String reqId = (String) redisTemplate.execute(query);
////        if (!requestId.equals(reqId)) {
////            return true;
////        }
////        return false;
////    }
//
//}
