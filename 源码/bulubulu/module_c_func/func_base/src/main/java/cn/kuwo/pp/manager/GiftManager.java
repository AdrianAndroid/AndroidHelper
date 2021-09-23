package cn.kuwo.pp.manager;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import io.reactivex.Observable;

public final class GiftManager {

    private static GiftManager instance = new GiftManager();

    public static GiftManager getInstance() {
        return instance;
    }

    public static interface IGiftRequestResult {
        void IGiftRequestResult_success(int arg);
        void IGiftRequestResult_failed(String msg);
    }

    public void sendGift(String uid, int giftId, @Nullable final IGiftRequestResult result) {
        RetrofitClient.getInstance().execute(
                RetrofitClient.getApiService().pointsUse(UserInfoManager.INSTANCE.getUid(), uid, giftId)
                , new CustomObserver<Object>() {
                    public void onNext(Object info) {
                        result.IGiftRequestResult_success(0);
                    }

                    public void  _onError(ApiException e) {
                        result.IGiftRequestResult_failed(e.getMessage());
                    }
                });
    }

    public void increasePoints(String oid, int giftId, @Nullable final IGiftRequestResult result) {
        RetrofitClient.getInstance().execute(
                RetrofitClient.getApiService().pointsUse(UserInfoManager.INSTANCE.getUid(), oid, giftId)
                , new CustomObserver<Object>() {
                    public void onNext(Object info) {
                        result.IGiftRequestResult_success(0);
                    }

                    public void  _onError(ApiException e) {
                        result.IGiftRequestResult_failed(e.getMessage());
                    }
                });
    }

    public void getMyPoints(final BaseFragment view, final IGiftRequestResult result) {
        Observable<BaseHttpResult<VoiceInfo>> observable = RetrofitClient.getApiService().getUserDetail(UserInfoManager.INSTANCE.getUid());
        if(view != null){
            observable = observable.compose(view.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }
        RetrofitClient.getInstance().execute(
                observable
                , new CustomObserver<VoiceInfo>() {
                    public void onNext(VoiceInfo info) {
                        if(info!=null&&info.getUrl()!=null){
                            result.IGiftRequestResult_success(info.getIntegral());
                        }
                    }

                    public void  _onError(ApiException e) {
                        result.IGiftRequestResult_failed(e.getMessage());
                    }
                });
    }


}
