package com.elbbbird.android.socialsdk.sso.weibo;


import com.elbbbird.android.socialsdk.Constants;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 微博授权proxy
 * Created by zhanghailong-ms on 2015/11/16.
 */
public class WeiboSSOProxy {

    private static final String TAG = "WeiboSSOProxy";

    private static SsoHandler ssoHandler;

    public static SsoHandler getSsoHandler() {
        if (ssoHandler == null) {
            initWeibo();
            ssoHandler = new SsoHandler(UtilsCode.INSTANCE.getTopActivity());
        }
        return ssoHandler;
    }

    /**
     * 初始化微博
     */
    public static void initWeibo() {
        AuthInfo authInfo = new AuthInfo(UtilsCode.INSTANCE.getApp(), Constants.Weibo.APP_KEY, Constants.Weibo.REDIRECT_URL, "");
        WbSdk.install(UtilsCode.INSTANCE.getApp(), authInfo);
    }


    public static void login(WbAuthListener listener) {
        getSsoHandler().authorize(listener);
    }

    public static void logout() {
        ssoHandler = null;
    }
}