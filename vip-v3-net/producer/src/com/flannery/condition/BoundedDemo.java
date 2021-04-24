package com.flannery.condition;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoundedDemo {

    public static void main(String[] args) {
        BoundedQueue demo = new BoundedQueue(new ArrayList<String>(),10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    demo.put("hello");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
