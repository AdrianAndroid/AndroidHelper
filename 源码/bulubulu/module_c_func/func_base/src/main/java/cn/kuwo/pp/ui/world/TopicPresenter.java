package cn.kuwo.pp.ui.world;

import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class TopicPresenter {
    private final TopicListItemFragment mView;

    public TopicPresenter(TopicListItemFragment view) {
        mView = view;
    }

    // 热门接口 让
    public void requestHotTopicList(Integer pn) {
        Observable<BaseHttpResult<BaseListData<TopicItemBean>>> observable =
                RetrofitClient.getApiService()
                        .getHotTopicList(UserInfoManager.INSTANCE.getUid(), pn, 10)
                        .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<TopicItemBean>>() {
                    @Override
                    public void onNext(BaseListData<TopicItemBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTopics(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTopicFail();
                    }
                });
    }

    // 订阅接口
    public void requestSubTopicList(Integer pn) {
        Observable<BaseHttpResult<BaseListData<TopicItemBean>>> observable =
                RetrofitClient.getApiService()
                        .getSubTopicList(UserInfoManager.INSTANCE.getUid(), pn, 10)
                        .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<TopicItemBean>>() {
                    @Override
                    public void onNext(BaseListData<TopicItemBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTopics(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTopicFail();
                    }
                });
    }

    public void searchTopic(String name) {
        Observable<BaseHttpResult<BaseListData<TopicItemBean>>> observable = RetrofitClient.getApiService().searchTopic(UserInfoManager.INSTANCE.getUid(), name, 1, 10).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<TopicItemBean>>() {
                    @Override
                    public void onNext(BaseListData<TopicItemBean> o) {
                        mView.onSearchTopicComplete(o.getList());
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }
}
