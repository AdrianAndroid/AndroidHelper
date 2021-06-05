package com.zhl.userguideview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public final class GuideUtils {

    public static void showGuideAtTop(FrameLayout frameLayout, View anchorView, final AnimGuideView.OnGuideListener listener) {
        for (int childCount = frameLayout.getChildCount(); childCount > frameLayout.getChildCount() - 3 /*就检查2层*/; childCount--) {
            if (frameLayout.getChildAt(childCount) instanceof AnimGuideView) {
                frameLayout.removeViewAt(childCount);
            }
        }
        // 创建Anim
        final AnimGuideView animGuideView = new AnimGuideView(frameLayout.getContext());
        animGuideView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        frameLayout.addView(animGuideView);
        // 播放动画
        animGuideView.setHighLightView(anchorView);
        //animGuideView.setStatusBarHeight(MeasureUtil.getStatuBarHeight(activity));
        animGuideView.startHandsAnimate();
        animGuideView.bringToFront();//放到最前面
        animGuideView.setOnGuideListener(new AnimGuideView.OnGuideListener() {
            @Override
            public void onTouched(final AnimGuideView view, AnimGuideView.TYPE type) {
                if (listener != null) listener.onTouched(view, type);
            }
        });
    }


    // 在Activity显示最上面
    public static void showGuideAtTop(Activity act, View anchorView, final AnimGuideView.OnGuideListener listener) {
        Activity activity = new WeakReference<Activity>(act).get();
        if (activity == null) return;
        if (anchorView == null) return;
        View viewById = activity.findViewById(android.R.id.content);
        if (viewById instanceof FrameLayout) {
            showGuideAtTop((FrameLayout) viewById, anchorView, listener);
        }
    }

    public static void dismissGuideView(final AnimGuideView view) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha"
                , 1F, 0.9F, 0.8F, 0.7F, 0.6F, 0.5F, 0.4F, 0.3F, 0.2F, 0.1F, 0F)
                .setDuration(200);
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view != null && view.getParent() != null) {
                    try {
                        ((FrameLayout) view.getParent()).removeView(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 设置alpha
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alpha.start();
    }

    private static View findTopView(FrameLayout layout) {
        if (layout != null
                && layout.getChildCount() > 0) {
            return layout.getChildAt(layout.getChildCount() - 1);
        }
        return null;
    }

    // 移除最上层的AnimGuideView
    public static void dismisssTopGuideView(Activity act) {
        Activity o = new WeakReference<>(act).get();
        View viewById = o.findViewById(android.R.id.content);
        if (viewById instanceof FrameLayout) {
            View topView = findTopView((FrameLayout) viewById);
            if (topView instanceof AnimGuideView) {
                ((FrameLayout) viewById).removeView(topView);
            }
        }
    }

}
