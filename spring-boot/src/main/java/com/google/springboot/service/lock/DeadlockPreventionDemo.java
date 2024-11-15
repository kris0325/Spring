package com.google.springboot.service.lock;

/**
 * @Author kris
 * @Create 2024-06-13 20:06
 * @Description
 */
public class DeadlockPreventionDemo {

    //死锁问题
//    public static void main(String[] args) {
//        Account accountA = new Account();
//        accountA.balance = 1000;
//        Account accountB = new Account();
//        accountB.balance = 500;
//
//        Thread thread1 = new Thread(() -> {
//            try {
//                accountA.transfer(accountB, 100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread thread2 = new Thread(() -> {
//            try {
//                accountB.transfer(accountA, 200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//    }

    //保证获取锁的顺序，解决死锁问题
    public static void main(String[] args) {
        AccountLock accountA = new AccountLock();
        accountA.setBalance(1000);
        accountA.accountName = "accountA";
        AccountLock accountB = new AccountLock();
        accountB.accountName = "accountB";
        accountB.setBalance(500);

        Thread thread1 = new Thread(() -> {
            try {
                accountA.transfer(accountB, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                accountB.transfer(accountA, 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}
