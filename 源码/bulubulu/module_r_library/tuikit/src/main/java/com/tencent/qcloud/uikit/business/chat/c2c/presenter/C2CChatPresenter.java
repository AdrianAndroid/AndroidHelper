package com.tencent.qcloud.uikit.business.chat.c2c.presenter;

import com.tencent.qcloud.uikit.BuildConfig;
import com.tencent.qcloud.uikit.TUIKit;
import com.tencent.qcloud.uikit.common.IUIKitCallBack;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatInfo;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatProvider;
import com.tencent.qcloud.uikit.business.chat.c2c.view.C2CChatPanel;
import com.tencent.qcloud.uikit.common.BackgroundTasks;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.tencent.qcloud.uikit.common.utils.UIUtils;


public class C2CChatPresenter {

    private C2CChatPanel mChatPanel;
    private C2CChatManager mChatManager;

    public C2CChatPresenter(C2CChatPanel chatPanel) {
        mChatPanel = chatPanel;
        mChatManager = C2CChatManager.getInstance();
    }

    // 获取聊天的具体信息
    public C2CChatInfo getC2CChatInfo(String peer) {
        C2CChatInfo chatInfo = mChatManager.getC2CChatInfo(peer); // 获取，ifnot 则创建一个新的
        mChatManager.setCurrentChatInfo(chatInfo);// 保存当前的信息
        return chatInfo;
    }

    public void loadChatMessages(final MessageInfo lastMessage) {
        mChatManager.loadChatMessages(lastMessage, new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (lastMessage == null && data != null)
                    mChatPanel.setDataProvider((C2CChatProvider) data);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                UIUtils.toastLongMessage("IM加载模块错误：" + errCode);
            }
        });
    }

    public void sendC2CMessage(MessageInfo message, boolean reSend) {
        mChatManager.sendC2CMessage(message, reSend, new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 消息发送成功后，需要滚动到最后面
                        mChatPanel.scrollToEnd();
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (BuildConfig.DEBUG) {
                    UIUtils.toastLongMessage("IM通信模块错误：" + errCode + TUIKit.getErrorCodeString(errCode));
                } else {
                    UIUtils.toastLongMessage("IM通信模块错误：" + errCode);
                }
            }
        });
    }

    public void deleteC2CMessage(int position, MessageInfo message) {
        mChatManager.deleteMessage(position, message);
    }

    public void revokeC2CMessage(int position, MessageInfo message) {
        mChatManager.revokeMessage(position, message);
    }

}
