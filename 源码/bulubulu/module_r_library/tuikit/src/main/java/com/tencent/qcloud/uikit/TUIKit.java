package com.tencent.qcloud.uikit;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMLogListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMMessageReceipt;
import com.tencent.imsdk.ext.message.TIMMessageReceiptListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;
import com.tencent.qcloud.uikit.business.chat.group.model.GroupChatManager;
import com.tencent.qcloud.uikit.business.session.model.SessionManager;
import com.tencent.qcloud.uikit.common.BackgroundTasks;
import com.tencent.qcloud.uikit.common.IUIKitCallBack;
import com.tencent.qcloud.uikit.common.component.face.FaceManager;
import com.tencent.qcloud.uikit.common.utils.FileUtil;
import com.tencent.qcloud.uikit.operation.UIKitMessageRevokedManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;


public class TUIKit {

    private static Context appContext;
    private static BaseUIKitConfigs baseConfigs;
    public static int IM_APP_ID = 1400200637; //bulubulu
    private static int IM_APP_ID_DEBUG = 1400461918; //flannery<----仅供测试用


    private static final Boolean DEBUG = false;

    public static String getUserSig(String userId, String userSig) {
        if (DEBUG) { // 只有debug环境用这个，可别影响了其他功能
            // DEBUG的时候我们自己生成
            return GenerateTestUserSig.genTestUserSig(userId, IM_APP_ID_DEBUG);
        } else {
            return userSig;
        }
    }

    public static void init(Context context) {
        if (DEBUG) {
            TUIKit.init(context, IM_APP_ID_DEBUG, BaseUIKitConfigs.getDefaultConfigs());  //这需
        } else {
            TUIKit.init(context, IM_APP_ID, BaseUIKitConfigs.getDefaultConfigs());  //这需
        }
    }

    /**
     * TUIKit的初始化函数
     *
     * @param context  应用的上下文，一般为对应应用的ApplicationContext
     * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
     * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
     */
    public static void init(Context context, int sdkAppID, BaseUIKitConfigs configs) {
        if (BuildConfig.DEBUG) TUIKit.m(TUIKit.class);
        appContext = context; // 保存Context
        baseConfigs = configs;
        baseConfigs.setAppCacheDir(context.getFilesDir().getPath()); // 设置缓存目录
        long current = System.currentTimeMillis();

        initIM(context, sdkAppID); // 初始化IM
        System.out.println("TUIKIT>>>>>>>>>>>>>>>>>>" + (System.currentTimeMillis() - current));
        current = System.currentTimeMillis();

        BackgroundTasks.initInstance(); // 做背景任务的时候
        FileUtil.initPath(); // 取决于app什么时候获取到权限，即使在application中初始化，首次安装时，存在获取不到权限，建议app端在activity中再初始化一次，确保文件目录完整创建
        System.out.println("TUIKIT>>>>>>>>>>>>>>>>>>" + (System.currentTimeMillis() - current));
        current = System.currentTimeMillis();
        FaceManager.loadFaceFiles(); // emoj的管理类
        System.out.println("TUIKIT>>>>>>>>>>>>>>>>>>" + (System.currentTimeMillis() - current));

        SessionManager.getInstance().init(); // 会话管理类
        C2CChatManager.getInstance().init(); // 聊天管理类
        GroupChatManager.getInstance().init(); // 分组管理类（群）
    }

