package com.enjoy.enjoyfix;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.enjoy.patch.EnjoyFix;

import java.io.File;

/**
 * @author Lance
 * @date 2019-09-03
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        EnjoyFix.installPatch(this, new File("/sdcard/patch.jar"));// 加载


        ClassLoader classLoader = MainActivity.class.getClassLoader();
        ClassLoader classLoader1 = AppCompatActivity.class.getClassLoader();
        ClassLoader classLoader2 = Application.class.getClassLoader();

        ClassLoader classLoader3 = getClassLoader();
    }

}
