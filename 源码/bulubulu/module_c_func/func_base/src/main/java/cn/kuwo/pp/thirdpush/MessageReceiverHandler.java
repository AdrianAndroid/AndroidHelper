package cn.kuwo.pp.thirdpush;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.qcloud.uikit.common.BackgroundTasks;

import java.util.List;

import cn.kuwo.pp.ui.main.MainActivity;
import cn.kuwo.pp.ui.main.SplashActivity;

import static cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY;

public class MessageReceiverHandler {
    public static void handlerMessage(Context context, String intentExtra){
        if(isMainActivityAlive(context, "cn.kuwo.pp.ui.main.MainActivity")){
            BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(new ComponentName(context.getApplicationContext(), MainActivity.class));
                    if(intentExtra != null){
                        intent.putExtra(OFFLINE_PUSH_KEY, intentExtra);
                    }

                    context.startActivity(intent);
                }
            });
        }else{
            Intent intent = new Intent(context, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName(context.getApplicationContext(), SplashActivity.class));
            if(intentExtra != null){
                intent.putExtra(OFFLINE_PUSH_KEY, intentExtra);
            }

            context.startActivity(intent);
        }
    }

    private static boolean isMainActivityAlive(Context context, String activityName){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            Log.d("push activity", info.topActivity.toString());
            Log.d("push activity", info.baseActivity.toString());
            if (info.topActivity.toString().contains(activityName) || info.baseActivity.toString().contains(activityName)) {
                return true;
            }
        }
        return false;
    }
}
