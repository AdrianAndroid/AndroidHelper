package com.example.interceptor.interceptor;

import com.example.interceptor.Chain;
import com.example.interceptor.Interceptor;

public class CallServerInterceptor implements Interceptor {

    @Override
    public String intercept(Chain chain) {
        System.out.println("开始执行请求服务器拦截器");
        System.out.println("===发起请求===");
        System.out.println("结束执行请求服务器拦截器");
        return chain.request + "==>经过请求服务器拦截器\nHttp响应==>经过请求服务器拦截器";
    }
}