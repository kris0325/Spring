package com.google.springboot.service.lock;

/**
 * @Author kris
 * @Create 2024-06-14 23:05
 * @Description
 */
import java.util.concurrent.atomic.AtomicInteger;

public class AlternatingPrinterAtomic {
    private final AtomicInteger counter = new AtomicInteger(1);

    public static void main(String[] args) {
        AlternatingPrinterAtomic printer = new AlternatingPrinterAtomic();
        Thread t1 = new Thread(printer.new PrintOdd());
        Thread t2 = new Thread(printer.new PrintEven());
        t1.start();
        t2.start();
    }

    class PrintOdd implements Runnable {
        @Override
        public void run() {
            while (counter.get() <= 100) {
                if (counter.get() % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + " - " + counter.getAndIncrement());
                }
            }
        }
    }

    class PrintEven implements Runnable {
        @Override
        public void run() {
            while (counter.get() <= 100) {
                if (counter.get() % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + " - " + counter.getAndIncrement());
                }
            }
        }
    }
}

