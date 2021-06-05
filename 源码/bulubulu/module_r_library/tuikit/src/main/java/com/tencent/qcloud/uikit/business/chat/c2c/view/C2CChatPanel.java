package com.tencent.qcloud.uikit.business.chat.c2c.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.tencent.imsdk.TIMTextElem;
import com.tencent.qcloud.uikit.api.chat.IChatPanel;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatInfo;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;
import com.tencent.qcloud.uikit.business.chat.c2c.presenter.C2CChatPresenter;
import com.tencent.qcloud.uikit.common.component.action.PopActionClickListener;
import com.tencent.qcloud.uikit.common.component.action.PopMenuAction;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.tencent.qcloud.uikit.business.chat.view.ChatPanel;
import com.tencent.qcloud.uikit.business.chat.view.widget.ChatAdapter;
import com.tencent.qcloud.uikit.common.component.audio.UIKitAudioArmMachine;
import com.tencent.qcloud.uikit.common.component.titlebar.PageTitleBar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 显示聊天界面的
 */
public class C2CChatPanel extends ChatPanel implements IChatPanel {

    private C2CChatPresenter mPresenter; // 请求网络的
    private C2CChatInfo mBaseInfo; // 保存信息的
    private SendMessageListener mSendMessageListener; //往外回调的

    public C2CChatPanel(Context context) {
        super(context);
    }

    public C2CChatPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public C2CChatPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBaseChatId(String chatId) {
        mPresenter = new C2CChatPresenter(this);
        mBaseInfo = mPresenter.getC2CChatInfo(chatId); // 获取会话的信息，没有就创建
        if (mBaseInfo == null)
            return;
        String chatTitle = mBaseInfo.getChatName();
        mTitleBar.setTitle(chatTitle, PageTitleBar.POSITION.CENTER);
        mPresenter.loadChatMessages(null);
    }

    @Override
    public void exitChat() {
        UIKitAudioArmMachine.getInstance().stopRecord();
        C2CChatManager.getInstance().destroyC2CChat();
    }

    @Override
    public void sendMessage(MessageInfo messageInfo) {
        mPresenter.sendC2CMessage(messageInfo, false); //请求网络，发送出去

        if (mSendMessageListener != null) {
            mSendMessageListener.onSendMessage(messageInfo);
        }
    }

    @Override
    public void loadMessages() {
        mPresenter.loadChatMessages(mAdapter.getItemCount() > 0 ? mAdapter.getItem(1) : null);
    }

    @Override
    public void initDefault() {
        super.initDefault();
        mTitleBar.getLeftGroup().setVisibility(View.VISIBLE);
        mTitleBar.getRightGroup().setVisibility(GONE);
        ChatAdapter adapter = new ChatAdapter();
        setChatAdapter(adapter);
        initDefaultEvent();
    }

    @Override
    protected void initPopActions(final MessageInfo msg) {
        if (msg == null) {
            return;
        }
        List<PopMenuAction> actions = new ArrayList<>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName("删除");
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mPresenter.deleteC2CMessage(position, (MessageInfo) data);
            }
        });
        actions.add(action);
        if (msg.isSelf()) {
            action = new PopMenuAction();
            action.setActionName("撤销");
            action.setActionClickListener(new PopActionClickListener() {
                @Override
                public void onActionClick(int position, Object data) {
                    mPresenter.revokeC2CMessage(position, (MessageInfo) data);
                }
            });
            actions.add(action);
            if (msg.getStatus() == MessageInfo.MSG_STATUS_SEND_FAIL) {
                action = new PopMenuAction();
                action.setActionName("重发");
                action.setActionClickListener(new PopActionClickListener() {
                    @Override
                    public void onActionClick(int position, Object data) {
                        mPresenter.sendC2CMessage(msg, true);
                    }
                });
                actions.add(action);
            }
        }
        if (msg.getMsgType() == MessageInfo.MSG_TYPE_TEXT) {
            action = new PopMenuAction();
            action.setActionName("复制");
            action.setActionClickListener(new PopActionClickListener() {
                @Override
                public void onActionClick(int position, Object data) {
                    copy(msg);
                }
            });
            actions.add(action);
        }
        setMessagePopActions(actions, false);
    }

    private void copy(MessageInfo msg) {
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        myClip = ClipData.newPlainText("text", ((TIMTextElem) msg.getTIMMessage().getElement(0)).getText());
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getContext(), "已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        exitChat();
    }

    public void setSendMessageListener(SendMessageListener listener) {
        mSendMessageListener = listener;
    }
}
