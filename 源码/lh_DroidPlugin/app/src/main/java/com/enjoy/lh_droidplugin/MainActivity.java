package com.enjoy.lh_droidplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.morgoo.droidplugin.pm.PluginManager;

public class MainActivity extends AppCompatActivity {

    private final static String apkPath = "/sdcard/plugin-debug.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PluginManager.getInstance().installPackage(apkPath, 0);
                    Log.e("leo", "完成插件的加载");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.enjoy.plugin", "com.enjoy.plugin.MainActivity"));
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("leo", "加载出错了");
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PluginManager.getInstance().deletePackage("com.enjoy.plugin", 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
