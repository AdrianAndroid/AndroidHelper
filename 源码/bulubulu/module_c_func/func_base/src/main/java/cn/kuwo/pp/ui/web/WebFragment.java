package cn.kuwo.pp.ui.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.flannery.kuwowebview.KwWebView;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.pp.R;
import cn.kuwo.pp.util.UIUtil;

/**
 * 通用浏览器加载控件
 */
public class WebFragment extends BaseFragment implements KwWebWindowInterface {
    public final static int FILECHOOSER_RESULTCODE = 1011;

    private static final String KEY_PSRC = "key_psrc";
    private static final String KEY_URL = "key_url";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_ENABLE_REFRESH = "key_refresh_flag";

    MultipleStatusView statusView;
    protected KwWebView webView;
    SwipeRefreshLayout refreshLayout;


    protected void initViews(View view) {
        statusView = view.findViewById(R.id.web_status_view);
        webView = view.findViewById(R.id.web_view);
        refreshLayout = view.findViewById(R.id.web_refresh_layout);
    }


    private String mPsrc;

    private String mInitUrl;

    private String mTitle;

    private boolean mEnableRefresh;

    private boolean bResumeReload;

    protected KwJavaScriptInterfaceEx jsInterface;

    private long mStartTime;

    private long mFinishTime;

    private ValueCallback<Uri> mUploadMessage;

    private String mCameraFilePath;

    private boolean mCaughtActivityNotFoundException;

