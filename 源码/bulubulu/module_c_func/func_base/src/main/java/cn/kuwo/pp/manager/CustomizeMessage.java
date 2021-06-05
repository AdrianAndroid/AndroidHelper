package cn.kuwo.pp.manager;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;

import java.util.HashMap;
import java.util.Map;

import static cn.kuwo.common.util.RxUtilKt.runOnUIThread;

public final class CustomizeMessage {
    public static final int MESSAGE_MIN = 100;

    public static final int MESSAGE_HELLO = 101;
    public static final int MESSAGE_GIFT = 102;
    public static final int MESSAGE_PK_INVITE = 103;
    public static final int MESSAGE_PK_PICK = 104;

    public static final int MESSAGE_MAX = 110;

    public enum PKPick{
        PICK_NULL,      //未选择
        MY_PICK_ONE,    //我选择了1 对方未选
        MY_PICK_TWO,    //我选择了2 对方未选
        USER_PICK_ONE,  //对方选择1 我未选
        USER_PICK_TWO,  //对方选择2 我未选

        PICK_SAME_ONE,  //双方选择1
        PICK_SAME_TWO,  //双方选择2
        PICK_DIFF_ONE,  //我选1 对方2
        PICK_DIFF_TWO,  //我选2 对方1
    }

    public static interface ICustomizeMessageResult {
        void ICustomizeMessageResult_success(TIMMessage msg);
        void ICustomizeMessageResult_failed(String desc);
    }

    public static void send(final String uid, final int type, @Nullable final Map<String, String> content, @Nullable final String showInfo, @Nullable final ICustomizeMessageResult result) {
        new Thread() {
            public void run() {
                final JsonObject json = new JsonObject();
                json.addProperty("type", type);

                if (content != null) {
                    for (Map.Entry<String, String> entry : content.entrySet()) {
                        json.addProperty(entry.getKey(), entry.getValue());
                    }
                }

                final MessageInfo message = buildCustomMessage(uid, json.toString(), showInfo);

                if(type == MESSAGE_PK_INVITE){
                    message.setMsgType(MessageInfo.MSG_TYPE_PK_INVITE);
                }
                if(type == MESSAGE_PK_PICK){
                    message.setMsgType(MessageInfo.MSG_TYPE_PK_PICK);
                }

                TIMConversation conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, uid);
                conv.sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(final int code, final String desc) {
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
                                runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(result != null) result.ICustomizeMessageResult_failed(desc);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final TIMMessage timMessage) {
                        message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                        message.setMsgId(timMessage.getMsgId());

                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if(result != null) result.ICustomizeMessageResult_success(timMessage);
                            }
                        });
                    }
                });
            }
        }.start();
    }

    public static int getType(final TIMMessage timMsg) {
        if(timMsg.getElementCount() ==0) {
            return 0;
        }
        TIMElem element = timMsg.getElement(0);
        if (!(element instanceof TIMCustomElem)) {
            return 0;
        }

        TIMCustomElem customElem = (TIMCustomElem) element;
        if (customElem.getData()[0] != "{".getBytes()[0]) {
            return 0;
        }

        int type = 0;
        try {
            JsonObject json = new JsonParser().parse(new String(customElem.getData())).getAsJsonObject();
            type = json.get("type").getAsInt();
            return type;
        } catch(Exception e) { }

        return 0;
    }

    public static Map<String, String> getContent(final TIMMessage timMsg) {
        HashMap<String, String> ret = new HashMap<String, String>();

        if(timMsg.getElementCount() ==0) {
            return ret;
        }
        TIMElem element = timMsg.getElement(0);
        if (!(element instanceof TIMCustomElem)) {
            return ret;
        }

        TIMCustomElem customElem = (TIMCustomElem) element;
        if (customElem.getData()[0] != "{".getBytes()[0]) {
            return ret;
        }

        int type = 0;
        JsonObject json = null;
        try {
            json = new JsonParser().parse(new String(customElem.getData())).getAsJsonObject();
            type = json.get("type").getAsInt();
        } catch(Exception e) {
            return ret;
        }
        if (type <= MESSAGE_MIN || type >= MESSAGE_MAX) {
            return ret;
        }

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            ret.put(entry.getKey(), entry.getValue().getAsString());
        }

        return ret;
    }

    private static MessageInfo buildCustomMessage(String uid, String data, String extra) {
        TIMCustomElem ele = new TIMCustomElem();
        ele.setData(data.getBytes());
        if(!TextUtils.isEmpty(extra)) {
            ele.setExt(extra.getBytes());
        }

        TIMMessage TIMMsg = new TIMMessage();
        TIMMsg.addElement(ele);

        MessageInfo info = new MessageInfo();
        info.setTIMMessage(TIMMsg);
        if(!TextUtils.isEmpty(extra)) info.setExtra(extra);
        info.setSelf(true);
        info.setRead(true);
        info.setMsgTime(System.currentTimeMillis());
        info.setPeer(uid);
        info.setStatus(MessageInfo.MSG_STATUS_SENDING);
        return info;
    }

    public static MessageInfo buildPKInviteMessage(String action, String message) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMCustomElem ele = new TIMCustomElem();
        ele.setData(action.getBytes());
        ele.setExt(message.getBytes());
        TIMMsg.addElement(ele);
        info.setTIMMessage(TIMMsg);
        info.setExtra(message);
        info.setSelf(true);
        info.setMsgTime(System.currentTimeMillis());
        info.setMsgType(MessageInfo.MSG_TYPE_PK_INVITE);
        return info;
    }
}
