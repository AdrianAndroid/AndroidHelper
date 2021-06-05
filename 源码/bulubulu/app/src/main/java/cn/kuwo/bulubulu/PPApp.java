package cn.kuwo.bulubulu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.elbbbird.android.analytics.AnalyticsUtils;
import com.flannery.kuwowebview.X5;
import com.github.anrwatchdog.ANRWatchDog;
import com.huawei.android.hms.agent.HMSAgent;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.uikit.TUIKit;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.kuwo.common.app.App;
import cn.kuwo.common.pictureselector.BuluPictureSelector;
import cn.kuwo.common.util.SP;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.AppUidBean;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.thirdpush.Constants;
import cn.kuwo.pp.util.notify.NotifyMgr;
import io.reactivex.plugins.RxJavaPlugins;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * @author shihc
 */
public class PPApp extends App {

    public static AppUidBean serverAppUid = null;   //保存从服务器取回的appUid

    public static String getAppUid() {
        if (serverAppUid != null) {
            return serverAppUid.getAppuid();
        }
        return "";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        SP.initialize(this);
    }

    @Override
    public void onCreate() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
        if (!BuildConfig.DEBUG) {
            new ANRWatchDog().setReportMainThreadOnly().setANRListener(error -> CrashReport.postCatchedException(error)).start();
        }

//        LeakCanary.install(this);
        super.onCreate();
        //要在registerPush之前调用
        SP.initialize(this);
        boolean mainProgress = isMainProgress();
        //主进程
        if (mainProgress) {
            // 聊天
            TUIKit.init(this);  //这需要在所有网络请求前调用，因为网络组件触发UserInfoManager的init代码段，触发登录动作
            requestAppUid();
            X5.init(this); // x5webview初始化
            //setRxJavaErrorHandler();
            //setTheme();
            initPushs();// 初始化推送
            createNotificationChannel();
        }
        initBugly(mainProgress);
        initUMeng();

        if (App.DEBUG) {
            initFragmentation();
        }
        BuluPictureSelector.init();
    }

    private void initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                })
                .install();
    }

    private void requestAppUid() {
        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().getAppUid(),
                new CustomObserver<AppUidBean>() {
                    @Override
                    public void onNext(AppUidBean o) {
                        serverAppUid = o;
                    }

                    @Override
                    public void _onError(ApiException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void initPushs() {
        if (IMFunc.isBrandXiaoMi()) {
            // 小米离线推送
            MiPushClient.registerPush(this, Constants.XM_PUSH_APPID, Constants.XM_PUSH_APPKEY);
        }
        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.init(this);
        }
        if (MzSystemUtils.isBrandMeizu(this)) {
            // 魅族离线推送
            PushManager.register(this, Constants.MZ_PUSH_APPID, Constants.MZ_PUSH_APPKEY);
        }
        if (IMFunc.isBrandVivo()) {
            // vivo 离线推送
            PushClient.getInstance(getApplicationContext()).initialize();
        }
    }

    private void initUMeng() {
        AnalyticsUtils.INSTANCE.initUMeng(this, appGetChannel());
    }

    private void initBugly(boolean isMainProgress) {
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(isMainProgress);
        //debug版本号设置为0.0
        if (BuildConfig.DEBUG) {
            strategy.setAppVersion("0.0");
        }
        strategy.setAppChannel(appGetChannel());
        Beta.autoDownloadOnWifi = true;
        Bugly.init(getApplicationContext(), "1851019961", BuildConfig.DEBUG, strategy);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = NotifyMgr.NEW_MESSAGE_CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, NotifyMgr.NEW_MESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
        });
    }

    @Override
    public void exit() {
        super.exit();
    }


    ///////////////////
    ///getChannel()////
    ///////////////////
    @Override
    public String appGetChannel() {
        return cn.kuwo.bulubulu.BuildConfig.CHANNEL;
    }

    @Override
    public Boolean appDebugUrl() {
        return cn.kuwo.bulubulu.BuildConfig.IS__DEBUG;
    }


    @Override
    public String appGetToken() {
        return UserInfoManager.INSTANCE.getToken();
    }

    @Override
    public String appGetUid() {
        return UserInfoManager.INSTANCE.getUid();
    }

    @Override
    public boolean appIsLogin() {
        return UserInfoManager.INSTANCE.isLogin();
    }

    @Override
    public String appGetUserGender() {
        return UserInfoManager.INSTANCE.getUserGender();
    }

    @Override
    public String appPackageTime() {
        return BuildConfig.PACKAGE_TIME;
    }
}
