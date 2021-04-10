package com.example.rxjava207;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
    static Disposable disposable;
    private void test3(){
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        e.onNext("lfkdsk");

                        e.onNext("Hello");

                        //SystemClock.sleep(100);

                        disposable.dispose();

                        e.onNext("World");

                        e.onComplete();
                    }
                })
                //.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                       // mTextView.append("bind on subscribe \n");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                       /// mTextView.append(s);
                        //mTextView.append("\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //mTextView.append(" onComplete ");
                    }
                });
    }

    private void test2(){
        Observable.just("");
        Observable.fromArray("");
        Observable.just("", "");
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {

            }
        });
    }

    private void test() {
        Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                        log("create subscribe");
                        e.onNext("");
//                        e.onComplete();
                        e.onError(new Throwable(""));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
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

