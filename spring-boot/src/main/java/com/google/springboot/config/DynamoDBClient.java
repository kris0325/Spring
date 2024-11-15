package com.google.springboot.config;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
/**
 * @Author kris
 * @Create 2024-10-16 15:32
 * @Description
 */
@Configuration
public class DynamoDBClient {
    private final DynamoDbClient dynamoDbClient;

    public DynamoDBClient() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials
                                .create("fakeMyKeyId", "fakeSecretAccessKey")))
                .build();
    }

    public DynamoDbClient getClient() {
        return dynamoDbClient;
    }
}