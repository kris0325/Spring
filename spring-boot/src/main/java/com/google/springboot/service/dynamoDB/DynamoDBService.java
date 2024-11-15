package com.google.springboot.service.dynamoDB;

import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

/**
 * @Author kris
 * @Create 2024-10-16 16:16
 * @Description
 */
public interface DynamoDBService {
    void createTable();

    void insertItem();

    String queryItem(String key);
}
