package com.joyy.module_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.joyy.router.annotations.Destination;

@Destination(
        url = "module_2/ModuleTwoActivity",
        description = "ModuleTwoActivity"
)
public class ModuleTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_two);
    }
}