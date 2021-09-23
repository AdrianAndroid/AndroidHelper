package com.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ListDemo {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Semaphore semaphore = new Semaphore(3);
        List<String> l = new ArrayList<>();
        l.add("");
        l.get(0);

        // 上界通配符
        Plate<? extends Fruit> p1 = new Plate<Apple>(new Apple());
        // 不能存入任何元素
        //p1.set(new Fruit()); // Error
        //p1.set(new Apple()); // Error
        //读取出来的东西只能存放Fruit或它的基类里
        Fruit newFruit1 = p1.get();
        Object newFruit2 = p1.get();
        //Apple newFruit3 = p1.get(); //Error


        // 下届通配符
        Plate<? super Fruit> p2 = new Plate<>(new Fruit());
        //存入元素正常
        p2.set(new Fruit());
        p2.set(new Apple());
        // 独取出来的只能存放在Object里面
        //Apple nf1 = p2.get(); // Error
        //Fruit nf2 = p2.get(); // Error
        Object nf3 = p2.get();
    }

    // lev1
    static class Food{}
    // lev2
    static class Fruit extends Food{ }
    static class Meat extends Food{ }
    // lve3
    static class Apple extends Fruit { }
    static class Banana extends Fruit { }
    static class Pork extends Meat { }
    static class Beef extends Meat { }

    static class Plate<T> {
        private T item;
        public Plate(T t) { item = t; }
        public void set(T t){item = t;}
        public T get(){return item;}
    }

    // 下届通配符



}