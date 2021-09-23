package cn.kuwo.pp.ui.record;

import android.text.TextUtils;

import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.HashMap;
import java.util.Map;

import cn.kuwo.common.base.BasePresenter;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.kwMp3Recorder.BuluRecorder;
import cn.kuwo.kwMp3Recorder.IRecordCallback;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.GuideBean;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.upload.IUploadCallback;
import cn.kuwo.pp.upload.IUploader;
import cn.kuwo.pp.upload.UploadMgr;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RecordMyVoicePresenter implements BasePresenter {

    private final RecordMyVoiceView mView;
    private IRecordCallback mRecordCallback = new IRecordCallback() {
        @Override
        public void onStart(String saveFileName) {
            isUserCancel = false;
            mView.onRecordStart(saveFileName);
        }

        @Override
        public void onEnd(String mp3File, String wavFile, boolean isUserStop) {
            if (isUserCancel) {
                mView.onRecordFailed(-5, "user cancel");
                return;
            }
            mView.onRecordSuccess(mp3File, wavFile, isUserStop);
        }

        @Override
        public void onFailed(int code, String msg) {
            mView.onRecordFailed(code, msg);
        }
    };
    private String mWavFilePath;
    private boolean isUserCancel;

    public RecordMyVoicePresenter(RecordMyVoiceView mView) {
        this.mView = mView;
    }

    public void requestGuideList() {
        Observable<BaseHttpResult<BaseListData<GuideBean>>> observable = RetrofitClient.getApiService().getGuideList().compose(mView.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<GuideBean>>() {
                    @Override
                    public void onNext(BaseListData<GuideBean> o) {
                        mView.onGuideListSuccess(o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        mView.onGuideListFailed(e.getCode(), e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {

    }

    public boolean isRecording() {
        return BuluRecorder.getInstance().isRecording();
    }

    public void startRecording() {
        String baseRootDir = UtilsCode.INSTANCE.getSDCardPathByEnvironment();
        if (TextUtils.isEmpty(baseRootDir)) {
            mView.onRecordFailed(-1, "SD卡不可用");
            return;
        }
        String saveFilePath = baseRootDir + "/bulu/record";
        if (UtilsCode.INSTANCE.createOrExistsDir(saveFilePath) == false) {
            mView.onRecordFailed(-2, "创建目录失败");
            return;
        }
        String mp3FilePath = saveFilePath + "/bulu" + System.currentTimeMillis() + ".mp3";
        BuluRecorder.getInstance().setCallback(mRecordCallback);
        BuluRecorder.getInstance().startRecord(mp3FilePath, 0);
    }

    public void uploadAndAnalysis(String mp3FilePath, String wavFilePath) {
        mWavFilePath = wavFilePath;
        UploadMgr.getInstance().uploadToQiniu(mp3FilePath, uploadCallback);
    }

    private IUploadCallback uploadCallback = new IUploadCallback() {
        @Override
        public void onUploadStart(IUploader uploader) {
            mView.onUploadStart();
        }

        @Override
        public void onUploadProcess(IUploader uploader, long percent, long total) {
            mView.onUploadProcess(percent, total);
        }

        @Override
        public void onUploadSuccess(IUploader uploader, String url) {
            byte[] wavBytes = UtilsCode.INSTANCE.readFile2BytesByChannel(mWavFilePath);
            if (wavBytes == null || wavBytes.length < 45) {
                mView.onUploadFailed(-1, "录音数据读取失败");
                return;
            }
            int pcmLength = wavBytes.length - 44;
            byte[] pcmBytes = new byte[pcmLength];
            System.arraycopy(wavBytes, 44, pcmBytes, 0, pcmLength);
            Map<String, RequestBody> params = new HashMap<>(2);
            params.put("url", RequestBody.create(MediaType.parse("text/plain"), url));
            params.put("file\"; filename=\"" + mWavFilePath, MultipartBody.create(MediaType.parse("application/octet-stream"), pcmBytes));
            RetrofitClient.getInstance().execute(
                    RetrofitClient.getApiService().postVoiceForJudge(params),
                    new CustomObserver<VoiceInfo>() {
                        @Override
                        public void onNext(VoiceInfo o) {
                            mView.onUploadSuccess(o);
                        }

                        @Override
                        public void _onError(ApiException e) {
                            mView.onUploadFailed(e.getCode(), e.getMessage());
                        }
                    }
            );
        }

        @Override
        public void onUploadFailed(IUploader uploader, int code, String msg) {
            mView.onUploadFailed(code, msg);
        }
    };

    public void stopRecording() {
        if (BuluRecorder.getInstance().isRecording()) {
            BuluRecorder.getInstance().stopRecord();
        } else {
            mView.onRecordFailed(-3, "not start recording");
        }
    }

    public void cancelRecording() {
        if (BuluRecorder.getInstance().isRecording()) {
            isUserCancel = true;
            BuluRecorder.getInstance().stopRecord();
        } else {
            mView.onRecordFailed(-3, "not start recording");
        }
    }
}
