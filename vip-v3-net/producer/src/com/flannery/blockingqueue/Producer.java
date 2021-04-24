package com.flannery.blockingqueue;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 */
public class Producer implements Runnable{

    private final Random random = new Random();
    private volatile boolean isRunning = true;
    private final BlockingQueue<String> queue; //阻塞队列
    private final String name;
    private AtomicInteger count = new AtomicInteger();

    public Producer(BlockingQueue<String> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("启动生产者线程!");
        String data = null;
        try {
            while (isRunning) {
                Thread.sleep(1000);
                data = name+"data:" + count.incrementAndGet();
                queue.put(data);
                   System.out.println(name+"  -->  数据记载成功-->"+data + "  队列大小"+queue.size());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
