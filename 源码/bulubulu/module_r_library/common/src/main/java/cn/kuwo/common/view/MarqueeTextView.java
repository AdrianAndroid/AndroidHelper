package cn.kuwo.common.view;

import android.content.Context;
import android.graphics.Rect;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 一直走马灯效果控件
 */
public class MarqueeTextView extends AppCompatTextView {

    private Runnable mRunnable;

    public MarqueeTextView(Context context) {
        super(context);
        createView();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView();
    }

    private void createView() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setSingleLine();
        setFocusable(true);
        setFocusableInTouchMode(true);

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }

    public void stop() {
        removeCallbacks(mRunnable);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public void start(int delay) {
        removeCallbacks(mRunnable);
        if (delay <= 0) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            mRunnable = () -> {
                setEllipsize(TextUtils.TruncateAt.MARQUEE);
            };
            postDelayed(mRunnable, delay);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mRunnable);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

