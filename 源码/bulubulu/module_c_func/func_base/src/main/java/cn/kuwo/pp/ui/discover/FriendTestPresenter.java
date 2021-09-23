package cn.kuwo.pp.ui.discover;


import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.MatchCountBean;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class FriendTestPresenter {
    private final FriendTestFragment mView;

    public FriendTestPresenter(FriendTestFragment view) {
        mView = view;
    }

    public void requestQuestionList() {
        Observable<BaseHttpResult<BaseListData<QuestionModel>>> observable = RetrofitClient.getApiService()
                .getQuestionList(UserInfoManager.INSTANCE.getUid(), 10)
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<QuestionModel>>() {
                    @Override
                    public void onNext(BaseListData<QuestionModel> o) {
                        mView.addQuestions(o.getList());
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }

    // 回答问题
    public void answerQuestion(QuestionModel model, int position) {
        final String answer = model.getAnswerModel().getUserAnswer();
        Observable<BaseHttpResult<QuestionAnswerModel>> observable = RetrofitClient.getApiService().getQuestionAnswer(model.getId(), answer).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<QuestionAnswerModel>() {
                    @Override
                    public void onNext(QuestionAnswerModel o) {
                        o.setUserAnswer(answer);
                        mView.updateAnswer(model, position, o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showShort("答题失败: " + e.getMessage());
                    }
                });
    }

    public void passQuestion(QuestionModel model) {
        Observable<BaseHttpResult<Object>> observable
                = RetrofitClient.getApiService()
                .passQuestion(UserInfoManager.INSTANCE.getUid(), model.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }

    public void likeQuestion(boolean like, QuestionModel model) {
        if (like) {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().likeTrend(UserInfoManager.INSTANCE.getUid(), model.getSourceType(), model.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeResult(model, true, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            mView.onLikeResult(model, true, false);
                        }
                    });
        } else {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().unlikeTrend(UserInfoManager.INSTANCE.getUid(), model.getSourceType(), model.getId()).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            mView.onLikeResult(model, false, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            mView.onLikeResult(model, false, true);
                        }
                    });
        }
    }

    public void getComment(QuestionModel model, int pn) {
        if (model == null) return;
        Observable<BaseHttpResult<CommentBean>> observable = RetrofitClient.getApiService().getComments(Integer.parseInt(model.getSourceType()), model.getId(), 2, pn).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<CommentBean>() {
                    @Override
                    public void onNext(CommentBean o) {
                        mView.onGetComment(model, o, pn);
                    }

                    @Override
                    public void _onError(ApiException e) {
                    }
                });
    }

    // 获取匹配的数字
    public void getMatchCount() {
        Observable<BaseHttpResult<MatchCountBean>> observable = RetrofitClient.getApiService()
                .getMatchLimit().compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable, new CustomObserver<MatchCountBean>() {
            @Override
            public void onNext(MatchCountBean o) {
                mView.updateMatchCount(o.getNum(), o.getCnt());
            }

            @Override
            public void _onError(ApiException e) {

            }
        });
    }

    public void syncMatchCount(int num, int cnt) {
        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().syncMatchCount(num, cnt).compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }
}
