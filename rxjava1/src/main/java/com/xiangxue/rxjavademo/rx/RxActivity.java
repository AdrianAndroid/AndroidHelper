package com.xiangxue.rxjavademo.rx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.xiangxue.rxjavademo.R;
import com.xiangxue.rxjavademo.source.L;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        Button button = findViewById(R.id.button);

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));

        RxView.clicks(button)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //test();
                        testDisposable();
                        System.out.println(sdf.format(new Date())); //测试防抖
                    }
                });
    }


    Disposable d = null;

    void testDisposable() {
        if (d != null && !d.isDisposed()) DisposableHelper.dispose(new AtomicReference<>(d));
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
        interval.map(new Function<Long, Long>() {
            @Override
            public Long apply(@NonNull Long aLong) throws Exception {
                return aLong;
            }
        })
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return aLong;
                    }
                })
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return aLong;
                    }
                })
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return aLong;
                    }
                })
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return aLong;
                    }
                });
        interval.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.println(aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    ObservableTransformer<Object, Object> CA = new ObservableTransformer<Object, Object>() {
        @NonNull
        @Override
        public ObservableSource<Object> apply(@NonNull Observable<Object> upstream) {
            return upstream.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    };


    ObservableTransformer<Object, Object> CB = new ObservableTransformer<Object, Object>() {
        @NonNull
        @Override
        public ObservableSource<Object> apply(@NonNull Observable<Object> upstream) {
            return upstream.observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread());
        }
    };

    class M implements Function<Object, Object> {

        final String name;

        M(String name) {
            this.name = name;
        }

        @Override
        public Object apply(@NonNull Object o) throws Exception {
            //打印ThreadName
            // 打印名称
            System.out.println("[RXJAVA][" +
                    Thread.currentThread().getName() +
                    "]" +
                    name);
            return o;
        }
    }


    @SuppressLint("CheckResult")
    void test() {
        //结论：subscribeOn 向上
        // observeOn向下
        Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                        e.onNext(e);
                    }
                })
                .map(new M("A1"))
                .map(new M("A2"))
                .map(new M("A3"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new M("B1"))
                .map(new M("B2"))
                .map(new M("B3"))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new M("C1"))
                .map(new M("C2"))
                .map(new M("C3"))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });


//        Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @SuppressLint("CheckResult")
//                    @Override
//                    public void subscribe(ObservableEmitter<String> e) throws Exception {
//                        e.onNext("Derry");
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.d(L.TAG, "accept: 终点：" + s);
//                    }
//                });
    }
}
