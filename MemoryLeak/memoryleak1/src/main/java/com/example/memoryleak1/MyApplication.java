package com.example.memoryleak1;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
