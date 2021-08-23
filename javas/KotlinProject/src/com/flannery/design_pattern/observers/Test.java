package com.flannery.design_pattern.observers;

public class Test {
    public static void main(String[] args) {
        ConcreteObserverable observerable = new ConcreteObserverable(); //被观察着
        observerable.registerObserver(new ConcreteObserver(111));
        observerable.registerObserver(new ConcreteObserver(222));
        observerable.registerObserver(new ConcreteObserver(3333));
        observerable.registerObserver(new ConcreteObserver(444));
        observerable.registerObserver(new ConcreteObserver(555));


        observerable.setInformation(99, 88);
    }
}
