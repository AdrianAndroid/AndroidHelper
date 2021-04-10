package com.xiangxue.rxjavademo.source;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * TODO ObserveOn() 源码分析
 */
public class RxJavaThreadSource2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(){

            @Override
            public void run() {
                super.run();

                test();
            }
        }.start();

    }

    private void test() {
        Observable.create(

                // 自定义source
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("Derry");

                        Log.d(L.TAG, "自定义source: " + Thread.currentThread().getName());
                    }
                })

                // TODO 第二步骤
                .observeOn(

                        // TODO 第一步   主线程的 Handlnr
                        AndroidSchedulers.mainThread()

                )

                // subsceOn(1)  // 会显示是这个线程的原因，上一层卡片是被1线程执行
                // subsceOn(2)
                // subsceOn(3)

                // observeOn(A)
                // observeOn(B)
                // observeOn(C) // 终点是被C执行


                // ObservableObserveOn.subscribe
                .subscribe(

                        // 终点
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(L.TAG, "onSubscribe: " +  Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(L.TAG, "onNext: " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }


    private void t() {
        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                // UI 操作 ...
            }
        };


        new Thread(){
            @Override
            public void run() {
                super.run();

                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);

                        // UI 操作 ...  百分之百 Ui线程 没有问题
                    }
                };
            }
        }.start();


       new Handler(new Handler.Callback() {
           @Override
           public boolean handleMessage(@NonNull Message msg) {
               return false;
           }
       });
    }
}
