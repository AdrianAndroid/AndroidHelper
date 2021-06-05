package cn.kuwo.pp.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.elbbbird.android.socialsdk.SocialSDK;
import com.elbbbird.android.socialsdk.model.SocialShareScene;
import com.elbbbird.android.socialsdk.otto.ShareBusEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tencent.connect.common.Constants;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.ext.message.TIMMessageReceipt;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.uikit.IMEventListener;
import com.tencent.qcloud.uikit.TUIKit;
import com.tencent.qcloud.uikit.common.BackgroundTasks;
import com.tencent.qcloud.uikit.common.utils.FileUtil;
import com.tencent.qcloud.uikit.common.utils.ScreenUtil;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseActivity;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.event.CloseMainActivityEvent;
import cn.kuwo.common.event.LikeAnimEvent;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.viewmodel.MainViewModel;
import cn.kuwo.func_base.utils.PlayBgMusic;
import cn.kuwo.networker.NetworkConnectChangedReceiver;
import cn.kuwo.networker.Urls;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.MessageReceiptEvent;
import cn.kuwo.pp.event.NewSystemMessageHintEvent;
import cn.kuwo.pp.event.TimForceOfflineEvent;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.thirdpush.ThirdPushTokenMgr;
import cn.kuwo.pp.ui.chat.C2CChatFragment;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.publish.PublishMainFragment;
import cn.kuwo.pp.ui.system.SystemMessageFragment;
import cn.kuwo.pp.ui.topic.ChallengeFragment;
import cn.kuwo.pp.util.IconFountTextView;
import cn.kuwo.pp.util.WaterImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.yokeyword.fragmentation.ISupportFragment;

//import static cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY;
//import static cn.kuwo.pp.thirdpush.Constants.SYSTEM_MESSAGE_CHAT_ID;

/**
 * @author shihc
 */
public class MainActivity extends BaseActivity implements WbShareCallback {
    private static final String TAG = "push";
    private long mLastClickTime;
    private LottieAnimationView likeAnimView;
    private WaterImageView imgHeaderView;
    private TextView tvName;
    private IconFountTextView tvSex;
    private TextView tvCity;
    private TextView tvSong;  //音色
    private TextView tvConstellation; //星座
    private AnimatorSet LikeAnimSet, textAnimSet;
    private MediaPlayer hintPlayer;   //新消息提示音播放器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainViewModel();

