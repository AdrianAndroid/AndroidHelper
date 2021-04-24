package com.flannery.blockingqueue;

import java.util.concurrent.*;

public class BlockingQueueQueue {


    public static void main(String[] args) {
        //blockingQueueDemo(new LinkedBlockingQueue<String>());
        blockingQueueDemo(new ArrayBlockingQueue<String>(10));
        //blockingQueueDemo(new LinkedBlockingQueue<String>(10));
    }

    private static void blockingQueueDemo(BlockingQueue<String> queue) {
        // 声明一个容量为10的缓存队列
        //BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        Producer p1 = new Producer(queue, "producer1");
        Producer p2 = new Producer(queue, "producer2");
        Producer p3 = new Producer(queue, "producer3");
        Consumer c1 = new Consumer(queue);

        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(p3).start();
        new Thread(c1).start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
