package com.flannery.app_test.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flannery.app_test.MainActivity;
import com.flannery.app_test.R;

public class LeakHandlerActivity extends AppCompatActivity {

    private ImageView imageView;
    private Object object;

    private final Handler handler = new MyHandler(this);

    class MyHandler extends Handler {
        LeakHandlerActivity activity;

        public MyHandler(LeakHandlerActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity.imageView != null) {
                activity.imageView.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }


    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_handler);

        imageView = findViewById(R.id.imageView);


        for (int i = 0; i < 10; i++) {
            new Handler();
        }

        handler.sendEmptyMessage(555);

        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);

//        Message.obtain(handler, 11).getTarget().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(imageView != null) {
//                    imageView.setImageResource(R.mipmap.ic_launcher);
//                }
//            }
//        }, 2000);
    }
}