package cn.kuwo.common.base;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.kuwo.common.BuildConfig;
import cn.kuwo.common.R;
import cn.kuwo.common.app.App;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.event.ConnectStatusEvent;
import cn.kuwo.common.event.LoginEvent;
import cn.kuwo.common.event.LogoutEvent;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.CustomToolbar;
import cn.kuwo.common.viewmodel.MainViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

//import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @author shihc
 */
public class BaseFragment extends SwipeBackFragment implements BaseView {
    protected View mRootView;

//    private MaterialDialog mLoadingDialog;

    protected CustomToolbar mToolbar;

//    protected Unbinder mUnbinder;

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    private int mSoftInputMode;//activity的默认弹出模式，用户子fragment修改mode之后的自动恢复

    protected boolean mIsFirstVisible = true;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private BroadcastReceiver mBroadcastReceiver;

    // 设置Toolbar
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        if (_mActivity != null) {
            _mActivity.setSupportActionBar(toolbar);
        }
        //setHasOptionsMenu(true);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (App.DEBUG)
            L.m(getPrintClass(), childFragment.getClass().getSimpleName());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
        mRootView = view;
        if (getStatusBarOffsetView() != null) {
            ImmersionBar.setTitleBarMarginTop(_mActivity, getStatusBarOffsetView());
        }
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
    }

    @ColorInt
    public final int getColor(@ColorRes int id) {
        return ContextCompat.getColor(_mActivity.getApplicationContext(), id);
    }

    @Nullable
    public final Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(_mActivity.getApplicationContext(), id);
    }

    @Nullable
    public float getDimension(@DimenRes int id) {
        return getResources().getDimension(id);
    }


    /**
     * 用于处理沉浸式状态栏，默认布局是到手机屏幕顶端，在状态栏之下，所以要某个view设置间距，使布局不和状态栏重叠
     * 默认用Toolbar进行处理，如果没有Toolbar，请重写该方法，返回需要处理的view
     * 也可以自己在布局中使用使用 R.dimen.statusbar_view_height进行处理
     *
     * @return
     */
    protected View getStatusBarOffsetView() {
        return mRootView.findViewById(R.id.toolbar);
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog("正在加载");
    }

    @Override
    public void showLoadingDialog(String title) {

        DialogUtils.INSTANCE.showLoading(title, this);
//        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
//            mLoadingDialog = new MaterialDialog.Builder(_mActivity)
//                    .content(title)
//                    .progress(true, 0)
//                    .canceledOnTouchOutside(true)
//                    .cancelable(true)
//                    .show();
//            mLoadingDialog.setOnKeyListener((dialog, keyCode, event) -> {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    return onBackPressedSupport();
//                }
//                return false;
//            });
//        }
//        if (mLoadingDialog.isShowing()) {
//            mLoadingDialog.setContent(title);
//        } else {
//            mLoadingDialog.show();
//        }
//        return mLoadingDialog;
    }

    @Override
    public void showLoadingDialog(int titleId) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        showLoadingDialog(getString(titleId));
    }

    @Override
    public void dismissLoadingDialog() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
//        if (mLoadingDialog != null && isAdded()) {
//            try {
//                mLoadingDialog.dismiss();
//                mLoadingDialog.setOnKeyListener(null);
//                mLoadingDialog = null;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        DialogUtils.INSTANCE.dismissDialog();
    }

    @Override
    public void showEmptyView(CharSequence content) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }

    }

    @Override
    public void showErrorView(int errorCode, String error) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        if (isPageStatisticsEnable()) {
            //MobclickAgent.onPageStart(getPageName());

        }
        //有父fragment，代表是viewpager中的子fragment，忽略
        if (getParentFragment() != null) {
            return;
        }
        //通知activity，有独立的fragment显示
        ((BaseActivity) _mActivity).onFragmentVisible(this);
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        if (isPageStatisticsEnable()) {
            //MobclickAgent.onPageEnd(getPageName());
        }
        mIsFirstVisible = false;
    }

    @Override
    public void onChildSupportVisible() {
        super.onChildSupportVisible();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onChildSupportInvisible() {
        super.onChildSupportInvisible();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    /**
     * 是否需要界面时长统计，拥挤操作路径分析，viewpager类不需要
     *
     * @return
     */
    protected boolean isPageStatisticsEnable() {
        // flannery 这里有个地方调用错误，可能会崩溃
        return false;
    }


    /**
     * 界面名称，用于统计
     *
     * @return
     */
    protected String getPageName() {
        return this.getClass().getName();
    }

    /**
     * 是否显示主页右上角播放视图，点击播放视图可以跳到
     *
     * @return true:显示，false：不显示
     */
    public boolean showMainPlayView() {
        return true;
    }

    public CustomToolbar enableToolbar(int id) {
        return enableToolbar(id, "", true);
    }

    public CustomToolbar enableToolbar(int id, int title) {
        return enableToolbar(id, getString(title), true);
    }

    public CustomToolbar enableToolbar(int id, String title) {
        return enableToolbar(id, title, true);
    }

    public CustomToolbar enableToolbar(int id, int title, boolean showBackBtn) {
        return enableToolbar(id, getString(title), showBackBtn);
    }

    public CustomToolbar enableToolbar(int id, boolean showBackBtn) {
        return enableToolbar(id, "", showBackBtn);
    }

    /**
     * 初始化Toolbar
     *
     * @param id          Toolbar的id
     * @param title       Toolbar title的资源id
     * @param showBackBtn 是否显示返回按钮
     * @return
     */
    public CustomToolbar enableToolbar(int id, CharSequence title, boolean showBackBtn) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        mToolbar = mRootView.findViewById(id);
        if (mToolbar != null) {
            if (UtilsCode.INSTANCE.isEmpty(title)) {
                title = "";
            }
            mToolbar.setTitle(title);
            setDisplayHomeAsUpEnabled(showBackBtn);
        }
        return mToolbar;
    }

    private void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        if (enabled) {
            mToolbar.getToolbar().setNavigationOnClickListener(v -> onBackBtnClicked(v));
        } else {
            mToolbar.getToolbar().setNavigationIcon(null);
        }
    }

    /**
     * Toolbar 返回按钮点击事件，默认处理是关闭当前界面
     *
     * @param view
     */
    protected void onBackBtnClicked(View view) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        pop();
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
        if (App.DEBUG) L.m(getPrintClass(), getOtherTag());
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }


    @Override
    public void onDestroyView() {
        hideSoftInput();
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        EventBus.getDefault().unregister(this);
        if (mSoftInputMode > 0) {
            _mActivity.getWindow().setSoftInputMode(mSoftInputMode);
            mSoftInputMode = 0;
        }
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
        }
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        if (mToolbar != null && mToolbar.getToolbar() != null) {
            mToolbar.getToolbar().setNavigationOnClickListener(null);
        }
        super.onDestroy();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
