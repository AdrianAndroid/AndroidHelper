package cn.kuwo.pp.upload;

import android.text.TextUtils;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.Etag;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.kuwo.common.util.RxUtilKt;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.QiniuToken;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.util.PathUtils;

public class QiniuUploader implements IUploader {
    private final static String BASE_URL = "http://buluaudiocdn.kuwo.cn/";  //资源上传后返回的相对地址的基地址
    private static final String TAG = "QiniuUploader";
    private static UploadManager uploadManager = null;  //使用类变量，是为了全局只需要new一个七牛对象
    private IUploadCallback mCallback;
    private boolean userCancel = false;
    private String mLocalFileName;

    @Override
    public void upload(final String fileName, final IUploadCallback callback) {
        if (uploadManager == null) {
            uploadManager = buildUploadManager();
        }
        mLocalFileName = fileName;
        mCallback = callback;
        if (mCallback != null) {
            mCallback.onUploadStart(this);
        }
        //第一步，使用userid和sessionid在酷我的后台服务，请求token
        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().requestQiniuToken(),
                new CustomObserver<QiniuToken>() {
                    @Override
                    public void onNext(QiniuToken o) {
                        File targetFile = new File(fileName);
                        if (!targetFile.exists()) {
                            if (mCallback != null) {
                                RxUtilKt.asyncRun(() -> {
                                    mCallback.onUploadFailed(QiniuUploader.this, -1, "上传的文件不存在");
                                });
                            }
                            return;
                        }
                        String uniqueKey = createUniqueKey(fileName);
                        if (TextUtils.isEmpty(uniqueKey)) {
                            if (mCallback != null) {
                                RxUtilKt.asyncRun(() -> {
                                    mCallback.onUploadFailed(QiniuUploader.this, -2, "生成上传文件的唯一文件名失败");
                                });
                            }
                            return;
                        }
                        String format = UtilsCode.INSTANCE.getFileExtension(targetFile.getPath());
                        String key = "resource/video/" + uniqueKey + "." + format;
                        uploadManager.put(targetFile, key, o.getKey(), completionHandler, getDefaultOption());
                    }

                    @Override
                    public void _onError(ApiException e) {
                        if (mCallback != null) {
                            RxUtilKt.asyncRun(() -> {
                                mCallback.onUploadFailed(QiniuUploader.this, e.getCode(), e.getMessage());
                            });
                        }
                    }
                });
        //返回token后，
    }

    @Override
    public void cancelUpload() {
        userCancel = true;
    }

    @Override
    public String getLocalFileName() {
        return mLocalFileName;
    }

    private UploadOptions getDefaultOption() {
        return new UploadOptions(null, null, false, progressHandler, cancellationSignal);
    }

    public String createUniqueKey(String filePath) {
        String uniqeKey = null;
        try {
            uniqeKey = Etag.file(filePath);
            uniqeKey = uniqeKey + System.currentTimeMillis();
        } catch (IOException e) {
            //empty
        }
        if (TextUtils.isEmpty(uniqeKey)) {
            uniqeKey = String.valueOf(System.currentTimeMillis());
        }
        return uniqeKey;
    }


    private UpProgressHandler progressHandler = new UpProgressHandler() {
        @Override
        public void progress(String key, double percent) {
            if (mCallback != null) {
                RxUtilKt.asyncRun(() -> {
                    mCallback.onUploadProcess(QiniuUploader.this, (long) percent, 0);
                });
            }
        }
    };

    private UpCancellationSignal cancellationSignal = new UpCancellationSignal() {
        @Override
        public boolean isCancelled() {
            return userCancel;
        }
    };

    private UpCompletionHandler completionHandler = new UpCompletionHandler() {
        @Override
        public void complete(String key, ResponseInfo info, JSONObject response) {
            if (info != null) {
                //这里要判断info的状态码，确认是否真的上传成功。不是这里返回就是成功的
                if (info.statusCode == 200) {
                    if (mCallback != null) {
                        RxUtilKt.asyncRun(() -> {
                            mCallback.onUploadSuccess(QiniuUploader.this, BASE_URL + key);
                        });
                    }
                } else {
                    if (mCallback != null) {
                        RxUtilKt.asyncRun(() -> {
                            mCallback.onUploadFailed(QiniuUploader.this, info.statusCode, info.error);
                        });
                    }
                }
            } else {
                if (mCallback != null) {
                    RxUtilKt.asyncRun(() -> {
                        mCallback.onUploadFailed(QiniuUploader.this, -1, "返回Info数据错误");
                    });
                }
            }
        }
    };

    private synchronized UploadManager buildUploadManager() {
        //初始化七牛SDK
        String dirPath = PathUtils.getExternalAppCachePath();
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            //empty
        }
        KeyGenerator keyGen = (key, file) -> key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone1)// 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        return new UploadManager(config); // 重用uploadManager。一般地，只需要创建一个uploadManager对象
    }
}
