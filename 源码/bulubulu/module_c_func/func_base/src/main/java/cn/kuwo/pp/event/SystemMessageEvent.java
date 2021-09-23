package cn.kuwo.pp.event;

public class SystemMessageEvent {
    boolean mNotifyNewMessage = false;  //用于消息页面滚动到最底下,仅在用户回复时为false

    public SystemMessageEvent(boolean notifyNew){
        mNotifyNewMessage = notifyNew;
    }

    public boolean isNotifyNewMessage() {
        return mNotifyNewMessage;
    }
}
