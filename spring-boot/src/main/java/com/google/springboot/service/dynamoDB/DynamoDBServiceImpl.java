package com.google.springboot.service.dynamoDB;

import com.alibaba.fastjson.JSONArray;
import com.google.springboot.config.DynamoDBClient;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.internal.document.JsonStringFormatHelper;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.UUID;

/**
 * @Author kris
 * @Create 2024-10-16 15:32
 * @Description
 */
@Service
public class DynamoDBServiceImpl implements DynamoDBService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBServiceImpl(DynamoDBClient dbClient) {
        this.dynamoDbClient = dbClient.getClient();
    }

    @Override
    public void createTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName("TestTable")
                .keySchema(KeySchemaElement.builder()
                        .attributeName("id").keyType(KeyType.HASH).build())
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("id").attributeType(ScalarAttributeType.S).build())
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();

        dynamoDbClient.createTable(createTableRequest);
    }

    @Override
    public void insertItem() {
        String newId = UUID.randomUUID().toString();
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("TestTable")
                .item(Map.of("id", AttributeValue.builder().s(newId).build(),
                        "name", AttributeValue.builder().s("Test Item : "+newId).build()))
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    @Override
    public String queryItem(String key) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("TestTable")
                .key(Map.of("id", AttributeValue.builder().s(key).build()))
                .build();

        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        String res = String.valueOf(getItemResponse.item());
        System.out.println(res);
        return String.valueOf(res);
    }
}
