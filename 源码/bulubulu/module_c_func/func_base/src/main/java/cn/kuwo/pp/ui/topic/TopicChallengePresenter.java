package cn.kuwo.pp.ui.topic;


import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import io.reactivex.Observable;

public class TopicChallengePresenter {
    //api/bulu/topic/questions
    public static void requestQuestionList(ChallengeFragment fragment, String topicId, final int pn) {
        Observable<BaseHttpResult<BaseListData<QuestionModel>>> observable = RetrofitClient.getApiService()
                .getTopicQuestions(UserInfoManager.INSTANCE.getUid(), topicId, pn, 30)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));

        RetrofitClient.getInstance().execute(observable
                , new CustomObserver<BaseListData<QuestionModel>>() {
                    @Override
                    public void onNext(BaseListData<QuestionModel> o) {
                        fragment.addQuestions(o.getList(), pn + 1); // 成功之后才➕1
                    }

                    @Override
                    public void _onError(ApiException e) {
                        fragment.addQuestions(new ArrayList<>(), pn);
                    }
                });


//        Observable<BaseHttpResult<BaseListData<QuestionModel>>> observable = RetrofitClient.getApiService()
//                .getQuestionList(UserInfoManager.INSTANCE.getUid(), 10, topicId)
//                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
//
//        RetrofitClient.getInstance().execute(observable,
//                new CustomObserver<BaseListData<QuestionModel>>() {
//                    @Override
//                    public void onNext(BaseListData<QuestionModel> o) {
//                        fragment.addQuestions(o.getList());
//                    }
//
//                    @Override
//                    public void _onError(ApiException e) {
//                        fragment.addQuestions(new ArrayList<>()); // 也返回数据，否则无法停止
//                    }
//                }
//        );
    }

    public static void answerQuestion(ChallengeFragment fragment, QuestionModel model, int position, String answer, String topicid) {
        Observable<BaseHttpResult<QuestionAnswerModel>> observable = RetrofitClient.getApiService()
                .getTopicAnswer(UserInfoManager.INSTANCE.getUid(),model.getId(), answer, topicid)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<QuestionAnswerModel>() {
                    @Override
                    public void onNext(QuestionAnswerModel o) {
                        o.setUserAnswer(answer);
                        fragment.updateAnswer(model, position, o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showShort("答题失败: " + e.getMessage());
                    }
                });

//        Observable<BaseHttpResult<QuestionAnswerModel>> observable = RetrofitClient.getApiService().getQuestionAnswer(model.getId(), answer).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
//        RetrofitClient.getInstance().execute(observable,
//                new CustomObserver<QuestionAnswerModel>() {
//                    @Override
//                    public void onNext(QuestionAnswerModel o) {
//                        o.setUserAnswer(answer);
//                        fragment.updateAnswer(model, position, o);
//                    }
//
//                    @Override
//                    public void _onError(ApiException e) {
//                        UtilsCode.INSTANCE.showShort("答题失败: " + e.getMessage());
//                    }
//                });
    }

    public static void likeQuestion(ChallengeFragment fragment, boolean like, QuestionModel model) {
        if (like) {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().likeTrend(UserInfoManager.INSTANCE.getUid(), model.getSourceType(), model.getId()).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            fragment.onLikeResult(model, true, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            fragment.onLikeResult(model, true, false);
                        }
                    });
        } else {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().unlikeTrend(UserInfoManager.INSTANCE.getUid(), model.getSourceType(), model.getId()).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            fragment.onLikeResult(model, false, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            fragment.onLikeResult(model, false, true);
                        }
                    });
        }
    }

    public static void subscribeTopic(TopicDetailFragment fragment, boolean subscribe, String topicId) {
        if (subscribe) {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().subscribeTopic(UserInfoManager.INSTANCE.getUid(), topicId).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            fragment.onSubscribeResult(true, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            fragment.onSubscribeResult(true, false);
                        }
                    });
        } else {
            Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().cancelSubscribeTopic(UserInfoManager.INSTANCE.getUid(), topicId).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            RetrofitClient.getInstance().execute(observable,
                    new CustomObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            fragment.onSubscribeResult(false, true);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            fragment.onSubscribeResult(false, true);
                        }
                    });
        }
    }

    public static void requestTopicDetail(TopicDetailFragment fragment, String topicId) {
        Observable<BaseHttpResult<TopicItemBean>> observable = RetrofitClient.getApiService().getTopicDetail(UserInfoManager.INSTANCE.getUid(), topicId).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<TopicItemBean>() {
                    @Override
                    public void onNext(TopicItemBean o) {
                        fragment.onGetTopicDetail(o);
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }
}
