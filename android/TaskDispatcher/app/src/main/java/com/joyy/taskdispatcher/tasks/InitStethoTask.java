package com.joyy.taskdispatcher.tasks;

import android.os.Handler;
import android.os.Looper;

import com.joyy.dispatcher.Task;
import com.joyy.dispatcher.utils.DLog;
import com.joyy.taskdispatcher.utils.LaunchTimer;

/**
 * Time:2021/9/7 14:14
 * Author: flannery
 * Description:
 */
public class InitStethoTask extends Task {
    @Override
    public void run() {
        DLog.i("InitStethoTask", new Runnable() {
            @Override
            public void run() {
                LaunchTimer.runStetho(mContext);
            }
        });
    }
}
