package com.example.ffmpeg2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "" + stringFromJNI(), Toast.LENGTH_SHORT).show();
    }

         //native String stringFromJNI();
    public native String stringFromJNI();
}