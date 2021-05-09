package cn.kuwo.common.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private boolean canScroll = true; //true 代表能滑动 //false 代表不能滑动

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (canScroll)
            return super.onTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (canScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (canScroll) {
            return super.canScrollVertically(direction);
        } else {
            return false;
        }
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        if (canScroll) {
            return super.canScrollHorizontally(direction);
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);//表示切换的时候，不需要切换时间。
    }

}