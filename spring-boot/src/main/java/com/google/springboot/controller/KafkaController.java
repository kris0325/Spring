package com.google.springboot.controller;

//import com.google.springboot.config.RedisConfig;
import com.google.springboot.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.Greeting;

/**
 * @author: yangwenkang
 * @date: 2022/07/02
 * @description:
 **/
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        kafkaProducerService.sendMsg();
        return "success";
    }
}



