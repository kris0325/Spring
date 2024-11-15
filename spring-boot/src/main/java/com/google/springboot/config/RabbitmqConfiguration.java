package com.google.springboot.config;

import com.google.springboot.service.rabbitmq.TaskReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

/**
 * @Author kris
 * @Create 2024-11-11 23:14
 * @Description
 */
@Configuration
public class RabbitmqConfiguration {
    public static final String RHEA_TASK_QUEUE = "rhea_task_queue";

    public static final String RHEA_TASK_QUEUE_TTL = "rhea_task_queue_ttl";

    public static final String RHEA_TASK_DELAY_EXCHANGE = "rhea_task_delay_exchange_name";

    public static final String RHEA_NOTICE_QUEUE = "rhea_notice_queue";

    public static final String RHEA_TASK_NRT_QUEUE = "rhea_task_nrt_queue";

    public static final String RHEA_COLLECT_QUEUE = "rhea_collect_queue";

    //    @Resource(name = "rabbitMQListenExecutor")
    @Autowired
    private Executor executor;


    //    @Resource(name = "nonRealTimeTaskExecutor")
    @Autowired
    private Executor nonRealTimeTaskExecutor;


    //    @Resource(name = "collectTaskExecutor")
    @Autowired
    private Executor collectTaskExecutor;

    @Bean
    Queue taskQueue() {
        return QueueBuilder.durable(RHEA_TASK_QUEUE)
                .build();
    }

    /**
     * x-dead-letter-exchange DLX，dead letter发送到的exchange
     * x-dead-letter-routing-key dead letter携带的routing key
     *
     * @return
     */
    @Bean
    Queue taskDelayQueue() {
        return QueueBuilder.durable(RHEA_TASK_QUEUE_TTL)
                .withArgument("x-dead-letter-exchange", RHEA_TASK_DELAY_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", RHEA_TASK_QUEUE)
                .build();
    }

    /**
     * 创建延迟任务交换机
     *
     * @return
     */
    @Bean
    DirectExchange rheaTaskQueueExchange() {
        return new DirectExchange(RHEA_TASK_DELAY_EXCHANGE);
    }

    @Bean
    Binding rheaTaskQueueExchangeBinding(Queue taskQueue, DirectExchange exchange) {
        return BindingBuilder.bind(taskQueue)
                .to(exchange)
                .with(RHEA_TASK_QUEUE);
    }

    @Bean
    @ConditionalOnProperty(name = "rhea.rabbitmq.listen.close", matchIfMissing = true, havingValue = "false")
    SimpleMessageListenerContainer taskListenerContainer(ConnectionFactory connectionFactory, TaskReceiver taskReceiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setMaxConcurrentConsumers(10);
        container.setConcurrentConsumers(10);
        container.setTaskExecutor(executor);
        container.setQueueNames(RHEA_TASK_QUEUE);
        container.setPrefetchCount(3);
        container.setMessageListener(taskReceiver);
        return container;
    }

    @Bean
    Queue noticeQueue() {
        return QueueBuilder.durable(RHEA_NOTICE_QUEUE)
                .build();
    }

    @Bean
    Queue rheaTaskBatchQueue() {
        return QueueBuilder.durable(RHEA_TASK_NRT_QUEUE)
                .build();
    }

    @Bean
    Queue rheaCollectQueue() {
        return QueueBuilder.durable(RHEA_COLLECT_QUEUE)
                .build();
    }

//    @Bean(name = "noticeListenerContainer")
//    SimpleMessageListenerContainer noticeListenerContainer(ConnectionFactory connectionFactory, NoticeReceiver noticeReceiver){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        //container.setQueueNames(RHEA_NOTICE_QUEUE);
//        container.setMessageListener(noticeReceiver);
//        return container;
//    }

//    @Bean(name = "nonRealTimeTaskListenerContainer")
//    @ConditionalOnProperty(name = "rhea.rabbitmq.listen.close", matchIfMissing = true, havingValue = "false")
//    SimpleMessageListenerContainer nonRealTimeTaskListenerContainer(ConnectionFactory connectionFactory, NoRealTimeTaskReceiver receiver){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setMaxConcurrentConsumers(6);
//        container.setConcurrentConsumers(6);
//        container.setQueueNames(RHEA_TASK_NRT_QUEUE);
//        container.setTaskExecutor(nonRealTimeTaskExecutor);
//        container.setMessageListener(receiver);
//        return container;
//    }

//    @Bean(name = "collectTaskListenerContainer")
//    @ConditionalOnProperty(name = "rhea.rabbitmq.listen.close", matchIfMissing = true, havingValue = "false")
//    SimpleMessageListenerContainer collectTaskListenerContainer(ConnectionFactory connectionFactory, CollectReceiver receiver){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setMaxConcurrentConsumers(3);
//        container.setConcurrentConsumers(3);
//        container.setQueueNames(RHEA_COLLECT_QUEUE);
//        container.setTaskExecutor(collectTaskExecutor);
//        container.setPrefetchCount(1);
//        container.setMessageListener(receiver);
//        return container;
//    }
}
