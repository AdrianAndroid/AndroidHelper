
package cn.kuwo.networker;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String serverCache = response.header("Cache-Control");
        // 如果服务端设置相应的缓存策略那么遵从服务端的不做修改
        if (TextUtils.isEmpty(serverCache)) {
            String cacheControl = request.cacheControl().toString();
            if (!TextUtils.isEmpty(cacheControl)) {
                return response.newBuilder()
                        .addHeader("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        }
        return response;
    }
}