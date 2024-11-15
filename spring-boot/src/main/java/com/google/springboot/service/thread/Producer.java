package com.google.springboot.service.thread;

import java.util.concurrent.BlockingQueue;

/**
 * @Author kris
 * @Create 2024-06-15 15:29
 * @Description
 */
public class Producer {

    private final BlockingQueue<Integer> buffer;

    public Producer(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
    }

    public void produce() {
        for (int i = 0; i < 20; i++) {
            while (true) {
                if (buffer.offer(i)) {
                    System.out.println("生产者生产了: " + i);
                    break;
                } else {
                    // 缓冲区满，等待
                    System.out.println("缓冲区已满，生产者等待...");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

