package com.google.springboot.controller;

//import com.google.common.util.concurrent.RateLimiter;

import com.google.springboot.service.apiratelimiter.BucketRateRateLimiter;
import com.google.springboot.service.apiratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/rateLimiter")
public class BusinessController {
    private static final Logger log = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private RateLimiter rateLimiter;

    @Autowired
    private BucketRateRateLimiter bucketRateRateLimiter;

    //    @RateLimit(maxRequests = 100, timeWindowSeconds = 60)
//    @GetMapping("/business")
//    public ResponseEntity<String> handleRequest(@RequestParam String input) {
//        // 業務邏輯
//        log.info("handleRequest:{}", input);
//        // log.info("input: {}", input);
//        return ResponseEntity.ok("Processed: " + input);
//    }
    @GetMapping("/business")
    public ResponseEntity<String> handleRequest(@RequestParam String clientId) throws InterruptedException {
        for (int i = 1; i <= 20; i++) {
            if (bucketRateRateLimiter.isAllowed(clientId,1)) {
                System.out.println("### Request is allowed for client: " + clientId);
                // 执行具体// 業務邏輯
                Thread.sleep(1);
            } else {
                System.out.println("*** Request is rate limited for client: " + clientId);
                // 返回限流响应或执行限流后的逻辑
            }
        }
        return ResponseEntity.ok("Processed: " + clientId);
    }
}
