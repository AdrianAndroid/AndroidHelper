package cn.kuwo.pp.event;

import cn.kuwo.pp.http.bean.topic.TopicItemBean;

public class TopicClickEvent {
    private TopicItemBean mTopic;

    public TopicClickEvent(TopicItemBean bean){
        mTopic = bean;
    }

    public TopicItemBean getTopic() {
        return mTopic;
    }
}
