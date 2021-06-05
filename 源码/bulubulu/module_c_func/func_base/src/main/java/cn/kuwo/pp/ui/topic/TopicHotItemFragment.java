package cn.kuwo.pp.ui.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.kuwo.pp.ui.world.HotListFragment;

public class TopicHotItemFragment extends HotListFragment {
    public static TopicHotItemFragment newInstance(String topicId){return new TopicHotItemFragment(topicId);}

    private String mTopicId;

    public TopicHotItemFragment(String id){
        super();
        mTopicId = id;
    }

    @Override
    protected void queryData(){
        mPresenter.requestTopicHotTrendList(mTopicId, startPage, pageCount);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
