package com.flannery.customview.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class View1 extends View {

    public boolean custom;
    public boolean dispatchTouchEvent;
    public boolean onTouchEvent;
    public boolean setOnTouchEvent;

    public View1(Context context) {
        super(context);
        init();
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                breakpoint();
                L.m(getClass(), "TouchTest", setOnTouchEvent);
                return setOnTouchEvent;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        breakpoint();
        L.m(getClass(), "TouchTest");
        boolean b;
        if (custom && dispatchTouchEvent) {
            b = true;
        } else {
            b = super.dispatchTouchEvent(ev);
        }
        L.m(getClass(), "TouchTest", b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        breakpoint();
        L.m(getClass(), "TouchTest");
        boolean b;
        if (custom && onTouchEvent) {
            b = true;
        } else {
            b = super.onTouchEvent(event);
        }
        L.m(getClass(), "TouchTest", b);
        return b;
    }

    private void breakpoint() {
        if (getClass() == View1.class) {
            String breakpoint = "";
        }
    }
}
