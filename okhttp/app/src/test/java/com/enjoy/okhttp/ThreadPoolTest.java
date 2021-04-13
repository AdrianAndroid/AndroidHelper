package com.enjoy.okhttp;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    public void testQueue() {
        // 没有容量的容器
        SynchronousQueue<Runnable> queue = new SynchronousQueue<>();
//        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
//        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60, TimeUnit.SECONDS, queue);

        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1：" + Thread.currentThread());
                while (true) {
                }
            }
        });

        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2：" + Thread.currentThread());
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3：" + Thread.currentThread());
            }
        });





        while (true) {

        }
    }







}
