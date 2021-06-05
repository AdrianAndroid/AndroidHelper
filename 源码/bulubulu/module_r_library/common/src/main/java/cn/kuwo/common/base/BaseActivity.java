package cn.kuwo.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.assist.AssistContent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ActionMode;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.List;

import cn.kuwo.common.BuildConfig;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity implements LifecycleProvider<ActivityEvent> {

    //    private MaterialDialog mLoadingDialog;
    //沉浸式状态栏工具类
    private ImmersionBar mImmersionBar;

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

//    public MaterialDialog showLoadingDialog() {
//        return showLoadingDialog("正在加载");
//    }
//
//    public MaterialDialog showLoadingDialog(String title) {
//        if (mLoadingDialog == null && !mLoadingDialog.isShowing()) {
//            mLoadingDialog = new MaterialDialog.Builder(this)
//                    .content(title)
//                    .progress(true, 0)
//                    .show();
//        }
//        if (mLoadingDialog.isShowing()) {
//            mLoadingDialog.setContent(title);
//        } else {
//            mLoadingDialog.show();
//        }
//        return mLoadingDialog;
//    }
//
//    public MaterialDialog showLoadingDialog(int titleId) {
//        return showLoadingDialog(getString(titleId));
//    }
//
//    public void dismissLoadingDialog() {
//        if (mLoadingDialog != null && !isFinishing()) {
//            try {
//                mLoadingDialog.dismiss();
//                mLoadingDialog = null;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void onFragmentVisible(Fragment fragment) {
        if (BuildConfig.DEBUG) {
            Log.i(getClass().getSimpleName()
                    , Thread.currentThread().getStackTrace()[2].getMethodName());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return new DefaultHorizontalAnimator();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(false);
        mImmersionBar.init();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        setSwipeBackEnable(false);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onWindowStartingSupportActionMode(callback);
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onPrepareSupportNavigateUpTaskStack(builder);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onSupportNavigateUp();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " 1-> " + name
            );
        return super.onCreateView(parent, name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " 2-> " + name
            );
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onPreparePanel(int featureId, @Nullable View view, @NonNull Menu menu) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onPreparePanel(featureId, view, menu);
    }


    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    @Nullable
    @Override
    public CharSequence onCreateDescription() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreateDescription();
    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onProvideAssistContent(AssistContent outContent) {
        super.onProvideAssistContent(outContent);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onGenericMotionEvent(event);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onNavigateUp();
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onNavigateUpFromChild(child);
    }

    @Override
    public void onCreateNavigateUpTaskStack(android.app.TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPrepareNavigateUpTaskStack(android.app.TaskStackBuilder builder) {
        super.onPrepareNavigateUpTaskStack(builder);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreateDialog(id);
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onCreateDialog(id, args);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public boolean onSearchRequested(@Nullable SearchEvent searchEvent) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onSearchRequested(searchEvent);
    }

    @Override
    public boolean onSearchRequested() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onSearchRequested();
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public Uri onProvideReferrer() {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onProvideReferrer();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onVisibleBehindCanceled() {
        super.onVisibleBehindCanceled();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Nullable
    @Override
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onWindowStartingActionMode(callback);
    }

    @Nullable
    @Override
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int type) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        return super.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(android.view.ActionMode mode) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(android.view.ActionMode mode) {
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onActionModeFinished(mode);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }


}
