package com.google.springboot.config;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

/**
 * @Author kris
 * @Create 2024-10-16 18:44
 * @Description
 */
@Configuration
public class AmazonSqsClient {
    private final SqsClient sqsClient;
    // 创建AWS凭证（虚拟）
    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
            "fakeAccessKey",
            "fakeSecretAccessKey");

    public AmazonSqsClient() {
        // 创建SQS客户端，设置连接到LocalStack
        sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create("http://localhost:4566"))  // LocalStack的SQS端口
                .build();
    }

    public SqsClient getSqsClient() {
        return sqsClient;
    }


}
