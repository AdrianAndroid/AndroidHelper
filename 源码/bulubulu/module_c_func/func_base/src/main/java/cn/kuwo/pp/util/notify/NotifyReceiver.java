package cn.kuwo.pp.util.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotifyReceiver extends BroadcastReceiver {
    //下面是定义的intent点击响应action值，通知栏界面按钮点击后发出
    public static final String GO_LAUNCHER_APP = "cn.kuwo.pop.launch_app";  //这里定义字符串需要和对应app的启动页自定义intent-filter对应

    //============================================================================
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
    }
}
