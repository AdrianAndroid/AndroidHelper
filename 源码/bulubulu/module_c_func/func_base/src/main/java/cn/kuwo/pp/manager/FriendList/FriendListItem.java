package cn.kuwo.pp.manager.FriendList;

import cn.kuwo.pp.http.bean.voice.VoiceInfo;

/**
 * 每条消失的bean类
 */
public final class FriendListItem {
    public VoiceInfo user;
    public String time;
    public String lastMessage; // 消息
    public long lastMessageTime;
    public long unReadMsgNum; // 每一个用户需要的未读数
    public int expireType;          // 0 未过期，1 超过24小时， 2 超过48小时
    public boolean systemMessage;   // 是否系统消息项

    @Override
    public String toString() {
        return "FriendListItem{" +
                "user=" + user +
                ", time='" + time + '\'' +
                '}';
    }
}
