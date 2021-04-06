package com.example.ffmpeg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());


        try {
            Open("/sdcard/test.mp4", this); //testff: File /sdcard/test.mp4 open succes!
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, "" + stringFromJNI(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + Open("/sdcard/test.mp4", true), Toast.LENGTH_SHORT).show();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native boolean Open(String url, Object handle);
}

// 问题1
// jniLib.srcDirs= 没有添加
// 问题2
// 。c文件路径问题