package com.example.interceptor;


import java.util.List;

public class Chain {
    private List<Interceptor> interceptors;
    private int index;
    public String request;

    public Chain(List<Interceptor> interceptors, int index, String request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    public Chain(List<Interceptor> interceptors, int index) {
        this.interceptors = interceptors;
        this.index = index;
    }

    public String processd(String request) {
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }
        Chain chain = new Chain(interceptors, index + 1, request);
        Interceptor interceptor = interceptors.get(index);
        return interceptor.intercept(chain);
    }
}