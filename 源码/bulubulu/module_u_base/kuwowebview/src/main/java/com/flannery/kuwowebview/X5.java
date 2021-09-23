package com.flannery.kuwowebview;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;


import com.tencent.smtt.sdk.QbSdk;

public class X5 {
    public static void init(Context context) {
        QbSdk.initX5Environment(context, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                if (BuildConfig.DEBUG) {
                    Log.i("X5", "onCoreInitFinished");
                    Toast.makeText(context, "onCoreInitFinished", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onViewInitFinished(boolean b) {
                if (BuildConfig.DEBUG) {
                    // true表示加载成功，false表示加载失败，会自动切换到系统内核
                    Log.i("X5", "onViewInitFinished b=" + b);
                    Toast.makeText(context, "onViewInitFinished b=" + b, Toast.LENGTH_SHORT).show();
                }
                // 内核加载失败, 并且LOLLIPOP以上
                if (!b && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    WebView.enableSlowWholeDocumentDraw();
                }
            }
        });
    }
}
