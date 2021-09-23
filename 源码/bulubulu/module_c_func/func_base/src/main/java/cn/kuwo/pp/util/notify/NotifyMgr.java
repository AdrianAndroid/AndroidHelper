package cn.kuwo.pp.util.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.tencent.imsdk.TIMMessage;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;

import org.greenrobot.eventbus.EventBus;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.player.R;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.event.UnreadMessageEvent;
import cn.kuwo.pp.manager.FriendList.FriendList;
import cn.kuwo.pp.manager.FriendList.FriendListItem;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.thirdpush.Constants;

//import static cn.kuwo.pp.thirdpush.Constants.SYSTEM_MESSAGE_CHAT_ID;

/**
 * @author shihc
 */
public class NotifyMgr {
    public static final String NEW_MESSAGE_CHANNEL_ID = "cn.kuwo.pp";

    public static final CharSequence NEW_MESSAGE_CHANNEL_NAME = "bulu消息";

    private static NotifyMgr instance;

    private final Context mContext;

    private int mNotifyId = 100001;

    private NotificationManager mNotificationManager;

    public static NotifyMgr getInstance() {
        if (instance == null) {
            synchronized (NotifyMgr.class) {
                if (instance == null) {
                    instance = new NotifyMgr(App.getInstance());
                }
            }
        }
        return instance;
    }

    public NotifyMgr(Context mContext) {
        if (App.DEBUG) L.L(getClass(), "NotifyMgr");
        this.mContext = mContext;
        this.mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        C2CChatManager.getInstance().setNotifyListener(new C2CChatManager.IC2CChatManagerNotifyListener() {
            @Override
            public void IC2CChatManagerNotifyListener_newMsg(TIMMessage message) {
                if (App.DEBUG)
                    L.L(getClass(), "NotifyMgr", "IC2CChatManagerNotifyListener_newMsg");
                showNewMessageNotification(message);
            }
        });
    }

    private Notification buildNewMessageNotification(String title, String content, String extra) {
        if (App.DEBUG) L.L(getClass(), "buildNewMessageNotification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NEW_MESSAGE_CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setContentIntent(PendingIntentBuilder.buildIntent(PendingIntentBuilder.PENDING_INTENT_GO_MAIN, mContext, extra));
        return builder.build();
    }

    public void showNewMessageNotification(TIMMessage message) {
        if (App.DEBUG) L.L(getClass(), "showNewMessageNotification");
        try {
            FriendListItem friendListItem = FriendListManager.getInstance().getUser(message.getConversation().getPeer());
            if (friendListItem == null) {
                return;
            }

            String extContent = String.format("{OpenChatId:%s}", message.getConversation().getPeer());

            Notification notification;
            notification = buildNewMessageNotification(friendListItem.user.getName(), FriendList.getMessageTip(message), extContent);
            if (notification == null) {
                return;
            }

            mNotificationManager.notify(mNotifyId++, notification);
            //EventBus.getDefault().post(new UnreadMessageEvent(UnreadMessageEvent.TYPE_NOTIFY));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showNewSystemMessageNotification(String title, String systemMessage) {
        if (App.DEBUG) L.L(getClass(), "showNewSystemMessageNotification");

        String extContent = String.format("{OpenChatId:%s}", Constants.SYSTEM_MESSAGE_CHAT_ID);
        if (title == null) {
            title = "布鲁小助手";
        }
        if (title.isEmpty()) {
            title = "布鲁小助手";
        }


        Notification notification = buildNewMessageNotification(title, systemMessage, extContent);
        if (notification == null) {
            return;
        }

        try {
            mNotificationManager.notify(mNotifyId++, notification);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
