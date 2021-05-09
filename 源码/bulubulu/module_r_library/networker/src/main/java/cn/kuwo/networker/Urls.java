package cn.kuwo.networker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;

public class Urls {
    public final static String URL_DEBUG = "http://test-bulu-api.kuwo.cn/";
    public final static String URL_RELEASE = "http://bulu-api.kuwo.cn/";


    public final static String KEY_URL_USE = "KEY_URL_USE"; // 为了性能，保存到本地
    public static String URL_USE = null; // 如果为空，则使用原来的地址


    public final static String KEY_BASEURL = "BASEURL";
    public final static String HEADER_DEBUG = "BASEURL:" + URL_DEBUG;//KEY_BASEURL + ":" + URL_DEBUG; // DEBUG环境的Header
    public final static String HEADER_RELEASE = "BASEURL:" + URL_RELEASE;//KEY_BASEURL + ":" + URL_RELEASE; // RELEASE换季ing的Header


    public static void initURL_USE(String base_url_header) {
        if (TextUtils.isEmpty(base_url_header)) {
            throw new IllegalArgumentException("base_url_header 不能为空");
        }
        if (TextUtils.isEmpty(Urls.URL_USE)) { // 等更换环境的时候直接替换URL_USE就行了
            // 当为空的时候才去判断本地记录
            String localUrl = SP.decodeString(Urls.KEY_URL_USE);
            if (TextUtils.isEmpty(localUrl)) {
                Urls.URL_USE = base_url_header;
            } else {
                Urls.URL_USE = localUrl;
            }
        }
    }

    // 目前就两个URL
    public static void changeBaseUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("BaseUrl 不能设置为空");
        }
//        if (debug) {
//            Urls.URL_USE = URL_DEBUG;
//        } else {
//            Urls.URL_USE = URL_RELEASE;
//        }
        Urls.URL_USE = url;
        // 保存起来
        SP.encode(KEY_URL_USE, Urls.URL_USE);
    }


    public static void showBaseUrlDialog(final Context context, final DialogInterface.OnClickListener onClickListener) {
        final String[] items = {
                URL_DEBUG, URL_RELEASE
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        builder.setTitle("切换地址");
        builder.setIcon(R.mipmap.ic_launcher);
        //设置列表显示，
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeBaseUrl(items[which]);
                UtilsCode.INSTANCE.showLong("切换成功-》" + items[which]);
                dialog.dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
        // 弹出手动输入对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 弹出可以输入的对话框
                showInputBaseUrlDialog(context, onClickListener);
            }
        });
        builder.create().show();
    }

    // 可自己输入的对话框
    private static void showInputBaseUrlDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        final EditText editText = new EditText(context);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        builder.setTitle("请输入地址:");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = editText.getText().toString();
                changeBaseUrl(url);
                UtilsCode.INSTANCE.showLong("输入成功=》" + url);
                if (onClickListener != null) {
                    onClickListener.onClick(dialog, which);
                }
            }
        });
    }

    // 是否是Debug环境
    public static boolean isReleaseUrl() {
        return Urls.URL_RELEASE.equals(Urls.URL_USE);
    }

    public static boolean isDebugUrl() {
        return Urls.URL_DEBUG.equals(Urls.URL_USE);
    }

    // 显示debug界面
    public static void showDebuLog(Activity _mActivity) {
        if (TextUtils.isEmpty(Urls.URL_USE)) {
            return;
        }
        ViewGroup container = _mActivity.findViewById(android.R.id.content);
        View viewById = container.findViewById(R.id.debugImageView);
        if (viewById != null && isReleaseUrl()) {
            container.removeView(viewById); // 删掉
        } else if (viewById == null && !isReleaseUrl()) {
            View debugView = LayoutInflater.from(App.getInstance()).inflate(R.layout.networker_debug, container, false);
            container.addView(debugView);
        }
    }
}
