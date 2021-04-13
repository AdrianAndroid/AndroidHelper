package com.example.interceptor;

import com.example.interceptor.interceptor.CacheInterceptor;
import com.example.interceptor.interceptor.CallServerInterceptor;
import com.example.interceptor.interceptor.ConnectInterceptor;
import com.example.interceptor.interceptor.RetryAndFollowUpInterceptor;
import com.example.interceptor.interceptor.BridgeInterceptor;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public static void main(String[] args) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new RetryAndFollowUpInterceptor());
//        interceptors.add(new BridgeInterceptor());
//        interceptors.add(new CacheInterceptor());
//        interceptors.add(new ConnectInterceptor());
        interceptors.add(new CallServerInterceptor());

        //链条对象
        Chain chain = new Chain(interceptors, 0);
        System.out.println(chain.processd("Http请求"));

    }
}