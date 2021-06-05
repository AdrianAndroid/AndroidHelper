package cn.kuwo.pp.http.bean.topic;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.kuwo.common.util.KStringUtils;
import cn.kuwo.pp.http.bean.user.UserInfo;

public class TopicItemBean {
    private String id;
    private String name;
    private int questionCnt;
    private int userCnt;
    private List<UserInfo> userList;
    private int subed;
    private boolean isNew;
    private int colorIndex = -1;

    public String getId() {
        return id;
    }

    public String getName() {
        return KStringUtils.INSTANCE.avoidNull(name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getQuestionCnt() {
        return questionCnt;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setQuestionCnt(int questionCnt) {
        this.questionCnt = questionCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    public int getSubed() {
        return subed;
    }

    public void setSubed(int subed) {
        this.subed = subed;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public static TopicItemBean fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TopicItemBean.class);
    }

    @NotNull
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
