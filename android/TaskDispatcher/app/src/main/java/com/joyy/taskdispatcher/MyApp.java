package com.joyy.taskdispatcher;

import android.app.Application;
import android.os.Debug;

import com.joyy.dispatcher.DelayInitDispatcher;
import com.joyy.dispatcher.TaskDispatcher;
import com.joyy.taskdispatcher.tasks.GetDeviceIdTask;
import com.joyy.taskdispatcher.tasks.InitStethoTask;
import com.joyy.taskdispatcher.tasks.InitUmengTask;
import com.joyy.taskdispatcher.tasks.delay.DelayInitTaskA;
import com.joyy.taskdispatcher.tasks.delay.DelayInitTaskB;
import com.joyy.taskdispatcher.utils.LaunchTimer;

/**
 * Time:2021/9/7 14:21
 * Author: flannery
 * Description:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Debug.startMethodTracing("TaskDispatcher");
        LaunchTimer.startRecord();

        //test1();
        testDispatcher();

        LaunchTimer.endRecord();
        Debug.stopMethodTracing();

    }

    private void test1() {
        LaunchTimer.runDelayA();
        LaunchTimer.runDelayB();
        LaunchTimer.runGetDevices(MyApp.this); //不能在主线程
        LaunchTimer.runStetho(this);
        LaunchTimer.runUmeng();
    }

    private void testDispatcher() {
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new InitStethoTask())
                .addTask(new InitUmengTask())
                .addTask(new GetDeviceIdTask())
                .start();
        dispatcher.await();

        DelayInitDispatcher delayInitDispatcher = new DelayInitDispatcher();
        delayInitDispatcher.addTask(new DelayInitTaskA())
                .addTask(new DelayInitTaskB())
                .start();
    }
}
