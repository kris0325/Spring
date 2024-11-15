package com.google.springboot.service.lock;

/**
 * @Author kris
 * @Create 2024-06-13 21:54
 * @Description deadlock
 *
 * 在accountA.transfer(accountB, 100);
 * 这行代码中，transfer方法首先通过synchronized(this)获取了accountA的锁（因为在这个上下文中，this就是accountA）。
 * 然后，当transfer方法试图更新accountB的余额时（other.balance += amount;），它实际上是在尝试获取accountB的锁，
 * 因为修改一个对象的状态通常需要获取该对象的锁以确保线程安全。
 * 但是，请注意，由于Java的内置锁（即synchronized关键字使用的锁）是可重入的，
 * 所以如果accountB的锁已经被同一个线程持有，那么这个线程可以再次获取该锁而不会被阻塞。
 * 然而，在这个例子中，accountB的锁可能已经被另一个线程（即thread2）持有，因此thread1可能会被阻塞，等待thread2释放accountB的锁。
 * 这就是我们说"thread1尝试获取accountB的锁"的含义
 */
class Account {
    private int balance;

    public void transfer(Account other, int amount) throws InterruptedException {
        // 线程获取到this对象锁， 即 Lock account A 对象锁，
        synchronized (this) {
            if (balance >= amount) {
                balance -= amount;
                //线程尝试获取other 锁， 即Lock account A
                other.balance += amount;
                System.out.println("Transfer successful: " + this.getName() + " -> " + other.getName() + ", Amount: $" + amount);
            } else {
                System.out.println("Insufficient funds: " + this.getName() + ", Transfer amount: $" + amount);
            }
        }
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