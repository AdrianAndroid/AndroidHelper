package cn.kuwo.pp.ui.publish;


import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class SelectTopicPresenter {
    private SelectTopicFragment mView;

    public SelectTopicPresenter(SelectTopicFragment view){
        mView = view;
    }

    public void createTopic(String name){
        Observable<BaseHttpResult<TopicItemBean>> observable = RetrofitClient.getApiService().addTopic(UserInfoManager.INSTANCE.getUid(), name).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<TopicItemBean>() {
                    @Override
                    public void onNext(TopicItemBean o) {
                        mView.onSelectedTopic(o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showLong("创建话题失败: " + e.getMessage());
                    }
                });
    }

    public void searchTopic(String name){
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

    public void requestHotTopicList(Integer pn, Integer rn){
        Observable<BaseHttpResult<BaseListData<TopicItemBean>>> observable = RetrofitClient.getApiService().getHotTopicList(UserInfoManager.INSTANCE.getUid(), pn, rn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<TopicItemBean>>() {
                    @Override
                    public void onNext(BaseListData<TopicItemBean> o) {
                        if(o.getList() != null){
                            mView.onGetHotTopicComplete(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }
}
