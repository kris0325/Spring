package com.google.springboot.controller;

import com.google.springboot.DTO.MessagDTO;
import com.google.springboot.service.amazonSQS.AmazonSqsServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author kris
 * @Create 2024-10-16 19:37
 * @Description
 */
@RestController
@RequestMapping("/amazonSqs")
public class AmazonSqsController {
    @Autowired
    private AmazonSqsServiceHandler amazonSqsServiceHandler;

    @PostMapping("/createQueue")
    public ResponseEntity<String> createQueue(@RequestBody MessagDTO messagDTO) {
        String res = amazonSqsServiceHandler.createQueue(messagDTO.getName());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessagDTO messagDTO) {
        String res =  amazonSqsServiceHandler.sendMessage(messagDTO.getQueueUrl(), messagDTO.getMessage());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/receiveMessage")
    public ResponseEntity<String> receiveMessage(@RequestBody MessagDTO messagDTO) {
        String res = amazonSqsServiceHandler.receiveMessage(messagDTO.getQueueUrl(), messagDTO.getMaxNumberOfMessages());
        return ResponseEntity.ok(res);
    }
}
