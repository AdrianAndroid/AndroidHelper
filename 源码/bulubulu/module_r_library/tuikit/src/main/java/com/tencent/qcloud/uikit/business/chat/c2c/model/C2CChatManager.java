package com.tencent.qcloud.uikit.business.chat.c2c.model;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMMessageOfflinePushSettings;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMMessageExt;
import com.tencent.imsdk.ext.message.TIMMessageLocator;
import com.tencent.qcloud.uikit.BuildConfig;
import com.tencent.qcloud.uikit.TUIKit;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfoUtil;
import com.tencent.qcloud.uikit.common.IUIKitCallBack;
import com.tencent.qcloud.uikit.common.utils.UIUtils;
import com.tencent.qcloud.uikit.operation.UIKitMessageRevokedManager;
import com.tencent.qcloud.uikit.operation.message.UIKitRequest;
import com.tencent.qcloud.uikit.operation.message.UIKitRequestDispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class C2CChatManager implements TIMMessageListener, UIKitMessageRevokedManager.MessageRevokeHandler {

    public static interface IC2CChatManagerDataListener {
        void IC2CChatManagerDataListener_loadMsg(List<TIMMessage> messages);

        void IC2CChatManagerDataListener_newMsg(TIMMessage message);

        void IC2CChatManagerDataListener_newMsgAll(TIMMessage message);
    }

    public static interface IC2CChatManagerNotifyListener {
        void IC2CChatManagerNotifyListener_newMsg(TIMMessage message);
    }

    private static final String TAG = "tuikit/" + C2CChatManager.class.getSimpleName();
    private static C2CChatManager sInstance = new C2CChatManager();
    private static final int MSG_PAGE_COUNT = 10;
    private static final int REVOKE_TIME_OUT = 6223;
    private C2CChatProvider mCurrentProvider;
    private TIMConversation mCurrentConversation;
    private TIMConversationExt mCurrentConversationExt;
    private C2CChatInfo mCurrentChatInfo;
    private Map<String, C2CChatInfo> mC2CChatMap = new HashMap<>();
    private boolean mIsMore;
    private boolean mIsLoading;
    private IC2CChatManagerDataListener mDataListener;
    private IC2CChatManagerNotifyListener mNotifyListener;

    public static C2CChatManager getInstance() {
        return sInstance;
    }

    private C2CChatManager() {

    }

    public void init() {
        destroyC2CChat();
        TIMManager.getInstance().addMessageListener(sInstance);
        UIKitMessageRevokedManager.getInstance().addHandler(this);
    }

    public void setCurrentChatInfo(C2CChatInfo info) {
        if (info == null) {
            return;
        }
        mCurrentChatInfo = info;
        mCurrentConversation = TIMManager.getInstance().getConversation(info.getType(), info.getPeer());//获取会话信息
        mCurrentConversationExt = new TIMConversationExt(mCurrentConversation);
        mCurrentProvider = new C2CChatProvider();
        mIsMore = true;
        mIsLoading = false;
    }

    public void setDataListener(final IC2CChatManagerDataListener l) {
        mDataListener = l;
    }

    public void setNotifyListener(final IC2CChatManagerNotifyListener l) {
        mNotifyListener = l;
    }

    public boolean addChatInfo(C2CChatInfo chatInfo) {
        return mC2CChatMap.put(chatInfo.getPeer(), chatInfo) == null;
    }

    public C2CChatInfo getC2CChatInfo(String peer) {
        C2CChatInfo chatInfo = mC2CChatMap.get(peer); // 从map里面获取
        if (chatInfo == null) {// 如果获取不到
            chatInfo = new C2CChatInfo(); // 创建一个新的
            chatInfo.setPeer(peer); // 保存
            chatInfo.setChatName(peer); // 保存
            mC2CChatMap.put(peer, chatInfo);// 保存
        }
        return chatInfo;
    }

    public synchronized void loadChatMessages(MessageInfo lastMessage, final IUIKitCallBack callBack) {
        if (mIsLoading)
            return;
        mIsLoading = true; // 正在加载中
        if (!mIsMore) { // 加载更多
            mCurrentProvider.addMessageInfo(null);
            callBack.onSuccess(null);
            mIsLoading = false;
            return;
        }

        TIMMessage lastTIMMsg = null;
        if (lastMessage == null) {
            mCurrentProvider.clear();
        } else {
            lastTIMMsg = lastMessage.getTIMMessage();
        }
        final int unread = (int) mCurrentConversationExt.getUnreadMessageNum(); // 获取未读的消息
        // 获取未读的消息
        mCurrentConversationExt.getMessage(Math.max(unread, MSG_PAGE_COUNT), lastTIMMsg
                , new TIMValueCallBack<List<TIMMessage>>() {
                    @Override
                    public void onError(int code, String desc) {
                        // 获取消息失败
                        mIsLoading = false;
                        callBack.onError(TAG, code, desc);
                        if (BuildConfig.DEBUG)
                            TUIKit.m(getClass(), TAG, "loadChatMessages() getMessage failed, code = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> timMessages) {
                        mIsLoading = false;
                        if (mCurrentProvider == null)
                            return;

                        if (timMessages.size() < MSG_PAGE_COUNT)
                            mIsMore = false;
                        ArrayList<TIMMessage> messages = new ArrayList<>(timMessages);
                        Collections.reverse(messages); // 反转消息【应该是用于显示的】
                        if (mDataListener != null) {
                            mDataListener.IC2CChatManagerDataListener_loadMsg(messages); // 回调消息
                        }

                        List<MessageInfo> msgInfos = MessageInfoUtil.TIMMessages2MessageInfos(messages, false);
                        mCurrentProvider.addMessageInfos(msgInfos, true);
                        for (int i = 0; i < msgInfos.size(); i++) {
                            final MessageInfo info = msgInfos.get(i);
                            if (info.getStatus() == MessageInfo.MSG_STATUS_SENDING) {
                                sendC2CMessage(info, true, null);
                            }
                            if (info.getMsgType() != MessageInfo.MSG_TYPE_AUDIO && !info.isRead()) {
                                mCurrentConversation.setReadMessage(info.getTIMMessage(), new TIMCallBack() {
                                    @Override
                                    public void onError(int code, String desc) {
                                        if (BuildConfig.DEBUG)
                                            TUIKit.m(getClass(), TAG, "loadChatMessages() setReadMessage failed, code = " + code + ", desc = " + desc);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        info.setRead(true);
                                        if(BuildConfig.DEBUG) TUIKit.m(getClass(),TAG, "loadChatMessages() setReadMessage success");
                                    }
                                });
                            }
                        }
                        callBack.onSuccess(mCurrentProvider);
                    }
                });
    }


    public synchronized void sendC2CMessage(final MessageInfo message, boolean reSend, final IUIKitCallBack callBack) {
        if (mCurrentChatInfo == null || mCurrentConversation == null)
            return;
        message.setSelf(true);
        message.setRead(true);
        message.setPeer(mCurrentConversation.getPeer());

        //添加离线消息信息
        String extContent = String.format("{OpenChatId:%s}", message.getFromUser());
        TIMMessageOfflinePushSettings settings = new TIMMessageOfflinePushSettings();
        settings.setExt(extContent.getBytes());
        message.getTIMMessage().setOfflinePushSettings(settings);

        //消息先展示，通过状态来确认发送是否成功
        if (message.getMsgType() < MessageInfo.MSG_TYPE_TIPS) {
            if (mCurrentProvider == null)
                return;
            message.setStatus(MessageInfo.MSG_STATUS_SENDING);
            if (!reSend) {
                mCurrentProvider.addMessageInfo(message);
            }
            mCurrentProvider.updateMessageInfo(message);

            if (callBack != null)
                callBack.onSuccess(mCurrentProvider);
        }
        new Thread() {
            public void run() {

                final long current = System.currentTimeMillis();

                mCurrentConversation.sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(final int code, final String desc) {
                        if(BuildConfig.DEBUG) TUIKit.m(getClass(),TAG, "sendC2CMessage fail:" + code + "=" + desc);
                        if (mCurrentProvider == null)
                            return;
                        message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
                        mCurrentProvider.updateMessageInfo(message);
                        if (callBack != null)
                            callBack.onError(TAG, code, desc);

                    }

                    @Override
                    public void onSuccess(final TIMMessage timMessage) {
                        if (BuildConfig.DEBUG)
                            TUIKit.m(getClass(), TAG, "sendC2CMessage onSuccess time=" + (System.currentTimeMillis() - current));
                        if (mCurrentProvider == null)
                            return;
                        message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                        message.setMsgId(timMessage.getMsgId());
                        mCurrentProvider.updateMessageInfo(message);
                        if (callBack != null)
                            callBack.onSuccess(mCurrentProvider);
                    }
                });
            }
        }.start();

    }

    public void deleteMessage(int position, MessageInfo messageInfo) {
        TIMMessageExt ext = new TIMMessageExt(messageInfo.getTIMMessage());
        if (ext.remove()) {
            if (mCurrentProvider == null)
                return;
            mCurrentProvider.remove(position);
        }
    }


    public void revokeMessage(final int position, final MessageInfo messageInfo) {
        mCurrentConversationExt.revokeMessage(messageInfo.getTIMMessage(), new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                if (code == REVOKE_TIME_OUT) {
                    UIUtils.toastLongMessage("消息发送已超过2分钟");
                } else {
                    UIUtils.toastLongMessage("撤销失败:" + code + "=" + desc);
                }
            }

            @Override
            public void onSuccess() {
                if (mCurrentProvider == null)
                    return;
                mCurrentProvider.updateMessageRevoked(messageInfo.getMsgId());
                //C2C撤销没有回调通知，这里需要发个消息通知session模块刷新
                UIKitRequest request = new UIKitRequest();
                request.setModel(UIKitRequestDispatcher.MODEL_SESSION);
                request.setAction(UIKitRequestDispatcher.SESSION_REFRESH);
                request.setRequest(messageInfo.getMsgId());
                UIKitRequestDispatcher.getInstance().dispatchRequest(request);
            }
        });
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> msgs) {
        for (TIMMessage msg : msgs) {
            TIMConversation conversation = msg.getConversation();
            TIMConversationType type = conversation.getType();
            if (type == TIMConversationType.C2C) {
                TIMElem ele = msg.getElement(0);
                TIMElemType eleType = ele.getType();
                // 用户资料修改通知，不需要在聊天界面展示，可以通过 TIMUserConfig 中的 setFriendshipListener 处理
                if (eleType == TIMElemType.ProfileTips) {
                    if (BuildConfig.DEBUG)
                        TUIKit.m(getClass(), TAG, "onNewMessages() eleType is ProfileTips, ignore");
                    return false;
                }
                // 关系链变更通知，不需要在聊天界面展示，可以通过 TIMUserConfig 中的 setFriendshipListener 处理
                if (eleType == TIMElemType.SNSTips) {
                    if (BuildConfig.DEBUG)
                        TUIKit.m(getClass(), TAG, "onNewMessages() eleType is SNSTips, ignore");
                    return false;
                }

                if (BuildConfig.DEBUG) TUIKit.m(getClass(), TAG, "onNewMessages() msg = " + msg);
                onReceiveMessage(conversation, msg);

            }
        }
        return false;
    }

    private void onReceiveMessage(final TIMConversation conversation, final TIMMessage msg) {
        if (conversation == null || conversation.getPeer() == null || mCurrentChatInfo == null) {
            if (mDataListener != null) {
                mDataListener.IC2CChatManagerDataListener_newMsgAll(msg);
            }

            if (mNotifyListener != null) {
                boolean notify = true;
                if (mCurrentConversation != null && mCurrentConversation.getPeer().equalsIgnoreCase(conversation.getPeer())) {
                    notify = false;
                }

                if (notify) {
                    mNotifyListener.IC2CChatManagerNotifyListener_newMsg(msg);
                }
            }
            return;
        }
        //图片，视频类的消息先把快照下载了再通知用户
        /*
        现在用占位图，直接通知用户了
        if (MessageInfoUtil.checkMessage(msg, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "checkMessage failed, code: " + code + "|desc: " + desc);
            }

            @Override
            public void onSuccess() {
                executeMessage(conversation, msg);
            }
        })) {
            return;
        }*/
        executeMessage(conversation, msg);
    }

    private void executeMessage(TIMConversation conversation, TIMMessage msg) {
        final MessageInfo msgInfo = MessageInfoUtil.TIMMessage2MessageInfo(msg, false);
        if (msgInfo != null && mCurrentConversation != null && mCurrentConversation.getPeer().equals(conversation.getPeer())) {
            if (mDataListener != null) {
                mDataListener.IC2CChatManagerDataListener_newMsg(msg);
            }

            mCurrentProvider.addMessageInfo(msgInfo);

            if (msgInfo.getMsgType() != MessageInfo.MSG_TYPE_AUDIO) {
                mCurrentConversation.setReadMessage(msg, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        msgInfo.setRead(true);
                        if (BuildConfig.DEBUG)
                            TUIKit.m(getClass(), TAG, "executeMessage() setReadMessage failed, code = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        if(BuildConfig.DEBUG) TUIKit.m(getClass(),TAG, "executeMessage() setReadMessage success");
                    }
                });
            } else {
                msgInfo.setRead(false);
            }
        }
    }


    @Override
    public void handleInvoke(TIMMessageLocator locator) {
        if (mCurrentChatInfo != null && locator.getConversationId().equals(mCurrentChatInfo.getPeer())) {
            if(BuildConfig.DEBUG) TUIKit.m(getClass(),TAG, "handleInvoke() locator = " + locator);
            mCurrentProvider.updateMessageRevoked(locator);
        }
    }

    public void destroyC2CChat() {
        mCurrentChatInfo = null;
        mCurrentConversation = null;
        mCurrentProvider = null;
        mCurrentConversationExt = null;
        mIsMore = true;
    }

    public void sendUserReadMessage(final MessageInfo msgInfo) {
        if (msgInfo.isRead()) {
            return;
        }

        if (mCurrentConversation != null) {
            mCurrentConversation.setReadMessage(msgInfo.getTIMMessage(), new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    msgInfo.setRead(true);
                    if (BuildConfig.DEBUG)
                        TUIKit.m(getClass(), TAG, "executeMessage() setReadMessage failed, code = " + code + ", desc = " + desc);
                }

                @Override
                public void onSuccess() {
                    if (BuildConfig.DEBUG)
                        TUIKit.m(getClass(), TAG, "executeMessage() setReadMessage success");
                }
            });
        }
    }
}
