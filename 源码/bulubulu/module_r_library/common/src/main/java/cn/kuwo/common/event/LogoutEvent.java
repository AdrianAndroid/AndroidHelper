package cn.kuwo.common.event;

/**
 * @author shihc
 * // cn.kuwo.pp.manager.FriendList
 * // cn.kuwo.pp.manager.UserInfoManager
 * //
 */
public class LogoutEvent {
    private String uid;

    public LogoutEvent(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
