package com.example.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LinearLayout3 extends LinearLayout {
    public LinearLayout3(@NonNull Context context) {
        super(context);
        L.m3();
    }

    public LinearLayout3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        L.m3();
    }

    public LinearLayout3(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        L.m3();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        L.m3(L.getMotionEventName(ev));
        boolean b = super.dispatchTouchEvent(ev);
        L.m3(L.getMotionEventName(ev));
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        L.m3(L.getMotionEventName(ev), "拦截");
        //return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.m3(L.getMotionEventName(event));
        boolean b = super.onTouchEvent(event);
        return b;
    }
}
