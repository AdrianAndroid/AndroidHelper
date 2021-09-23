package cn.kuwo.pp.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.Random;

import cn.kuwo.common.utilscode.UtilsCode;

public class BubbleBackground extends FrameLayout {

    private boolean mIsVisible;

    private class BubbleInfo {
        private int left;
        private int top;
        private int width;
        private int height;

        public BubbleInfo() {
            left = 0;
            top = 0;
            width = 0;
            height = 0;
        }

        public BubbleInfo(int left, int top, int width, int height) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
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
            this.top -= 2;
            if (this.top < -height) {
                this.top = mHeight;  //设置球复位到屏幕最下面
            }
        }
    }

    private ValueAnimator waveAnimator;
    private int mWidth;
    private int mHeight;
    private Paint mColorFilterPaint;
    private BubbleInfo bubblePt1 = new BubbleInfo();
    private BubbleInfo bubblePt2 = new BubbleInfo();
    private BubbleInfo bubblePt3 = new BubbleInfo();
    private BubbleInfo bubblePt4 = new BubbleInfo();
    private Bitmap bubbleBmp1;
    private Bitmap bubbleBmp2;
    private Bitmap bubbleBmp3;
    private Bitmap bubbleBmp4;

    public void setBubbleColorFilter(int color) {
        mColorFilterPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void setBubbleBmps(Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4) {
        bubbleBmp1 = bmp1;
        bubbleBmp2 = bmp2;
        bubbleBmp3 = bmp3;
        bubbleBmp4 = bmp4;
        updateBubbleSize();
        initSizeParams();
    }

    private void updateBubbleSize() {
        if (bubbleBmp1 != null) {
            bubblePt1.setSize(bubbleBmp1.getWidth(), bubbleBmp1.getHeight());
        }
        if (bubbleBmp2 != null) {
            bubblePt2.setSize(bubbleBmp2.getWidth(), bubbleBmp2.getHeight());
        }
        if (bubbleBmp3 != null) {
            bubblePt3.setSize(bubbleBmp3.getWidth(), bubbleBmp3.getHeight());
        }
        if (bubbleBmp4 != null) {
            bubblePt4.setSize(bubbleBmp4.getWidth(), bubbleBmp4.getHeight());
        }
    }

    public BubbleBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
    }

    public BubbleBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context);
    }

    public BubbleBackground(Context context) {
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
            bubblePt1.move();
            bubblePt2.move();
            bubblePt3.move();
            bubblePt4.move();
            postInvalidate();
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsVisible) {
            if (bubbleBmp1 != null) {
                canvas.drawBitmap(bubbleBmp1, bubblePt1.left, bubblePt1.top, mColorFilterPaint);
            }
            if (bubbleBmp2 != null) {
                canvas.drawBitmap(bubbleBmp2, bubblePt2.left, bubblePt2.top, mColorFilterPaint);
            }
            if (bubbleBmp3 != null) {
                canvas.drawBitmap(bubbleBmp3, bubblePt3.left, bubblePt3.top, mColorFilterPaint);
            }
            if (bubbleBmp4 != null) {
                canvas.drawBitmap(bubbleBmp4, bubblePt4.left, bubblePt4.top, mColorFilterPaint);
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
            bubblePt1.left = -UtilsCode.INSTANCE.dp2px(40);
            bubblePt1.top = UtilsCode.INSTANCE.dp2px(307);
            bubblePt2.left = UtilsCode.INSTANCE.dp2px(58);
            bubblePt2.top = UtilsCode.INSTANCE.dp2px(28);
            bubblePt4.left = mWidth - bubblePt4.width + UtilsCode.INSTANCE.dp2px(30);
            bubblePt4.top = UtilsCode.INSTANCE.dp2px(166);
            bubblePt3.left = bubblePt4.left - bubblePt3.width / 2;
            bubblePt3.top = UtilsCode.INSTANCE.dp2px(496);
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
