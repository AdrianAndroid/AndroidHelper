package com.flannery.personal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.flannery.arouter_annotation.ARouter;

@ARouter(path = "/personal/Personal_Main4Activity")
public class Personal_Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
    }
}
