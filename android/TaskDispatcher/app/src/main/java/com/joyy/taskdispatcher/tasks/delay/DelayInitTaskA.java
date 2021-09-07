package com.joyy.taskdispatcher.tasks.delay;

import com.joyy.dispatcher.MainTask;
import com.joyy.dispatcher.utils.DLog;
import com.joyy.taskdispatcher.utils.LaunchTimer;

/**
 * Time:2021/9/7 14:07
 * Author: flannery
 * Description:
 */
public class DelayInitTaskA extends MainTask {
    @Override
    public void run() {
        DLog.i("DelayInitTaskA", new Runnable() {
            @Override
            public void run() {
                LaunchTimer.runDelayA();
            }
        });
    }
}
