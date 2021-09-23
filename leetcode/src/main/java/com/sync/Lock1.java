package com.sync;

// 不可重入锁
public class Lock1 {
    private boolean isLocked = false;
    public synchronized void lock() throws InterruptedException{
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }

    private Lock1 lock = new Lock1();
    public void print() throws InterruptedException {
        lock.lock();
        doAdd();
        lock.unlock();
    }
    public void doAdd() throws InterruptedException {
        lock.lock();
        //do something
        lock.unlock();
    }

}
