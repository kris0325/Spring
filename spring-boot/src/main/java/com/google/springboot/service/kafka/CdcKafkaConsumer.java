package com.google.springboot.service.kafka;

/**
 * @Author kris
 * @Create 2024-11-13 17:58
 * @Description 实现基于 Debezium + Kafka Connect 的 PostgreSQL CDC 方案并将数据流式传输到 Kafka，
 * 可以按照以下步骤来直接在 Spring Boot 中 消費Kafka中的消息
 * https://docs.google.com/document/d/1O_cv366JEMcvzC2M_JuctE_Am01LjmgiWITBoBIjPns/edit?tab=t.0
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CdcKafkaConsumer {

    @KafkaListener(topicPattern = "cdc_kafka_\\.public\\..*", groupId = "cdc-consumer-group")
    public void consume(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        String message = record.value();

        // 根据主题名称做不同的处理
        if (topic.contains("employees")) {
            System.out.println("Handling employees table change: " + message);
            // 处理 employees 表的数据
        } else if (topic.contains("shipments")) {
            System.out.println("Handling shipments table change: " + message);
            // 处理 orders 表的数据
        } else {
            System.out.println("Handling other table change: " + message);
        }
    }
}
