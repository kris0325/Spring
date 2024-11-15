package com.google.springboot.service.rabbitmq;

import com.google.springboot.config.RabbitmqConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @Author kris
 * @Create 2024-11-11 23:26
 * @Description
 */

@Component
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TaskProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTask(String task) {
        rabbitTemplate.convertAndSend(RabbitmqConfiguration.RHEA_TASK_QUEUE, task);
        System.out.println("Sent task: " + task);
    }
}