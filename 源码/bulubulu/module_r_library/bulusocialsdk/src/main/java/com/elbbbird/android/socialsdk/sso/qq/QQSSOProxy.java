package com.elbbbird.android.socialsdk.sso.qq;

import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * QQ授权proxy
 * <p>
 * Created by zhanghailong-ms on 2015/11/17.
 */
public class QQSSOProxy {

    public static void login(Context context, String appId, String scope, IUiListener listener) {
        Tencent tencent = Tencent.createInstance(appId, context);
        if (tencent != null) {
            tencent.login(UtilsCode.INSTANCE.getTopActivity(), scope, listener);
        }
    }

}
