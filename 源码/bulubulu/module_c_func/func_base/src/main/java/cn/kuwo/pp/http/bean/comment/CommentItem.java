package cn.kuwo.pp.http.bean.comment;

import java.util.List;

import cn.kuwo.pp.http.bean.user.UserInfo;

public class CommentItem {
    private int     beReplied;
    private long    commentId;
    private String  content;
    private String  dateTime;
    private int     liked;
    private int     likedCount;
    private int     repliedCount;
    private List<CommentItem> replieds;
    private int     status;
    private long     times;
    private int     uid;
    private UserInfo userInfo;

    public int getBeReplied() {
        return beReplied;
    }

    public long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getLiked() {
        return liked;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public int getRepliedCount() {
        return repliedCount;
    }

    public List<CommentItem> getReplieds() {
        return replieds;
    }

    public int getStatus() {
        return status;
    }

    public long getTimes() {
        return times;
    }

    public int getUid() {
        return uid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setBeReplied(int beReplied) {
        this.beReplied = beReplied;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public void setRepliedCount(int repliedCount) {
        this.repliedCount = repliedCount;
    }

    public void setReplieds(List<CommentItem> replieds) {
        this.replieds = replieds;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
