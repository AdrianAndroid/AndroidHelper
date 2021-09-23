package cn.kuwo.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import cn.kuwo.common.R;
import cn.kuwo.common.utilscode.UtilsCode;


/**
 * 类描述：  一个方便在多种状态切换的view
 * <p>
 * 创建人:   续写经典
 * 创建时间: 2016/1/15 10:20.
 */
public class MultipleStatusView extends RelativeLayout {
    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;
    public static final int STATUS_HIDE_ALL_VIEW = 0x05;

    private static final int NULL_RESOURCE_ID = -1;

    public static boolean isNightMode = false;  //是否夜间模式中使用

    private View mEmptyView;
    private View mErrorView;
    private TextView mTvErrorViewMessage;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;
    private View mEmptyRetryView;
    private TextView mEmptyMessageView;
    private View mErrorRetryView;
    private View mNoNetworkRetryView;
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;
    private int mViewStatus;

    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private boolean mCenterInParent;
    private int mBottomPadding;

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view);
        mErrorViewResId = a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.error_view);
        mLoadingViewResId = a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.loading_night_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView, R.layout.no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
//        setPaddingTop(SizeUtils.dp2px(84) + ImmersionBar.getStatusBarHeight(ActivityUtils.getTopActivity()), false);
        setCenterInParent(20);
    }

    public void setPaddingTop(int paddingTop, boolean isDpValue) {
        if (isDpValue) {
            paddingTop = dp2px(paddingTop);
        }
        this.setPadding(getPaddingLeft(), paddingTop, getPaddingRight(), getPaddingBottom());
        invalidate();
    }

    /**
     * 居中显示
     *
     * @param topOffset 居中情况下的偏移量，偏上传>0，偏下传<0
     */
    public void setCenterInParent(int topOffset) {
        this.setPadding(getPaddingLeft(), 0, getPaddingRight(), getPaddingBottom());
        mBottomPadding = UtilsCode.INSTANCE.dp2px(topOffset) * 2;
        mCenterInParent = true;
        if (mEmptyView != null) {
            ((LayoutParams) mEmptyView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mEmptyView.setPadding(0, 0, 0, mBottomPadding);
            mEmptyView.invalidate();
        }
        if (mErrorView != null) {
            ((LayoutParams) mErrorView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mErrorView.setPadding(0, 0, 0, mBottomPadding);
            mErrorView.invalidate();
        }
        if (mLoadingView != null) {
            ((LayoutParams) mLoadingView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mLoadingView.setPadding(0, 0, 0, mBottomPadding);
            mLoadingView.invalidate();
        }
        if (mNoNetworkView != null) {
            ((LayoutParams) mNoNetworkView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mNoNetworkView.setPadding(0, 0, 0, mBottomPadding);
            mNoNetworkView.invalidate();
        }
        if (mContentView != null) {
            ((LayoutParams) mContentView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mContentView.invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    public void setEmptyViewResId(int emptyViewResId) {
        mEmptyViewResId = emptyViewResId;
    }

    public void setErrorViewResId(int errorViewResId) {
        mErrorViewResId = errorViewResId;
    }

    public void setLoadingViewResId(int loadingViewResId) {
        mLoadingViewResId = loadingViewResId;
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    // 当前是错误状态
    public boolean isViewStatusError() {
        return getViewStatus() == STATUS_ERROR;
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        showEmpty(0, "暂无数据");
    }

    /**
     * 显示空视图
     *
     * @param message
     */
    public final void showEmpty(CharSequence message) {
        showEmpty(0, message);
    }

    /**
     * 显示空视图
     */
    public final void showEmpty(int iconResId, CharSequence message) {
        if (UtilsCode.INSTANCE.isEmpty(message)) {
            message = "";
        }
        mViewStatus = STATUS_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = mInflater.inflate(mEmptyViewResId, this, false);
            mEmptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            mEmptyMessageView = mEmptyView.findViewById(R.id.empty_message_view);
            if (null != mOnRetryClickListener && null != mEmptyRetryView) {
                mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            if (mCenterInParent) {
                ((LayoutParams) mEmptyView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mEmptyView.setPadding(0, 0, 0, mBottomPadding);
            }
            addView(mEmptyView);

        }
        if (mEmptyMessageView != null) {
            mEmptyMessageView.setMovementMethod(LinkMovementMethod.getInstance());
            mEmptyMessageView.setText(message);
            if (iconResId > 0) {
                mEmptyMessageView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
            }
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        showError(0, getResources().getString(R.string.error_view_hint));
    }

    public final void showError(CharSequence message) {
        showError(0, message);
    }

    /**
     * 显示错误视图
     */
    public final void showError(int iconResId, CharSequence message) {
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewResId, this, false);
            mErrorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickListener && null != mErrorRetryView) {
                mErrorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mTvErrorViewMessage = mErrorView.findViewById(R.id.error_view_message);
            if (mCenterInParent) {
                ((LayoutParams) mErrorView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mErrorView.setPadding(0, 0, 0, mBottomPadding);
            }
            addView(mErrorView);
        }
        if (mTvErrorViewMessage != null) {
            mTvErrorViewMessage.setText(message);
            if (iconResId > 0) {
                mTvErrorViewMessage.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
            }
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = mInflater.inflate(mLoadingViewResId, this, false);
            if (mCenterInParent) {
                ((LayoutParams) mLoadingView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mLoadingView.setPadding(0, 0, 0, mBottomPadding);
            }
            addView(mLoadingView);
        }
        if (mLoadingView != null) {
            LottieAnimationView animView = mLoadingView.findViewById(R.id.anim_loading);
            if (animView != null) {
                animView.clearAnimation();
                if (isNightMode) {
                    animView.setAnimation("loading_women.json");
                } else {
                    animView.setAnimation("loading_man.json");
                }
                animView.playAnimation();
            }
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        mViewStatus = STATUS_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = mInflater.inflate(mNoNetworkViewResId, this, false);
            mNoNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if (null != mOnRetryClickListener && null != mNoNetworkRetryView) {
                mNoNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            if (mCenterInParent) {
                ((LayoutParams) mNoNetworkView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mNoNetworkView.setPadding(0, 0, 0, mBottomPadding);
            }
            addView(mNoNetworkView);
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView) {
            if (mContentViewResId != NULL_RESOURCE_ID) {
                mContentView = mInflater.inflate(mContentViewResId, this, false);
                if (mCenterInParent) {
                    ((LayoutParams) mContentView.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                }
                addView(mContentView);
            } else {
                mContentView = findViewById(R.id.content_view);
            }
        }
        showViewByStatus(mViewStatus);
    }

    /**
     * 隐藏所有视图
     */
    public final void hideAllView() {
        mViewStatus = STATUS_HIDE_ALL_VIEW;
        showViewByStatus(mViewStatus);
    }

    private void showViewByStatus(int viewStatus) {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(viewStatus == STATUS_LOADING ? View.VISIBLE : View.GONE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE : View.GONE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE : View.GONE);
        }
        if (null != mNoNetworkView) {
            mNoNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE : View.GONE);
        }
        if (null != mContentView) {
            mContentView.setVisibility(viewStatus == STATUS_CONTENT ? View.VISIBLE : View.GONE);
        }
//        if (viewStatus == STATUS_LOADING && null != mLoadingView) {
//            mLoadingView.bringToFront();
//        } else if (viewStatus == STATUS_EMPTY && null != mEmptyView) {
//            mEmptyView.bringToFront();
//        } else if (viewStatus == STATUS_ERROR && null != mErrorView) {
//            mErrorView.bringToFront();
//        } else if (viewStatus == STATUS_NO_NETWORK && null != mNoNetworkView) {
//            mNoNetworkView.bringToFront();
//        } else if (viewStatus == STATUS_CONTENT && null != mContentView) {
//            mContentView.bringToFront();
//        }
    }

    public int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
