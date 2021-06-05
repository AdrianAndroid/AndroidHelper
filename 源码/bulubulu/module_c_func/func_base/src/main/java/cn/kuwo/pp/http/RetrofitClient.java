package cn.kuwo.pp.http;

import android.util.Log;


import java.io.File;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.networker.BaseInterceptor;
import cn.kuwo.networker.CacheInterceptor;
import cn.kuwo.networker.EncryptInterceptor;
import cn.kuwo.networker.SchedulerTransformer;
import cn.kuwo.networker.Urls;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.networker.exception.ApiException;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by shihc on 2016/10/31.
 */

public class RetrofitClient {
    private Retrofit mRetrofit;
    private ApiService mApiService;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    //获取单例
    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public RetrofitClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Urls.URL_RELEASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return getInstance().mApiService;
    }

    //设置OkHttpClient参数，拦截器、超时等
    private OkHttpClient getHttpClient() {
        //缓存容量
        long SIZE_OF_CACHE = 100 * 1024 * 1024; // 100 MiB
        File cacheDir = App.getInstance().getCacheDir();
        if (App.DEBUG) Log.d("cacheDir", cacheDir.getAbsolutePath());
        Cache cache = new Cache(new File(cacheDir, "responses"), SIZE_OF_CACHE);
        //设置日志拦截器
        OkHttpClient.Builder builder = ProgressManager.getInstance().with(new OkHttpClient.Builder());// 构建 OkHttpClient 时,将 OkHttpClient.Builder() 传入 with() 方法,进行初始化配置
        builder.addInterceptor(getHttpLoggingInterceptor());
        builder.addNetworkInterceptor(new CacheInterceptor());
        builder.addInterceptor(new BaseInterceptor());
        builder.addInterceptor(new EncryptInterceptor());
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(40, TimeUnit.SECONDS);
        builder.writeTimeout(40, TimeUnit.SECONDS);
        builder.cache(cache);
        builder.retryOnConnectionFailure(true);

        return builder.build();
    }

    //请求头中的参数，公共也放到这里
    private Map<String, String> getHeaderParams() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    //日志拦截器，打印请求日志
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(App.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

    public void execute(Observable observable, Observer observer) {
        toSubscribe(observable, observer, true);
    }

    public void executeWithoutBaseBean(Observable observable, Observer observer) {
        toSubscribe(observable, observer, false);
    }

    /**
     * 发起请求并处理返回数据
     *
     * @param observable    ApiService 定义
     * @param observer      请求回调
     * @param parseBaseBean 是否解析 BaseHttpResult
     * @param <T>
     */
    private <T> void toSubscribe(Observable<T> observable, final Observer<T> observer, boolean parseBaseBean) {
        if (observer != null && !(observer instanceof CustomObserver)) {
            throw new RuntimeException("observer must use CustomObserver");
        }
        if (parseBaseBean) {
            observable = observable.map(new ServerResponseFunc());
        }
        observable.onTerminateDetach().compose(new SchedulerTransformer<>()).subscribe(observer);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static class ServerResponseFunc<T> implements Function<BaseHttpResult<T>, T> {

        @Override
        public T apply(BaseHttpResult<T> httpResult) throws Exception {
            if (httpResult.isSuccess()) {
                T data = httpResult.getData();
                if (data == null) {
                    throw new ApiException(null, httpResult.getStatus(), "返回数据为空");
                }
                return data;
            }
            if (!UtilsCode.INSTANCE.isConnected()) {
                throw new ConnectException("网络不可用");
            } else {
                throw new ApiException(null, httpResult.getStatus(), httpResult.getMsg());
            }
        }
    }

}