        FileUtil.initPath(); // IM聊天的一些东西
        setupView();
        setupPushs();
        openOfflinePushChat(getIntent());


    }


    private void initMainViewModel() {
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.start.observe(this, new Observer<BaseFragment>() {
            @Override
            public void onChanged(BaseFragment baseFragment) {
                // 跳转到新的Fragment
                if (baseFragment != null) {
                    start(baseFragment);
                }
            }
        });
        mainViewModel.startSingleTask.observe(this, new Observer<BaseFragment>() {
            @Override
            public void onChanged(BaseFragment baseFragment) {
                if (baseFragment != null) {
                    start(baseFragment, ISupportFragment.SINGLETASK); // 此时应该调用Fragment的newBundle
                }
            }
        });
        mainViewModel.startWithPop.observe(this, new Observer<BaseFragment>() {
            @Override
            public void onChanged(BaseFragment baseFragment) {
                if (baseFragment != null) {
                    startWithPop(baseFragment);
                }
            }
        });
//        mainViewModel.popTo.observe(this, new Observer<Class<?>>() {
//            @Override
//            public void onChanged(Class<?> aClass) {
//                if(aClass == null) {
//                    pop();
//                } else {
//                    popTo(aClass, );
//                }
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkConnectChangedReceiver.Companion.registerConnectState(); // 监听网络状态

        // 是否显示debug模式
        if (!Urls.isReleaseUrl()) {
            Urls.showDebuLog(this);//
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkConnectChangedReceiver.Companion.unRegisterConnectState(); // 监听网络状态
    }

    private void setupPushs() {
        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    Log.i("Push", "huawei push HMS connect end:" + rst);
                }
            });
            getHuaWeiPushToken();
        } else if (IMFunc.isBrandVivo()) {
            // vivo 离线推送
            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int state) {
                    if (state == 0) {
                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();
                        Log.i(TAG, "vivopush open vivo push success regId = " + regId);
                        ThirdPushTokenMgr.getInstance().setIsLogin(UserInfoManager.INSTANCE.isLogin());
                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
                    } else {
                        // 根据 vivo 推送文档说明，state = 101表示该 vivo 机型或者版本不支持 vivo 推送，详情请参考 vivo 推送常见问题汇总
                        Log.i(TAG, "vivopush open vivo push fail state = " + state);
                    }
                }
            });
        }
    }

    private void getHuaWeiPushToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                Log.i("Push", "huawei push get token: end" + rtnCode);
            }
        });
    }


    private void playNewMsgSong() {
        if (hintPlayer != null) {
            hintPlayer.stop();
            hintPlayer.release();
            hintPlayer = null;
        }
        hintPlayer = MediaPlayer.create(this, R.raw.bubblesound);
        hintPlayer.setOnCompletionListener(mp -> {
            hintPlayer.release();
            hintPlayer = null;
        });
        hintPlayer.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (isReChallenge(intent)) {
            // 重新挑战
            AnalyticsUtils.INSTANCE.onEvent(this, UmengEventId.CHANLLEGE_RESULT_RECHANLLEGE, MainActivity.class.getSimpleName());
            startChallengeFragment();
        } else if (isC2CChat(intent)) {
            // 撩他
            AnalyticsUtils.INSTANCE.onEvent(this, UmengEventId.CHANNLEGE_RESULT_FLIRT, MainActivity.class.getSimpleName());
            startC2CChatFragment(intent);
        } else if (isPublish(intent)) {
            start(PublishMainFragment.newInstance(PublishMainFragment.FROM_TREND_H5));
        } else {
            setIntent(intent);
        }
    }

    // 是否是我要出题界面
    private boolean isPublish(Intent intent) {
        // 后期要弄一个工具类
        if (intent != null && intent.getData() != null
                && "kwbulu://challenge/issue".equals(intent.getData().toString())) {
            //kwbulu://challenge/issue
            return true;
        }
        return false;
    }

    // 是否是聊天页面
    private boolean isC2CChat(Intent intent) {
        //kwbulu://chat/chat?voiceUid=1586
        Uri data = intent.getData();
        if (data != null && "chat".equals(data.getAuthority())) {
            return true;
        }
        return false;
    }

    // 开启聊天页面
    private void startC2CChatFragment(Intent intent) {
        try {
            String voiceUid = intent.getData().getQueryParameter("voiceUid");
            start(C2CChatFragment.Companion.newInstance("", voiceUid, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 发起挑战页面
    private void startChallengeFragment() {
        startWithPop(ChallengeFragment.newInstance());
    }

    //重新挑战,来源h5
    private Boolean isReChallenge(Intent intent) {
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null && "kwbulu://challenge/challenge".equals(uri.toString())) {
                return true;
            }
        }
        return false;
    }

    private void setupView() {
        EventBus.getDefault().register(this);
        imgHeaderView = findViewById(R.id.acv);
        imgHeaderView.setMarkColor(getResources().getColor(R.color.colorMask));
        tvName = findViewById(R.id.look_for_name);
        tvSex = findViewById(R.id.look_for_tag_sex);
        tvCity = findViewById(R.id.look_for_tag_city);
        tvSong = findViewById(R.id.look_for_tag_song);
        tvConstellation = findViewById(R.id.look_for_tag_constellation);
        likeAnimView = findViewById(R.id.like_anim_view);
        updateAnimViewVisible(View.INVISIBLE);
        textAnimSet = new AnimatorSet();
        textAnimSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resetInfoLayout();
                updateAnimViewVisible(View.INVISIBLE);
                imgHeaderView.setImageDrawable(null);
                EventBus.getDefault().post(LikeAnimEvent.getTxtEndAnimEvent());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                resetInfoLayout();
                updateAnimViewVisible(View.INVISIBLE);
                imgHeaderView.setImageDrawable(null);
                EventBus.getDefault().post(LikeAnimEvent.getTxtEndAnimEvent());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        likeAnimView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                EventBus.getDefault().post(LikeAnimEvent.getStartedEvent());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resetInfoLayout();
                updateAnimViewVisible(View.INVISIBLE);
                likeAnimView.setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(LikeAnimEvent.getEndAnimEvent());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                resetInfoLayout();
                updateAnimViewVisible(View.INVISIBLE);
                likeAnimView.setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(LikeAnimEvent.getEndAnimEvent());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.container_id, MainFragment.Companion.newInstance());
        }
        setSwipeBackEnable(false);
        // 初始化聊天
        TUIKit.getBaseConfigs().setIMEventListener(new IMEventListener() {
            @Override
            public void onForceOffline() {
                super.onForceOffline();
                if (App.DEBUG) L.L(getClass(), "TUIKit", "onFOrceOffline");
                MainActivity.this.showForceOfflineDialog();//强制下线
            }

            @Override
            public void onNewMessages(List<TIMMessage> msgs) { //应该是来了新消息之后
                super.onNewMessages(msgs);
                if (App.DEBUG) L.L(getClass(), "TUIKit", "onNewMessages");
                playNewMsgSong();//播放来消息的语音
            }

            @Override
            public void onRefreshConversation(List<TIMConversation> conversations) {
                if (App.DEBUG) {
                    for (TIMConversation conversation : conversations) {
                        L.L(getClass()
                                , "TUIKit"
                                , "onRefreshConversation"
                                , "peer=", conversation.getPeer()
                                , "cptr=", conversation.getType().toString());
                    }
                }
                FriendListManager.getInstance().onRefreshConversation(conversations);// 刷新会话
            }

            @Override
            public void onRecvReceipt(List<TIMMessageReceipt> list) {
                if (App.DEBUG) L.L(getClass(), "TUIKit", "onRecvReceipt");
                EventBus.getDefault().post(new MessageReceiptEvent(list));
            }

            @Override
            public void onAddOfflineConfigError() {
                super.onAddOfflineConfigError();
                if (App.DEBUG) L.L(getClass(), "TUIKit", "onAddOfflineConfigError");
//                TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//                settings.setEnabled(true);
//                settings.setC2cMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bubblesound));
//                TIMManager.getInstance().setOfflinePushSettings(settings);
            }

            @Override
            public void onAddOfflineConfigSuccess(TIMOfflinePushSettings settings) {
                super.onAddOfflineConfigSuccess(settings);
                if (App.DEBUG) L.L(getClass(), "TUIKit", "onAddOfflineConfigSuccess");
                settings.setEnabled(true);
                //android的离线推送，不支持自定义提醒声音
//                settings.setC2cMsgRemindSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bubblesound));
                TIMManager.getInstance().setOfflinePushSettings(settings);
            }
        });


//        PrivacyDialogUtil.showPrivacy(findViewById(R.id.privacy), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                start(WebFragment.newInstance("https://h5app.kuwo.cn/m/3dab9c3a/server.html", "用户协议", ""));
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                start(WebFragment.newInstance("https://h5app.kuwo.cn/m/3d724391/secret.html", "隐私政策", ""));
//            }
//        });
    }

    // 强制下线是TIM做的！！！
    private void showForceOfflineDialog() {
        UserInfoManager.INSTANCE.onLogoutSuccess();
        if (this.isFinishing()) {
            return;
        }
        DialogUtils.INSTANCE.showForceOfflineDialog(new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean) {
                start(LoginFragment.Companion.newInstance(false));
                AnalyticsUtils.INSTANCE.onEvent(MainActivity.this, UmengEventId.OTHER_LOGIN, TAG);
                return null;
            }
        }, null);
