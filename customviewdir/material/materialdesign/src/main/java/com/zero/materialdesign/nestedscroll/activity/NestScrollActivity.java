package com.zero.materialdesign.nestedscroll.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zero.materialdesign.R;
import com.zero.materialdesign.databinding.ActivityNestScrollBinding;
import com.zero.materialdesign.databinding.ActivityTabBinding;

public class NestScrollActivity extends AppCompatActivity {

    private ActivityNestScrollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNestScrollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}