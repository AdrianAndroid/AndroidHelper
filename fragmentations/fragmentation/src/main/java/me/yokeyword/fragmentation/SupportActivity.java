package me.yokeyword.fragmentation;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.app.DirectAction;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.SharedElementCallback;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;

import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Display;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Base class for activities that use the support-based
 * {@link ISupportActivity} and
 * {@link AppCompatActivity} APIs.
 * <p>
 * Created by YoKey on 17/6/20.
 */
public class SupportActivity extends AppCompatActivity implements ISupportActivity {
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    @Override
    public ExtraTransaction extraTransaction() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.extraTransaction();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    /**
     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
     */
    @Override
    final public void onBackPressed() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onBackPressed();
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.onBackPressedSupport();
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.getFragmentAnimator();
    }

    /**
     * Set all fragments animation.
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建Fragment转场动画
     * <p/>
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
     *
     * @return FragmentAnimator对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.post(runnable);
    }

    /****************************************以下为可选方法(Optional methods)******************************************************/

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnimation) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation);
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment... toFragments) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
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
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.showHideFragment(showFragment);
    }

    /**
     * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
     */
    public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.showHideFragment(showFragment, hideFragment);
    }

    /**
     * It is recommended to use {@link SupportFragment#start(ISupportFragment)}.
     */
    public void start(ISupportFragment toFragment) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.start(toFragment);
    }

    /**
     * It is recommended to use {@link SupportFragment#start(ISupportFragment, int)}.
     *
     * @param launchMode Similar to Activity's LaunchMode.
     */
    public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * It is recommended to use {@link SupportFragment#startForResult(ISupportFragment, int)}.
     * Launch an fragment for which you would like a result when it poped.
     */
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startForResult(toFragment, requestCode);
    }

    /**
     * It is recommended to use {@link SupportFragment#startWithPop(ISupportFragment)}.
     * Start the target Fragment and pop itself
     */
    public void startWithPop(ISupportFragment toFragment) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startWithPop(toFragment);
    }

    /**
     * It is recommended to use {@link SupportFragment#startWithPopTo(ISupportFragment, Class, boolean)}.
     *
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    /**
     * It is recommended to use {@link SupportFragment#replaceFragment(ISupportFragment, boolean)}.
     */
    public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    /**
     * Pop the fragment.
     */
    public void pop() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.pop();
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
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * Fragmentation默认使用Theme的android:windowbackground作为Fragment的背景,
     * 可以通过该方法改变其内所有Fragment的默认背景。
     */
    public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        mDelegate.setDefaultFragmentBackground(backgroundRes);
    }

    /**
     * 得到位于栈顶Fragment
     */
    public ISupportFragment getTopFragment() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }

    public SupportActivity() {
        super();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    public SupportActivity(int contentLayoutId) {
        super(contentLayoutId);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTheme(int resId) {
        super.setTheme(resId);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSupportActionBar();
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getMenuInflater();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.findViewById(id);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean supportRequestWindowFeature(int featureId) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.supportRequestWindowFeature(featureId);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onWindowStartingSupportActionMode(callback);
    }

    @Nullable
    @Override
    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startSupportActionMode(callback);
    }

    @Override
    public void setSupportProgressBarVisibility(boolean visible) {
        super.setSupportProgressBarVisibility(visible);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
        super.setSupportProgressBarIndeterminateVisibility(visible);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setSupportProgressBarIndeterminate(boolean indeterminate) {
        super.setSupportProgressBarIndeterminate(indeterminate);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setSupportProgress(int progress) {
        super.setSupportProgress(progress);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onPrepareSupportNavigateUpTaskStack(builder);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onSupportNavigateUp();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSupportParentActivityIntent();
    }

    @Override
    public boolean supportShouldUpRecreateTask(@NonNull Intent targetIntent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.supportShouldUpRecreateTask(targetIntent);
    }

    @Override
    public void supportNavigateUpTo(@NonNull Intent upIntent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        super.supportNavigateUpTo(upIntent);
    }

    @Override
    public void onContentChanged() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        super.onContentChanged();
    }

    @Override
    public void onSupportContentChanged() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        super.onSupportContentChanged();
    }

    @Nullable
    @Override
    public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getDrawerToggleDelegate();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, @NonNull Menu menu) {
        super.onPanelClosed(featureId, menu);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getDelegate();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass(), "action",event.getAction());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public Resources getResources() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getResources();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void closeOptionsMenu() {
        super.closeOptionsMenu();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setEnterSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setExitSharedElementCallback(@Nullable SharedElementCallback listener) {
        super.setExitSharedElementCallback(listener);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void supportPostponeEnterTransition() {
        super.supportPostponeEnterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void supportStartPostponedEnterTransition() {
        super.supportStartPostponedEnterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateView(parent, name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onPreparePanel(int featureId, @Nullable View view, @NonNull Menu menu) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onPreparePanel(featureId, view, menu);
    }


    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @NonNull
    @Override
    public FragmentManager getSupportFragmentManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSupportFragmentManager();
    }

    @NonNull
    @Override
    public LoaderManager getSupportLoaderManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSupportLoaderManager();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityFromFragment(@NonNull Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityFromFragment(@NonNull Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityFromFragment(fragment, intent, requestCode, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderFromFragment(@NonNull Fragment fragment, IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderFromFragment(fragment, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Nullable
    @Override
    public Object getLastCustomNonConfigurationInstance() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLastCustomNonConfigurationInstance();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLifecycle();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getViewModelStore();
    }

    @Override
    public Intent getIntent() {        if(BuildConfig.DEBUG) F.m(getPrintClass());

        return super.getIntent();
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public WindowManager getWindowManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getWindowManager();
    }

    @Override
    public Window getWindow() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getWindow();
    }

    @Override
    public android.app.LoaderManager getLoaderManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLoaderManager();
    }

    @Nullable
    @Override
    public View getCurrentFocus() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getCurrentFocus();
    }

    @Override
    public void registerActivityLifecycleCallbacks(@NonNull Application.ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(@NonNull Application.ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {
        super.onTopResumedActivityChanged(isTopResumedActivity);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean isVoiceInteraction() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isVoiceInteraction();
    }

    @Override
    public boolean isVoiceInteractionRoot() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isVoiceInteractionRoot();
    }

    @Override
    public VoiceInteractor getVoiceInteractor() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getVoiceInteractor();
    }

    @Override
    public boolean isLocalVoiceInteractionSupported() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isLocalVoiceInteractionSupported();
    }

    @Override
    public void startLocalVoiceInteraction(Bundle privateOptions) {
        super.startLocalVoiceInteraction(privateOptions);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void stopLocalVoiceInteraction() {
        super.stopLocalVoiceInteraction();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    @Nullable
    @Override
    public CharSequence onCreateDescription() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateDescription();
    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onProvideAssistContent(AssistContent outContent) {
        super.onProvideAssistContent(outContent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onGetDirectActions(@NonNull CancellationSignal cancellationSignal, @NonNull Consumer<List<DirectAction>> callback) {
        super.onGetDirectActions(cancellationSignal, callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPerformDirectAction(@NonNull String actionId, @NonNull Bundle arguments, @NonNull CancellationSignal cancellationSignal, @NonNull Consumer<Bundle> resultListener) {
        super.onPerformDirectAction(actionId, arguments, cancellationSignal, resultListener);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean showAssist(Bundle args) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.showAssist(args);
    }

    @Override
    public void reportFullyDrawn() {
        super.reportFullyDrawn();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean isInMultiWindowMode() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isInMultiWindowMode();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean isInPictureInPictureMode() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isInPictureInPictureMode();
    }

    @Override
    public void enterPictureInPictureMode() {
        super.enterPictureInPictureMode();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean enterPictureInPictureMode(@NonNull PictureInPictureParams params) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.enterPictureInPictureMode(params);
    }

    @Override
    public void setPictureInPictureParams(@NonNull PictureInPictureParams params) {
        super.setPictureInPictureParams(params);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public int getMaxNumPictureInPictureActions() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getMaxNumPictureInPictureActions();
    }

    @Override
    public int getChangingConfigurations() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getChangingConfigurations();
    }

    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLastNonConfigurationInstance();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public android.app.FragmentManager getFragmentManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getFragmentManager();
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startManagingCursor(Cursor c) {
        super.startManagingCursor(c);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void stopManagingCursor(Cursor c) {
        super.stopManagingCursor(c);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public android.app.ActionBar getActionBar() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getActionBar();
    }

    @Override
    public void setActionBar(@Nullable android.widget.Toolbar toolbar) {
        super.setActionBar(toolbar);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public TransitionManager getContentTransitionManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getContentTransitionManager();
    }

    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public Scene getContentScene() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getContentScene();
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onGenericMotionEvent(event);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if(BuildConfig.DEBUG) F.m(getPrintClass(), "交互作用");
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean hasWindowFocus() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.hasWindowFocus();
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.dispatchTrackballEvent(ev);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.dispatchGenericMotionEvent(ev);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.dispatchPopulateAccessibilityEvent(event);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onNavigateUp();
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onNavigateUpFromChild(child);
    }

    @Override
    public void onCreateNavigateUpTaskStack(android.app.TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onPrepareNavigateUpTaskStack(android.app.TaskStackBuilder builder) {
        super.onPrepareNavigateUpTaskStack(builder);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void unregisterForContextMenu(View view) {
        super.unregisterForContextMenu(view);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void openContextMenu(View view) {
        super.openContextMenu(view);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void closeContextMenu() {
        super.closeContextMenu();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(@NonNull Menu menu) {
        super.onContextMenuClosed(menu);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateDialog(id);
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onCreateDialog(id, args);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean onSearchRequested(@Nullable SearchEvent searchEvent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onSearchRequested(searchEvent);
    }

    @Override
    public boolean onSearchRequested() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onSearchRequested();
    }

    @Override
    public void startSearch(@Nullable String initialQuery, boolean selectInitialQuery, @Nullable Bundle appSearchData, boolean globalSearch) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void triggerSearch(String query, @Nullable Bundle appSearchData) {
        super.triggerSearch(query, appSearchData);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLayoutInflater();
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public boolean isActivityTransitionRunning() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isActivityTransitionRunning();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivities(Intent[] intents, @Nullable Bundle options) {
        super.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean startActivityIfNeeded(@NonNull Intent intent, int requestCode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startActivityIfNeeded(intent, requestCode);
    }

    @Override
    public boolean startActivityIfNeeded(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startActivityIfNeeded(intent, requestCode, options);
    }

    @Override
    public boolean startNextMatchingActivity(@NonNull Intent intent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startNextMatchingActivity(intent);
    }

    @Override
    public boolean startNextMatchingActivity(@NonNull Intent intent, @Nullable Bundle options) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startNextMatchingActivity(intent, options);
    }

    @Override
    public void startActivityFromChild(@NonNull Activity child, Intent intent, int requestCode) {
        super.startActivityFromChild(child, intent, requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityFromChild(@NonNull Activity child, Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityFromChild(child, intent, requestCode, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityFromFragment(@NonNull android.app.Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startActivityFromFragment(@NonNull android.app.Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityFromFragment(fragment, intent, requestCode, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderFromChild(Activity child, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderFromChild(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startIntentSenderFromChild(Activity child, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderFromChild(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public Uri getReferrer() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getReferrer();
    }

    @Override
    public Uri onProvideReferrer() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onProvideReferrer();
    }

    @Nullable
    @Override
    public String getCallingPackage() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getCallingPackage();
    }

    @Nullable
    @Override
    public ComponentName getCallingActivity() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getCallingActivity();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean isFinishing() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isFinishing();
    }

    @Override
    public boolean isDestroyed() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isDestroyed();
    }

    @Override
    public boolean isChangingConfigurations() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isChangingConfigurations();
    }

    @Override
    public void recreate() {
        super.recreate();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finish() {
        super.finish();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishAffinity() {
        super.finishAffinity();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishFromChild(Activity child) {
        super.finishFromChild(child);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishActivityFromChild(@NonNull Activity child, int requestCode) {
        super.finishActivityFromChild(child, requestCode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void finishAndRemoveTask() {
        super.finishAndRemoveTask();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean releaseInstance() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.releaseInstance();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public PendingIntent createPendingResult(int requestCode, @NonNull Intent data, int flags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createPendingResult(requestCode, data, flags);
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public int getRequestedOrientation() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getRequestedOrientation();
    }

    @Override
    public int getTaskId() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getTaskId();
    }

    @Override
    public boolean isTaskRoot() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isTaskRoot();
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.moveTaskToBack(nonRoot);
    }

    @NonNull
    @Override
    public String getLocalClassName() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getLocalClassName();
    }

    @Override
    public ComponentName getComponentName() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getComponentName();
    }

    @Override
    public SharedPreferences getPreferences(int mode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getPreferences(mode);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass(), name);
        return super.getSystemService(name);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTitleColor(int textColor) {
        super.setTitleColor(textColor);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
        super.setTaskDescription(taskDescription);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean isImmersive() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isImmersive();
    }

    @Override
    public boolean requestVisibleBehind(boolean visible) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.requestVisibleBehind(visible);
    }

    @Override
    public void onVisibleBehindCanceled() {
        super.onVisibleBehindCanceled();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setImmersive(boolean i) {
        super.setImmersive(i);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setVrModeEnabled(boolean enabled, @NonNull ComponentName requestedComponent) throws PackageManager.NameNotFoundException {
        super.setVrModeEnabled(enabled, requestedComponent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Nullable
    @Override
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startActionMode(callback);
    }

    @Nullable
    @Override
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback, int type) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startActionMode(callback, type);
    }

    @Nullable
    @Override
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onWindowStartingActionMode(callback);
    }

    @Nullable
    @Override
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int type) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(android.view.ActionMode mode) {
        super.onActionModeStarted(mode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void onActionModeFinished(android.view.ActionMode mode) {
        super.onActionModeFinished(mode);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean shouldUpRecreateTask(Intent targetIntent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.shouldUpRecreateTask(targetIntent);
    }

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.navigateUpTo(upIntent);
    }

    @Override
    public boolean navigateUpToFromChild(Activity child, Intent upIntent) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.navigateUpToFromChild(child, upIntent);
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getParentActivityIntent();
    }

    @Override
    public void setEnterSharedElementCallback(android.app.SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setExitSharedElementCallback(android.app.SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void postponeEnterTransition() {
        super.postponeEnterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void startPostponedEnterTransition() {
        super.startPostponedEnterTransition();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent event) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.requestDragAndDropPermissions(event);
    }

    @Override
    public void startLockTask() {
        super.startLockTask();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void stopLockTask() {
        super.stopLockTask();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void showLockTaskEscapeMessage() {
        super.showLockTaskEscapeMessage();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setShowWhenLocked(boolean showWhenLocked) {
        super.setShowWhenLocked(showWhenLocked);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setInheritShowWhenLocked(boolean inheritShowWhenLocked) {
        super.setInheritShowWhenLocked(inheritShowWhenLocked);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setTurnScreenOn(boolean turnScreenOn) {
        super.setTurnScreenOn(turnScreenOn);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        super.applyOverrideConfiguration(overrideConfiguration);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public AssetManager getAssets() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getAssets();
    }

    @Override
    public void setTheme(@Nullable Resources.Theme theme) {
        super.setTheme(theme);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public Resources.Theme getTheme() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getTheme();
    }

    @Override
    public Context getBaseContext() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getBaseContext();
    }

    @Override
    public PackageManager getPackageManager() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getPackageManager();
    }

    @Override
    public ContentResolver getContentResolver() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getContentResolver();
    }

    @Override
    public Looper getMainLooper() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getMainLooper();
    }

    @Override
    public Executor getMainExecutor() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getMainExecutor();
    }

    @Override
    public Context getApplicationContext() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getApplicationContext();
    }

    @Override
    public ClassLoader getClassLoader() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getClassLoader();
    }

    @Override
    public String getPackageName() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getPackageName();
    }

    @Override
    public String getOpPackageName() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getOpPackageName();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getApplicationInfo();
    }

    @Override
    public String getPackageResourcePath() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getPackageResourcePath();
    }

    @Override
    public String getPackageCodePath() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getPackageCodePath();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getSharedPreferences(name, mode);
    }

    @Override
    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.moveSharedPreferencesFrom(sourceContext, name);
    }

    @Override
    public boolean deleteSharedPreferences(String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.deleteSharedPreferences(name);
    }

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.openFileInput(name);
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.openFileOutput(name, mode);
    }

    @Override
    public boolean deleteFile(String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.deleteFile(name);
    }

    @Override
    public File getFileStreamPath(String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getFileStreamPath(name);
    }

    @Override
    public String[] fileList() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.fileList();
    }

    @Override
    public File getDataDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getDataDir();
    }

    @Override
    public File getFilesDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getFilesDir();
    }

    @Override
    public File getNoBackupFilesDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getNoBackupFilesDir();
    }

    @Override
    public File getExternalFilesDir(String type) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExternalFilesDir(type);
    }

    @Override
    public File[] getExternalFilesDirs(String type) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getObbDir();
    }

    @Override
    public File[] getObbDirs() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getObbDirs();
    }

    @Override
    public File getCacheDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getCacheDir();
    }

    @Override
    public File getCodeCacheDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getCodeCacheDir();
    }

    @Override
    public File getExternalCacheDir() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExternalCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExternalCacheDirs();
    }

    @Override
    public File[] getExternalMediaDirs() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getExternalMediaDirs();
    }

    @Override
    public File getDir(String name, int mode) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getDir(name, mode);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    @Override
    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.moveDatabaseFrom(sourceContext, name);
    }

    @Override
    public boolean deleteDatabase(String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.deleteDatabase(name);
    }

    @Override
    public File getDatabasePath(String name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getDatabasePath(name);
    }

    @Override
    public String[] databaseList() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.databaseList();
    }

    @Override
    public Drawable getWallpaper() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getWallpaper();
    }

    @Override
    public Drawable peekWallpaper() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.peekWallpaper();
    }

    @Override
    public int getWallpaperDesiredMinimumWidth() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getWallpaperDesiredMinimumWidth();
    }

    @Override
    public int getWallpaperDesiredMinimumHeight() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.getWallpaperDesiredMinimumHeight();
    }

    @Override
    public void setWallpaper(Bitmap bitmap) throws IOException {
        super.setWallpaper(bitmap);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void setWallpaper(InputStream data) throws IOException {
        super.setWallpaper(data);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void clearWallpaper() throws IOException {
        super.clearWallpaper();
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        super.sendBroadcast(intent, receiverPermission);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        super.sendOrderedBroadcast(intent, receiverPermission);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        super.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }


    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.registerReceiver(receiver, filter, flags);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public ComponentName startService(Intent service) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startService(service);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startForegroundService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.stopService(name);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.bindService(service, conn, flags);
    }

    @Override
    public boolean bindService(Intent service, int flags, Executor executor, ServiceConnection conn) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.bindService(service, flags, executor, conn);
    }

    @Override
    public boolean bindIsolatedService(Intent service, int flags, String instanceName, Executor executor, ServiceConnection conn) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.bindIsolatedService(service, flags, instanceName, executor, conn);
    }

    @Override
    public void updateServiceGroup(ServiceConnection conn, int group, int importance) {
        super.updateServiceGroup(conn, group, importance);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.startInstrumentation(className, profileFile, arguments);
    }

    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        if(BuildConfig.DEBUG) F.m(getPrintClass(), serviceClass.getName());
        return super.getSystemServiceName(serviceClass);
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkPermission(permission, pid, uid);
    }

    @Override
    public int checkCallingPermission(String permission) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkCallingPermission(permission);
    }

    @Override
    public int checkCallingOrSelfPermission(String permission) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkCallingOrSelfPermission(permission);
    }

    @Override
    public int checkSelfPermission(String permission) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkSelfPermission(permission);
    }

    @Override
    public void enforcePermission(String permission, int pid, int uid, String message) {
        super.enforcePermission(permission, pid, uid, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void enforceCallingPermission(String permission, String message) {
        super.enforceCallingPermission(permission, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void enforceCallingOrSelfPermission(String permission, String message) {
        super.enforceCallingOrSelfPermission(permission, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        super.grantUriPermission(toPackage, uri, modeFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        super.revokeUriPermission(uri, modeFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void revokeUriPermission(String targetPackage, Uri uri, int modeFlags) {
        super.revokeUriPermission(targetPackage, uri, modeFlags);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        super.enforceUriPermission(uri, pid, uid, modeFlags, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        super.enforceCallingUriPermission(uri, modeFlags, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        super.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        super.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createPackageContext(packageName, flags);
    }

    @Override
    public Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createContextForSplit(splitName);
    }

    @Override
    public Context createConfigurationContext(Configuration overrideConfiguration) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createConfigurationContext(overrideConfiguration);
    }

    @Override
    public Context createDisplayContext(Display display) {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createDisplayContext(display);
    }

    @Override
    public boolean isRestricted() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isRestricted();
    }

    @Override
    public Context createDeviceProtectedStorageContext() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.createDeviceProtectedStorageContext();
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        if(BuildConfig.DEBUG) F.m(getPrintClass());
        return super.isDeviceProtectedStorage();
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
        if(BuildConfig.DEBUG) F.m(getPrintClass());
    }



    public Class<?> getPrintClass(){
        return getClass();
    }
}
