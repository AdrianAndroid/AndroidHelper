package cn.kuwo.pp.ui.chat;

import com.tencent.qcloud.uikit.business.chat.model.MessageInfo;
import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.networker.exception.ApiException;
import io.reactivex.Observable;

public class C2CChatFragmentPresenter {

    // api/bulu/question/chat， 破冰问题
    public static void requestQuestionList(C2CChatFragment view, String oid) {
        if (App.DEBUG) L.m(C2CChatFragmentPresenter.class, oid);

        Observable<BaseHttpResult<BaseListData<QuestionModel>>> observable = RetrofitClient.getApiService().getChatQuestion(oid, 1).compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<QuestionModel>>() {
                    @Override
                    public void onNext(BaseListData<QuestionModel> o) {
                        if (o.getList().size() > 0) {
                            QuestionModel questionModel = o.getList().get(0);
                            if (App.DEBUG)
                                L.m(C2CChatFragmentPresenter.class, questionModel.getId(), questionModel.getQuestion());
                            view.sendPKMessage(questionModel);
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (App.DEBUG) L.m(C2CChatFragmentPresenter.class, e.getMessage());
                    }
                });
    }

    // 获取题目信息
    public static void queryQuestionDetail(String tid, int position, MessageInfo messageInfo, C2CChatFragment view) {
        if (App.DEBUG) L.m(C2CChatFragmentPresenter.class);
        Observable<BaseHttpResult<BaseListData<QuestionModel>>> observable = RetrofitClient.getApiService()
                .getQuestionDetail(tid).compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<QuestionModel>>() {
                    @Override
                    public void onNext(BaseListData<QuestionModel> o) {
                        if (o.getList().size() > 0) {
                            // 获取这个题目
                            view.onGetQuestionDetailResult(position, messageInfo, o.getList().get(0));
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }
}