//        if (mUnbinder != null) {
//            mUnbinder.unbind();
//        }
    }

    public ViewModelStoreOwner getViewModelStoreOwner(Class<? extends Fragment> aClass) {
        if (aClass == null) {
            return null;
        }
        String aName = aClass.getSimpleName();
        Fragment f = this;
        while (f != null) {
            // 满足条件，就是它了
            if (aName.equals(f.getClass().getSimpleName())) break;
            // 否则向上找
            f = f.getParentFragment();
        }
        return f;
    }


    public ViewModelStoreOwner getParentViewModelStoreOwner() {
        return getParentFragment();
    }

    public ViewModelStoreOwner getParantParentViewModelStoreOwner() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null) {
            return parentFragment.getParentFragment();
        }
        return parentFragment;
    }

    @NonNull
    @MainThread
    public <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(_mActivity).get(modelClass);
    }

    // 获取MainViewModel
    public MainViewModel getMainViewModel() {
        return getActivityViewModel(MainViewModel.class);
    }

    public void startFromMain(BaseFragment toFragment) {
        getActivityViewModel(MainViewModel.class).start.setValue(toFragment);
    }

    public void startSingleTaskFromMain(BaseFragment toFragment) {
        getActivityViewModel(MainViewModel.class).startSingleTask.setValue(toFragment);
    }

    public void startWithPopFromMain(BaseFragment toFragment) {
        getActivityViewModel(MainViewModel.class).startWithPop.setValue(toFragment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        onLoginSuccess();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        onLogoutSuccess();
    }

    protected void onLoginSuccess() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    protected void onLogoutSuccess() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectedChanged(ConnectStatusEvent event) {
        onConnectChanged();
    }

    /**
     * 有网络调用都会调用这里，需要适当使用
     */
    protected void onConnectChanged() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }


    protected void setSoftInputMode(int mode) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        mSoftInputMode = _mActivity.getWindow().getAttributes().softInputMode;
        _mActivity.getWindow().setSoftInputMode(mode);
    }

    public boolean isLoadingDlgShowing() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return DialogUtils.INSTANCE.isLoadingShowing();
//        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        //https://www.cnblogs.com/mengdd/p/5590634.html
        //menu.clear();
        //inflater.inflate(0x0, menu);
    }


    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onBackPressedSupport();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.onCreateFragmentAnimator();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }


    @Override
    public View attachToSwipeBack(View view) {
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
        return super.attachToSwipeBack(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 在此处进行懒加载
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag(), "isVisibleToUser", isVisibleToUser);
        }
    }


    @Override
    public void pop() {
        super.pop();

        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popChild() {
        super.popChild();
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.popTo(targetFragmentClass, includeTargetFragment);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        super.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        super.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.popToChild(targetFragmentClass, includeTargetFragment);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        super.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        super.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void startWithPop(ISupportFragment toFragment) {
        super.startWithPop(toFragment);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void start(ISupportFragment toFragment) {
        super.start(toFragment);
        // 请使用startFromMain
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void start(ISupportFragment toFragment, int launchMode) {
        super.start(toFragment, launchMode);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        super.startForResult(toFragment, requestCode);
        if (App.DEBUG) {
            L.m(getPrintClass(), getOtherTag());
        }
    }


    public Class<?> getPrintClass() {
        return getClass();
    }

    public String getOtherTag() {
        return null;
    }
}
