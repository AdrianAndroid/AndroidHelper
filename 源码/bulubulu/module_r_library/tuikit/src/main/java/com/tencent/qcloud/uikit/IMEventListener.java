package com.tencent.qcloud.uikit;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.ext.message.TIMMessageReceipt;

import java.util.List;

/**
 * IM事件监听
 */

public abstract class IMEventListener {
    private final static String TAG = IMEventListener.class.getSimpleName();

    /**
     * 被踢下线时回调
     */
    public void onForceOffline() {
        if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "recv onForceOffline");
    }

    /**
     * 用户票据过期
     */
    public void onUserSigExpired() {
        if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "recv onUserSigExpired");
    }

    /**
     * 连接建立
     */
    public void onConnected() {
        if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "recv onConnected");
    }

    /**
     * 连接断开
     *
     * @param code 错误码
     * @param desc 错误描述
     */
    public void onDisconnected(int code, String desc) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onDisconnected, code " + code + "|desc " + desc);
    }

    /**
     * WIFI需要验证
     *
     * @param name wifi名称
     */
    public void onWifiNeedAuth(String name) {
        if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "recv onWifiNeedAuth, wifi name " + name);
    }

    /**
     * 部分会话刷新（包括多终端已读上报同步）
     *
     * @param conversations 需要刷新的会话列表
     */
    public void onRefreshConversation(List<TIMConversation> conversations) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onRefreshConversation, size " + (conversations != null ? conversations.size() : 0));
    }

    /**
     * 收到新消息回调
     *
     * @param msgs 收到的新消息
     */
    public void onNewMessages(List<TIMMessage> msgs) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onNewMessages, size " + (msgs != null ? msgs.size() : 0));
    }

    /**
     * 群Tips事件通知回调
     *
     * @param elem 群tips消息
     */
    void onGroupTipsEvent(TIMGroupTipsElem elem) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onGroupTipsEvent, groupid: " + elem.getGroupId() + "|type: " + elem.getTipsType());
    }

    /**
     * 登陆后，注册离线推送服务端配置信息失败回调接口
     */
    public void onAddOfflineConfigError() {
        if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "recv onAddOfflineConfigError: ");
    }

    /**
     * 登陆后，注册离线推送服务端配置成功回调接口
     *
     * @param settings
     */
    public void onAddOfflineConfigSuccess(TIMOfflinePushSettings settings) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onAddOfflineConfigSuccess" + settings.toString());
    }

    public void onRecvReceipt(List<TIMMessageReceipt> list) {
        if (BuildConfig.DEBUG)
            TUIKit.m(getClass(), TAG, "recv onRecvReceipt, size " + (list != null ? list.size() : 0));
    }
}
