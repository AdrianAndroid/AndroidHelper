package cn.kuwo.common.view;

import android.widget.TextView;

/**
 * Created by shihc on 2017/3/15.
 */

public class DrawableUtil {
    public static void drawableLeft(TextView textView, int resourceId) {
        if (textView == null){
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0);
    }
    public static void drawableTop(TextView textView, int resourceId) {
        if (textView == null){
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(0, resourceId, 0, 0);
    }
    public static void drawableRight(TextView textView, int resourceId) {
        if (textView == null){
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resourceId, 0);
    }
    public static void drawableBottom(TextView textView, int resourceId) {
        if (textView == null){
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resourceId);
    }
}
