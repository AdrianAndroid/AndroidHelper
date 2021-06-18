package com.flannery.jetpackapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.flannery.jetpackapp2.ui.main.FragmentActivity2Fragment;

import org.jetbrains.annotations.NotNull;

public class FragmentActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity2_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FragmentActivity2Fragment.newInstance())
                    .commitNow();
        }

        getMeminfo();

    }

    void test22() {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return 0;
            }

            @Override
            public int getNewListSize() {
                return 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }
        });
    }

    //https://blog.csdn.net/itfootball/article/details/22281549
    void getMeminfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    activityManager.getMemoryInfo(new ActivityManager.MemoryInfo());
    }

    void test() {
        //IMyAidlInterface.Stub
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
        L.m3();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        L.m3();
    }

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        L.m3();
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.m3();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        L.m3();
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.m3();
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.m3();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.m3();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        L.m3();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        L.m3();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.m3();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.m3();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.m3();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }
}