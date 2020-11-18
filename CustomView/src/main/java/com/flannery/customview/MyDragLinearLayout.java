package com.flannery.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDragLinearLayout extends LinearLayout {
    public MyDragLinearLayout(Context context) {
        super(context);
        initDragHelper();
    }

    public MyDragLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDragHelper();
    }

    public MyDragLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDragHelper();
    }


    ViewDragHelper mViewDragHelper;

    private void initDragHelper() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            private int mLeft;
            private int mTop;

            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {

                Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
                return true;
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mLeft = capturedChild.getLeft();
                mTop = capturedChild.getTop();

                Log.i(getClass().getSimpleName()
                        , Thread.currentThread().getStackTrace()[2].getMethodName() + " " + mLeft + " " + mTop);
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {

                Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

                Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
                return left;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
                mViewDragHelper.settleCapturedViewAt(mLeft, mTop);
                invalidate();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewDragHelper.shouldInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {

        Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

}
