package com.elbbbird.android.socialsdk.sso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.elbbbird.android.socialsdk.BuildConfig;
import com.elbbbird.android.socialsdk.R;
import com.elbbbird.android.socialsdk.model.SocialInfo;
import com.elbbbird.android.socialsdk.model.SocialToken;
import com.elbbbird.android.socialsdk.otto.SSOBusEvent;
import com.elbbbird.android.socialsdk.sso.qq.QQSSOProxy;
import com.elbbbird.android.socialsdk.sso.wechat.IWXCallback;
import com.elbbbird.android.socialsdk.sso.wechat.WeChatSSOProxy;
import com.elbbbird.android.socialsdk.sso.weibo.WeiboSSOProxy;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 社交授权proxy
 * Created by zhanghailong-ms on 2015/11/16.
 */
public class SocialSSOProxy {

    private static final String TAG = "SocialSSOProxy";

    private static boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 登录微博
     */
    public static void loginWeibo() {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.loginWeibo");
        WeiboSSOProxy.login(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                if (DEBUG) Log.i(TAG, "SocialSSOProxy.loginWeibo#login onComplete");
                Bundle bundle = oauth2AccessToken.getBundle();
                final String token = bundle.getString("access_token");
                final String expiresIn = bundle.getString("expires_in", "0");
                final String code = bundle.getString("code");
                final String openId = bundle.getString("uid");
                final SocialToken socialToken = new SocialToken(openId, token, "", Long.valueOf(expiresIn));
                if (DEBUG)
                    Log.i(TAG, "social token info: code=" + code + ", token=" + socialToken.toString());
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_GET_TOKEN, SSOBusEvent.PLATFORM_WEIBO, socialToken));
            }

            @Override
            public void cancel() {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeibo#login onCancel");
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_CANCEL, SSOBusEvent.PLATFORM_WEIBO));
            }

            @Override
            public void onFailure(WbConnectErrorMessage errorMessage) {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeibo#login onWeiboException, e=" + errorMessage.getErrorMessage());
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_FAILURE, SSOBusEvent.PLATFORM_WEIBO, errorMessage.getErrorMessage()));
            }
        });
    }

    /**
     * 登录微博
     */
    public static void clearWeibo() {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.logoutWeibo");
        WeiboSSOProxy.logout();
    }

    /**
     * 微博登录状态回调接口
     *
     * @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public static void loginWeiboCallback(int requestCode, int resultCode, Intent data) {
        if (WeiboSSOProxy.getSsoHandler() != null) {
            try {
                WeiboSSOProxy.getSsoHandler().authorizeCallBack(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录微信
     *
     * @param context context
     * @param info    社交信息
     */
    public static void loginWeChat(final Context context, final SocialInfo info) {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.loginWeChat");
        WeChatSSOProxy.login(context, new IWXCallback() {
            @Override
            public void onGetCodeSuccess(String code) {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeChat onGetCodeSuccess, code=" + code);
                WeChatSSOProxy.getToken(code, info.getUrlForWeChatToken());
            }

            @Override
            public void onGetTokenSuccess(SocialToken token) {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeChat onGetCodeSuccess, token=" + token.toString());
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_GET_TOKEN, SSOBusEvent.PLATFORM_WECHAT, token));
            }

            @Override
            public void onFailure(Exception e) {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeChat onFailure");
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_FAILURE, SSOBusEvent.PLATFORM_WECHAT, e.getMessage()));
            }

            @Override
            public void onCancel() {
                if (DEBUG)
                    Log.i(TAG, "SocialSSOProxy.loginWeChat onCancel");
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_CANCEL, SSOBusEvent.PLATFORM_WECHAT));
            }
        }, info);
    }


    /**
     * 登出微信
     *
     * @param context context
     */
    public static void logoutWeChat(Context context) {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.logoutWeChat");
    }

    private static Context context;
    private static IUiListener qqLoginListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if (DEBUG)
                Log.i(TAG, "SocialSSOProxy.loginQQ onComplete, info=" + o.toString());
            try {
                JSONObject info = new JSONObject(o.toString());
                final String openId = info.getString("openid");
                final String token = info.getString("access_token");
                final long expiresIn = info.getLong("expires_in");
                final SocialToken socialToken = new SocialToken(openId, token, "", expiresIn);
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_GET_TOKEN, SSOBusEvent.PLATFORM_QQ, socialToken));
            } catch (Exception e) {
                EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_FAILURE, SSOBusEvent.PLATFORM_QQ, e.getMessage()));
            }
        }

        @Override
        public void onError(UiError uiError) {
            if (DEBUG)
                Log.i(TAG, "SocialSSOProxy.loginQQ onError");
            EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_FAILURE, SSOBusEvent.PLATFORM_QQ,
                    uiError.errorCode + "#" + uiError.errorMessage + "#" + uiError.errorDetail));
        }

        @Override
        public void onCancel() {
            if (DEBUG)
                Log.i(TAG, "SocialSSOProxy.loginQQ onCancel");
            EventBus.getDefault().post(new SSOBusEvent(SSOBusEvent.TYPE_CANCEL, SSOBusEvent.PLATFORM_QQ));
        }
    };

    /**
     * 登录QQ
     *
     * @param context context
     * @param info    社交信息
     */
    public static void loginQQ(Context context, SocialInfo info) {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.loginQQ");
        SocialSSOProxy.context = context;
        QQSSOProxy.login(context, info.getQqAppId(), info.getQqScope(), qqLoginListener);
    }

    /**
     * QQ登录状态回调
     *
     * @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public static void loginQQCallback(int requestCode, int resultCode, Intent data) {
        try {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqLoginListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login(Context context, SocialInfo info) {
        if (DEBUG)
            Log.i(TAG, "SocialSSOProxy.login");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        intent.putExtras(bundle);
        intent.setClass(context, SocialOauthActivity.class);
        context.startActivity(intent);
        if (UtilsCode.INSTANCE.getTopActivity() != null) {
            UtilsCode.INSTANCE.getTopActivity().overridePendingTransition(R.anim.es_snack_in, 0);
        }
    }
}
