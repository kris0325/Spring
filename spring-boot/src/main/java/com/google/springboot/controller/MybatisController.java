package com.google.springboot.controller;

//import com.google.springboot.config.RedisConfig;
import com.google.springboot.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/mybatis")
public class MybatisController {
	private static final Logger log = LoggerFactory.getLogger(MybatisController.class);



	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));

		log.info("success call /greeting,  result:{}",greeting);

//		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RedisConfig.class);
//		RedisConfig redisConfig = applicationContext.getBean(RedisConfig.class);
//		RedisTemplate redisTemplate = redisConfig.redisTemplate();
//		redisTemplate.opsForValue().set("key11","value11");
//		redisTemplate.opsForValue().set("key12","value12");
//		String value11 = (String) redisTemplate.opsForValue().get("key11");


		return greeting;
	}
}
