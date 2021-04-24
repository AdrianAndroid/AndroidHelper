package com.producer;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class A2Lock {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new Productor(list, 8));
        }
        for (int i = 0; i < 10; i++) {
            service.submit(new Consumer(list));
        }
    }


    static class Productor implements Runnable {
        final LinkedList<String> list;
        final int MAX;

        public Productor(LinkedList<String> list, int MAX) {
            this.list = list;
            this.MAX = MAX;
        }

        @Override
        public void run() {
            while (true) { //不退出
                lock.lock();
                try {
                    while (list.size() == MAX) {
                        full.await();
                    }
                    //生产
                    list.add("new Product");
                    empty.signalAll(); //通知
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private final LinkedList<String> list;

        public Consumer(LinkedList<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (list.isEmpty()) {
                        empty.await();
                    }
                    String remove = list.remove(0);
                    full.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