    public static void login(String userid, String usersig, final IUIKitCallBack callback) {
        // 登陆消息
        TIMManager.getInstance().login(userid, usersig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                callback.onError("TUIKit login", code, desc);
            }

            @Override
            public void onSuccess() {
                callback.onSuccess(null);
                addOfflineMsgConfig();
            }
        });
    }

    // 初始化IM
    private static void initIM(Context context, int sdkAppID) {
        TIMSdkConfig config = getBaseConfigs().getTIMSdkConfig();
        if (config == null) {
            config = new TIMSdkConfig(sdkAppID).setLogLevel(TIMLogLevel.DEBUG);
            if (App.DEBUG) {
                config.setLogCallbackLevel(TIMLogLevel.DEBUG);
                config.setLogListener(new TIMLogListener() {
                    @Override
                    public void log(int level, String tag, String msg) {
                        //Log.e("TUIKit", "test session wrapper jni, msg = " + msg);
                        L.m(getClass(), TUIKit.class.getSimpleName(), tag, msg);
                    }
                });
                config.enableLogPrint(App.DEBUG); // 允许打印
                //config.setLogPath(PathUtils.getPath());
            }
        }
        TIMManager.getInstance().init(context, config);
        // 设置离线消息通知
        TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {

            @Override
            public void handleNotification(TIMOfflinePushNotification var1) {

            }
        });
        // 用户配置的状态
        TIMUserConfig userConfig = new TIMUserConfig();
        userConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() { // 强制退出
                if (baseConfigs.getIMEventListener() != null) {
                    baseConfigs.getIMEventListener().onForceOffline();
                }
                TUIKit.unInit(); // 反初始化
            }

            @Override
            public void onUserSigExpired() { // 用户期望
                if (baseConfigs.getIMEventListener() != null) {
                    baseConfigs.getIMEventListener().onUserSigExpired();
                }
                TUIKit.unInit();
            }
        });

        userConfig.setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() { // 用户连接上
                if (getBaseConfigs().getIMEventListener() != null)
                    getBaseConfigs().getIMEventListener().onConnected();
            }

            @Override
            public void onDisconnected(int code, String desc) { //取消连接
                if (getBaseConfigs().getIMEventListener() != null)
                    getBaseConfigs().getIMEventListener().onDisconnected(code, desc);
            }

            @Override
            public void onWifiNeedAuth(String name) {// 用户需要配置
                if (getBaseConfigs().getIMEventListener() != null)
                    getBaseConfigs().getIMEventListener().onWifiNeedAuth(name);
            }
        });

        userConfig.setRefreshListener(new TIMRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefreshConversation(List<TIMConversation> conversations) {
                SessionManager.getInstance().onRefreshConversation(conversations);
                if (TUIKit.getBaseConfigs().getIMEventListener() != null) {
                    TUIKit.getBaseConfigs().getIMEventListener().onRefreshConversation(conversations);
                }
            }
        });

        userConfig.setGroupEventListener(new TIMGroupEventListener() {
            @Override
            public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                if (TUIKit.getBaseConfigs().getIMEventListener() != null) {
                    TUIKit.getBaseConfigs().getIMEventListener().onGroupTipsEvent(elem);
                }
            }
        });

        userConfig.setReadReceiptEnabled(true);
        userConfig.setMessageReceiptListener(new TIMMessageReceiptListener() {
            @Override
            public void onRecvReceipt(List<TIMMessageReceipt> list) {
                if (TUIKit.getBaseConfigs().getIMEventListener() != null) {
                    TUIKit.getBaseConfigs().getIMEventListener().onRecvReceipt(list);
                }
            }
        });

        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {
                if (TUIKit.getBaseConfigs().getIMEventListener() != null) {
                    TUIKit.getBaseConfigs().getIMEventListener().onNewMessages(msgs);
                }
                return false;
            }
        });

        TIMUserConfigMsgExt ext = new TIMUserConfigMsgExt(userConfig);
        ext.setMessageRevokedListener(UIKitMessageRevokedManager.getInstance());
        ext.disableAutoReport(true);
        TIMManager.getInstance().setUserConfig(ext);

    }


    public static void unInit() {
        SessionManager.getInstance().destroySession();
        C2CChatManager.getInstance().destroyC2CChat();
        GroupChatManager.getInstance().destroyGroupChat();
    }


    public static Context getAppContext() {
        return appContext;
    }

    public static BaseUIKitConfigs getBaseConfigs() {
        return baseConfigs;
    }

    public static void addOfflineMsgConfig() {
        // 离线接收消息
        TIMManager.getInstance().getOfflinePushSettings(new TIMValueCallBack<TIMOfflinePushSettings>() {
            @Override
            public void onError(int i, String s) {
                if (DEBUG) BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TUIKit.getAppContext(), "addOfflineMsgConfig 失败了", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSuccess(TIMOfflinePushSettings timOfflinePushSettings) {
                if (timOfflinePushSettings != null) {
                    if (getBaseConfigs() != null) {
                        getBaseConfigs().setOfflinePushSettings(timOfflinePushSettings);
                    }
                }
            }
        });
    }


    // 打印IM相关
    public static void m(Class<?> aClass, Object... o) {
        if (DEBUG) L.m(aClass, "tuikit", o);
    }


    public static String getErrorCodeString(int errorCode) {
        String s = UtilsCode.INSTANCE.readAssets2String("imerrorcode.json");
        if (!TextUtils.isEmpty(s)) {
            try {
                JSONObject o = new JSONObject(s);
                return o.optString(String.valueOf(errorCode), "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
