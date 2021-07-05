package com.example.routerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imooc.router.annotations.Destination;

@Destination(
        url = "router://MainActivity3",
        description = "第三个"
)
public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}