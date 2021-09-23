package com.sync;

public class Sync1 {

    static synchronized void test0() {
        System.out.println("test0");
    }
    synchronized void test1() {
        System.out.println("test1");
    }

    synchronized void test2() {
        System.out.println("test2");
    }

    void test11() {
        synchronized (this) {
            System.out.println("test11");
        }
    }

    public static void main(String[] args) {
        // 类锁
//        synchronized (Sync1.class){
//            System.out.println("one");
//            synchronized (Sync1.class){
//                System.out.println("two");
//            }
//        }

//        // 对象
        final Sync1 sync1 = new Sync1();
//        synchronized (sync1){
//            System.out.println("one");
//            synchronized (sync1){
//                System.out.println("two");
//            }
//        }

        //
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    sync1.test1();
//                }
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    sync1.test2();
//                }
//            }
//        }).start();
    }


}
