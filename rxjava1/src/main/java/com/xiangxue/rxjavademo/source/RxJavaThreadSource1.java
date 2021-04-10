package com.xiangxue.rxjavademo.source;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * TODO subscribeOn() 源码分析
 */
public class RxJavaThreadSource1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Hook  IO 传递进去的Hook
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                Log.d(L.TAG, "apply: 全局 监听 scheduler：" +scheduler);
                return scheduler;
            }
        });

        // TODO hook 给 IO 初始化
        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                Log.d(L.TAG, "apply: 全局 监听 init scheduler：" +schedulerCallable.call());
                return schedulerCallable.call();
            }
        });









        Observable.create(

                // 自定义source
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("Derry");

                        Log.d(L.TAG, "自定义source: " + Thread.currentThread().getName());
                    }
        })

        // ObservbaleCreate.subscribeOn
        // TODO 第二步     new IOScheduler ---> 线程池 传递进去
        .subscribeOn(

                // TODO 第一步  到底干了什么 （ new IOScheduler ---> 线程池）
                Schedulers.io() // 耗时读取的异步

                // Schedulers.newThread() // 开启新线程

        )

         // A线程. subscribe
         //  ObservableSubscribeOn.subscribe
        .subscribe(

                // 终点
                new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Disposable disposable = d;
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
}
