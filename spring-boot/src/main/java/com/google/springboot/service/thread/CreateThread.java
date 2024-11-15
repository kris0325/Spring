package com.google.springboot.service.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Author kris
 * @Create 2024-10-17 12:03
 * @Description different  way to create thread
 */
public class CreateThread {
    private static final Logger logger = LoggerFactory.getLogger(CreateThread.class);

    //1.extends Thread
    static class MutilpleThreadExtendsThread extends Thread {
        @Override
        public void run() {
            // task logic
            try {
                logger.info("MutilpleThreadExtendsThread Creating thread {} :", Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //2.implement runnable interface
    static class MutilpleThreadImplementRunnable implements Runnable {
        @Override
        public void run() {
            // task logic
            try {
                Thread.sleep(1000);
                logger.info("MutilpleThreadImplementRunnable Creating thread {} :", Thread.currentThread().getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 3 implement callable interface
     */
    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws InterruptedException {
            System.out.println("Callable result.start");
            Thread.sleep(3000);
            System.out.println("Callable result.finished");
            return "Callable result.";
        }
    }

    public static void useMyCallable(String para) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new MyCallable());
        try {
            System.out.println("start");
            String reslut = future.get();
            System.out.println(reslut);
            System.out.println("finished");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            //关闭线程池
            executor.shutdown();
        }
    }


    /**
     * 4 Executors  thread pool
     * ExecutorService 是一个更高级的线程池接口，提供了更灵活的线程管理。
     */

    public static void executors2CreateThread() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //可以使用 Lambda 表达式来简化 Runnable 的实现。
        executor.submit(() ->{
            //异步任务runnable
            logger.info("Task 1 is running.");
        });

        //可以使用 Lambda 表达式来简化 callable 的实现。
       Future<String> future = executor.submit(() -> {
           //异步任务 Future 获取结果
            return "Task 2 is running." ;
        });
        executor.shutdown();
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) {
//            MutilpleThreadExtendsThread thread1 = new MutilpleThreadExtendsThread();
//            Thread thread2 = new Thread(new MutilpleThreadImplementRunnable());
//            thread1.start();
//            thread2.start();
//        }

//        useMyCallable("test");
        executors2CreateThread();
    }



}
