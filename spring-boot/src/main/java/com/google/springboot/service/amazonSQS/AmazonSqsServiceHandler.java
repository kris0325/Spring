package com.google.springboot.service.amazonSQS;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * @Author kris
 * @Create 2024-10-16 19:12
 * @Description
 */
@Service
public class AmazonSqsServiceHandler {
    private final Logger logger = LoggerFactory.getLogger(AmazonSqsServiceHandler.class);

    private static final String queueUrl = "http://localhost:4566/000000000000/test-queue";
    @Autowired
    private AmazonSqsService amazonSqsService;

    public String createQueue(String queueName) {
        String res = "";
        try {
            // 创建队列
//            String queueName = "test-queue";
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();

            res = amazonSqsService.createQueue(createQueueRequest);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;
    }

    public String sendMessage(String url, String messageBody) {
        String res = "";
        try {
            // 发送消息
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(StringUtils.isAllEmpty(url) ? queueUrl : url)
                    .messageBody(messageBody)
                    .build();
            res = amazonSqsService.sendMessage(sendMsgRequest);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;
    }


    public String receiveMessage(String url, Integer maxNumberOfMessages) {
        String res = "";
        try {
            // 接收消息
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(StringUtils.isAllEmpty(url) ? queueUrl : url)
                    .maxNumberOfMessages(maxNumberOfMessages != null? maxNumberOfMessages:1)
                    .build();
           return amazonSqsService.receiveMessage(receiveMessageRequest);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;
    }
}