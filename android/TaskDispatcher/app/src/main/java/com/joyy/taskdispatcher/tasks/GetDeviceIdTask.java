package com.joyy.taskdispatcher.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.joyy.dispatcher.Task;
import com.joyy.dispatcher.utils.DLog;
import com.joyy.taskdispatcher.utils.LaunchTimer;

/**
 * Time:2021/9/7 14:13
 * Author: flannery
 * Description:
 */
public class GetDeviceIdTask extends Task {
    private String mDeviceId;

    @Override
    public boolean needWait() {
        return super.needWait();
    }

    @Override
    public void run() {
        // 真正自己的代码
        DLog.i("GetDeviceIdTask", new Runnable() {
            @SuppressLint("HardwareIds")
            @Override
            public void run() {
                mDeviceId = LaunchTimer.runGetDevices(mContext);
            }
        });

    }
}
