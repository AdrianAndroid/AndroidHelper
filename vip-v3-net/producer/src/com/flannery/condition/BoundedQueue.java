package com.flannery.condition;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {
    final List<String> list;
    final int maxSize;
    private final Lock lock = new ReentrantLock();
    private final Condition fullCondition = lock.newCondition();
    private final Condition notFullCondition = lock.newCondition();

    public BoundedQueue(List<String> list, int maxSize) {
        this.list = list;
        this.maxSize = maxSize;
    }

    public void put(String data) throws InterruptedException {
        lock.lock();
        try{
            while (maxSize == list.size()){
                notFullCondition.await();
            }
            list.add(data);
            fullCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    public String get() throws InterruptedException {
        String data = null;
        lock.lock();
        try{
            while (list.size() == 0){
                fullCondition.await();
            }
            data = list.get(list.size()-1); //获取最后一个
            notFullCondition.signal();//通知
        } finally {
            lock.unlock();
        }
        return data;
    }
}
