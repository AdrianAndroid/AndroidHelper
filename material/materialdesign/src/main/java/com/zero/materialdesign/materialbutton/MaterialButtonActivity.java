package com.zero.materialdesign.materialbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zero.materialdesign.R;
import com.zero.materialdesign.databinding.ActivityMaterialButtonBinding;

public class MaterialButtonActivity extends AppCompatActivity {

    private ActivityMaterialButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaterialButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.materialbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.materialbtn.isEnabled()){
                    binding.materialbtn.setEnabled(false);
                }else{
                    binding.materialbtn.setEnabled(true);
                }
            }
        });
    }
}