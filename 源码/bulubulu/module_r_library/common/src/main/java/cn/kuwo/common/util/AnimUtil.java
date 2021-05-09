package cn.kuwo.common.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.Animation;

public class AnimUtil {

    /**
     * 带动画隐藏view
     *
     * @param v           目标view
     * @param setGone     true：GONE，false：INVISIBLE
     * @param immediately 是否跳过动画，立即执行
     */
    public static void animOut(final View v, final boolean setGone, final boolean immediately) {
        ViewExtensionKt.animOut(v, setGone, immediately);
    }

    /**
     * 带动画显示view
     *
     * @param v
     * @param immediately 是否跳过动画，立即执行
     */
    public static void animIn(final View v, boolean immediately) {
        ViewExtensionKt.animIn(v, immediately);
    }

    // 同时播放动画
    public static void playTogether(Animator.AnimatorListener listener, Animator... items) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(items);
        // 播放的动画
        animatorSet.addListener(listener);
        animatorSet.start();
    }


}
