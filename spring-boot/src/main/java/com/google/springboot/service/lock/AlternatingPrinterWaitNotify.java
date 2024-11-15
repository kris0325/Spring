package com.google.springboot.service.lock;

/**
 * @Author kris
 * @Create 2024-06-15 14:25
 * @Description lock + synchronized + Wait/Notify 实现n个线程，交替打印
 */
public class AlternatingPrinterWaitNotify {

    private int num;
    private static final Object LOCK = new Object();
    private int maxnum = 10;

    private void printABC(int targetNum) {
        while (true) {
            synchronized (LOCK) {
                while (num % 3 != targetNum) { //想想这里为什么不能用if代替，想不起来可以看公众号上一篇文章
                    if (num >= maxnum) {
                        break;
                    }
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (num >= maxnum) {
                    break;
                }
                num++;
                System.out.println(Thread.currentThread().getName() + ": " + num);
                LOCK.notifyAll();
            }
        }

    }

    public static void main(String[] args) {
        AlternatingPrinterWaitNotify wait_notify_100 = new AlternatingPrinterWaitNotify();
        new Thread(() -> {
            wait_notify_100.printABC(0);
        }, "thread1").start();
        new Thread(() -> {
            wait_notify_100.printABC(1);
        }, "thread2").start();
        new Thread(() -> {
            wait_notify_100.printABC(2);
        }, "thread3").start();
    }
}
