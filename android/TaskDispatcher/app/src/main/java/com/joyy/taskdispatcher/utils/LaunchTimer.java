package com.joyy.taskdispatcher.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.joyy.dispatcher.utils.DLog;

/**
 * Time:2021/9/7 14:05
 * Author: flannery
 * Description:
 */
public class LaunchTimer {

    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord() {
        endRecord("");
    }

    private static void endRecord(String s) {
        long cost = System.currentTimeMillis() - sTime;
        DLog.i("[LaunchTimer] " + "cost -> " + cost);
    }


    public static void runDelayA() {
        // 模拟一些操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runDelayB() {
        // 模拟一些操作

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String runGetDevices(Context mContext) {
        // 真正自己的代码
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "mDeviceId";
    }

    public static void runStetho(Context mContext) {
        Handler handler = new Handler(Looper.getMainLooper());
        Stetho.initializeWithDefaults(mContext);
    }

    public static void runUmeng() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
