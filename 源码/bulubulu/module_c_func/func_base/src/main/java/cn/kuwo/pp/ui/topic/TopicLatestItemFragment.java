package cn.kuwo.pp.ui.topic;

import cn.kuwo.pp.ui.world.HotListFragment;

public class TopicLatestItemFragment extends HotListFragment {
    public static TopicLatestItemFragment newInstance(String topicId){return new TopicLatestItemFragment(topicId);}

    private String mTopicId;

    public TopicLatestItemFragment(String id){
        super();
        mTopicId = id;
    }

    @Override
    protected void queryData(){
        mPresenter.requestTopicLatestTrendList(mTopicId, startPage, pageCount);
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }
}
