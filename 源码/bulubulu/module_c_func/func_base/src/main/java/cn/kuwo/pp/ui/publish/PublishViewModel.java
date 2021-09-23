package cn.kuwo.pp.ui.publish;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.util.ArrayUtils;
import cn.kuwo.common.viewmodel.BaseViewModel;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import kotlin.jvm.functions.Function1;

public class PublishViewModel extends BaseViewModel {
    MutableLiveData<Boolean> topicItemChanged = new MutableLiveData<>(); // true 增加 false 删除

    List<TopicItemBean> mTopicList = new ArrayList<TopicItemBean>(); // 存放

    @Override
    protected void onCleared() {
         super.onCleared();
        // 清除掉所有
        mTopicList.clear();
    }

    public void addTopicList(TopicItemBean bean) {
        if (bean != null) {
            mTopicList.add(0, bean); // 放在第一位
            topicItemChanged.setValue(true);
        }
    }

    // 根据title删除
    public void removeTopicItem(int position) {
        if (position <= mTopicList.size()) {
            mTopicList.remove(position);
            topicItemChanged.setValue(false);
        }
    }

    public String getTopicIds() {
        return ArrayUtils.INSTANCE.join(",", mTopicList, new Function1<TopicItemBean, CharSequence>() {
            @Override
            public CharSequence invoke(TopicItemBean bean) {
                return bean.getId();
            }
        });
    }

}