//        AlertDialog.getInstance(this, "", "你的账号在另一台手机上登录，您已被迫下线，如非本人操作，请及时修改密码", "去登录", "取消", (dialog, which) -> {
//            //startActivity(new Intent(this, LoginActivity.class));
//            start(LoginFragment.Companion.newInstance(false));
//            AnalyticsUtils.INSTANCE.onEvent(this, UmengEventId.OTHER_LOGIN, TAG);
//        }).show();
    }

    private void resetInfoLayout() {
        if (imgHeaderView == null) {
            return;
        }
        imgHeaderView.setScaleX(1f);
        imgHeaderView.setScaleY(1f);
        imgHeaderView.setTranslationY(0f);
        imgHeaderView.setTranslationX(0f);
        tvName.setTranslationY(0f);
        tvSex.setTranslationY(0f);
        tvConstellation.setTranslationY(0f);
        tvCity.setTranslationY(0f);
        tvSong.setTranslationY(0f);
    }

    private void updateAnimViewVisible(int visibleFlag) {
        if (imgHeaderView == null) {
            return;
        }
        imgHeaderView.setVisibility(visibleFlag);
        tvName.setVisibility(visibleFlag);
        if (TextUtils.isEmpty(tvSex.getText().toString())) {
            tvSex.setVisibility(View.INVISIBLE);
        } else {
            tvSex.setVisibility(visibleFlag);
        }
        if (TextUtils.isEmpty(tvSong.getText().toString())) {
            tvSong.setVisibility(View.INVISIBLE);
        } else {
            tvSong.setVisibility(visibleFlag);
        }
        if (TextUtils.isEmpty(tvCity.getText().toString())) {
            tvCity.setVisibility(View.INVISIBLE);
        } else {
            tvCity.setVisibility(visibleFlag);
        }
        if (TextUtils.isEmpty(tvConstellation.getText().toString())) {
            tvConstellation.setVisibility(View.INVISIBLE);
        } else {
            tvConstellation.setVisibility(visibleFlag);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
            return;
        }
        long timeMillis = System.currentTimeMillis();
        if (timeMillis - mLastClickTime > 1000) {
            UtilsCode.INSTANCE.showShort("再按一次退出应用");
            mLastClickTime = timeMillis;
        } else {
            ActivityCompat.finishAfterTransition(this);
            BuluPlayer.getInstance().releasePlayer(true);
            //debug版本不杀进程，方便调试
            if (!App.DEBUG) {
                App.getInstance().exit();
            }
        }
    }

    // TIM 并没有登陆成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onForceOffline(TimForceOfflineEvent event) {
        showForceOfflineDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (hintPlayer != null) {
            hintPlayer.stop();
            hintPlayer.release();
            hintPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtils.INSTANCE.onResume(this);
        openOfflinePushChat(getIntent());
    }

    @Override
    protected void onPause() {
        AnalyticsUtils.INSTANCE.onPause(this);
        super.onPause();
        PlayBgMusic.stopBgMusic(); // 全局只有一个MainActivity, 退出应用后不能播放音乐
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN || resultCode == Constants.REQUEST_LOGIN) {
            SocialSDK.getInstance().oauthQQCallback(requestCode, resultCode, data);
        } else if (requestCode == Constants.REQUEST_QZONE_SHARE || requestCode == Constants.REQUEST_QQ_SHARE) {
            SocialSDK.getInstance().shareToQCallback(requestCode, resultCode, data);
        } else if (requestCode == 1 && data != null) {
            SocialSDK.getInstance().shareToWeiboCallback(data, this);
        }
    }

    @Override
    public void onWbShareSuccess() {
        EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_SUCCESS, SocialShareScene.SHARE_TYPE_WEIBO));
    }

    @Override
    public void onWbShareCancel() {
        EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_CANCEL, SocialShareScene.SHARE_TYPE_WEIBO));
    }

    @Override
    public void onWbShareFail() {
        EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_FAILURE, SocialShareScene.SHARE_TYPE_WEIBO));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnShareResultEvent(ShareBusEvent event) {
        switch (event.getType()) {
            case ShareBusEvent.TYPE_CANCEL:
                UtilsCode.INSTANCE.showShort("取消分享");
                break;
            case ShareBusEvent.TYPE_FAILURE:
                UtilsCode.INSTANCE.showShort("分享失败：" + (event.getException() != null ? event.getException().getMessage() : "未知错误"));
                break;
            case ShareBusEvent.TYPE_SUCCESS:
//                SimpleHttpRequest.sendShareStaticLog("2");  //根据活动定义，2表示分享
//                MobclickAgent.onEvent(getActivity(), UmengEventId.SHARE_MUSIC_SUCCESS, event.getPlatform() + "");
//                LogHelper.sendShareSuccessOptLog(LogDeL.OPT_TYPE_SHARE_MUSIC_SUCCESS,
//                        mTargetItem != null ? String.valueOf(mTargetItem.getMid()) : "",
//                        mTargetItem != null ? mTargetItem.getName() : null,
//                        event.getPlatform() + "");
                UtilsCode.INSTANCE.showShort("分享成功");
                break;
        }
    }

    @Subscribe
    public void onLikeAnimEvent(LikeAnimEvent event) {
        if (event.getAnimEvent() == LikeAnimEvent.ANIM_START) {
            resetInfoLayout();
            likeAnimView.setVisibility(View.VISIBLE);
            updateAnimViewVisible(View.VISIBLE);
            if (LikeAnimSet == null) {
                float moveX_Offet = (UtilsCode.INSTANCE.getScreenWidth() - likeAnimView.getWidth()) / 2f - likeAnimView.getLeft();
                float moveY_Offet = (UtilsCode.INSTANCE.getScreenHeight() - likeAnimView.getHeight()) / 2f - likeAnimView.getTop();
                ObjectAnimator moveX_Anim = ObjectAnimator.ofFloat(likeAnimView, "translationX", 0f, moveX_Offet);
                ObjectAnimator moveY_Anim = ObjectAnimator.ofFloat(likeAnimView, "translationY", 0f, moveY_Offet);
                float txt_Offet = -UtilsCode.INSTANCE.dp2px(290);
                ObjectAnimator name_moveY_Anim = ObjectAnimator.ofFloat(tvName, "translationY", 0f, txt_Offet);
                ObjectAnimator name_moveX_Anim = ObjectAnimator.ofFloat(tvName, "translationX", 0f, -UtilsCode.INSTANCE.dp2px(20));
                ObjectAnimator sex_moveY_Anim = ObjectAnimator.ofFloat(tvSex, "translationY", 0f, txt_Offet);
                ObjectAnimator city_moveY_Anim = ObjectAnimator.ofFloat(tvCity, "translationY", 0f, txt_Offet);
                ObjectAnimator song_moveY_Anim = ObjectAnimator.ofFloat(tvSong, "translationY", 0f, txt_Offet);
                ObjectAnimator cons_moveY_Anim = ObjectAnimator.ofFloat(tvConstellation, "translationY", 0f, txt_Offet);
                ObjectAnimator info_moveY_Anim = ObjectAnimator.ofFloat(imgHeaderView, "translationY", 0f, -UtilsCode.INSTANCE.dp2px(170));
                ObjectAnimator info_moveX_Anim = ObjectAnimator.ofFloat(imgHeaderView, "translationX", 0f, -UtilsCode.INSTANCE.dp2px(100));
                ObjectAnimator info_scaleX_Anim = ObjectAnimator.ofFloat(imgHeaderView, "scaleX", 1f, 0.3f);
                ObjectAnimator info_scaleY_Anim = ObjectAnimator.ofFloat(imgHeaderView, "scaleY", 1f, 0.3f);
                long shortTime = 300;
                name_moveY_Anim.setDuration(shortTime);
                name_moveX_Anim.setDuration(shortTime);
                sex_moveY_Anim.setDuration(shortTime);
                city_moveY_Anim.setDuration(shortTime);
                song_moveY_Anim.setDuration(shortTime);
                cons_moveY_Anim.setDuration(shortTime);
                info_moveY_Anim.setDuration(shortTime);
                info_moveX_Anim.setDuration(shortTime);
                info_scaleX_Anim.setDuration(shortTime);
                info_scaleY_Anim.setDuration(shortTime);
                LikeAnimSet = new AnimatorSet();
                LikeAnimSet.setStartDelay(100);
                LikeAnimSet.play(moveX_Anim).with(moveY_Anim);
                LikeAnimSet.setDuration(700);
                textAnimSet.play(name_moveY_Anim).with(name_moveX_Anim)
                        .with(sex_moveY_Anim).with(city_moveY_Anim).with(song_moveY_Anim).with(cons_moveY_Anim)
                        .with(info_moveY_Anim).with(info_moveX_Anim).with(info_scaleX_Anim).with(info_scaleY_Anim);
            } else {
                LikeAnimSet.cancel();
            }
            likeAnimView.playAnimation();
            LikeAnimSet.start();
            textAnimSet.start();
        } else if (event.getAnimEvent() == LikeAnimEvent.ANIM_INFO) {
            VoiceInfo info = (VoiceInfo) event.getSrcInfo();
            if (info != null) {
                String ageText = "";
                if (info.getAge() != null && !"0".equals(info.getAge())) {
                    ageText = info.getAge();
                }
                if ("2".equals(info.getSex())) {
                    if (event.getAnimImage() != null) {
                        imgHeaderView.setImageDrawable(event.getAnimImage());
                    } else {
                        ImageLoader.showImage(imgHeaderView, info.getHeadImg(), R.drawable.default_header_pic_famale_round);
                    }
                    tvSex.setIconText("&#xe6d8;" + ageText);
                    tvSex.setBackgroundResource(R.drawable.tag_sex_woman_rounded_placeholder);
                    tvSex.setTextColor(Color.parseColor("#FFFFA4A4"));
                } else if ("1".equals(info.getSex())) {
                    if (event.getAnimImage() != null) {
                        imgHeaderView.setImageDrawable(event.getAnimImage());
                    } else {
                        ImageLoader.showImage(imgHeaderView, info.getHeadImg(), R.drawable.default_header_pic_male_round);
                    }
                    tvSex.setIconText("&#xe6d7;" + ageText);
                    tvSex.setBackgroundResource(R.drawable.tag_sex_man_rounded_placeholder);
                    tvSex.setTextColor(Color.parseColor("#FF6ED2D5"));
                }
                tvName.setText(info.getName());
                tvCity.setText(info.getCity());
                tvSong.setText(info.getCard());
                tvConstellation.setText(info.getConstellation());
            } else {
                updateAnimViewVisible(View.INVISIBLE);
            }
        } else if (event.getAnimEvent() == LikeAnimEvent.ANIM_CANCEL) {
            if (LikeAnimSet != null && LikeAnimSet.isRunning()) {
                LikeAnimSet.cancel();
            }
            if (likeAnimView != null && likeAnimView.isAnimating()) {
                likeAnimView.cancelAnimation();
            }
            if (textAnimSet != null && textAnimSet.isRunning()) {
                textAnimSet.cancel();
            }
            resetInfoLayout();
            updateAnimViewVisible(View.INVISIBLE);

        } else if (event.getAnimEvent() == LikeAnimEvent.ANIM_INIT) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) likeAnimView.getLayoutParams();
            lp.width = event.getSrcWidth();
            lp.height = event.getSrcHeight();
            lp.rightMargin = ScreenUtil.getScreenWidth(this) - event.getSrcLeft() - lp.width;
            lp.topMargin = event.getSrcTop();
            likeAnimView.setLayoutParams(lp);
        }
    }

    @Subscribe
    public void onCloseEvent(CloseMainActivityEvent event) {
        this.finish();
    }


    private void openOfflinePushChat(Intent intent) {

        String data = intent.getStringExtra(cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY);
        if (data != null) {
            if (!data.isEmpty()) {
                Log.i(TAG, "openOfflinePushChat data: " + data);
                intent.putExtra(cn.kuwo.pp.thirdpush.Constants.OFFLINE_PUSH_KEY, "");
                BackgroundTasks.getInstance().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String chatId = "";
                        try {
                            JsonObject object = new JsonParser().parse(data).getAsJsonObject();
                            if (object != null) {
                                chatId = object.get("OpenChatId").getAsString();
                                Log.i(TAG, "openOfflinePushChat chatId: " + chatId);
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "openOfflinePushChat Exception: " + e.getMessage());
                        }
                        if (!chatId.isEmpty()) {
                            if (chatId.equalsIgnoreCase(cn.kuwo.pp.thirdpush.Constants.SYSTEM_MESSAGE_CHAT_ID)) {
                                start(SystemMessageFragment.newInstance());
                            } else {
                                C2CChatFragment fragment = C2CChatFragment.Companion.newInstance("", chatId, null);
                                start(fragment);
                            }
                        }
                    }
                }, 1000);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnNewSystemMessageHintEvent(NewSystemMessageHintEvent event) {
        playNewMsgSong();
    }
}
