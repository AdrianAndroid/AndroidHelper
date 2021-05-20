package com.flannery.whale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.flannery.leak.Utils;
import com.flannery.leak.Watcher;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {


    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.i("MyApp", x);
//            }
//        });

//        Main.hook_BinderProxy();
//        Main.hook_Thread();


//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            long startTime;
//
//            @Override
//            public void println(String x) {
//                if (x.startsWith(">>>>>")) {
//                    startTime = System.currentTimeMillis();
//                } else if (x.startsWith("<<<<<")) {
//                    long end = System.currentTimeMillis();
//                    //事件阈值可以自己定义，这里是10
//                    if ((end - startTime) > 10) {
//                    Log.i("buder mainLoop ---- :", (end - startTime) + " " + Log.getStackTraceString(new Throwable()));
//                    }
//                }
//            }
//        });
//        new Main().executeHook2(getClassLoader());
//        Toast.makeText(this, Test.get(), Toast.LENGTH_SHORT).show();
    }

    public void clickTest(View view) {
//        new Main().executeHook2(getClassLoader());
//        Toast.makeText(this, Test.get(), Toast.LENGTH_SHORT).show();
//
//        startActivity(new Intent(this, SecondActivity.class));
//        for (int i = 0; i < 10; i++) {
//            handler.sendEmptyMessage(0);
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("hhello world!");
//            }
//        }).start();
//        testQueue();
        testLeak();
    }


    void testLeak() {
        Watcher watcher = new Watcher();

        Object obj = new Object();
        System.out.println("obj:" + obj);
        watcher.watch(obj, "referenceName");
        Utils.sleep(500);
        //释放对象
        obj = null;
        Utils.gc();
        // ？？？ 思考如何判断被观察的可能存在泄漏嫌疑
        Utils.sleep(500);
        System.out.println("查看是否在怀疑列表：" + watcher.getRetainedReferences().size());
    }


    void testQueue() {
        System.out.println("hello world!");
        ReferenceQueue referenceQueue = new ReferenceQueue(); // 存放的引用列表
        Object obj = new Object(); // 创建一个引用列表

        // 将obj放入weakReference，并和一个referenceQueue关联
        // 将obj被gc回收后，放它的weakReference会被添加与之关联的referenceQueue
        WeakReference weakReference = new WeakReference(obj, referenceQueue);
        System.out.println("放obj的weakReference= " + weakReference);

        // 把obj置空，让它没有强引用
        obj = null;
        Runtime.getRuntime().gc();//gc 让可以回收的对象回收

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        Reference findRef = null;
        do {
            findRef = referenceQueue.poll();
            //如果能找到上面的weakReference=>说明它放法obj被gc回收了
            System.out.println("findRef = " + findRef + "是否等于上面的weakReference = " + (findRef == weakReference));
        } while (findRef != null);//将所有放到referenceQueue引用容器找出来
    }

    void test() {
        getWindow().getDecorView().getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {

            }
        });
    }
}