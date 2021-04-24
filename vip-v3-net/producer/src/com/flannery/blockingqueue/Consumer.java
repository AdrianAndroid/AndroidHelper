package com.flannery.blockingqueue;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private final BlockingQueue<String> queue;
    private volatile boolean isRunning= true;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("启动消费者线程!");
        try {
            while (isRunning) {
                final String data = queue.take();
                System.out.println("拿到数据:" + data + "  队列大小"+queue.size());
                Thread.sleep(3000);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