    public static WebFragment newInstance(String url, String psrc) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        args.putString(KEY_PSRC, psrc);
        args.putString(KEY_TITLE, "");
        args.putBoolean(KEY_ENABLE_REFRESH, false);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WebFragment newInstance(String url, String title, String psrc) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        args.putString(KEY_PSRC, psrc);
        args.putString(KEY_TITLE, title);
        args.putBoolean(KEY_ENABLE_REFRESH, false);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WebFragment newInstance(String url, boolean enableRefresh, String psrc) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        args.putString(KEY_PSRC, psrc);
        args.putString(KEY_TITLE, "");
        args.putBoolean(KEY_ENABLE_REFRESH, enableRefresh);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WebFragment newInstance(String url, String title, boolean enableRefresh, String psrc) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        args.putString(KEY_PSRC, psrc);
        args.putString(KEY_TITLE, title);
        args.putBoolean(KEY_ENABLE_REFRESH, enableRefresh);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArguments();
    }

    @Override
    public boolean showMainPlayView() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_web, container, false);
        initViews(view);
        return attachToSwipeBack(view);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, mTitle, true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
        // 放在Toolbar下边
        initWebViewEvent();
        refreshLayout.setEnabled(mEnableRefresh);
        webView.loadUrl(mInitUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bResumeReload) {
            webView.reload();
        }
    }

    @Override
    public void onDestroy() {
        if (jsInterface != null) {
            jsInterface.release();
            jsInterface = null;
        }
        super.onDestroy();
    }

    //========================================================================

    private void initWebViewEvent() {
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
            } else {
                webView.setLayerType(WebView.LAYER_TYPE_NONE, null);
            }
        } catch (Throwable e) {
        }
        webView.setBackgroundResource(R.color.colorWindowBackground);
        if (Build.VERSION.SDK_INT > 27) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new KwWebChromeClient());
        webView.setOnLongClickListener(v -> true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        disableAccessibility();
        //webView的字体，防止用户自己修改系统字体导致webView显示有问题
        webView.getSettings().setSupportZoom(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextZoom(100);
        // 修改ua使得web端正确判断,打榜的需求
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + "/ kuwobulu");
        //开启localStorage
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        Context context = getActivity();
        if (context != null) {
            File cacheFile = context.getCacheDir();
            if (cacheFile != null) {
                String appCachePath = cacheFile.getAbsolutePath();
                webView.getSettings().setAppCachePath(appCachePath);
                webView.getSettings().setAppCacheEnabled(true);
            }
        }
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        jsInterface = new KwJavaScriptInterfaceEx(this) {
            @Override
            public String getClientInfo() throws JSONException {

                return new JSONObject().put("keyStatusBarOffset", UIUtil.px2Dip(mToolbar.getBottom())).toString();
            }
        };
        jsInterface.setPsrc(mPsrc);
        webView.addJavascriptInterface(jsInterface, "KuwoBuluInterface");
    }

    private void initArguments() {
        Bundle args = getArguments();
        if (null != args) {
            mPsrc = args.getString(KEY_PSRC);
            if (TextUtils.isEmpty(mPsrc)) {
                mPsrc = "Web浏览器";
            } else {
                mPsrc += "->Web浏览器";
            }
            mInitUrl = args.getString(KEY_URL);
            mTitle = args.getString(KEY_TITLE);
            mEnableRefresh = args.getBoolean(KEY_ENABLE_REFRESH);
        }
    }

    /**
     * 关闭辅助功能，针对4.2.1和4.2.2 崩溃问题
     * java.lang.NullPointerException
     * at android.webkit.AccessibilityInjector$TextToSpeechWrapper$1.onInit(AccessibilityInjector.java:753)
     * ... ...
     * at android.webkit.CallbackProxy.handleMessage(CallbackProxy.java:321)
     */
    private void disableAccessibility() {
        if (Build.VERSION.SDK_INT == 17/*4.2 (Build.VERSION_CODES.JELLY_BEAN_MR1)*/) {
            Context context;
            if ((context = getActivity()) != null) {
                try {
                    AccessibilityManager am = (AccessibilityManager)
                            context.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (am == null || !am.isEnabled()) {
                        return;
                    }
                    Method set = am.getClass().getDeclaredMethod("setState", int.class);
                    set.setAccessible(true);
                    set.invoke(am, 0);/**{@link AccessibilityManager#STATE_FLAG_ACCESSIBILITY_ENABLED}*/
                } catch (Exception e) {
                }
            }
        }
    }

    //-----------------------------------------------------------------------
    @Override
    public Activity getActivity_WebWindow() {
        return _mActivity;
    }

    @Override
    public KwWebView getWebView_WebWindow() {
        return webView;
    }

    @Override
    public void setTitle_WebWindow(String title) {

    }

    @Override
    public void setTitleColor_WebWindow(String colorStr) {

    }

    @Override
    public void setResume_Reload(boolean breload) {
        bResumeReload = breload;
    }

    @Override
    public void onWebError_WebWindow() {
        post(() -> {
            if (statusView != null) {
                statusView.showError();
            }
        });
    }

    @Override
    public void webCommand_WebWindow(String cmd) {
        if (TextUtils.isEmpty(cmd)) {
            return;
        }

        if ("hideloading".equals(cmd)) {
            post(() -> {
                if (statusView != null) {
                    statusView.hideAllView();
                }
            });
        }
    }

    @Override
    public void openNewWebFragment(String url, String title, String psrc) {
        startFromMain(WebFragment.newInstance(url, title, psrc));
    }

    //-----------------------------------------------------------------------
    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (TextUtils.isEmpty(url)) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(App.getInstance().getPackageManager()) != null) {
                    try {
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {

                    }
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            // 李建衡：启动非标准的协议
            if (errorCode == ERROR_UNSUPPORTED_SCHEME) {
                try {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(failingUrl));
                    startActivity(in);
                    return;
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {
                if (statusView != null) {
                    statusView.showError(description);
                }
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
//            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.proceed();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (statusView != null) {
                statusView.showLoading();
            }
            if (mStartTime == 0) {
                mStartTime = System.currentTimeMillis();
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (statusView != null) {
                statusView.hideAllView();
            }
            boolean hasException = false;
            if (Build.VERSION.SDK_INT == 16) {
                try {
                    URLEncoder.encode(url, "UTF-8");
//                    URLEncodedUtils.parse(new URI(url), null);
                } catch (IllegalArgumentException e) {
                    //4.1系统bug
                    hasException = true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    hasException = true;
                }
            }
            if (!hasException) {
                super.onPageFinished(view, url);//这个地方在4.1系统上可能会有系统bug
            }
            if (mFinishTime == 0) {
                mFinishTime = System.currentTimeMillis();
            }

            if (view != null) {
                view.loadUrl(KwJavaScriptInterfaceEx.httpStatusCodeJS);
            }

        }

    }

    //-----------------------------------------------------------------------
    class KwWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(WebFragment.this.mTitle) && !TextUtils.isEmpty(title)) {
                WebFragment.this.mTitle = title;
                if (mToolbar != null) {
                    mToolbar.setTitle(title);
                }
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startIntentActivity(createDefaultOpenableIntent());
        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startIntentActivity(createDefaultOpenableIntent());
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startIntentActivity(createDefaultOpenableIntent());
        }

        //配置权限（同样在WebChromeClient中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
            geolocationPermissionsCallback.invoke(s, true, false);
            super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (resultCode == Activity.RESULT_CANCELED && mCaughtActivityNotFoundException) {
                // Couldn't resolve an activity, we are going to try again so
                // skip
                // this result.
                mCaughtActivityNotFoundException = false;
                return;
            }
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data
                    .getData();
            // As we ask the camera to save the result of the user taking
            // a picture, the camera application does not return anything other
            // than RESULT_OK. So we need to check whether the file we expected
            // was written to disk in the in the case that we
            // did not get an intent returned but did get a RESULT_OK. If it
            // was,
            // we assume that this result has came back from the camera.
            if (result == null && data == null && resultCode == Activity.RESULT_OK) {
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    // Broadcast to the media scanner that we have a new photo
                    // so it will be added into the gallery for the user.
                    getActivity().sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            mUploadMessage.onReceiveValue(result);
            mCaughtActivityNotFoundException = false;
        }
    }

    //-----------------------------------------------------------------------
    private Intent createDefaultOpenableIntent() {
        // Create and return a chooser with the default OPENABLE
        // actions including the camera, camcorder and sound
        // recorder where available.
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");

        Intent chooser = createChooserIntent(createCameraIntent(), createCamcorderIntent()
                /* createSoundRecorderIntent() */);
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "选择要上传的文件");
        return chooser;
    }

    private void startIntentActivity(Intent intent) {
        try {
            startActivityForResult(intent, FILECHOOSER_RESULTCODE);
        } catch (ActivityNotFoundException e) {
            // No installed app was able to handle the intent that
            // we sent, so fallback to the default file upload control.
            try {
                mCaughtActivityNotFoundException = true;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            } catch (ActivityNotFoundException e2) {
                // Nothing can return us a file, so file upload is effectively
                // disabled.
                UtilsCode.INSTANCE.showShort("上传文件失败");
            }
        }
    }

    @SuppressLint("NewApi")
    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator
                + "browser-photos");
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
                + System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    private Intent createCamcorderIntent() {
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
