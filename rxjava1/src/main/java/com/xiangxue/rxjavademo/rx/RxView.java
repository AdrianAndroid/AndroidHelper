package com.xiangxue.rxjavademo.rx;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;

public class RxView {

    private final static String TAG = RxView.class.getSimpleName();

    // 我们自己的操作符 == 函数
    public static Observable<Object> clicks(View view) {
        return new ViewClickObservable(view);
    }


}
