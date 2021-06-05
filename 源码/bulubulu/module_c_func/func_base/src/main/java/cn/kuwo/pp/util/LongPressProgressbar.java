package cn.kuwo.pp.util;
/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.ColorInt;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;

public class LongPressProgressbar extends androidx.appcompat.widget.AppCompatTextView {

    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private static final int STATE_TIME_END = 4;
    private static final int DISTANCE_Y_CANCEL = 50;
    private int mCurrentState = STATE_NORMAL;
    // 已经开始按住
    private boolean isPressing = false;
    private boolean isClickStart = false;  //是否点击了
    private float mTime = 0;

    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = Color.parseColor("#2475B5A7");

    /**
     * 外部轮廓的宽度。
     */
    private int outLineWidth = 0;

    /**
     * 内部圆的颜色。
     */
    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.parseColor("#33FFE43F"));
    /**
     * 中心圆的颜色。
     */
    private int circleColor;

    /**
     * 进度条的颜色。
     */
    private int progressLineColor = Color.parseColor("#FFE43F");

    /**
     * 进度条的宽度。
     */
    private int progressLineWidth = UtilsCode.INSTANCE.dp2px(6);

    /**
     * 画笔。
     */
    private Paint mPaint = new Paint();

    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();

    /**
     * 进度。
     */
    private int progress = 0;
    /**
     * 进度条类型。
     */
    private ProgressType mProgressType = ProgressType.COUNT;
    /**
     * 进度倒计时时间。
     */
    private long timeMillis = 60000;

    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();
    /**
     * 进度条通知。
     */
    private OnCountdownProgressListener mCountdownProgressListener;
    /**
     * Listener what。
     */
    private int listenerWhat = 0;
    private long mPressStartTime; //保存按下时长

    public LongPressProgressbar(Context context) {
        this(context, null);
    }

    public LongPressProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongPressProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    /**
     * 初始化。
     *
     * @param context      上下文。
     * @param attributeSet 属性。
     */
    private void initialize(Context context, AttributeSet attributeSet) {
        mPaint.setAntiAlias(true);
        setClickable(true);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextProgressbar);
        if (typedArray.hasValue(R.styleable.CircleTextProgressbar_in_circle_color))
            inCircleColors = typedArray.getColorStateList(R.styleable.CircleTextProgressbar_in_circle_color);
        else
            inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        typedArray.recycle();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineColor 颜色值。
     */
    public void setOutLineColor(@ColorInt int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineWidth 颜色值。
     */
    public void setOutLineWidth(@ColorInt int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    /**
     * 设置圆形的填充颜色。
     *
     * @param inCircleColor 颜色值。
     */
    public void setInCircleColor(@ColorInt int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    /**
     * 是否需要更新圆的颜色。
     */
    private void validateCircleColor() {
        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp;
            invalidate();
        }
    }

    /**
     * 设置进度条颜色。
     *
     * @param progressLineColor 颜色值。
     */
    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置进度条线的宽度。
     *
     * @param progressLineWidth 宽度值。
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度。
     *
     * @param progress 进度。
     */
    private void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }

    /**
     * 验证进度。
     *
     * @param progress 你要验证的进度值。
     * @return 返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 拿到此时的进度。
     *
     * @return 进度值，最大100，最小0。
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置倒计时总时间。
     *
     * @param timeMillis 毫秒。
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 拿到进度条计时时间。
     *
     * @return 毫秒。
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型。
     *
     * @param progressType {@link ProgressType}.
     */
    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    /**
     * 重置进度。
     */
    private void resetProgress() {
        switch (mProgressType) {
            case COUNT:
                progress = 0;
                break;
            case COUNT_BACK:
                progress = 100;
                break;
        }
    }

    /**
     * 拿到进度条类型。
     *
     * @return
     */
    public ProgressType getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听。
     *
     * @param mCountdownProgressListener 监听器。
     */
    public void setCountdownProgressListener(int what, OnCountdownProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    /**
     * 开始。
     */
    protected void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 重新开始。
     */
    protected void reStart() {
        resetProgress();
        start();
    }

    /**
     * 停止。
     */
    protected void stop() {
        removeCallbacks(progressChangeTask);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);
        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        int deleteWidth = (progressLineWidth + outLineWidth);
        float outerRadius = size / 2 - deleteWidth;

        //画内部背景
        mPaint.setStrokeWidth(progressLineWidth);
        int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(circleColor);
        int pressOffet = 0;
        if (mCurrentState == STATE_RECORDING) {
            mPaint.setAlpha(204);
            pressOffet = 0;
        } else {
            mPaint.setAlpha(255);
            pressOffet = -UtilsCode.INSTANCE.dp2px(10);
        }
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius + pressOffet, mPaint);
        //画字
        final String content = getText().toString();
        if (TextUtils.isEmpty(content) == false) {
            Paint paint = getPaint();
            paint.setColor(getCurrentTextColor());
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            float textY = bounds.centerY() - (paint.descent() + paint.ascent()) / 2;
            canvas.drawText(content, bounds.centerX(), textY, paint);
        }
        if (progress != 0) {
            //画进度条
            mPaint.setAlpha(255);
            mPaint.setColor(progressLineColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mArcRect.set(bounds.left + deleteWidth / 2, bounds.top + deleteWidth / 2, bounds.right - deleteWidth / 2, bounds.bottom - deleteWidth / 2);
            canvas.drawArc(mArcRect, -90, 360 * progress / 100, false, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineWidth = 4 * (outLineWidth + progressLineWidth);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height) + lineWidth;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        validateCircleColor();
    }

    private Runnable delayDownCall = new Runnable() {
        @Override
        public void run() {
            changeState(STATE_RECORDING);
        }
    };

    /**
     * 直接复写这个监听函数
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(delayDownCall);
                postDelayed(delayDownCall, 500);
                isClickStart = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isPressing) {
                    // 根据x，y来判断用户是否想要取消
                    if (mCurrentState != STATE_TIME_END) {  //如果已经定时结束，这里移动也不应有变化了
                        if (wantToCancel(x, y)) {
                            changeState(STATE_WANT_TO_CANCEL);
                        } else {
                            changeState(STATE_RECORDING);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClickStart && isPressing == false) {
                    removeCallbacks(delayDownCall);
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onClick();
                    }
                    break;
                }
            case MotionEvent.ACTION_CANCEL:
                removeCallbacks(delayDownCall);
                // 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
                mTime = System.currentTimeMillis() - mPressStartTime;
                if (!isPressing || mTime < 3000) {
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressCancel();
                    }
                } else if (mCurrentState == STATE_RECORDING) {//正常录制结束
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressEnd(true, System.currentTimeMillis() - mPressStartTime);
                    }
                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
                    // cancel
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressCancel();
                    }
                }
                reset();// 恢复标志位
                break;
        }
        return super.onTouchEvent(event);
    }

    /*
     * 恢复标志位以及状态
     */
    private void reset() {
        isPressing = false;
        changeState(STATE_NORMAL);
        mTime = 0;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {// 判断是否在左边，右边，上边，下边
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    //改变状态
    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
//                    this.setBackgroundResource(R.drawable.common_btn_yellow_dash_frame);
//                    this.setText("按住 说话");
                    stop();
                    resetProgress();
                    mPressStartTime = System.currentTimeMillis();
                    break;
                case STATE_RECORDING:
//                    this.setBackgroundResource(R.drawable.common_btn_yellow_solid);
//                    this.setText("松开 结束");
                    isPressing = true;
                    reStart();
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressStart();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
//                    this.setBackgroundResource(R.drawable.common_btn_yellow_solid);
//                    this.setText("松开手指，取消录音");
                    stop();
                    resetProgress();
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressCancel();
                    }
                    break;
                case STATE_TIME_END:
                    stop();
                    resetProgress();
                    if (mCountdownProgressListener != null) {
                        mCountdownProgressListener.onProgressEnd(false, System.currentTimeMillis() - mPressStartTime);
                    }
                    break;
            }
        }
    }

    /**
     * 进度更新task。
     */
    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                case COUNT:
                    progress += 1;
                    break;
                case COUNT_BACK:
                    progress -= 1;
                    break;
            }
            if (progress >= 0 && progress <= 100) {
                if (mCountdownProgressListener != null) {
                    if (progress >= 100) {
                        changeState(STATE_TIME_END);
                        invalidate();
                    } else {
                        mCountdownProgressListener.onProgress(listenerWhat, progress);
                        invalidate();
                        postDelayed(progressChangeTask, timeMillis / 100);
                    }
                }
            } else {
                progress = validateProgress(progress);
                if (progress >= 100 || progress <= 0) {
                    changeState(STATE_TIME_END);
                    invalidate();
                }
            }
        }
    };

    public void changeToNormal() {
        stop();
        resetProgress();
        reset();
        invalidate();
    }

    /**
     * 进度条类型。
     */
    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK;
    }

    /**
     * 进度监听。
     */
    public interface OnCountdownProgressListener {
        /**
         * 用户长按触发进度开始事件
         */
        void onProgressStart();

        /**
         * 进度通知。
         *
         * @param progress 进度值。
         */
        void onProgress(int what, int progress);

        /**
         * 用户松开手或倒计时结束触发结束事件
         */
        void onProgressEnd(boolean isUser, long runTime);

        void onProgressCancel();

        /**
         * 用户点击操作
         */
        void onClick();
    }
}