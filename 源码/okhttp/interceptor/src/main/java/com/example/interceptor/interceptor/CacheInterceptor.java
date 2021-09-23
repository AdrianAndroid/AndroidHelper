package com.example.interceptor.interceptor;

import com.example.interceptor.Chain;
import com.example.interceptor.Interceptor;

public class CacheInterceptor implements Interceptor {

    @Override
    public String intercept(Chain chain) {
        System.out.println("开始执行缓存拦截器");
        String result = chain.processd(chain.request + "==>经过缓存拦截器");
        System.out.println("结束执行缓存拦截器");
        return result + "==>经过缓存拦截器";
    }
}