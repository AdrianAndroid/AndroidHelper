package cn.kuwo.pp.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.R;

public class FeedBackFragment extends BaseFragment {

    public static FeedBackFragment newInstance(){
        return new FeedBackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        //_initView(view);
        return view;
    }

    void test() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        enableToolbar(R.id.toolbar, "问题和反馈", true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
