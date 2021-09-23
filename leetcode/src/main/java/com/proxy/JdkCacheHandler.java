package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkCacheHandler implements InvocationHandler {

    // 目标类对象
    private Object target;

    // 获取目标类对象
    public JdkCacheHandler(Object target) {
        this.target = target;
    }

    // 创建JDK代理
    public Object createJDKProxy() {
        Class clazz = target.getClass();
        // 创建JDK代理需要3个参数，目标类加载器、目标类接口、代理类对象(即本身)
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("查找数据库前，在缓存中查找是否存在:" + args[0]);
        // 触发目标类方法
        Object result = method.invoke(target, args);
        System.out.printf("查找数据库后，将%s加入到缓存中\r\n", result);
        return result;
    }
}