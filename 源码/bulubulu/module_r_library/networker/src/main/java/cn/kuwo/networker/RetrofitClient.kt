package cn.kuwo.networker

import android.text.TextUtils
import cn.kuwo.common.app.App
import me.jessyan.progressmanager.ProgressManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {


    companion object {
        // 双重校验锁
        private val instance: RetrofitClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitClient() }

        fun <T> api(service: Class<T>): T {
            return instance.api(service)
        }
    }


    private val mOkHttpClient: OkHttpClient =
        ProgressManager.getInstance().with(OkHttpClient.Builder())
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(BaseInterceptor())
            .addInterceptor(EncryptInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .cache(Cache(File(App.getInstance().cacheDir, ""), 104_857_600)) //104_857_600 100M
            .build()


    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(mOkHttpClient)
        .build()


    private val mapApi: HashMap<String, Any?> = hashMapOf()

    // 确保每个返回的都是正确的
    fun <T> api(service: Class<T>): T {
        // 先看看map中是否有缓存
        var get = mapApi[service.simpleName] as? T
        if (get != null) {
            get = mRetrofit.create(service)//instance.create(service)
            // 不允许为空
            mapApi[service.simpleName] = get
        }
        return get ?: throw NullPointerException("创造的接口不允许为空！")
    }


    interface ApiTest {
        fun test()
    }

    private fun test() {
        val api = RetrofitClient.instance.api(ApiTest::class.java) // 可
        api.test()
    }


    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val serverCache = response.header("Cache-Control")
            // 如果服务端设置相应的缓存策略那么遵从服务端的不做修改
            // 如果服务端设置相应的缓存策略那么遵从服务端的不做修改
            if (TextUtils.isEmpty(serverCache)) {
                val cacheControl = request.cacheControl().toString()
                if (!TextUtils.isEmpty(cacheControl)) {
                    return response.newBuilder()
                        .addHeader("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build()
                }
            }
            return response
        }
    }

    class BaseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
//        if (TextUtils.equals("false", request.header("addBaseParameter"))) {
//            return chain.proceed(request);
//        }
//        //添加公共参数
//        HttpUrl httpUrl = request.url();
//        HttpUrl.Builder builder = httpUrl.newBuilder();
//        if (!TextUtils.isEmpty(UserInfoManager.INSTANCE.getUid())){
//            builder.addQueryParameter("uid", UserInfoManager.INSTANCE.getUid());
//            builder.addQueryParameter("sid", UserInfoManager.INSTANCE.getToken());
//        }else{
//            builder.addQueryParameter("uid", "0");
//            builder.addQueryParameter("sid", "0");
//        }
//        if (UserInfoManager.INSTANCE.isLogin()){
//            builder.addQueryParameter("sex", UserInfoManager.INSTANCE.getUserGender());
//        } else {
//            builder.addQueryParameter("sex", String.valueOf(MMKV.defaultMMKV().getInt("gender", 1)));
//        }
//        Request.Builder requestBuilder = request.newBuilder().url(builder.build());
//        requestBuilder.addHeader("devId",DeviceUtils.getAndroidID());
//        requestBuilder.addHeader("appUid", PPApp.getAppUid());
//        requestBuilder.addHeader("token", UserInfoManager.INSTANCE.getToken());
//        requestBuilder.addHeader("plat","android");
//        requestBuilder.addHeader("from","ar");
//        requestBuilder.addHeader("src","kuwo");
//        requestBuilder.addHeader("version",String.valueOf(AppUtils.getAppVersionCode()));
//        requestBuilder.addHeader("api-ver","1");
//        Request request1 = requestBuilder.build();
//        LogUtils.d(request1.headers().toString());
            return chain.proceed(request)
        }

    }

    class EncryptInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            /*
                    Request request = chain.request();
        if (TextUtils.equals("true", request.header("encrypt"))) {
            HttpUrl oldHttpUrl = request.url();
            HttpUrl newUrl = oldHttpUrl.newBuilder().build();
            String url = newUrl.url().toString();
            LogUtils.d("--> " + url);
            String timeValue = String.valueOf(System.currentTimeMillis());
            String encodeParams = AccountUrlUtil.encryptJsonParams(url.substring(url.indexOf("?") + 1));
            LogUtils.d(encodeParams);
            String sign = MD5.getMD5Str("bulubulu"+encodeParams+timeValue);
            newUrl = newUrl.newBuilder().encodedQuery(null)
                    .addQueryParameter("sign", sign.toUpperCase())
                    .addQueryParameter("appId", "bulubulu")
                    .addQueryParameter("time", timeValue)
                    .addQueryParameter("data", encodeParams).build();
            Request.Builder requestBuilder = request.newBuilder().url(newUrl);
            return chain.proceed(requestBuilder.build());
        }else{
            return chain.proceed(request);
        }
             */
            return chain.proceed(request)
        }

    }


}