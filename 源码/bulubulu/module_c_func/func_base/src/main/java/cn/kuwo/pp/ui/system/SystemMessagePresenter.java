package cn.kuwo.pp.ui.system;

import android.net.Uri;
import android.util.Log;

import com.tencent.qcloud.uikit.common.UIKitConstants;
import com.tencent.qcloud.uikit.common.utils.FileUtil;
import com.tencent.qcloud.uikit.common.utils.ImageUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.SystemMessageManager;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

// 系统的网络请求
public class SystemMessagePresenter {
    private final SystemMessageFragment mView;

    public SystemMessagePresenter(SystemMessageFragment view){
        mView = view;
    }

    public String selectImagePath(Uri uri, boolean appPhoto){
        if (!appPhoto) {
            ImageUtil.CopyImageInfo copInfo = ImageUtil.copyImage(uri, UIKitConstants.IMAGE_DOWNLOAD_DIR);
            if(copInfo != null){
                return copInfo.getPath();
            }
        }

        return FileUtil.getPathFromUri(uri);
    }

    // 回复消息-发送到后台
    public void userReply(int type, String msg){ // yong
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(type == 1){
            builder = builder.addFormDataPart("text", msg);
        }else if(type == 2){
            File file = new File(msg);
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder = builder.addFormDataPart("file", file.getName(), imageBody);
        } else if(type == 3){
            builder = builder.addFormDataPart("url", msg);
        }
        List<MultipartBody.Part> parts = builder.build().parts();

        Observable<BaseHttpResult<Object>> observable = RetrofitClient.getApiService()
                .userReplyMessage(type, parts)
                .compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if(App.DEBUG) Log.d("user_reply", "反馈上报成功: " + msg);
                        SystemMessageManager.getInstance().getLatestMessage(false); // 获取最新消息
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if(App.DEBUG) Log.d("user_reply", "反馈上报失败: "+ msg);
                    }
                });
    }
}
