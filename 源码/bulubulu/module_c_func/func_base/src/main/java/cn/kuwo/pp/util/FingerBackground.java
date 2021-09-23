package cn.kuwo.pp.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;

public class FingerBackground extends FrameLayout {

    private boolean mIsVisible;

    private class FingerInfo {
        private int left;
        private int top;
        private int width;
        private int height;

        private int resetLeft;
        private int resetTop;

        public FingerInfo() {
            left = 0;
            top = 0;
            width = 0;
            height = 0;
            resetLeft = 0;
            resetTop = 0;
        }

        public FingerInfo(int left, int top, int width, int height, int resetLeft, int resetTop) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
            this.resetLeft = resetLeft;
            this.resetTop = resetTop;
        }

        public int getCenterX() {
            return width / 2;
        }

        public int getCenterY() {
            return height / 2;
        }

        public void setPosition(int left, int top) {
            this.left = left;
            this.top = top;
        }

        public void setSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void move() {
            this.top += 1;
            this.left -= 2;
            if (this.left < 0 - mBitmapWidth) {
                this.left = resetLeft;
                this.top = resetTop;
            }
        }
    }

    private ValueAnimator waveAnimator;
    private int mWidth;
    private int mHeight;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private Paint mColorFilterPaint;

    private ArrayList<ArrayList<FingerInfo>> mFingerList = new ArrayList<ArrayList<FingerInfo>>();
    private ArrayList<ArrayList<Bitmap>> mBitmapList = new ArrayList<ArrayList<Bitmap>>();

    public void setBitmaps() {
        for (int i = 0; i < 4; i++) {
            ArrayList infoList = new ArrayList<FingerInfo>();
            ArrayList bmpList = new ArrayList<Bitmap>();
            for (int j = 0; j < 3; j++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.finger);
                bmpList.add(bitmap);

                FingerInfo info = new FingerInfo();
                info.setSize(bitmap.getWidth(), bitmap.getHeight());
                infoList.add(info);

                if (mBitmapHeight == 0) {
                    mBitmapHeight = bitmap.getHeight();
                }
                if (mBitmapWidth == 0) {
                    mBitmapWidth = bitmap.getWidth();
                }
            }

            mFingerList.add(infoList);
            mBitmapList.add(bmpList);
        }

        initSizeParams();
    }

    public FingerBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
    }

    public FingerBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context);
    }

    public FingerBackground(Context context) {
        super(context);
        initParams(context);
    }

    private void initParams(Context context) {
        mColorFilterPaint = new Paint();
        mColorFilterPaint.setColor(Color.RED);
        mColorFilterPaint.setTextSize(UtilsCode.INSTANCE.sp2px(20));

        waveAnimator = ValueAnimator.ofFloat(0f, 100f);
        waveAnimator.setDuration(10000);  //十秒一循环
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        waveAnimator.addUpdateListener(animation -> {
            for (int i = 0; i < mFingerList.size(); i++) {
                ArrayList<FingerInfo> infos = mFingerList.get(i);
                for (int j = 0; j < infos.size(); j++) {
                    FingerInfo info = infos.get(j);
                    info.move();
                }
            }
            postInvalidate();
        });

        setBitmaps();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsVisible) {
            for (int i = 0; i < mBitmapList.size(); i++) {
                ArrayList<FingerInfo> infos = mFingerList.get(i);
                ArrayList<Bitmap> bitmaps = mBitmapList.get(i);
                for (int j = 0; j < bitmaps.size(); j++) {
                    FingerInfo info = infos.get(j);
                    canvas.drawBitmap(bitmaps.get(j), info.left, info.top, mColorFilterPaint);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initSizeParams();
    }


    private void initSizeParams() {
        if (mWidth > 0 && mHeight > 0) {
            int rowTop = 0 - mBitmapHeight;
            int rowSpace = (mHeight - UtilsCode.INSTANCE.dp2px(120)) / 3;

            int itemXSpace = (mWidth - UtilsCode.INSTANCE.dp2px(20)) / 2;
            int itemYSpace = mBitmapHeight;

            for (int i = 0; i < mFingerList.size(); i++) {
                int top = rowTop;
                int left = mWidth - UtilsCode.INSTANCE.dp2px(20) - mBitmapWidth;

                int resetTop = rowTop;
                int resetLeft = mWidth;

                ArrayList row = mFingerList.get(i);
                for (int j = 0; j < row.size(); j++) {
                    FingerInfo info = (FingerInfo) row.get(j);
                    info.top = top;
                    info.left = left;
                    info.resetLeft = resetLeft;
                    info.resetTop = resetTop;

                    top += itemYSpace;
                    left -= itemXSpace - mBitmapWidth;
                }

                rowTop += rowSpace;
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIsVisible) {
            if (waveAnimator.isStarted()) {
                waveAnimator.resume();
            } else {
                waveAnimator.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (waveAnimator.isRunning()) {
            waveAnimator.pause();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mIsVisible = (visibility == VISIBLE);
        if (mIsVisible) {
            if (waveAnimator.isRunning() == false) {
                if (waveAnimator.isStarted()) {
                    waveAnimator.resume();
                } else {
                    waveAnimator.start();
                }
            }
        } else if (waveAnimator.isRunning()) {
            waveAnimator.pause();
        }
    }

    public void animPause() {
        waveAnimator.pause();
    }

    public void animResume() {
        waveAnimator.resume();
    }
}
