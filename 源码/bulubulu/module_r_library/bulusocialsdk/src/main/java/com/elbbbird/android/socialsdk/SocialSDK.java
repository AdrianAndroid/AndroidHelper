package com.elbbbird.android.socialsdk;

import android.content.Context;
import android.content.Intent;

import com.elbbbird.android.socialsdk.model.SocialInfo;
import com.elbbbird.android.socialsdk.model.SocialShareScene;
import com.elbbbird.android.socialsdk.share.SocialShareProxy;
import com.elbbbird.android.socialsdk.sso.SocialSSOProxy;
import com.sina.weibo.sdk.share.WbShareCallback;

/**
 * 社交SDK
 * Created by zhanghailong-ms on 2015/11/13.
 */
public class SocialSDK {

    private static SocialSDK mSocialSDK = null;

    public static SocialSDK getInstance() {
        synchronized (SocialSDK.class) {
            if (mSocialSDK == null) {
                mSocialSDK = new SocialSDK();
            }
        }
        return mSocialSDK;
    }

    private SocialSDK() {
    }

    /**
     * 初始化微信
     *
     */
    public SocialInfo initWeChat() {
        SocialInfo info = new SocialInfo();
        info.setWechatAppId(Constants.Weixin.APP_ID);
        info.setWeChatAppSecret(Constants.Weixin.APP_SECRET);
        return info;
    }

    /**
     * 初始化QQ
     */
    public SocialInfo initQQ() {
        SocialInfo info = new SocialInfo();
        info.setQqAppId(Constants.QQ.APP_ID);
        return info;
    }

    /**
     * 清楚授权和引用
     *
     * @param context context
     */
    public void revoke(Context context) {
        clearWeibo();
        clearQQ(context);
        clearWeChat(context);
    }

    /**
     * 授权微信
     *
     * @param context context
     */
    public void oauthWeChat(Context context) {
        SocialSSOProxy.loginWeChat(context.getApplicationContext(), initWeChat());
    }

    /**
     * 移除微信授权
     *
     * @param context context
     */
    public void clearWeChat(Context context) {
        SocialSSOProxy.logoutWeChat(context.getApplicationContext());
    }

    /**
     * 微博授权
     *
     */
    public void oauthWeibo() {
        SocialSSOProxy.loginWeibo();
    }

    /**
     * 移除微博授权
     *
     */
    public void clearWeibo() {
        SocialSSOProxy.clearWeibo();
    }

    /**
     * 微博授权回调
     *  @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public void oauthWeiboCallback(int requestCode, int resultCode, Intent data) {
        SocialSSOProxy.loginWeiboCallback(requestCode, resultCode, data);
    }

    /**
     * QQ授权
     *
     * @param context context
     */
    public void oauthQQ(Context context) {
        SocialSSOProxy.loginQQ(context.getApplicationContext(), initQQ());
    }

    /**
     * 移除QQ授权
     *
     * @param context context
     */
    public void clearQQ(Context context) {
    }

    /**
     * QQ授权回调
     *
     * @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public void oauthQQCallback(int requestCode, int resultCode, Intent data) {
        SocialSSOProxy.loginQQCallback(requestCode, resultCode, data);
    }

    /**
     * 分享到微信
     *
     * @param context context
     * @param appId   app id
     * @param scene   社会化分享数据
     */
    public void shareToWeChat(Context context, String appId, SocialShareScene scene) {
        SocialShareProxy.shareToWeChat(context, appId, scene);
    }

    public boolean isWXAppInstalled(Context context) {
        return WeChat.getIWXAPIInstance(context, Constants.Weixin.APP_ID).isWXAppInstalled();
    }

    /**
     * 分享到微信朋友圈
     *
     * @param context context
     * @param scene   社会化分享数据
     */
    public void shareToWeChatTimeline(Context context, String appId, SocialShareScene scene) {
        SocialShareProxy.shareToWeChatTimeline(context, appId, scene);
    }

    /**
     * 分享到微博
     *
     * @param context context
     * @param appKey  appkey
     * @param scene   社会化分享数据
     */
    public void shareToWeibo(Context context, String appKey, SocialShareScene scene) {
        SocialShareProxy.shareToWeibo(context, appKey, scene);
    }

    /**
     * 分享到QQ
     *
     * @param context context
     * @param scene   社会化分享数据
     */
    public void shareToQQ(Context context, String appId, SocialShareScene scene) {
        SocialShareProxy.shareToQQ(context, appId, scene);
    }

    /**
     * 分享到QQ空间
     *
     * @param context context
     * @param scene   社会化分享数据
     */
    public void shareToQZone(Context context, String appId, SocialShareScene scene) {
        SocialShareProxy.shareToQZone(context, appId, scene);
    }

    /**
     * QQ分享回调
     *
     * @param requestCode request
     * @param resultCode  result
     * @param data        data
     */
    public void shareToQCallback(int requestCode, int resultCode, Intent data) {
        SocialShareProxy.shareToQCallback(requestCode, resultCode, data);
    }

    public void shareToWeiboCallback(Intent intent, WbShareCallback callback) {
        SocialShareProxy.shareToWeiboCallback(intent, callback);
    }

}
