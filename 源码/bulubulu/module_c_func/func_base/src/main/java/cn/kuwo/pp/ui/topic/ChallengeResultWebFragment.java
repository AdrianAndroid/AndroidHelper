package cn.kuwo.pp.ui.topic;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.elbbbird.android.share.ShareWebDialogFragment;
import com.elbbbird.android.socialsdk.otto.ShareBusEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.kuwo.common.util.screenshots.ScreenshotContentObserver;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.ui.web.WebFragment;

/**
 * WebView的页面
 */
public class ChallengeResultWebFragment extends WebFragment {

    private static final String KEY_PSRC = "key_psrc";
    private static final String KEY_URL = "key_url";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_ENABLE_REFRESH = "key_refresh_flag";
    private static final String KEY_STATUS_BAR_OFFSET = "key_status_bar_offset";
    private static final String KEY_CHALLENGE_ID = "key_challenge_id";
    private static final String KEY_TOPIC_TITLE = "key_topic_title";

    ImageView ivShare;
    //    KwWebView web_view;
    LinearLayout topHoverLayout;

    private ShareWebDialogFragment shareWebDialogFragment;

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        ivShare = view.findViewById(R.id.ivShare);
        topHoverLayout = view.findViewById(R.id.top_hover_layout);
    }


    private int mScrollPosition = 0;

//    ShareWebDialog shareWebDialog; //分享弹出框

    public static ChallengeResultWebFragment newInstance(String url, String title, boolean enableRefresh, String psrc, TopicItemBean topic) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        args.putString(KEY_PSRC, psrc);
        args.putString(KEY_TITLE, title);
        args.putBoolean(KEY_ENABLE_REFRESH, enableRefresh);
        String challengeId = topic.getId();
        String topicTitle = topic.getName();
        args.putString(KEY_CHALLENGE_ID, challengeId);
        args.putString(KEY_TOPIC_TITLE, topicTitle);
        ChallengeResultWebFragment fragment = new ChallengeResultWebFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_result, container, false);
        initViews(view);
        return attachToSwipeBack(view);
    }

    @SuppressLint({"ResourceAsColor", "ResourceType", "PrivateResource"})
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //enableToolbar(R.id.toolbar, true);
        mToolbar.getTvTitle().setAlpha(0);
        mToolbar.getTvTitle().setTextColor(Color.WHITE);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
        topHoverLayout.getBackground().mutate().setAlpha(0);
        initListeners();
//        initUI(view);
    }


    private void initListeners() {
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 右上角分享
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CHANNLEGE_RESULT_SHARE_TOPRIGHT, ChallengeResultWebFragment.class.getSimpleName());
                showDialog();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    mScrollPosition = scrollY;
                    float alpha = (float) Math.abs(mScrollPosition) / (float) UtilsCode.INSTANCE.dp2px(200);
                    if (alpha > 1.0) {
                        alpha = 1;
                    }

                    topHoverLayout.getBackground().mutate().setAlpha((int) (alpha * 255.0));
                }
            });
        }
    }

    @Override
    public void webCommand_WebWindow(String cmd) {
        super.webCommand_WebWindow(cmd);
        if ("showsharedialog".equals(cmd)) {
            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CHANLLEGE_RESULT_SHARE_UNLOCK, ChallengeResultWebFragment.class.getSimpleName());
            post(this::showDialog);
        }
    }

    public void showDialog() {
        String challengeId = getArguments().getString(KEY_CHALLENGE_ID);
        String topicTitle = getArguments().getString(KEY_TOPIC_TITLE);
        shareWebDialogFragment = new ShareWebDialogFragment(webView, challengeId, topicTitle);
        shareWebDialogFragment.show(getChildFragmentManager());
    }

    // 分享回来的结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnShareResultEvent(ShareBusEvent event) {
        try {
            if (shareWebDialogFragment != null && shareWebDialogFragment.isShowing()) {
                shareWebDialogFragment.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (event.getType()) {
            case ShareBusEvent.TYPE_CANCEL:
                UtilsCode.INSTANCE.showShort("取消分享");
                break;
            case ShareBusEvent.TYPE_FAILURE:
                UtilsCode.INSTANCE.showShort("分享失败：" + (event.getException() != null ? event.getException().getMessage() : "未知错误"));
                break;
            case ShareBusEvent.TYPE_SUCCESS:
                if (jsInterface != null) {
                    jsInterface.nativeCallJavascriptEvent("shareSuccess", "");
                }
                break;
        }
    }

    private ScreenshotContentObserver screenshotContentObserver;

    @Override
    public void onStart() {
        super.onStart();

        if (screenshotContentObserver == null) {
            screenshotContentObserver = new ScreenshotContentObserver(_mActivity);
            screenshotContentObserver.setListener(new ScreenshotContentObserver.Listener() {
                @Override
                public void onDetectScreenshot(Uri uri, String path, int fromType) {
                    Log.i("ddd", "");
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CHANLLEGE_RESULT_SCREENSHOT, ChallengeResultWebFragment.class.getSimpleName());
                    showDialog();
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (screenshotContentObserver != null) {
            screenshotContentObserver.onDestroy();
            screenshotContentObserver = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (shareWebDialogFragment != null) {
            shareWebDialogFragment.dismiss();
        }
    }

    @Override
    protected void onBackBtnClicked(View view) {
        if (getPreFragment() == null) {
            _mActivity.finish();
        } else {
            pop();
        }
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
