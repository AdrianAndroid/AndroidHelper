package com.example.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class Button4 extends androidx.appcompat.widget.AppCompatButton {


    public Button4(Context context) {
        super(context);
    }

    public Button4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Button4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.m3(L.getMotionEventName(event));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        L.m3(L.getMotionEventName(event));
        return super.dispatchTouchEvent(event);
    }
}
