package com.google.springboot.service.lock;


/**
 * @Author kris
 * @Create 2024-06-13 20:02
 * @Description  保证锁顺序，以解决deadlock
 *
 *
 *
 */

//            解决方案：
//
//            为了防止死锁，可以采用以下策略：
//
//            1.锁顺序: 确保所有线程以相同的顺序获取锁。例如，始终先获取accountA的锁，然后再获取accountB的锁。
//
//            2.避免嵌套锁: 尽量避免在锁的临界区内获取其他锁。
//
//            3.锁超时: 为锁获取设置超时时间，如果无法在指定时间内获得锁，则线程应释放任何持有的锁并稍后再试。
//
//            4.死锁检测和恢复: 实现死锁检测机制，识别并解除死锁情况。这可能涉及释放锁、终止线程或重构锁顺序。

public class AccountLock {
    private int balance;


    public String accountName;

    public void transfer(AccountLock other, int amount) throws InterruptedException {
        Object firstLock, secondLock;

        if (this.getName().compareTo(other.getName()) < 0) {
            firstLock = this;
            secondLock = other;
        } else {
            firstLock = other;
            secondLock = this;
        }


        synchronized (firstLock) {

            synchronized (secondLock) {
                if (balance >= amount) {
                    balance -= amount;
                    other.balance += amount;
                    System.out.println("Transfer successful threadName: " + this.getName() + this.accountName + " -> " + other.accountName + ", Amount: $" + amount
                            + ", accountName: $" + other.accountName
                            + ", accountBalance: $" + other.getBalance());
                } else {
                    System.out.println("Insufficient funds: " + this.accountName + ", Transfer amount: $" + amount);
                }
            }

        }
    }

    public String getAccountName() {
        return accountName;
    }
    public String getName() {
        return Thread.currentThread().getName();
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}