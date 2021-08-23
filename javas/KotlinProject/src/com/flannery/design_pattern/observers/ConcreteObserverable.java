package com.flannery.design_pattern.observers;

import java.util.ArrayList;
import java.util.List;

public class ConcreteObserverable implements Observerable {


    private List<Observer> mObservers;
    private int edition;
    private float cost;

    public ConcreteObserverable() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        mObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = mObservers.indexOf(o);
        if (i >= 0) {
            mObservers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer mObserver : mObservers) {
            mObserver.update(edition, cost);
        }
    }

    public void setInformation(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        // 信息更新完毕, 通缩所有观察着
        notifyObservers();
    }
}
