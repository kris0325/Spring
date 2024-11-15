package com.google.springboot.service.amazonSQS;

import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * @Author kris
 * @Create 2024-10-16 19:05
 * @Description
 */
public interface AmazonSqsService {
    public String createQueue(CreateQueueRequest createQueueRequest);

    public String sendMessage(SendMessageRequest sendMsgRequest);

    public String receiveMessage (ReceiveMessageRequest receiveMessageRequest);

}
