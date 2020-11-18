package com.flannery.app2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.flannery.arouter_annotation.ARouter;

@ARouter(path = "/app/Main2Activity")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
