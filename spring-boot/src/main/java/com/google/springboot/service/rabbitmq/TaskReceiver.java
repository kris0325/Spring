package com.google.springboot.service.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author kris
 * @Create 2024-11-11 23:20
 * @Description
 */
@Component
public class TaskReceiver implements MessageListener {
    Logger log = LoggerFactory.getLogger(TaskReceiver.class);

//    @Autowired
//    private Excutor excutor;

    @Override
    public void onMessage(Message message) {
        runTask(new String(message.getBody()));
    }


    private void runTask(String json){
//        ScriptExtend scriptExtend = JSONObject.parseObject(json, ScriptExtend.class);
        log.info("mq thread {} get task message json {}", Thread.currentThread().getName(), json);

        /**
         * executeAndNotice logic
         */
        //        excutor.executeAndNotice(scriptExtend);
    }
}