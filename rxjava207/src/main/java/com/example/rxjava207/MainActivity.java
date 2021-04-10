package com.example.rxjava207;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void log(String txt) {
        System.out.println("[RXJAVA][" +
                Thread.currentThread().getName() +
                "]" +
                txt);
    }

    private void test() {
        Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                        log("create subscribe");
                        e.onNext("");
                        e.onNext("");
                        e.onNext("");
                        e.onComplete();
//                        e.onError(new Throwable(""));
                    }
                })
                .map(new Function<Object, Object>() {
                    @NonNull
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        log("map Function apply");
                        return o;
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("subscribe onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        log("subscribe onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("subscribe onError");
                    }

                    @Override
                    public void onComplete() {
                        log("subscribe onComplete");
                    }
                });
    }
}