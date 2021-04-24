package com.collections;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ListDemo {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Semaphore semaphore = new Semaphore(3);
    }

}