package com.producer;

import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A1WaitNotify {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new Producer(list, 8));
        }
        for (int i = 0; i < 10; i++) {
            service.submit(new Consumer(list));
        }
    }

    static class Producer implements Runnable {
        final LinkedList<String> list;
        final int MAX;

        public Producer(LinkedList<String> list, int MAX) {
            this.list = list;
            this.MAX = MAX;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (list) { //必须再同步代码块里面
                        while (list.size() == MAX) {
                            list.wait();//等待
                        }
                        list.add("new product");
                        list.notifyAll();//通知
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class Consumer implements Runnable {
        LinkedList<String> list;

        public Consumer(LinkedList<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                while (true) { //不退出循环
                    synchronized (list) {
                        while (list.isEmpty()) {
                            list.wait();
                        }
                        String remove = list.remove(0);//
                        list.notifyAll();
                    }
                }
            } catch (Exception e) {

            }
        }
    }

}
