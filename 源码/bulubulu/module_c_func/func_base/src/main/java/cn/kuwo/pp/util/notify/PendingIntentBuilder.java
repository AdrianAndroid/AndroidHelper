package cn.kuwo.pp.util.notify;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import cn.kuwo.pp.ui.main.MainActivity;

import static cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY;


public class PendingIntentBuilder {
    //下面定义创建
    public static final int PENDING_INTENT_GO_MAIN = 0x020105;  //点击整个通知栏区域打开app，不需要对应action定义
    public static int mPendingIntentId = 100001;

    public static PendingIntent buildIntent(int notifyType, Context context, String extra) {
        Intent intent = null;
        switch (notifyType) {
            case PENDING_INTENT_GO_MAIN:
                intent = new Intent(NotifyReceiver.GO_LAUNCHER_APP);
                intent.setClass(context, MainActivity.class);
                intent.putExtra(OFFLINE_PUSH_KEY, extra);
                return PendingIntent.getActivity(context, mPendingIntentId++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return null;
    }
}
