//package com.google.springboot.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @author: yangwenkang
// * @date: 2021/08/22
// * @description:
// **/
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jcf = new JedisConnectionFactory();
//        jcf.setHostName("localhost");
//        return jcf;
//    }
//
//    @Bean
//    public RedisTemplate redisTemplate() {
//        RedisTemplate rt = new RedisTemplate();
//        rt.setConnectionFactory(jedisConnectionFactory());
//        rt.setKeySerializer(new StringRedisSerializer());
//        rt.setValueSerializer(new StringRedisSerializer());
//        return rt;
//    }
//
//}
