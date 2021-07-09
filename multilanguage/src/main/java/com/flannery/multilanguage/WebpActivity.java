package com.flannery.multilanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class WebpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webp);
        ImageView iv2 = findViewById(R.id.mIv2);
        iv2.setImageResource(R.drawable.ic_purchase_history_blank);
    }
}