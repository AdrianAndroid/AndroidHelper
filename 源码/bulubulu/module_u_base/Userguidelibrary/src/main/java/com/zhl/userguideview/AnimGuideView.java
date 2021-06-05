package com.zhl.userguideview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

public class AnimGuideView extends View {

    public enum TYPE {
        TARGET,
        HINT,
        OUTSIDE
    }

    public interface OnGuideListener {
        void onTouched(AnimGuideView view, TYPE type);
    }


    public static final int VIEWSTYLE_RECT = 0;
    public static final int VIEWSTYLE_CIRCLE = 1;
    public static final int VIEWSTYLE_OVAL = 2;
    public static final int MASKBLURSTYLE_SOLID = 0; // 模糊
    public static final int MASKBLURSTYLE_NORMAL = 1; //模糊


    private int screenW, screenH;// 屏幕宽高
    private int maskblurstyle = MASKBLURSTYLE_SOLID;
    private int highLightStyle = VIEWSTYLE_RECT; //高亮的模式

    private Rect targetRect = new Rect(); // 临时变量
    private Rect hintRect = new Rect(); // 提示的范围

    private RectF rectF = new RectF(); // 临时变量

    private Canvas mCanvas;// 给蒙版层的画布
    private Paint mPaint;//绘制蒙版层画笔
    private Bitmap fgBitmap; //前景
    private int maskColor = 0x55000000;// 蒙版层颜色

    private Bitmap bmHands;// 手指指向
    private Bitmap bmHints;

    private float corner = 20;
    private int statusBarHeight; // 状态栏的高度

    private float handsOffset = 40;

    //private Bitmap jtUpLeft, jtUpRight, jtUpCenter, jtDownRight, jtDownLeft, jtDownCenter;// 指示箭头
    private View targetView;

    private OnGuideListener onGuideListener;

    public AnimGuideView(Context context) {
        super(context);
        initialize(context, null);
    }

    public AnimGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public AnimGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {

        if (attrs != null) {
//            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UserGuideView);
//            highLightStyle = array.getInt(R.styleable.UserGuideView_HighlightViewStyle, VIEWSTYLE_RECT);
//            maskblurstyle = array.getInt(R.styleable.UserGuideView_MaskBlurStyle, MASKBLURSTYLE_SOLID);
//            BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(R.styleable.UserGuideView_tipView);
//            maskColor = array.getColor(R.styleable.UserGuideView_maskColor, maskColor);
//            if (drawable != null) {
//                tipBitmap = drawable.getBitmap();
//            }

//            array.recycle();

        }

//        if (bmHands == null) {
//            bmHands = BitmapFactory.decodeResource(getResources(), R.layout.userguide_hands);
//        }
//        if (bmHints == null) {
//            bmHints = BitmapFactory.decodeResource(getResources(), R.layout.userguide_tips);
//        }


        cal(context);
        init(context);
    }

    private void notifyListener(MotionEvent event) {
        if (onGuideListener != null) {
            // 判断区域范围

            int touchX = (int) event.getX();
            int touchY = (int) event.getY();
            if (this.targetRect.contains(touchX, touchY)) {
                onGuideListener.onTouched(this, TYPE.TARGET);
            } else if (this.hintRect.contains(touchX, touchY)) {
                onGuideListener.onTouched(this, TYPE.HINT);
            } else {
                onGuideListener.onTouched(this, TYPE.OUTSIDE);
            }
        }
    }

    /*
     int touchX = (int) event.getX();
                    int touchY = (int) event.getY();
                    if (tipViewHitRect != null && tipViewHitRect.contains(touchX, touchY)) {
                        if (targetViews == null || targetViews.size() == 0) {
                            this.setVisibility(View.GONE);
                            if (this.onDismissListener != null) {
                                onDismissListener.onDismiss(UserGuideView.this);
                            }
                        } else {
                            tipBitmap = getBitmapFromResId(tipViews.get(0));
                            tipViews.remove(0);
                            setHighLightView(targetViews.get(0));
                            targetViews.remove(0);
                        }
                        return true;
                    }
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            notifyListener(event);
        }
        return true;// super.onTouchEvent(event);
    }

    public Bitmap getBitmap(@DrawableRes final int resId,
                            final int maxWidth,
                            final int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * Return the sample size.
     *
     * @param options   The options.
     * @param maxWidth  The maximum width.
     * @param maxHeight The maximum height.
     * @return the sample size
     */
    private int calculateInSampleSize(final BitmapFactory.Options options,
                                      final int maxWidth,
                                      final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }


    private void cal(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize(context);
        screenW = screenSize[0];
        screenH = screenSize[1];
    }


