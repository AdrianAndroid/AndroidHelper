package cn.kuwo.common.dialog;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.kuwo.common.app.App;

/**
 * Created by shihc on 2017/1/11.
 */

public abstract class BaseBottomDialog extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM; //底部
        window.setAttributes(lp);
    }


    //https://blog.csdn.net/qq_37599958/article/details/89342708
    protected void setVerticalDragListener(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {

            private int lastY;
            private float offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event == null) {
                    return false;
                }
                int y = (int) event.getRawY();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastY = (int) event.getRawY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    offsetY = y - lastY;
                    if (offsetY > 0) {
                        view.setTranslationY(offsetY); // 跟随手指移动
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (offsetY > 0) {
                        if (offsetY < view.getHeight() / 3) {
                            view.setTranslationY(0);
                        } else {
                            dismiss();
                        }
                    }
                }

                return true;
            }
        });
    }
}
