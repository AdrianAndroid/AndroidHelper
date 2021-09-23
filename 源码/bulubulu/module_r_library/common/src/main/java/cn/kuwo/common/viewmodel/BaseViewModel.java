package cn.kuwo.common.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import cn.kuwo.common.BuildConfig;

public class BaseViewModel extends ViewModel {
    @Override
    protected void onCleared() {
        super.onCleared();
        if (BuildConfig.DEBUG) {
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName() + "(" + getClass().getSimpleName() + ".java:60)");
        }
    }
}
