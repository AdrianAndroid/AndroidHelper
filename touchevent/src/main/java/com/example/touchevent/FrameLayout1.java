package com.example.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FrameLayout1 extends FrameLayout {
    public FrameLayout1(@NonNull Context context) {
        super(context);
        L.m3();
    }

    public FrameLayout1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        L.m3();
    }

    public FrameLayout1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        L.m3();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.m3();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        L.m3();
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.m3();
        return super.onTouchEvent(event);
    }
}