    /**
     * 初始化对象
     */
    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mPaint.setARGB(0, 255, 0, 0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        BlurMaskFilter.Blur blurStyle = null;
        switch (maskblurstyle) {
            case MASKBLURSTYLE_SOLID:
                blurStyle = BlurMaskFilter.Blur.SOLID;
                break;
            case MASKBLURSTYLE_NORMAL:
                blurStyle = BlurMaskFilter.Blur.NORMAL;
                break;
        }
        mPaint.setMaskFilter(new BlurMaskFilter(1, blurStyle));

        fgBitmap = MeasureUtil.createBitmapSafely(screenW, screenH, Bitmap.Config.ARGB_8888, 2);
        if (fgBitmap == null) {
            throw new RuntimeException("out of memory cause fgbitmap create fail");
        }
        mCanvas = new Canvas(fgBitmap);
        mCanvas.drawColor(maskColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (targetView == null) {
            return;
        }

        // 画背景
        canvas.drawBitmap(fgBitmap, 0, 0, null);


        targetView.getGlobalVisibleRect(targetRect);
        targetRect.offset(0, -statusBarHeight);

        //  绘制高亮
        switch (highLightStyle) {
            case VIEWSTYLE_RECT: // 正方形
                rectF.set(targetRect);
                mCanvas.drawRoundRect(rectF, corner, corner, mPaint);
                break;
            case VIEWSTYLE_CIRCLE: // 圆形
                break;
            case VIEWSTYLE_OVAL: //椭圆形
                break;
        }


        if (bmHands != null) {
            canvas.drawBitmap(bmHands,
                    targetRect.right - Math.abs(targetRect.left - targetRect.right),
                    targetRect.top - bmHands.getHeight() - handsOffset,
                    null);
        }


        // "我知道了" 提示
        if (bmHints != null) {
            if (this.hintRect.isEmpty()) { // 省的刷新
                int left = targetRect.left - bmHints.getWidth() - 80;
                int top = targetRect.top + (Math.abs(targetRect.top - targetRect.bottom) >> 1 - bmHints.getHeight() >> 1);
                int right = left + bmHints.getWidth();
                int bottom = top + bmHints.getHeight();
                this.hintRect.set(left, top, right, bottom);
            }
            canvas.drawBitmap(bmHints, this.hintRect.left, this.hintRect.top, null);
        }
    }


    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    // 设置高亮的View
    public void setHighLightView(View targetView) {
        if (this.targetView != null && targetView != null && this.targetView != targetView) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)); // 清除之前的模式
            mCanvas.drawPaint(paint);
        }

        mCanvas.drawColor(maskColor);
        this.targetView = targetView;
        inflate2(R.layout.userguide_tips);

        this.bmHints = BitmapFactory.decodeResource(getResources(), R.mipmap.userguide_iknown);
        this.bmHands = MeasureUtil.drawViewToBitmap(inflate2(R.layout.userguide_hands), 180, 180);

//        this.tipBitmap = MeasureUtil.getViewBitmap(inflate2(R.layout.userguide_tips));//MeasureUtil.drawViewToBitmap(inflate2(R.layout.userguide_tips),);
//        this.bmHands = MeasureUtil.getViewBitmap(inflate2(R.layout.userguide_hands));//MeasureUtil.drawViewToBitmap(inflate2(R.layout.userguide_hands), 60, 60);
        invalidate();
        setVisibility(VISIBLE);
    }

    private View inflate2(int layoutId) {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            return LayoutInflater.from(getContext()).inflate(layoutId, (ViewGroup) parent, false);
        } else {
            return LayoutInflater.from(getContext()).inflate(layoutId, null);
        }
    }

    // 设置提示
    public void setTipBitmap(Bitmap tipBitmap) {
        this.bmHints = tipBitmap;
    }

    public void setHandsOffset(float handsOffset) {
        this.handsOffset = handsOffset;
        invalidate();
    }

    public float getHandsOffset() {
        return handsOffset;
    }

    public void startHandsAnimate() {
        ObjectAnimator handsOffset = ObjectAnimator.ofFloat(this, "handsOffset"
                , 0F, 1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, 17F, 18F, 19F, 20F);
        handsOffset.setInterpolator(new LinearInterpolator());
        handsOffset.setDuration(400);
        handsOffset.setRepeatCount(ObjectAnimator.INFINITE);
        handsOffset.setRepeatMode(ObjectAnimator.REVERSE);
        handsOffset.start();
    }

    public void setOnGuideListener(OnGuideListener onGuideListener) {
        this.onGuideListener = onGuideListener;
    }
}
