package com.google.springboot.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;

/**
 * @author: yangwenkang
 * @date: 2022/07/02
 * @description:
 **/
@Service
public class KafkaProducerServiceImpl implements  KafkaProducerService{

    public static final String  TP = "quickstart-events";

    @Override
    public void sendMsg(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 2; i++)
            producer.send(new ProducerRecord<String, String>(
                    TP
                    , UUID.randomUUID().toString().toLowerCase().replace("-","")
                    , "kafka"+Integer.toString(i)));

        producer.close();
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 2; i++)
            producer.send(new ProducerRecord<String, String>(
                    TP
                    , UUID.randomUUID().toString().toLowerCase().replace("-","")
                    , "kafka"+Integer.toString(i)));

        producer.close();
    }
}
