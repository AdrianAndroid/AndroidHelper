package cn.kuwo.pp.event;

import cn.kuwo.pp.http.bean.UserTrendBean;

public class UserPublishSuccessEvent {
    private UserTrendBean mBean;

    public  UserPublishSuccessEvent(UserTrendBean bean){
        mBean = bean;
    }

    public UserTrendBean getBean(){
        return mBean;
    }
}
