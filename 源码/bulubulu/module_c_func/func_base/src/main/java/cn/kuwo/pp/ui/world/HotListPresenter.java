package cn.kuwo.pp.ui.world;

import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class HotListPresenter {

    private final HotListFragment mView;

    public HotListPresenter(HotListFragment view) {
        mView = view;
    }

    public void requestHotTrendList(Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService()
                .getHotTrendList(pn, rn)
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTrends(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTrendFail();
                    }
                });
    }

    public void requestFavorTrendList(Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService().getFavorTrendList(pn, rn, UserInfoManager.INSTANCE.getUid()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTrends(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTrendFail();
                    }
                });
    }

    public void requestLatestTrendList(Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService().getLatestTrendList(pn, rn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTrends(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTrendFail();
                    }
                });
    }

    public void answerQuestion(String questionId, String answer) {
        Observable<BaseHttpResult<QuestionAnswerModel>> observable = RetrofitClient.getApiService().getQuestionAnswer(questionId, answer).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<QuestionAnswerModel>() {
                    @Override
                    public void onNext(QuestionAnswerModel o) {
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }

    public void likeTrend(boolean like, UserTrendBean userTrendBean) {
        if (like) {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().likeTrend(UserInfoManager.INSTANCE.getUid(), "1", userTrendBean.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeResult(userTrendBean, true, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            mView.onLikeResult(userTrendBean, true, false);
                        }
                    });
        } else {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().unlikeTrend(UserInfoManager.INSTANCE.getUid(), "1", userTrendBean.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeResult(userTrendBean, false, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            mView.onLikeResult(userTrendBean, false, true);
                        }
                    });
        }
    }

    // 请求最新数据
    public void requestTopicLatestTrendList(String topicId, Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService()
                .getTopicLatestTrendList(UserInfoManager.INSTANCE.getUid(), topicId, pn, rn)
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTrends(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTrendFail();
                    }
                });
    }

    public void requestTopicHotTrendList(String topicId, Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService().getTopicHotTrendList(UserInfoManager.INSTANCE.getUid(), topicId, pn, rn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            mView.onAddTrends(o.getList());
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGetTrendFail();
                    }
                });
    }
}
