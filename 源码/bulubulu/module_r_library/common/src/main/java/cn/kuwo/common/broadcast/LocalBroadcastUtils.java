package cn.kuwo.common.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import cn.kuwo.common.app.App;

public class LocalBroadcastUtils {
    public static void register(BroadcastReceiver mBroadcastReceiver, IntentFilter intentFilter) {
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public static void register(BroadcastReceiver mBroadcastReceiver, String... action) {
        IntentFilter intentFilter = new IntentFilter();
        for (String a : action) {
            intentFilter.addAction(a);
        }
        register(mBroadcastReceiver, intentFilter);
    }

    public static void unRegister(BroadcastReceiver mBroadcastReceiver) {
        try {
            LocalBroadcastManager.getInstance(App.getInstance()).unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBroadcast(String action) {
        sendBroadcast(new Intent(action));
    }

    public static void sendBroadcast(String... actions) {
        for (String action : actions) {
            sendBroadcast(new Intent(action));
        }
    }

    public static void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(App.getInstance()).sendBroadcast(intent);
    }
}
