package cn.kuwo.pp.thirdpush;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.main.SplashActivity;
import com.xiaomi.mipush.sdk.*;


import java.util.List;
import java.util.Map;

import static cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY;

/**
 * Created by vinsonswang on 2019/2/26.
 */

public class XiaomiMsgReceiver extends PushMessageReceiver {

    private static final String TAG = XiaomiMsgReceiver.class.getSimpleName();
    private String mRegId;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        MessageReceiverHandler.handlerMessage(context, (String) miPushMessage.getExtra().get("ext"));
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        Log.d(TAG, "onReceiveRegisterResult is called. " + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }

        ThirdPushTokenMgr.getInstance().setIsLogin(UserInfoManager.INSTANCE.isLogin());
        ThirdPushTokenMgr.getInstance().setThirdPushToken(mRegId);
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
    }
}
