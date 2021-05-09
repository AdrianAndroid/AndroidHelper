package cn.kuwo.common.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cn.kuwo.common.base.BaseFragment;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<BaseFragment> start = new MutableLiveData<>();
    public MutableLiveData<BaseFragment> startSingleTask = new MutableLiveData<>(); //SINGLETASK

    public MutableLiveData<BaseFragment> startWithPop = new MutableLiveData<>();

    @Deprecated
    public MutableLiveData<Class<?>> popTo = new MutableLiveData<>();

    // 切换登陆页面
//    onTabItemClicked
    public MutableLiveData<Integer> onTabItemClicked = new MutableLiveData<>();
    
//    pulibc MutableListData<Boola>
}
