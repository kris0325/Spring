package com.google.springboot.service.debezium2kafkacdc;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kris
 * @Create 2024-11-13 17:54
 * @Description
 */


@Component
public class DebeziumConnectorConfig {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DebeziumConnectorConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void configureConnector() {
        Map<String, String> config = new HashMap<>();
        config.put("name", "postgresql-connector");
        config.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        config.put("database.hostname", "localhost");
        config.put("database.port", "5432");
        config.put("database.user", "kris");
        config.put("database.password", "");
        config.put("database.dbname", "ecommerce");
        config.put("database.server.name", "postgres_prod");
        config.put("plugin.name", "pgoutput");
        config.put("slot.name", "debezium_slot");
        config.put("database.history.kafka.bootstrap.servers", "localhost:9092");
        config.put("database.history.kafka.topic", "db.history.topic");
        // 将所有表的变更推送到同一个 Kafka 主题
        config.put("topic.naming.strategy", "io.debezium.schema.kafka.KafkaTopicNamingStrategy$Constant");
        config.put("topic.prefix", "all_changes_topic");


        // 通过 Kafka Template 发送连接器配置到 Kafka Connect 端点
        kafkaTemplate.send("connect-configs", "debezium-config", config.toString());
    }
}
