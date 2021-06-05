package com.example.touchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 1、三个构造方法（一个参数， 两个参数， 三个参数）
 * 2、onMesure测量控件
 * 4、onLayout分配控件布局
 * 5、computeScroll() 计算滑动
 * 6、onDraw绘制控件
 * 7、onTouchEvent() 中断事件传递
 * 8、dispatchTouchEvent 分发事件
 * ————————————————
 * 版权声明：本文为CSDN博主「新梦起航」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/ydxzmhy/article/details/51693384
 */
public class MyViewPager extends ViewGroup {

    private GestureDetector mGestureDetector;
    private Scroller mScroller;

    public MyViewPager(Context context) {
        super(context);
        // 创建手势识别器
        initView();
    }

    //  如果没有两个参数构造方法 是不允许在布局文件中声明控件
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 创建手势识别器
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 创建手势识别器
        initView();
    }

    //手势识别监听器
    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        //滑动事件
        //distanceX x轴滑动的距离
        //distanceY y轴滑动的距离
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            scrollBy((int) distanceX, 0); //让viewGroup 移动多少距离
            //scrollBy  会自动调用invalidate() 该方法
            //invalidate();   自动调用onDraw
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void initView() {

        mGestureDetector = new GestureDetector(getContext(), new MySimpleOnGestureListener());

    }

    // 定义一个公开接口，设置回调方法
    public interface OnPageChangedListener {
        void onChange(int index);
    }

    private OnPageChangedListener mOnpageChangedListener;

    //定义一个公开的注册页面改变的方法
    public void setOnpageChangedListener(OnPageChangedListener listener) {
        mOnpageChangedListener = listener;
    }

    //  中断事件传递
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGestureDetector.onTouchEvent(ev); // 避免了中断事件 导致没有处理按下的操作
                startX2 = (int) ev.getX();
                startY2 = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: // 手指移动的事件
                int endX2 = (int) ev.getX();
                int endY2 = (int) ev.getY();
                int dx = endX2 - startX2;  // x轴的偏移量
                int dy = endY2 - startY2;  //y轴的偏移量

                if (Math.abs(dx) > Math.abs(dy)) { //如果为左右移动则中断手势事件响应的传递
                    return true;
                }
                break;
            default:
                break;
        }
        // 如果是上下滑动 屏幕的时候 不中断事件
        //  return false;
        //如果是左右滑动 中断事件
        // return true;
        //交给父类判断（即交给ViewGroup判断）父类的该方法返回值为false 不中断事件
        return super.onInterceptTouchEvent(ev);
    }

    private int startX2;
    private int startY2;

    private int index = 0; // 当前显示的位置
    private int startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event); //把手势识别器注册到触摸事件中
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当手指按下的时候 记录开始的坐标
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起的时候 记录结束的坐标
                int endX = (int) event.getX();

                if ((startX - endX) > getWidth() / 2) {
                    //进入下一个界面
                    index++;
                } else if ((endX - startX) > getWidth() / 2) {
                    // 进入上一个界面
                    index--;
                }
                moveToIndex();
                break;
            default:
                break;
        }
        //返回处理了触摸事件
        return true;

    }

    //外界通过指定索引将页面切换到指定的位置
    public void moveToIndex(int index) {
        this.index = index;
        moveToIndex();
    }

    private void moveToIndex() {
        if (index < 0) {
            index = 0;
        }
        if (index == getChildCount()) {
            index = getChildCount() - 1;
        }
        if (mOnpageChangedListener != null) {
            mOnpageChangedListener.onChange(index);
        }
        mScroller = new Scroller(getContext());
        mScroller.startScroll(getScrollX(), getScrollY(), (int) (getWidth() * index - getScrollX()), 0);
        invalidate();
    }

    //计算移动   每次刷新界面 该方法都会被调用
    //scroller.computeScrollOffset()   返回值是true 情况下  代表动作没有结束
    @Override
    public void computeScroll() {
        if (mScroller != null) {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.getCurrX(), 0);  //scrollTo这个方法一执行 会调用invalidate();,异步执行
                invalidate();
            }
        }
        super.computeScroll();
    }

    //  onMeasure  会在onLayout 之前调用
    // 要求父容器一定要测量子容器 ,如果不测量 子容器 子容器宽和高 都是0   子容器由于挂载到父容器可以正常显示,但是 孙子就不能显示
    // 父容器先知道自己大小（match_parent） 子容器先知道大小(wrap_content)
    //widthMeasureSpec不仅表示控件的宽，里面还带有控件的属性的基本信息
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("onMeasure");
        System.out.println(widthMeasureSpec);
        MeasureSpec.getMode(widthMeasureSpec); //获取控件的模型
        MeasureSpec.getSize(widthMeasureSpec); // 得到控件真正的尺寸
        System.out.println(heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);// 对每个孩子都测量
        }
    }

    //  分配孩子位置     在onDraw方法之前调用
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(0 + getWidth() * i, 0, getWidth() + getWidth() * i, getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}