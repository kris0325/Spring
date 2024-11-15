package com.google.springboot.service.thread;

import java.util.concurrent.BlockingQueue;

/**
 * @Author kris
 * @Create 2024-06-15 15:30
 * @Description
 */
public class Consumer {

    private final BlockingQueue<Integer> buffer;

    public Consumer(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
    }

    public void consume() {
        while (true) {
            try {
                Integer item = buffer.take();
                System.out.println("消费者消费了: " + item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

