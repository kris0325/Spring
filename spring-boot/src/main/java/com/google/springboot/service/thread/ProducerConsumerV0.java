package com.google.springboot.service.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author kris
 * @Create 2024-06-15 15:59
 * @Description
 */
public class ProducerConsumerV0 {

    private static final int BUFFER_SIZE = 10; // 缓冲区大小

    private static BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>(BUFFER_SIZE); // 缓冲区

    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        // 创建生产者线程
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    ProducerConsumerV0.produce(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 创建消费者线程
        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    ProducerConsumerV0.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();
    }

    private static void produce(int item) throws InterruptedException {
        // 生产者不断尝试将物品放入缓冲区
        while (true) {
            if (buffer.offer(item)) {
                System.out.println("生产者生产了: " + item);
                break;
            } else {
                // 缓冲区满，等待
                System.out.println("缓冲区已满，生产者等待...");
                Thread.sleep(1000);
            }
        }
    }

    private static void consume() throws InterruptedException {
        // 消费者不断尝试从缓冲区中取出物品
        Integer item = buffer.take();
        System.out.println("消费者消费了: " + item);
    }
}

