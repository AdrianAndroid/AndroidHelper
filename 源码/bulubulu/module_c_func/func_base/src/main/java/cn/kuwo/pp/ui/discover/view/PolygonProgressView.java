package cn.kuwo.pp.ui.discover.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.utilscode.UtilsCode;

/**
 * Created by wanghaofei on 17/6/29.
 */

public class PolygonProgressView extends ViewGroup {
    private boolean smallMode = false;
    private int spaceWidth = UtilsCode.INSTANCE.dp2px((float) 4.5);
    private int centerSpaceWidth = UtilsCode.INSTANCE.dp2px(80);

    public PolygonProgressView(Context context) {
        super(context);
        setViews();
    }

    public PolygonProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setViews();
    }

    public PolygonProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;

        //每一行的，宽，高
        int lineWidth = 0;
        int lineHeight = 0;
        //获取所有的子view
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);

            if (i == cCount - 1) {
                width = Math.max(lineWidth, width) + centerSpaceWidth;
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > mAllViews.size()) {
            return;
        }

        for (int i = 0; i < mAllViews.size(); i++) {
            PolygonView view = (PolygonView) mAllViews.get(i);
            view.setHighLight(i <= progress - 1);
        }
    }

    /**
     * 存储所有的View
     */
    private ArrayList<View> mAllViews = new ArrayList<View>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        int childWidth = 0;
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin + spaceWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            mAllViews.add(child);
        }

        // 设置子View的位置

        lineWidth += centerSpaceWidth;
        int left = getPaddingLeft() + (getWidth() - lineWidth) / 2;
        int top = getPaddingTop();

        for (int j = 0; j < mAllViews.size(); j++) {
            View child = mAllViews.get(j);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);

            left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + spaceWidth;
            if (j == mAllViews.size() / 2 - 1) {
                left += centerSpaceWidth + spaceWidth;
            }
        }
    }

    public void setSpaceWidth(int spaceWidth) {
        this.spaceWidth = spaceWidth;
    }

    public void setSmallMode() {
        smallMode = true;
    }

    private void setViews() {
        removeAllViews();
        addUrlView();
    }

    private void addUrlView() {
        for (int i = 0; i < 10; i++) {
            PolygonView polygonView = new PolygonView(getContext());
            polygonView.setLayoutParams(new LinearLayout.LayoutParams(UtilsCode.INSTANCE.dp2px(22), UtilsCode.INSTANCE.dp2px(5)));
            this.addView(polygonView);
            polygonView.setTag(i);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

