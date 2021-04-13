package com.example.interceptor.interceptor;

import com.example.interceptor.Chain;
import com.example.interceptor.Interceptor;

/**
 *  第一个拿到请求
 *  最后一个拿到响应
 */
public class RetryAndFollowUpInterceptor implements Interceptor {
    @Override
    public String intercept(Chain chain) {
        //可以在执行下一个拦截器之前，做自己的事情
        System.out.println("开始执行重试重定向拦截器");
        // 执行下一个拦截器
        String result = chain.processd(chain.request + "==>经过重试重定向拦截器");
        //获得结果后，加一些自己的东西
        System.out.println("结束执行重试重定向拦截器");
        return result + "==>经过重试重定向拦截器";
    }
}
