
package cn.kuwo.networker;

import android.text.TextUtils;

import java.io.IOException;

import cn.kuwo.networker.crypt.MD5;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * BaseInterceptor，use set okhttp call header
 * Created by Tamic on 2016-06-30.
 */
public class EncryptInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (TextUtils.equals("true", request.header("encrypt"))) {
            HttpUrl oldHttpUrl = request.url();
            HttpUrl newUrl = oldHttpUrl.newBuilder().build();
            String url = newUrl.url().toString();
            String timeValue = String.valueOf(System.currentTimeMillis());
            String encodeParams = AccountUrlUtil.encryptJsonParams(url.substring(url.indexOf("?") + 1));
            String sign = MD5.getMD5Str("bulubulu" + encodeParams + timeValue);
            newUrl = newUrl.newBuilder().encodedQuery(null)
                    .addQueryParameter("sign", sign.toUpperCase())
                    .addQueryParameter("appId", "bulubulu")
                    .addQueryParameter("time", timeValue)
                    .addQueryParameter("data", encodeParams).build();
            Request.Builder requestBuilder = request.newBuilder().url(newUrl);
            return chain.proceed(requestBuilder.build());
        } else {
            return chain.proceed(request);
        }
    }
}