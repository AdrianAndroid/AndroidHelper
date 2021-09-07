package com.joyy.taskdispatcher.tasks;

import com.joyy.dispatcher.Task;
import com.joyy.dispatcher.utils.DLog;
import com.joyy.taskdispatcher.utils.LaunchTimer;

/**
 * Time:2021/9/7 14:15
 * Author: flannery
 * Description:
 */
public class InitUmengTask extends Task {
    @Override
    public void run() {
        DLog.i("InitUmengTask", new Runnable() {
            @Override
            public void run() {
                LaunchTimer.runUmeng();
            }
        });
    }
}
