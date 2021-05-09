package com.elbbbird.android.socialsdk.otto;

import com.elbbbird.android.socialsdk.model.SocialToken;
import com.elbbbird.android.socialsdk.model.SocialUser;

/**
 * Otto Bus Event
 * Created by zhanghailong-ms on 2015/11/20.
 */
public class SSOBusEvent {

    public static final int PLATFORM_DEFAULT = 0;
    public static final int PLATFORM_WEIBO = 1;
    public static final int PLATFORM_WECHAT = 2;
    public static final int PLATFORM_QQ = 3;

    public static final int TYPE_GET_TOKEN = 0;
    public static final int TYPE_FAILURE = 2;
    public static final int TYPE_CANCEL = 3;

    private int type;
    private int platform;
    private SocialToken token;
    private String errorMessage;

    public SSOBusEvent(int type, int platform) {
        this.type = type;
        this.platform = platform;
    }

    public SSOBusEvent(int type, int platform, SocialToken token) {
        this.type = type;
        this.platform = platform;
        this.token = token;
    }

    public SSOBusEvent(int type, int platform, String errorMessage) {
        this.type = type;
        this.platform = platform;
        this.errorMessage = errorMessage;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SocialToken getToken() {
        return token;
    }

    public void setToken(SocialToken token) {
        this.token = token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
