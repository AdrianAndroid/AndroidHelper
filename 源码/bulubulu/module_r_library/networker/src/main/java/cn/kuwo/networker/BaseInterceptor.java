
package cn.kuwo.networker;

import android.text.TextUtils;

import java.io.IOException;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * BaseInterceptor，use set okhttp call header
 *
 * @author shihc
 * @date 2016-06-30
 */
public class BaseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (TextUtils.equals("false", request.header("addBaseParameter"))) {
            return chain.proceed(request);
        }
        //添加公共参数
        HttpUrl oldUrl = request.url();
        HttpUrl.Builder httpBuilder = null; // 需要新创建一个HttpUrl
        Request.Builder requestBuilder = request.newBuilder();


        // 替换BaseUrl
        String base_url_header = request.header(Urls.KEY_BASEURL);
        if (!TextUtils.isEmpty(base_url_header)) {
            //httpBuilder.remov
            requestBuilder.removeHeader(Urls.KEY_BASEURL); // 删除header中的baseUrl的key

//            //替换URL
//            if (TextUtils.isEmpty(Urls.URL_USE)) { // 等更换环境的时候直接替换URL_USE就行了
//                // 当为空的时候才去判断本地记录
//                String localUrl = SP.decodeString(Urls.KEY_URL_USE);
//                if (TextUtils.isEmpty(localUrl)) {
//                    Urls.URL_USE = base_url_header;
//                } else {
//                    Urls.URL_USE = localUrl;
//                }
//            }
            Urls.initURL_USE(base_url_header);


            //"http://bulu-api.kuwo.cn/" //目前没有port
            assert Urls.URL_USE != null;
            HttpUrl parse = HttpUrl.parse(Urls.URL_USE);
            assert parse != null;
            httpBuilder = oldUrl.newBuilder()
                    .scheme(parse.scheme())
                    .host(parse.host())
                    .port(parse.port());
        } else if (BuildConfig.DEBUG) {
            //DEBUG环境
            throw new NullPointerException("" + oldUrl);
        }

        if (httpBuilder == null) {
            httpBuilder = oldUrl.newBuilder();
        }

        // HttpUrl
        if (!TextUtils.isEmpty(App.getInstance().appGetUid()/*UserInfoManager.INSTANCE.getUid()*/)) {
            httpBuilder.addQueryParameter("uid", App.getInstance().appGetUid());
            httpBuilder.addQueryParameter("sid", App.getInstance().appGetToken()/*UserInfoManager.INSTANCE.getToken()*/);
        } else {
            httpBuilder.addQueryParameter("uid", "0");
            httpBuilder.addQueryParameter("sid", "0");
        }
        if (App.getInstance().appIsLogin()) {
            httpBuilder.addQueryParameter("sex", App.getInstance().appGetUserGender()/*UserInfoManager.INSTANCE.getUserGender()*/);
        } else {
            httpBuilder.addQueryParameter("sex", String.valueOf(SP.getInt("gender", 1)));
        }

        requestBuilder.url(httpBuilder.build());
        requestBuilder.addHeader("devId", UtilsCode.INSTANCE.getAndroidID());
        requestBuilder.addHeader("appUid", App.getInstance().appGetUid()/*UserInfoManager.INSTANCE.getUid()*/); //appiuid , IM相关
        requestBuilder.addHeader("token", App.getInstance().appGetToken());
        requestBuilder.addHeader("plat", "android");
        requestBuilder.addHeader("from", "ar");
        requestBuilder.addHeader("src", "kuwo");
        requestBuilder.addHeader("version", String.valueOf(UtilsCode.INSTANCE.getAppVersionCode()));
        requestBuilder.addHeader("api-ver", "1");
        Request request1 = requestBuilder.build();
        return chain.proceed(request1);
    }
}