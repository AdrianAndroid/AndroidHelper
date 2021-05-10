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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Log.i("MyApp", x);
            }
        });

        Main.hook_BinderProxy();

        Main.hook_Thread();

//        new Main().executeHook2(getClassLoader());
//        Toast.makeText(this, Test.get(), Toast.LENGTH_SHORT).show();
    }

    public void clickTest(View view) {
        new Main().executeHook2(getClassLoader());
        Toast.makeText(this, Test.get(), Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, SecondActivity.class));
        for (int i = 0; i < 10; i++) {
            handler.sendEmptyMessage(0);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hhello world!");
            }
        }).start();
    }
}