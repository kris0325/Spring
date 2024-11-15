package com.google.springboot.service.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author kris
 * @Create 2024-06-15 15:31
 * @Description
 */
public class ProducerConsumer {

    private static final int BUFFER_SIZE = 10;

    public static void main(String[] args) {
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        Thread producerThread = new Thread(producer::produce);
        Thread consumerThread = new Thread(consumer::consume);

        producerThread.start();
        consumerThread.start();
    }
}

