package me.yokeyword.fragmentation;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Base class for activities that use the support-based
 * {@link ISupportFragment} and
 * {@link Fragment} APIs.
 * Created by YoKey on 17/6/22.
 */
public class SupportFragment extends Fragment implements ISupportFragment {
    final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);
    protected SupportActivity _mActivity;

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    @Override
    public ExtraTransaction extraTransaction() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.extraTransaction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onAttach();
        _mActivity = (SupportActivity) mDelegate.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onPause();
    }

    @Override
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.setUserVisibleHint(isVisibleToUser);
    }


    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     */
    @Override
    public void post(Runnable runnable) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.post(runnable);
    }

    /**
     * Called when the enter-animation end.
     * 入栈动画 结束时,回调
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onEnterAnimationEnd(savedInstanceState);
    }


    /**
     * Lazy initial，Called when fragment is first called.
     * <p>
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onLazyInitView(savedInstanceState);
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportVisible() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onSupportVisible();
    }

    /**
     * Called when the fragment is invivible.
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportInvisible() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onSupportInvisible();
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    @Override
    final public boolean isSupportVisible() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.isSupportVisible();
    }

    /**
     * Set fragment animation with a higher priority than the ISupportActivity
     * 设定当前Fragmemt动画,优先级比在SupportActivity里高
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.onCreateFragmentAnimator();
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.getFragmentAnimator();
    }

    /**
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.onBackPressedSupport();
    }

    /**
     * 类似 {@link Activity#setResult(int, Intent)}
     * <p>
     * Similar to {@link Activity#setResult(int, Intent)}
     *
     * @see #startForResult(ISupportFragment, int)
     */
    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.setFragmentResult(resultCode, bundle);
    }

    /**
     * 类似  {@link Activity#onActivityResult(int, int, Intent)}
     * <p>
     * Similar to {@link Activity#onActivityResult(int, int, Intent)}
     *
     * @see #startForResult(ISupportFragment, int)
     */
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     * 类似 {@link Activity#onNewIntent(Intent)}
     * <p>
     * Similar to {@link Activity#onNewIntent(Intent)}
     *
     * @param args putNewBundle(Bundle newBundle)
     * @see #start(ISupportFragment, int)
     */
    @Override
    public void onNewBundle(Bundle args) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onNewBundle(args);
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see #start(ISupportFragment, int)
     */
    @Override
    public void putNewBundle(Bundle newBundle) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.putNewBundle(newBundle);
    }


    /****************************************以下为可选方法(Optional methods)******************************************************/

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.hideSoftInput();
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected void showSoftInput(final View view) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.showSoftInput(view);
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    public void loadRootFragment(int containerId, ISupportFragment toFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim);
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment... toFragments) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.loadMultipleRootFragment(containerId, showPosition, toFragments);
    }

    /**
     * show一个Fragment,hide其他同栈所有Fragment
     * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
     * <p>
     * 建议使用更明确的{@link #showHideFragment(ISupportFragment, ISupportFragment)}
     *
     * @param showFragment 需要show的Fragment
     */
    public void showHideFragment(ISupportFragment showFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.showHideFragment(showFragment);
    }

    /**
     * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
     */
    public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.showHideFragment(showFragment, hideFragment);
    }

    public void start(ISupportFragment toFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    public void start(final ISupportFragment toFragment, @LaunchMode int launchMode) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startForResult(toFragment, requestCode);
    }

    /**
     * Start the target Fragment and pop itself
     */
    public void startWithPop(ISupportFragment toFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startWithPop(toFragment);
    }

    /**
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }


    public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    public void pop() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.pop();
    }

    /**
     * Pop the child fragment.
     */
    public void popChild() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popChild();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     * <p>
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    /**
     * 得到位于栈顶Fragment
     */
    public ISupportFragment getTopFragment() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.getTopFragment(getFragmentManager());
    }

    public ISupportFragment getTopChildFragment() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    /**
     * @return 位于当前Fragment的前一个Fragment
     */
    public ISupportFragment getPreFragment() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.getPreFragment(this);
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.findFragment(getFragmentManager(), fragmentClass);
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findChildFragment(Class<T> fragmentClass) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.findFragment(getChildFragmentManager(), fragmentClass);
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLifecycle();
    }

    @NonNull
    @Override
    public LifecycleOwner getViewLifecycleOwner() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getViewLifecycleOwner();
    }

    @NonNull
    @Override
    public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getViewLifecycleOwnerLiveData();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getViewModelStore();
    }

    public SupportFragment() {
        super();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    public SupportFragment(int contentLayoutId) {
        super(contentLayoutId);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public String toString() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.toString();
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setInitialSavedState(@Nullable SavedState state) {
        super.setInitialSavedState(state);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Context getContext() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getContext();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean getUserVisibleHint() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getUserVisibleHint();
    }

    @NonNull
    @Override
    public LoaderManager getLoaderManager() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLoaderManager();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onGetLayoutInflater(savedInstanceState);
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater(@Nullable Bundle savedFragmentState) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLayoutInflater(savedFragmentState);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public View getView() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        super.onOptionsMenuClosed(menu);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void registerForContextMenu(@NonNull View view) {
        super.registerForContextMenu(view);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void unregisterForContextMenu(@NonNull View view) {
        super.unregisterForContextMenu(view);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onContextItemSelected(item);
    }

    @Override
    public void setEnterSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setExitSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getEnterTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getEnterTransition();
    }

    @Override
    public void setReturnTransition(@Nullable Object transition) {
        super.setReturnTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getReturnTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getReturnTransition();
    }

    @Override
    public void setExitTransition(@Nullable Object transition) {
        super.setExitTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getExitTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExitTransition();
    }

    @Override
    public void setReenterTransition(@Nullable Object transition) {
        super.setReenterTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getReenterTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getReenterTransition();
    }

    @Override
    public void setSharedElementEnterTransition(@Nullable Object transition) {
        super.setSharedElementEnterTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getSharedElementEnterTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSharedElementEnterTransition();
    }

    @Override
    public void setSharedElementReturnTransition(@Nullable Object transition) {
        super.setSharedElementReturnTransition(transition);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object getSharedElementReturnTransition() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSharedElementReturnTransition();
    }

    @Override
    public void setAllowEnterTransitionOverlap(boolean allow) {
        super.setAllowEnterTransitionOverlap(allow);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean getAllowEnterTransitionOverlap() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getAllowEnterTransitionOverlap();
    }

    @Override
    public void setAllowReturnTransitionOverlap(boolean allow) {
        super.setAllowReturnTransitionOverlap(allow);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean getAllowReturnTransitionOverlap() {
        if (BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getAllowReturnTransitionOverlap();
    }

    @Override
    public void postponeEnterTransition() {
        super.postponeEnterTransition();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startPostponedEnterTransition() {
        super.startPostponedEnterTransition();
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
        if (BuildConfig.DEBUG) F.m(getPrintClass());
    }

    public Class<?> getPrintClass() {
        return getClass();
    }
}
