package com.elbbbird.android.socialsdk.share.weibo;

import android.content.Context;
import android.graphics.Bitmap;

import com.elbbbird.android.socialsdk.Constants;
import com.elbbbird.android.socialsdk.SocialUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.LogUtil;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * Created by zhanghailong-ms on 2015/11/24.
 */
public class WeiboShareProxy {

    private static WbShareHandler shareHandler;

    public static WbShareHandler getInstance(Context context, String appKey) {
        LogUtil.enableLog();
        if (null == shareHandler) {
            initWeibo();
            shareHandler = new WbShareHandler(UtilsCode.INSTANCE.getTopActivity());
            shareHandler.registerApp();
        }

        return shareHandler;
    }

    /**
     * 初始化微博
     */
    public static void initWeibo() {
        AuthInfo authInfo = new AuthInfo(UtilsCode.INSTANCE.getApp(), Constants.Weibo.APP_KEY, Constants.Weibo.REDIRECT_URL, "");
        WbSdk.install(UtilsCode.INSTANCE.getApp(), authInfo);
    }

    public static WbShareHandler getInstance() {
        return shareHandler;
    }

    public static void shareTo(final Context context, final String appKey, final String title, final String desc,
                               final String imageUrl, final String shareUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeiboMultiMessage msg = new WeiboMultiMessage();
                TextObject text = new TextObject();
                text.text = desc;
                msg.textObject = text;
                WebpageObject web = new WebpageObject();
                web.description = desc;
                web.actionUrl = shareUrl;
                web.identify = imageUrl;
                web.title = title;
                msg.mediaObject = web;
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(SocialUtils.getHtmlBitmap(imageUrl));
                msg.imageObject = imageObject;
                getInstance(context, appKey).shareMessage(msg, false);
            }
        }).start();

    }

    public static void shareImage(final Context context, final String appKey, final Bitmap bitmap, final String desc) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeiboMultiMessage msg = new WeiboMultiMessage();
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(bitmap);
                msg.imageObject = imageObject;
                TextObject text = new TextObject();
                text.text = desc;
                msg.textObject = text;
                getInstance(context, appKey).shareMessage(msg, false);
            }
        }).start();

    }

}
