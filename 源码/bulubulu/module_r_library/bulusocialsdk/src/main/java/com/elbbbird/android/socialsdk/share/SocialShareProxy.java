package com.elbbbird.android.socialsdk.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.elbbbird.android.socialsdk.BuildConfig;
import com.elbbbird.android.socialsdk.model.SocialShareScene;
import com.elbbbird.android.socialsdk.otto.ShareBusEvent;
import com.elbbbird.android.socialsdk.share.qq.QQShareProxy;
import com.elbbbird.android.socialsdk.share.wechat.IWXShareCallback;
import com.elbbbird.android.socialsdk.share.wechat.WeChatShareProxy;
import com.elbbbird.android.socialsdk.share.weibo.WeiboShareProxy;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 社会化分享代理
 * Created by zhanghailong-ms on 2015/11/23.
 */
public class SocialShareProxy {

    private static final String TAG = "SocialShareProxy";
    private static boolean DEBUG = BuildConfig.DEBUG;

    private static SocialShareScene scene;

    private static IWXShareCallback wechatShareCallback = new IWXShareCallback() {
        @Override
        public void onSuccess() {
            if (DEBUG) {
                Log.i(TAG, "SocialShareProxy#wechatShareCallback onSuccess");
            }
            EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_SUCCESS, scene.getType(), scene.getId()));
        }

        @Override
        public void onCancel() {
            if (DEBUG) {
                Log.i(TAG, "SocialShareProxy#wechatShareCallback onCancel");
            }
            EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_CANCEL, scene.getType()));
        }

        @Override
        public void onFailure(Exception e) {
            if (DEBUG) {
                Log.i(TAG, "SocialShareProxy#wechatShareCallback onFailure");
            }
            EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_FAILURE, scene.getType(), e));
        }
    };

    /**
     * 分享到微信
     *
     * @param context context
     * @param appId   app id
     * @param scene   场景
     */
    public static void shareToWeChat(final Context context, String appId, final SocialShareScene scene) {
        if (DEBUG) {
            Log.i(TAG, "SocialShareProxy#shareToWeChat");
        }
        SocialShareProxy.scene = scene;
        if (!TextUtils.isEmpty(scene.getShareBitmapPath())) {
            WeChatShareProxy.shareImageToWeChat(context, appId, scene.getShareBitmapPath(), wechatShareCallback);
        } else if (scene.getBitmap() != null) {
            WeChatShareProxy.shareImageToWeChat(context, appId, scene.getBitmap(), wechatShareCallback);
        } else if (!TextUtils.isEmpty(scene.getMusicUrl())) {
            WeChatShareProxy.shareMusicToWeChat(context, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(), scene.getMusicUrl(),
                    scene.getThumbnail(), wechatShareCallback);
        } else {
            WeChatShareProxy.shareToWeChat(context, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(),
                    scene.getThumbnail(), wechatShareCallback);
        }
    }

    /**
     * 分享到微信朋友圈
     *
     * @param context context
     * @param appId   app id
     * @param scene   场景
     */
    public static void shareToWeChatTimeline(Context context, String appId, final SocialShareScene scene) {
        if (DEBUG) {
            Log.i(TAG, "SocialShareProxy#shareToWeChatTimeline");
        }
        SocialShareProxy.scene = scene;
        if (!TextUtils.isEmpty(scene.getShareBitmapPath())) {
            WeChatShareProxy.shareImageToWeChatTimeline(context, appId, scene.getShareBitmapPath(), wechatShareCallback);
        }
        if (scene.getBitmap() != null) {
            WeChatShareProxy.shareImageToWeChatTimeline(context, appId, scene.getBitmap(), wechatShareCallback);
        } else if (!TextUtils.isEmpty(scene.getMusicUrl())) {
            WeChatShareProxy.shareMusicToWeChatTimeline(context, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(), scene.getMusicUrl(),
                    scene.getThumbnail(), wechatShareCallback);
        } else {
            WeChatShareProxy.shareToWeChatTimeline(context, appId, scene.getTitle(), scene.getUrl(),
                    scene.getThumbnail(), wechatShareCallback);
        }
    }

    /**
     * 分享到微博
     *
     * @param context context
     * @param appKey  app key
     * @param scene   场景
     */
    public static void shareToWeibo(final Context context, String appKey, final SocialShareScene scene) {
        if (DEBUG) {
            Log.i(TAG, "SocialShareProxy#shareToWeibo");
        }
        if (scene.getBitmap() != null) {
            WeiboShareProxy.shareImage(context, appKey, scene.getBitmap(), scene.getDesc());
        } else {
            WeiboShareProxy.shareTo(context, appKey, scene.getTitle(), scene.getDesc(), scene.getThumbnail(), scene.getUrl());
        }
    }


    private static IUiListener qShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if (DEBUG)
                Log.i(TAG, "SocialShareProxy#qShareListener onComplete");
            if (scene == null) {
                EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_SUCCESS, 0, ""));
            } else {
                EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_SUCCESS, scene.getType(), scene.getId()));
            }
        }

        @Override
        public void onError(UiError uiError) {
            if (DEBUG)
                Log.i(TAG, "SocialShareProxy#qShareListener onError :" + uiError.errorCode + " "
                        + uiError.errorMessage + " " + uiError.errorDetail);
            EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_FAILURE, scene.getType(), new Exception(uiError.errorCode + " "
                    + uiError.errorMessage + " " + uiError.errorDetail)));
        }

        @Override
        public void onCancel() {
            if (DEBUG)
                Log.i(TAG, "SocialShareProxy#qShareListener onCancel");
            EventBus.getDefault().post(new ShareBusEvent(ShareBusEvent.TYPE_CANCEL, scene.getType()));
        }
    };

    /**
     * 分享到QQ
     *
     * @param context context
     * @param appId   app id
     * @param scene   场景
     */
    public static void shareToQQ(Context context, String appId, SocialShareScene scene) {
        Activity topActivity = UtilsCode.INSTANCE.getTopActivity();
        SocialShareProxy.scene = scene;
        if (!TextUtils.isEmpty(scene.getShareBitmapPath())) {
            QQShareProxy.shareImageToQQ(topActivity, appId, scene.getShareBitmapPath(), qShareListener);
        } else if (scene.getBitmap() != null) {
            QQShareProxy.shareImageToQQ(topActivity, appId, scene.getBitmap(), qShareListener);
        } else if (!TextUtils.isEmpty(scene.getMusicUrl())) {
            QQShareProxy.shareToQQ(topActivity, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(), scene.getMusicUrl(),
                    scene.getThumbnail(), qShareListener);
        } else {
            QQShareProxy.shareToQQ(topActivity, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(),
                    scene.getThumbnail(), qShareListener);
        }
    }


    /**
     * 分享到QQ空间
     *
     * @param context context
     * @param appId   app id
     * @param scene   场景
     */
    public static void shareToQZone(Context context, String appId, SocialShareScene scene) {
        SocialShareProxy.scene = scene;
        Context context1 = UtilsCode.INSTANCE.getTopActivity();
        if (!TextUtils.isEmpty(scene.getShareBitmapPath())) {
            QQShareProxy.shareImageToQZone(context1, appId, scene.getShareBitmapPath(), qShareListener);
        } else if (scene.getBitmap() != null) {
            QQShareProxy.shareImageToQZone(context1, appId, scene.getTitle(), scene.getDesc(), scene.getBitmap(), scene.getUrl(), scene.getThumbnail(), qShareListener);
        } else {
            QQShareProxy.shareToQZone(context1, appId, scene.getTitle(), scene.getDesc(), scene.getUrl(),
                    scene.getThumbnail(), qShareListener);
        }
    }

    /**
     * 分享到QQ，QQ空间结果回调
     *
     * @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public static void shareToQCallback(int requestCode, int resultCode, Intent data) {
        try {
            Tencent.onActivityResultData(requestCode, resultCode, data, qShareListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享到微博结果回调
     *
     * @param intent   intent
     * @param callback callback
     */
    public static void shareToWeiboCallback(Intent intent, WbShareCallback callback) {
        try {
            WeiboShareProxy.getInstance().doResultIntent(intent, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
