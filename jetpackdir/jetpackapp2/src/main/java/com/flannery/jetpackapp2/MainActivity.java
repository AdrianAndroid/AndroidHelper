package com.flannery.jetpackapp2;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    MyLinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(MainActivity.class.getClassLoader());
        //dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.flannery.jetpackapp2-ArMgkaOaW39-siebOYscug==/base.apk"],nativeLibraryDirectories=[/data/app/com.flannery.jetpackapp2-ArMgkaOaW39-siebOYscug==/lib/x86, /system/lib]]]
        System.out.println(AppCompatActivity.class.getClassLoader());
        //dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.flannery.jetpackapp2-ArMgkaOaW39-siebOYscug==/base.apk"],nativeLibraryDirectories=[/data/app/com.flannery.jetpackapp2-ArMgkaOaW39-siebOYscug==/lib/x86, /system/lib]]]
        System.out.println(Application.class.getClassLoader());
        //java.lang.BootClassLoader@2fbedc6
        System.out.println(getClassLoader());
        //dalvik.system.PathClassLoader[

        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            Log.e("leo", "classLoader -> " + classLoader);
            classLoader = classLoader.getParent();
        }
        //com.flannery.jetpackapp2 E/leo: classLoader -> dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.flannery.jetpackapp2-MdTQAa4cJhemd657NVJ5Lg==/base.apk"],nativeLibraryDirectories=[/data/app/com.flannery.jetpackapp2-MdTQAa4cJhemd657NVJ5Lg==/lib/x86, /system/lib]]]
        //com.flannery.jetpackapp2 E/leo: classLoader -> java.lang.BootClassLoader@2fbedc6


        linearLayout = findViewById(R.id.linear);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            TextView tv = (TextView) linearLayout.getChildAt(i);
            tv.setText("--->" + i);
        }

    }

    void test() {
        DexClassLoader dexClassLoader = new DexClassLoader("dexPath", getCacheDir().getAbsolutePath(), null, getClassLoader());
//        Class<?> aClass = dexClassLoader.loadClass("com.enjoy.plugin.Test");

        Choreographer.getInstance().postFrameCallback(null);
//        Choreographer.getInstance()
    }

    public void scrollto(View view) {
        linearLayout.scroller.startScroll(0, 0, 0, 1000, 1000);
        linearLayout.invalidate();
    }

    void test2() {
//        ActivityDatabindBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databind);
//        binding.mTextView.setText("");
    }


}