package com.flannery.webview;

import android.content.Context;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.util.HashMap;

public class X5 {

    public static void init(Context context) {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initX5Environment(context, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                ToastUtils.showShort("onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                ToastUtils.showShort("onViewInitFinished-->" + b);
            }
        });
        QbSdk.initTbsSettings(map);
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                ToastUtils.showShort("onDownloadFinish " + i);
            }

            @Override
            public void onInstallFinish(int i) {
                ToastUtils.showShort("onInstallFinish " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                ToastUtils.showShort("onDownloadProgress " + i);
            }
        });
    }

}
