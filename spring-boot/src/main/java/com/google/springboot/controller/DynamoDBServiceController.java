package com.google.springboot.controller;

import com.google.springboot.service.dynamoDB.DynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author kris
 * @Create 2024-10-16 16:13
 * @Description
 */
@RestController
@RequestMapping("/dynamoDB")
public class DynamoDBServiceController {
    @Autowired
    private DynamoDBService dynamoDBService;

    @GetMapping("/queryItem/{key}")
    public ResponseEntity<String> queryItem(@PathVariable String key) {
        return ResponseEntity.ok(dynamoDBService.queryItem(key));
    }

    @PostMapping("/insertItem")
    public ResponseEntity<String> insertItem() {
        dynamoDBService.insertItem();
        return ResponseEntity.ok("200");
    }


}
