package com.flannery.jetpackapp2.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.flannery.jetpackapp2.L;
import com.flannery.jetpackapp2.R;

import org.jetbrains.annotations.NotNull;

public class FragmentActivity2Fragment extends Fragment {

    private MainViewModel mViewModel;

    public static FragmentActivity2Fragment newInstance() {
        return new FragmentActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        L.m3();
    }


//    public FragmentActivity2Fragment(int contentLayoutId) {
//        super(contentLayoutId);
//        L.m3();
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        L.m3();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.m3();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        L.m3();
    }

    @NonNull
    @NotNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        L.m3();
        return super.onGetLayoutInflater(savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull @NotNull Context context, @NonNull @NotNull AttributeSet attrs, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        L.m3();
    }

    @Override
    public void onAttachFragment(@NonNull @NotNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        L.m3();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        L.m3();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        L.m3();
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        L.m3();
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.m3();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.m3();
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        L.m3();
    }

    @Override
    public void onStart() {
        super.onStart();
        L.m3();
    }

    @Override
    public void onResume() {
        super.onResume();
        L.m3();
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        L.m3();
    }


    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        L.m3();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        L.m3();
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.m3();
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
        L.m3();
    }

    @Override
    public void onPause() {
        super.onPause();
        L.m3();
    }

    @Override
    public void onStop() {
        super.onStop();
        L.m3();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        L.m3();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.m3();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.m3();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.m3();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        L.m3();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        L.m3();
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        L.m3();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        L.m3();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(@NonNull @NotNull Menu menu) {
        super.onOptionsMenuClosed(menu);
        L.m3();
    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        L.m3();
    }
}