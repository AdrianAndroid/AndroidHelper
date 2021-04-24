package com.example.memoryleak1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeakOneActivity extends AppCompatActivity {


    public static Activity sActivity;
    public static List<Activity> list = new ArrayList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_one);

        sActivity = this;
        list.add(this);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true);
                }
            },"Thread i=" + i).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        System.out.println(allStackTraces);
    }

    public void finishClick(View view) {

        finish();
    }
}