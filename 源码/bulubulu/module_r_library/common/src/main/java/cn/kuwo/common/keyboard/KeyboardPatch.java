package cn.kuwo.common.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class KeyboardPatch {

    private Activity activity;
    private View decorView;
    private View contentView;
    private View moveView;

    public KeyboardPatch(Activity activity, View contentView, View moveView) {
        this.activity = new WeakReference<>(activity).get();
        this.decorView = this.activity.getWindow().getDecorView();
        this.contentView = new WeakReference<>(contentView).get();
        this.moveView = new WeakReference<>(moveView).get();
    }

    public void enable() {
        if (false) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            if (Build.VERSION.SDK_INT >= 19) {
                decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            }
        }
    }

    /**
     * 取消监听
     */
    public void disable() {
        if (false) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            if (Build.VERSION.SDK_INT >= 19) {
                decorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
            }
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect(); //View在其中的位置
            contentView.getWindowVisibleDisplayFrame(r);
            int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
            int diff = height - r.bottom;
            if (diff != 0) {
                if (moveView.getTranslationY() != diff) {
                    //contentView.setPadding(0, 0, 0, diff);
                    moveView.setTranslationY(-diff);
                }
            } else {
                if (moveView.getTranslationY() != 0) {
//                    contentView.setPadding(0, 0, 0, 0);
                    moveView.setTranslationY(0);
                }
            }
        }
    };

}
