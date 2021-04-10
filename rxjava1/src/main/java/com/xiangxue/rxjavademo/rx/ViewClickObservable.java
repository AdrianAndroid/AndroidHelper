package com.xiangxue.rxjavademo.rx;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ViewClickObservable extends Observable<Object> {

    private final View view;

    // 事件  第一节课 防抖 事件 没用
    private static final Object EVENT = new Object();
    private static Object EVENT2;

    public ViewClickObservable(View view) {
        this.view = view;

        EVENT2 = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        // 可以干自己的

        MyListener myListener = new MyListener(view, observer);
        observer.onSubscribe(myListener);

        this.view.setOnClickListener(myListener);
    }

    // 我们的包裹
    static final class MyListener implements View.OnClickListener, Disposable {

        private final View view;
        private Observer<Object> observer;  // 存一份 下一层

        // 原子性，同学们自行看看文章
        // https://www.jianshu.com/p/8a44d4a819bc
        // boolean  == AtomicBoolean
        private final AtomicBoolean isDisposable = new AtomicBoolean();

        public MyListener(View view, Observer<Object> observer) {
            this.view = view;
            this.observer = observer;


        }

        @Override
        public void onClick(View v) {
            if (isDisposed() == false) {
                observer.onNext(EVENT);
            }

        }

        // 如果用调用了 中断
        @Override
        public void dispose() {
            // 如果没有中断过，才有资格，   取消view.setOnClickListener(null);
            if (isDisposable.compareAndSet(false, true)) {
                // 主线程 很好的中断
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    view.setOnClickListener(null);

                } else { // 主线程，通过Handler的切换
                    /*new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            view.setOnClickListener(null);
                        }
                    };*/

                    // HandlerScheduler.scheduleDirect

                    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                        @Override
                        public void run() {
                            view.setOnClickListener(null);
                        }
                    });
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return isDisposable.get();
        }
    }
}
