package com.example.hotfix;

import android.content.Context;
import android.content.SharedPreferences;

public class Demo {
    public static void test() {
        int a = 1;
        int b = 2;
        int c = a + b;
        //throw new UnsupportedOperationException("错啦");

    }
    void tst(){
        Context context = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.apply();
        edit.commit();
        edit.clear();
    }
}
