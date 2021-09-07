package com.joyy.dispatcher.utils;

import android.util.Log;

/**
 * Time:2021/9/7 13:44
 * Author: flannery
 * Description:
 */

public class DLog {

    private static boolean sDebug = true;

    public static void i(String msg, Runnable run) {
        i(msg + " start");
        long start = System.currentTimeMillis();
        run.run();
        i(msg + " end time=" + (System.currentTimeMillis() - start));
    }

    public static void i(String msg) {
        if (!sDebug) {
            return;
        }
        Log.i("[TaskDispatcher]", msg);
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

}
