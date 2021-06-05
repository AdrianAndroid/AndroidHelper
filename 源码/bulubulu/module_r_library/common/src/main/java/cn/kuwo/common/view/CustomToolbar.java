package cn.kuwo.common.view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.kuwo.common.R;


/**
 * @author shihc
 */
public class CustomToolbar extends RelativeLayout {
    private TextView mTvTitle;
    private Toolbar mToolbar;

    public CustomToolbar(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this);
        mTvTitle = findViewById(R.id.tv_title);
        mToolbar = findViewById(R.id.inner_toolbar);
    }

    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
        mTvTitle.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setTitle(int resId) {
        mTvTitle.setText(resId);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
