package cn.kuwo.common.dialog;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.BuildConfig;
import cn.kuwo.common.R;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by shihc on 2017/1/11.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    //    protected Unbinder mUnbinder;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreate");
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseDialog);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onDestroyView");
//        if (mUnbinder != null) {
//            mUnbinder.unbind();
//        }
    }

    public void show(FragmentManager manager) {
        show(manager, this.getClass().getName());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            if (!manager.isStateSaved()) {
                super.show(manager, tag);
            }
        } catch (Exception e) {
        }
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onStart");
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onResume");
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onPause");
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onStop");
    }

    @Override
    @CallSuper
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onDestroy");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onRequestPermissionsResult");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onLowMemory");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onDetach");
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onGetLayoutInflater");
        return super.onGetLayoutInflater(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateDialog");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCancel");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onDismiss");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onSaveInstanceState");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onHiddenChanged");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onActivityResult");
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onInflate");
    }

    @Override
    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onInfalte");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onAttachFragment");
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onAttach");
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateAnimation");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateAnimator");
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onViewCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onViewStateRestored");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onMultiWindowModeChanged");
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onPictureInPictureModeChanged");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onConfigurationChanged");
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
        if (BuildConfig.DEBUG)
            Log.i(getClass().getSimpleName(), "onPrimaryNavigationFragmentChanged");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateOpitonsMenu");
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onPrepareOptionMenu");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onDestroyOptionsMenu");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        super.onOptionsMenuClosed(menu);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onOptionMenuClosed");
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onCreateContextMenu");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (BuildConfig.DEBUG) Log.i(getClass().getSimpleName(), "onContextItemSeleced");
        return super.onContextItemSelected(item);
    }
}
