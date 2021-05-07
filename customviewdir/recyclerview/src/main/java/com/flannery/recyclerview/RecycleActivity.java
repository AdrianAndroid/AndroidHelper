package com.flannery.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RecycleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        findViewById(R.id.findUserChildView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecycleActivity.this, FindChildViewActivity.class));
            }
        });

    }

    public void onClick(View view) {
        if (view.getId() == R.id.findVisibatly) {
            startActivity(new Intent(RecycleActivity.this, FindCompatelyActivity.class));
        }
    }
}