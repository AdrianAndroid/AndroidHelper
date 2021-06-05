package com.tencent.qcloud.uikit.operation.group;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.qcloud.uikit.R;


public class GroupManagerActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_manager_activity);
        GroupInfoFragment fragment = new GroupInfoFragment();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.group_manager_base, fragment).commitAllowingStateLoss();
    }


    @Override
    public void finish() {
        super.finish();
        setResult(1001);
    }
}
