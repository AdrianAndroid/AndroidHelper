package cn.kuwo.common.view;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.kuwo.common.R;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;

/**
 * Created by wanghaofei on 17/6/29.
 */

public class UseHeadImageLayout extends ViewGroup {

    private Context context;

    private List<String> allUrls = new ArrayList<>();

    private boolean flag = false;//false表示右边增加，true表示左边增加
    private boolean smallMode = false;
    private boolean moreFirst = false;

    private int spWidth = UtilsCode.INSTANCE.dp2px(10);

    //在new的时候会调用此方法
    public UseHeadImageLayout(Context context) {
        super(context);
        this.context = context;
    }

    public UseHeadImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public UseHeadImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public interface ImageLayoutClickListener {
        void onImageClick(boolean isMore, int index);
    }

    public void setClickUserHeadListener(ImageLayoutClickListener clickUserHeadListener) {
        this.clickUserHeadListener = clickUserHeadListener;
    }

    public ImageLayoutClickListener clickUserHeadListener;

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
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }


    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();

        int childWidth = 0;
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin - spWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //反序显示
            if (flag) {
                Collections.reverse(lineViews);
            }
//            Collections.reverse(lineViews);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !moreFirst) {
                    child.setTranslationZ((float) (lineViews.size() - j));
                }

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin - spWidth;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setSpWidth(int spWidth) {
        this.spWidth = spWidth;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setUrls(List<String> listVals) {
        allUrls.addAll(listVals);
        //开始绘制view
        setViews();
    }

    public void removeAllUrls() {
        allUrls.clear();
    }

    public void setOneUrls(String urlVal) {
        allUrls.add(urlVal);
        setViews();
    }

    public void cancels(String urlVal) {
        allUrls.remove(urlVal);
        setViews();
    }

    public void setSmallMode() {
        smallMode = true;
    }

    public void setMoreFirst(boolean first) {
        moreFirst = first;
    }

    private void setViews() {
        //清空，重新绘制
        removeAllViews();
        addUrlView();
    }

    private void addUrlView() {
        for (int i = 0; i < allUrls.size(); i++) {
            //清空重新绘制

            ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(smallMode ? R.layout.item_user_head_image_small : R.layout.item_user_head_image, this, false);

            ImageLoader.showCircleImage(imageView, allUrls.get(i));
            this.addView(imageView);

            imageView.setTag(i);
            imageView.setClickable(true);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickUserHeadListener != null) {
                        clickUserHeadListener.onImageClick(false, (int) view.getTag());
                    }
                }
            });
        }
    }

//    private void addMoreView(){
//        //添加更多
//        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(smallMode ? R.layout.item_user_head_image_small : R.layout.item_user_head_image,  this, false);
//        imageView.setImageResource(R.drawable.icon_more);
//        this.addView(imageView);
//
//        imageView.setTag(allUrls.size());
//        imageView.setClickable(true);
//        imageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(clickUserHeadListener != null){
//                    clickUserHeadListener.onImageClick(true, allUrls.size());
//                }
//            }
//        });
//    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

