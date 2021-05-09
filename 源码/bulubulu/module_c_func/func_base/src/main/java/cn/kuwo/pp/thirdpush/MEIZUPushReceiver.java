package cn.kuwo.pp.thirdpush;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import cn.kuwo.pp.manager.UserInfoManager;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * Created by vinsonswang on 2019/2/27.
 */

public class MEIZUPushReceiver extends MzPushMessageReceiver {
    private static final String TAG = "MEIZUPushReceiver";
    @Override
    public void onMessage(Context context, String s) {
        Log.i(TAG, "onMessage method1 msg = " + s);
    }

    @Override
    public void onMessage(Context context, String message, String platformExtra) {
        Log.i(TAG, "onMessage method2 msg = " + message + ", platformExtra = " + platformExtra);
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        String content = intent.getExtras().toString();
        Log.i(TAG, "flyme3 onMessage = " + content);
    }

    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        super.onUpdateNotificationBuilder(pushNotificationBuilder);
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        Log.i(TAG, "onNotificationClicked");
        MessageReceiverHandler.handlerMessage(context, mzPushMessage.getSelfDefineContentString());
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        super.onNotificationArrived(context, mzPushMessage);
    }

    @Override
    public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
        super.onNotificationDeleted(context, mzPushMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, String s) {
        super.onNotifyMessageArrived(context, s);
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {

    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.i(TAG, "onRegisterStatus token = " + registerStatus.getPushId());
        ThirdPushTokenMgr.getInstance().setIsLogin(UserInfoManager.INSTANCE.isLogin());
        ThirdPushTokenMgr.getInstance().setThirdPushToken(registerStatus.getPushId());
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();

    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {

    }

    @Override
    public void onRegister(Context context, String s) {
    }

    @Override
    public void onUnRegister(Context context, boolean b) {

    }
}
