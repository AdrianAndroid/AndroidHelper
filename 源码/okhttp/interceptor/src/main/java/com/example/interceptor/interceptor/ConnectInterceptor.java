package com.example.interceptor.interceptor;

import com.example.interceptor.Chain;
import com.example.interceptor.Interceptor;

public class ConnectInterceptor implements Interceptor {

    @Override
    public String intercept(Chain chain) {
        System.out.println("开始执行连接拦截器");
        String result = chain.processd(chain.request + "==>经过连接拦截器");
        System.out.println("结束执行连接拦截器");
        return result +"==>经过连接拦截器";
    }
}