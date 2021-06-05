package cn.kuwo.pp.util;

import android.util.Log;

import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.networker.exception.ApiException;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class UserActionLog {
    public static String questionAnswerConsume = "questionAnswerConsume";
    public static String questionPageRemainConsume = "questionPageRemainConsume";
    public static String dailyOpenedTimes = "dailyOpenedTimes";
    public static String sendMessageTimes = "sendMessageTimes";
    public static String getMessageTimes = "getMessageTimes";

    public static void reportAction(String key, String value){ //发送给服务器
        if(App.DEBUG) L.m(UserActionLog.class, key, value);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart(key, value);

        List<MultipartBody.Part> parts = builder.build().parts();

        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService().reportUserAction(parts);
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Log.d("user_action", "日志上报成功: "+key);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        Log.d("user_action", "日志上报失败: "+key);
                    }
                });

    }
}
