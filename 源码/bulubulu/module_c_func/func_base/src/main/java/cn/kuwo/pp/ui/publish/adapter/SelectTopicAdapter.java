package cn.kuwo.pp.ui.publish.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;

public class SelectTopicAdapter extends BaseQuickAdapter<TopicItemBean, BaseViewHolder> {

    public SelectTopicAdapter(List dataList){
        super(R.layout.select_topic_item, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicItemBean item){
        helper.setText(R.id.tv_topic_title, "#"+item.getName()+"#");
        helper.setVisible(R.id.iv_new_flag, item.isNew());
    }
}
