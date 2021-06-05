package com.example.interceptor;

public interface Interceptor {
    String intercept(Chain chain);
}