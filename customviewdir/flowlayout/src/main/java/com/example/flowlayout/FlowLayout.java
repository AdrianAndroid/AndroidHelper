package com.example.flowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距


    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于layout


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void clearMeasureParams() {
        allLines.clear(); //记录所有的行
        lineHeights.clear(); //记录总高度
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasure1(widthMeasureSpec, heightMeasureSpec);
    }

    //度量
    protected void onMeasure2(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams();//内存 抖动
        //先度量孩子
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);  //ViewGroup解析的父亲给我的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec); // ViewGroup解析的父亲给我的高度

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有的view
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int parentNeededWidth = 0;  // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededHeight = 0; // measure过程中，子View要求的父ViewGroup的高

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            LayoutParams childLP = childView.getLayoutParams();
            if (childView.getVisibility() != View.GONE) {
                //将layoutParams转变成为 measureSpec
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                        childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                        childLP.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                //获取子view的度量宽高
                int childMesauredWidth = childView.getMeasuredWidth();
                int childMeasuredHeight = childView.getMeasuredHeight();

                //如果需要换行
                if (childMesauredWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {

                    //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);

                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }
                // view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
                lineViews.add(childView);
                //每行都会有自己的宽和高
                lineWidthUsed = lineWidthUsed + childMesauredWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);

                //处理最后一行数据
                if (i == childCount - 1) {
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                }

            }
        }


        //再度量自己,保存
        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;
        setMeasuredDimension(realWidth, realHeight);
    }

    protected void onMeasure1(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams(); //防止多次绘制
        int childCount = getChildCount();
        // 这个ViewGroup的padding
        int pl = getPaddingLeft();
        int pr = getPaddingRight();
        int pb = getPaddingBottom();
        int pt = getPaddingTop();

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 测量所有的子View
        List<View> lineViews = new ArrayList<>(); //这一行View
        int lineWUsed = 0;//这一行已经用了的宽度
        int lineHUsed = 0;//已经用了的高度
        int parentNeededWidth = 0; // 要求父亲给的宽度
        int parentNeededHeight = 0; // 要求父亲给的高度
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i); //  在array中获取
            LayoutParams lp = childView.getLayoutParams(); //获取这个View的的LayoutParams参数，主要宽高
            if (childView.getVisibility() != View.GONE) { //只对可见的View测量
                int wSpec = getChildMeasureSpec(widthMeasureSpec, pl + pr, lp.width);
                int hSpec = getChildMeasureSpec(heightMeasureSpec, pt + pb, lp.height);
                childView.measure(wSpec, hSpec); // 往下测量

                // 获取ziView的宽高, 用于对比
                int childMeasureWidth = childView.getMeasuredWidth();
                int childMeasureHeight = childView.getMeasuredHeight();

                if (childMeasureWidth + lineWUsed + mHorizontalSpacing > sizeWidth) { // 满足一行的条件
                    allLines.add(lineViews);
                    lineHeights.add(lineHUsed);
                    parentNeededHeight = parentNeededHeight + lineHUsed + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWUsed + mHorizontalSpacing);

                    lineViews = new ArrayList<>(); //新建立一行
                    lineWUsed = 0; //从新记一行
                    lineHUsed = 0; //从新记一行
                }
                // 加入这一行
                lineViews.add(childView);
                lineWUsed = lineWUsed + childMeasureWidth + mHorizontalSpacing;
                lineHUsed = Math.max(lineHUsed, childMeasureHeight); //用这一行最高的高度

                // 处理最后一行不满的的
                if (i == childCount - 1) {
                    allLines.add(lineViews);
                    lineHeights.add(lineHUsed);
                    parentNeededHeight = parentNeededHeight + lineHUsed + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWUsed + mHorizontalSpacing);
                }
            }
        }

        // 给自己设置
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(
                (modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : parentNeededWidth,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : parentNeededHeight
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size(); //所有的行
        int curL = getPaddingLeft();
        int curT = getPaddingRight();
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);

            Integer lineHeight = lineHeights.get(i); //这一行最高的高度
            for (int j = 0; j < lineViews.size(); j++) { //这一行所有的View
                View view = lineViews.get(j);
                int left = curL;
                int top = curT;

                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft(); //重新记
        }
    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
