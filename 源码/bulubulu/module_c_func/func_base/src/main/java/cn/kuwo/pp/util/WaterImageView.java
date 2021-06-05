package cn.kuwo.pp.util;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * 此控件显示图片时，能让图片边缘波动变形
 */
public class WaterImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int PATH_POINT_COUNT = 20;   //定义从圆上获取12个等距点作为生成path的基础半径位置,要能被4整除，便于叠加正弦波

    private int mCenterX;  //圆心坐标
    private int mCenterY;
    private float mDefaultRadius;   //设置的默认控件基准半径，头像图片在其内部波动
    private float mDefaultPerimeter;  //保存默认圆周长度，
    private float mDefaultSineA;      //正弦波振幅
    private float mDefaultSineW;      //正弦波基本周期长度
    private float mDefaultSinePhase;  //正弦波初始相位值（0-180）
    private Path mBitmapPath = new Path();      //头像周围path
    private List<Point> mBitmapRadius = new ArrayList<>(PATH_POINT_COUNT);    //保存叠加正弦波后的圆形上指定点的半径数组
    private ValueAnimator waveAnimator;
    private boolean hasMask = false;   //是否画个蒙层
    private int mMarkColor = Color.parseColor("#20000000");

    public WaterImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public WaterImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaterImageView(Context context) {
        super(context);
        init(context);
    }

    private void initRadius(float radius) {
        mDefaultRadius = radius;   //默认设置圆大小是控件小边的80%
        mDefaultPerimeter = (float) (2f * Math.PI * mDefaultRadius);
        mDefaultSineW = (float) (2f * Math.PI / (mDefaultPerimeter / (PATH_POINT_COUNT / 4f)));   //最小正周期T=2π/ω
        float angle = 360f / PATH_POINT_COUNT;
        mBitmapRadius.clear();
        for (int i = 0; i < PATH_POINT_COUNT; i++) {
            float sineHeight = (float) (mDefaultSineA * Math.sin(mDefaultSineW * mDefaultPerimeter * (i * angle) / 360 + mDefaultSinePhase) + UtilsCode.INSTANCE.dp2px(5));
            mBitmapRadius.add(getPointByRadiusAngle(mDefaultRadius - UtilsCode.INSTANCE.dp2px(10) - sineHeight / 4, i * angle));
        }
        createCurve(mBitmapRadius, mBitmapPath);
    }

    Point[] controlPoint = new Point[4];

    /**
     * 通过传入的离散点，获取把这些点穿过串起来的beziercurve点集合
     *
     * @param points
     * @return
     */
    private void createCurve(List<Point> points, Path path) {
        if (points == null || points.size() < 1) {
            return;
        }
        int oriSize = points.size();  //传入点数量
        float scale = 0.8f;   //控制点收缩系数 ，经调试0.6较好,数字越大越光滑
        List<Point> midpoints = new ArrayList<>(oriSize);
        Point item = null;
        //生成中点
        for (int i = 0; i < oriSize; i++) {
            int nextIndex = (i + 1) % oriSize;  //得到当前点的下一个点的索引
            item = new Point();
            item.x = (points.get(i).x + points.get(nextIndex).x) / 2;
            item.y = (points.get(i).y + points.get(nextIndex).y) / 2;
            midpoints.add(item);
        }
        //平移中点
        //List<Point> extraPoints = new ArrayList<>(oriSize*2);
        Point[] extrapoints = new Point[oriSize * 2];
        for (int i = 0; i < oriSize; i++) {
            int nexti = (i + 1) % oriSize;
            int backi = (i + oriSize - 1) % oriSize;
            Point midinmid = new Point();
            midinmid.x = (midpoints.get(i).x + midpoints.get(backi).x) / 2;
            midinmid.y = (midpoints.get(i).y + midpoints.get(backi).y) / 2;
            int offsetx = points.get(i).x - midinmid.x;
            int offsety = points.get(i).y - midinmid.y;
            int extraindex = 2 * i;
            extrapoints[extraindex] = new Point();
            extrapoints[extraindex].x = midpoints.get(backi).x + offsetx;
            extrapoints[extraindex].y = midpoints.get(backi).y + offsety;
            //朝 originPoint[i]方向收缩
            int addx = (int) ((extrapoints[extraindex].x - points.get(i).x) * scale);
            int addy = (int) ((extrapoints[extraindex].y - points.get(i).y) * scale);
            extrapoints[extraindex].x = points.get(i).x + addx;
            extrapoints[extraindex].y = points.get(i).y + addy;

            int extranexti = (extraindex + 1) % (2 * oriSize);
            extrapoints[extranexti] = new Point();
            extrapoints[extranexti].x = midpoints.get(i).x + offsetx;
            extrapoints[extranexti].y = midpoints.get(i).y + offsety;
            //朝 originPoint[i]方向收缩
            addx = (int) ((extrapoints[extranexti].x - points.get(i).x) * scale);
            addy = (int) ((extrapoints[extranexti].y - points.get(i).y) * scale);
            extrapoints[extranexti].x = points.get(i).x + addx;
            extrapoints[extranexti].y = points.get(i).y + addy;
        }

        //生成4控制点，产生贝塞尔曲线
        path.reset();
        path.moveTo(points.get(0).x, points.get(0).y);
        for (int i = 0; i < oriSize; i++) {
            controlPoint[0] = points.get(i);
            int extraindex = 2 * i;
            controlPoint[1] = extrapoints[extraindex + 1];
            int extranexti = (extraindex + 2) % (2 * oriSize);
            controlPoint[2] = extrapoints[extranexti];
            int nexti = (i + 1) % oriSize;
            controlPoint[3] = points.get(nexti);
            path.cubicTo(controlPoint[1].x, controlPoint[1].y,
                    controlPoint[2].x, controlPoint[2].y,
                    controlPoint[3].x, controlPoint[3].y
            );
        }
        path.close();
    }

    private Point getPointByRadiusAngle(float radius, float angle) {
        Point point = new Point();
        point.x = (int) (mCenterX + radius * Math.cos(angle * Math.PI / 180));
        //正弦曲线可表示为y=Asin(ωx+φ)+k,其中x为对应弧长来自于弧长=圆周长*圆心角度/360
        point.y = (int) (mCenterY + radius * Math.sin(angle * Math.PI / 180));
        return point;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mDefaultSineA = Math.min(mCenterX, mCenterY) / 20;  //波浪振幅为圆外面空白高度
        initRadius(Math.min(mCenterX, mCenterY) * 4 / 5);   //默认设置圆大小是控件小边的80%
    }

    private void init(Context context) {
        waveAnimator = ValueAnimator.ofFloat(0f, 180f);
        waveAnimator.setDuration(120000);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        waveAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveAnimator.addUpdateListener(animation -> {
            mDefaultSinePhase = (float) animation.getAnimatedValue();
            initRadius(mDefaultRadius);
            postInvalidate();
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmapPath != null && mBitmapPath.isEmpty() == false) {
            canvas.clipPath(mBitmapPath);
        }
        super.onDraw(canvas);
        if (hasMask) {
            canvas.drawColor(mMarkColor);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        waveAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        waveAnimator.end();
        super.onDetachedFromWindow();
    }

    public boolean isHasMask() {
        return hasMask;
    }

    public void setHasMask(boolean hasMask) {
        this.hasMask = hasMask;
    }

    public int getMarkColor() {
        return mMarkColor;
    }

    public void setMarkColor(int mMarkColor) {
        this.mMarkColor = mMarkColor;
    }
}
