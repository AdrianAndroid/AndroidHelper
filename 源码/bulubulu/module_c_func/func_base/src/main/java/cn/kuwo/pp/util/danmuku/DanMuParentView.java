package cn.kuwo.pp.util.danmuku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.util.danmuku.view.IDanMuParent;

/**
 * 弹幕需要处理事件的，请使用DanMuParentView作为DanMuView的根布局
 *
 * Created by android_ls on 2016/12/7.
 */

public class DanMuParentView extends RelativeLayout {

    public DanMuParentView(Context context) {
        super(context);
    }

    public DanMuParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 遍历所有的弹幕
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof IDanMuParent) { // 如果是弹幕消息的话
                if (((IDanMuParent) view).hasCanTouchDanMus()) {
                    view.bringToFront(); // 放到最上面
                    // 向父类请求
                    if(App.DEBUG) L.m(getClass(), "DanMu requestDisallowInterceptTouchEvent", false);
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    moveChildToBack(view); // 移动到最后边
                }
            }
        }
        boolean b = super.onInterceptTouchEvent(ev);
        if(App.DEBUG) L.m(getClass(), "DanMu onInterceptTouchEvent", b);
        return b;
    }

    public void moveChildToBack(View child) {
        if(App.DEBUG) L.m(getClass(), "DanMu");
        int index = indexOfChild(child);
        if (index > 0) {
            detachViewFromParent(index); //
            attachViewToParent(child, 0, child.getLayoutParams());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(App.DEBUG) L.m(getClass(), "DanMu");
        return super.onTouchEvent(event);
    }

}
