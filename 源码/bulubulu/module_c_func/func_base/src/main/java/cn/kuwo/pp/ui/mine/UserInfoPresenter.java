package cn.kuwo.pp.ui.mine;


import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class UserInfoPresenter {
    private UserInfoFragment mView;

    public UserInfoPresenter(UserInfoFragment view) {
        mView = view;
    }

    public void requestMyTrendList(String uid, Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService().getMyTrendList(uid, pn, rn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            if (o.getList().size() > 0) {
                                mView.onAddTrends(o.getList());
                                return;
                            }
                        }

                        mView.onNoTrends(false);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onNoTrends(true);
                    }
                });
    }

    public void requestUserTrendList(String uid, String oid, Integer pn, Integer rn) {
        Observable<BaseHttpResult<BaseListData<UserTrendBean>>> observable = RetrofitClient.getApiService().getUserTrendList(uid, oid, pn, rn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<UserTrendBean>>() {
                    @Override
                    public void onNext(BaseListData<UserTrendBean> o) {
                        if (o.getList() != null) {
                            if (o.getList().size() > 0) {
                                mView.onAddTrends(o.getList());
                                return;
                            }
                        }

                        mView.onNoTrends(false);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onNoTrends(true);
                    }
                });
    }

    public void deleteUserTrend(String uid, UserTrendBean bean) {
        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().deleteTrend(uid, bean.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        mView.onDeleteTrend(bean);
                        UtilsCode.INSTANCE.showShort("删除成功");
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showShort("删除失败: " + e.getMessage());
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

    public void getMatchValue(String oid) {
        //获取匹配度接口
        Observable<BaseHttpResult<Number>> observable = RetrofitClient.getApiService().friendMatchValue(oid).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Number>() {
                    @Override
                    public void onNext(Number o) {
                        mView.onGetMatchValue((int) o.floatValue());
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }

//    public void getUserStatus(final String oid){
//        //返回的用户状态，目前支持的状态有：
//        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().queryUserOnline(oid).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW)),
//                new CustomObserver<BaseListData<UserOnlineBean>>() {
//                    @Override
//                    public void onNext(BaseListData<UserOnlineBean> o) {
//                        if(o.getList().size() > 0){
//                            mView.onGetUserOnline(o.getList().get(0));
//                        }
//                    }
//
//                    @Override
//                    public void _onError(ApiException e) {
//                    }
//                });
//    }
}
