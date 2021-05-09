package cn.kuwo.common.base;

import android.os.Bundle;

public class BlankFragment extends BaseFragment {
    public static BlankFragment newInstance() {
        Bundle args = new Bundle();

        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }
}
