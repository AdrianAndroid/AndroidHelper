package com.zero.materialdesign.tab;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.zero.materialdesign.R;
import com.zero.materialdesign.databinding.ActivityTabBinding;

public class TabActivity extends AppCompatActivity {

    private ActivityTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}