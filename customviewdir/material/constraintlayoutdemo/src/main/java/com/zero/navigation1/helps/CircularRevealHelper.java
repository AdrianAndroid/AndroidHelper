package com.zero.navigation1.helps;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class CircularRevealHelper extends ConstraintHelper {
    public CircularRevealHelper(Context context) {
        super(context);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updatePostLayout(ConstraintLayout container) {
        super.updatePostLayout(container);
        //view  动画
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View[] views  = getViews(container);
            for(int i = 0; i < views.length; i++){
                View v = views[i];
                Animator animator = ViewAnimationUtils.createCircularReveal(v,
                       v.getMeasuredWidth()/2,v.getMeasuredHeight()/2,
                       50,50 );

                animator.setDuration(3000);
                animator.start();
            }
        }
    }

    @Override
    public void updatePreLayout(ConstraintLayout container) {
        super.updatePreLayout(container);
    }
}
