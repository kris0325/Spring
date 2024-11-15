package com.google.springboot.service.amazonSQS;

import com.google.gson.Gson;
import com.google.springboot.config.AmazonSqsClient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author kris
 * @Create 2024-10-16 19:05
 * @Description
 */
@Service
public class AmazonSqsServiceImpl implements AmazonSqsService {

    private SqsClient sqsClient;

    public AmazonSqsServiceImpl(AmazonSqsClient sqsClient) {
        this.sqsClient = sqsClient.getSqsClient();
    }

    @Override
    public String createQueue(CreateQueueRequest createQueueRequest) {
        try {
            // 创建队列
//            String queueName = "test-queue";
//            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
//                    .queueName(queueName)
//                    .build();

            CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
            String queueUrl = createQueueResponse.queueUrl();
            System.out.println("Queue created: " + queueUrl);
            return queueUrl;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception("");
        }
        return "createQueue fail";
    }

    @Override
    public String sendMessage(SendMessageRequest sendMsgRequest) {
        try {
            // 创建队列
//            String queueName = "test-queue";
//            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
//                    .queueName(queueName)
//                    .build();

            SendMessageResponse createQueueResponse = sqsClient.sendMessage(sendMsgRequest);
            System.out.println("Queue sendMsgRequest: " + sendMsgRequest);
            return createQueueResponse.messageId();
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception("");
        }
        return "createQueue fail";

    }


    @Override
    public String receiveMessage(ReceiveMessageRequest receiveMessageRequest) {
        List<String> messages = new ArrayList<>();
        try {
            // 发送消息
//            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
//                    .queueUrl(queueUrl)
//                    .maxNumberOfMessages(1)
//                    .build();
            sqsClient.receiveMessage(receiveMessageRequest).messages().forEach(msg -> {
                System.out.println("Message received: " + msg.body());
                messages.add(msg.body());
            });
            // 创建Gson对象
            Gson gson = new Gson();
            return gson.toJson(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "receiveMessage fail";
    }
}
