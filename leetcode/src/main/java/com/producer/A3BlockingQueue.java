package com.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class A3BlockingQueue {

    public static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new Producer());
        }
        for (int i = 0; i < 10; i++) {
            service.submit(new Consumer());
        }
    }

    static class Producer implements Runnable {

        @Override
        public void run() {
            while (true) { //停止退出
                queue.offer("new Product");
            }
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    String take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
