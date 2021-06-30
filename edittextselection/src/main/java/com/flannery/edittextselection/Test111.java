package com.flannery.edittextselection;

import java.util.ArrayList;
import java.util.List;

/**
 * Time:2021/6/30 18:57
 * Author:
 * Description:
 */
public class Test111 {

    class Animal {
    }

    class Dog extends Animal {
    }

    void test() {
        List<Animal> animals = new ArrayList<>();
        List<Dog> dogs = new ArrayList<>();
        // animals = dogs;  // error

        List<? extends Animal> animals1 = new ArrayList<>();
        List<Dog> dogs1 = new ArrayList<>();
        animals1 = dogs1;

    }

}
