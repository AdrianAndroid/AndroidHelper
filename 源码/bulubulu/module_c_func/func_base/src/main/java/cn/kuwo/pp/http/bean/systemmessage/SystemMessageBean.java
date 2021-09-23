package cn.kuwo.pp.http.bean.systemmessage;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.comment.CommentItem;
import cn.kuwo.pp.http.bean.user.UserInfo;

public class SystemMessageBean implements MultiItemEntity {
    public enum SystemMessageCategory {
        CONTENT_ACTION,
        SYSTEM_PUSH_TEXT,
        SYSTEM_PUSH_IMAGE,
        RELATION_SHIP,
        USER_SEND_TEXT,
        USER_SEND_IMAGE
    }
    public enum SystemMessageType{
        TYPE_NULL,
        COMMENT_REPLY,  //1 评论被回复 暂时用不着
        COMMENT_PRISE,  //2 评论被点赞
        TREND_COMMENT,  //3 动态被评论
        TREND_PRISE,    //4 动态被喜欢
        PK_VOTE,        //5 动态投票被参与
        USER_FAVOR,     //6 用户被喜欢
        USER_SEND,      //7 用户回复
        SYSTEM_REPLY,   //8 官方回复
        SYSTEM_NOTIFY,   //9 官方通知

        TREND_OFFLINE,   //动态被屏蔽
        COMMENT_OFFLINE  //评论被屏蔽
    }

    private int type;   //1 评论被回复 2 评论被点赞 3 动态被评论 4 动态被喜欢 5 投票被参与 6 用户被喜欢 7 用户回复 8 官方回复  9 官方通知
    private int sourceType;
    private UserInfo from;
    private UserInfo to;
    private UserTrendBean source;
    private SystemMessageSource messageSource;
    private UserMessageSource userMessageSource;
    private CommentItem comment;
    private long timemil;

    public int getItemType(){
        if(getType() == SystemMessageType.TREND_COMMENT.ordinal() || getType() == SystemMessageType.TREND_PRISE.ordinal() ||
                getType() == SystemMessageType.COMMENT_PRISE.ordinal() || getType() == SystemMessageType.PK_VOTE.ordinal() ||
                getType() == SystemMessageType.TREND_OFFLINE.ordinal() || getType() == SystemMessageType.COMMENT_OFFLINE.ordinal()){
            return SystemMessageCategory.CONTENT_ACTION.ordinal();
        }else if(getType() == SystemMessageType.USER_FAVOR.ordinal()){
            return SystemMessageCategory.RELATION_SHIP.ordinal();
        }else if(getType() == SystemMessageType.SYSTEM_REPLY.ordinal() || getType() == SystemMessageType.SYSTEM_NOTIFY.ordinal()){
            if(getMessageSource() != null){
                if(getMessageSource().getType() == 1){
                    return SystemMessageCategory.SYSTEM_PUSH_IMAGE.ordinal();
                }else{
                    return SystemMessageCategory.SYSTEM_PUSH_TEXT.ordinal();
                }
            }
        }else if(getType() == SystemMessageType.USER_SEND.ordinal() && getUserMessageSource() != null){
            if(getUserMessageSource().getType() == 2){
                return SystemMessageCategory.USER_SEND_IMAGE.ordinal();
            }else{
                return SystemMessageCategory.USER_SEND_TEXT.ordinal();
            }
        }

        return SystemMessageCategory.CONTENT_ACTION.ordinal();
    }

    public boolean isOffline(){
        return getType() == SystemMessageType.TREND_OFFLINE.ordinal() || getType() == SystemMessageType.COMMENT_OFFLINE.ordinal();
    }

    public int getType() {
        return type;
    }

    public int getSourceType() {
        return sourceType;
    }

    public UserInfo getFrom() {
        return from;
    }

    public UserInfo getTo() {
        return to;
    }

    public UserTrendBean getSource() {
        return source;
    }

    public SystemMessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(SystemMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public CommentItem getComment() {
        return comment;
    }

    public long getTimemil() {
        return timemil;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setFrom(UserInfo from) {
        this.from = from;
    }

    public void setTo(UserInfo to) {
        this.to = to;
    }

    public void setSource(UserTrendBean source) {
        this.source = source;
    }

    public void setComment(CommentItem comment) {
        this.comment = comment;
    }

    public void setTimemil(long timemil) {
        this.timemil = timemil;
    }

    public UserMessageSource getUserMessageSource() {
        return userMessageSource;
    }

    public void setUserMessageSource(UserMessageSource userMessageSource) {
        this.userMessageSource = userMessageSource;
    }


    @Override
    public String toString() {
        return "SystemMessageBean{" +
                "type=" + type +
                ", sourceType=" + sourceType +
                ", from=" + from +
                ", to=" + to +
                ", source=" + source +
                ", messageSource=" + messageSource +
                ", userMessageSource=" + userMessageSource +
                ", comment=" + comment +
                ", timemil=" + timemil +
                '}';
    }
}
