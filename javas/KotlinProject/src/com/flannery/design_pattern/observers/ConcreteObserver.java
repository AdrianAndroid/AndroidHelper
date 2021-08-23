package com.flannery.design_pattern.observers;

public class ConcreteObserver implements Observer {

    private String name;
    private int edition;
    private float cost;

    public ConcreteObserver(float cost) {
        this.cost = cost;
    }

    @Override
    public void update(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        buy();
    }

    private void buy() {
        System.out.println(name+"购买了第"+edition+"期的杂志，花费了"+cost+"元。");
    }
}
