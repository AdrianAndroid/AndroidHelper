package com.elbbbird.android.socialsdk;

/**
 * Created by shihc on 16/9/9.
 */
public class Constants {
    public static String MI_PUSH_APP_ID = "2882303761517845749";
    public static String MI_PUSH_APP_KEY = "5591784540749";

    public interface Weibo {
        String APP_KEY = "1144521861";
        String APP_SECRET = "cc2dea715b6e1e85d6a5627f1cbbce2e";
        // 应用的回调页
        String REDIRECT_URL = "http://boom-login.kuwo.cn/US_NEW/oauth/callback/sina";
        // 应用申请的高级权限
        String SCOPE ="";

    }

    public interface QQ {
        String APP_ID = "1109057278";
        String APP_KEY = "49d1sF3UMqrQYocA";
        String SCOPE = "all";
    }

    public interface Weixin {
        String APP_ID = "wxdf50f9d346985d91";
        String APP_SECRET = "d4579ff6cc1f548bbe294c5dacd9f813";
        String WX_GET_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    }
}
