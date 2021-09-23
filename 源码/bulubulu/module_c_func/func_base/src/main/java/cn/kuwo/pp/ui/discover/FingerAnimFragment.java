package cn.kuwo.pp.ui.discover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.R;
import cn.kuwo.pp.util.BubbleBackground;
import cn.kuwo.pp.util.FingerBackground;

public class FingerAnimFragment extends BaseFragment {
    private FingerBackground bubbleView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected View attachBackgroundView(View view) {
        if (view != null) {
            if (bubbleView == null) {
                bubbleView = new FingerBackground(getContext());
                bubbleView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                bubbleView.removeAllViews();
            }
            bubbleView.addView(view);
        }
        return bubbleView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (bubbleView != null) {
            bubbleView.animPause();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (bubbleView != null) {
            bubbleView.animResume();
        }
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
