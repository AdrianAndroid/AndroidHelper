package com.joyy.dispatcher;

/**
 * Time:2021/9/7 13:46
 * Author: flannery
 * Description:
 */
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }

}