package cn.kuwo.pp.ui.discover;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.event.ConnectStatusEvent;
import cn.kuwo.common.event.LoginEvent;
import cn.kuwo.common.event.LogoutEvent;
import cn.kuwo.common.view.CustomToolbar;
import cn.kuwo.common.view.NoScrollViewPager;
import cn.kuwo.common.viewmodel.MainViewModel;
import cn.kuwo.pp.R;
import cn.kuwo.pp.util.CommonViewPageAdapter;
import cn.kuwo.pp.util.magictabview.ScaleTransitionPagerTitleView;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class DiscoverFragment extends BaseFragment {

    MagicIndicator mTabLayout;
    NoScrollViewPager mViewPager;

    private CommonViewPageAdapter mMainAdapter;
    //private FriendTestFragment mFriendTestFragment;
//    private FindFriendFragment mFindFriendFragment;

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dicover, container, false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        mFriendTestFragment = FriendTestFragment.newInstance();
//        mFindFriendFragment = FindFriendFragment.newInstance();

        List<Fragment> fragments = Collections.singletonList(FriendTestFragment.newInstance());
        List<String> titles = Collections.singletonList("PK");
        mMainAdapter = new CommonViewPageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mMainAdapter);
        mViewPager.setCanScroll(false);

        initTabLayout(titles);
    }

    private void initTabLayout(List<String> titleList) {
        mTabLayout.setBackgroundColor(Color.TRANSPARENT);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText((String) titleList.get(index));
                simplePagerTitleView.setTextSize(24);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#7fffffff"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mViewPager.setCurrentItem(index);
//                        mFriendTestFragment.onSelectTab(index == 0);
//                        mFindFriendFragment.onSelectTab(index == 1);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 15));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#FFDF1F"));
                return indicator;
            }
        });
        mTabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTabLayout, mViewPager);
    }

    @Override
    public Class<?> getPrintClass() {
        return  getClass();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }



    /////////////////////////
    /////////////////////////
    // 测试用
    /////////////////////////
    /////////////////////////

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public float getDimension(int id) {
        return super.getDimension(id);
    }

    @Override
    protected View getStatusBarOffsetView() {
        return super.getStatusBarOffsetView();
    }

//    @Override
//    public MaterialDialog showLoadingDialog() {
//        return super.showLoadingDialog();
//    }
//
//    @Override
//    public MaterialDialog showLoadingDialog(String title) {
//        return super.showLoadingDialog(title);
//    }
//
//    @Override
//    public MaterialDialog showLoadingDialog(int titleId) {
//        return super.showLoadingDialog(titleId);
//    }

    @Override
    public void dismissLoadingDialog() {
        super.dismissLoadingDialog();
    }

    @Override
    public void showEmptyView(CharSequence content) {
        super.showEmptyView(content);
    }

    @Override
    public void showErrorView(int errorCode, String error) {
        super.showErrorView(errorCode, error);
    }

    @Override
    protected boolean isPageStatisticsEnable() {
        return super.isPageStatisticsEnable();
    }

    @Override
    protected String getPageName() {
        return super.getPageName();
    }

    @Override
    public boolean showMainPlayView() {
        return super.showMainPlayView();
    }

    @Override
    public CustomToolbar enableToolbar(int id) {
        return super.enableToolbar(id);
    }

    @Override
    public CustomToolbar enableToolbar(int id, int title) {
        return super.enableToolbar(id, title);
    }

    @Override
    public CustomToolbar enableToolbar(int id, String title) {
        return super.enableToolbar(id, title);
    }

    @Override
    public CustomToolbar enableToolbar(int id, int title, boolean showBackBtn) {
        return super.enableToolbar(id, title, showBackBtn);
    }

    @Override
    public CustomToolbar enableToolbar(int id, boolean showBackBtn) {
        return super.enableToolbar(id, showBackBtn);
    }

    @Override
    public CustomToolbar enableToolbar(int id, CharSequence title, boolean showBackBtn) {
        return super.enableToolbar(id, title, showBackBtn);
    }

    @Override
    protected void onBackBtnClicked(View view) {
        super.onBackBtnClicked(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public ViewModelStoreOwner getViewModelStoreOwner(Class<? extends Fragment> aClass) {
        return super.getViewModelStoreOwner(aClass);
    }

    @Override
    public ViewModelStoreOwner getParentViewModelStoreOwner() {
        return super.getParentViewModelStoreOwner();
    }

    @Override
    public ViewModelStoreOwner getParantParentViewModelStoreOwner() {
        return super.getParantParentViewModelStoreOwner();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return super.getActivityViewModel(modelClass);
    }

    @Override
    public MainViewModel getMainViewModel() {
        return super.getMainViewModel();
    }

    @Override
    public void startFromMain(BaseFragment toFragment) {
        super.startFromMain(toFragment);
    }

    @Override
    public void startWithPopFromMain(BaseFragment toFragment) {
        super.startWithPopFromMain(toFragment);
    }

    @Override
    public void onLoginEvent(LoginEvent event) {
        super.onLoginEvent(event);
    }

    @Override
    public void onLogoutEvent(LogoutEvent event) {
        super.onLogoutEvent(event);
    }

    @Override
    protected void onLoginSuccess() {
        super.onLoginSuccess();
    }

    @Override
    protected void onLogoutSuccess() {
        super.onLogoutSuccess();
    }

    @Override
    public void onConnectedChanged(ConnectStatusEvent event) {
        super.onConnectedChanged(event);
    }

    @Override
    protected void onConnectChanged() {
        super.onConnectChanged();
    }

    @Override
    protected void setSoftInputMode(int mode) {
        super.setSoftInputMode(mode);
    }

    @Override
    public boolean isLoadingDlgShowing() {
        return super.isLoadingDlgShowing();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public View attachToSwipeBack(View view) {
        return super.attachToSwipeBack(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void pop() {
        super.pop();
    }

    @Override
    public void popChild() {
        super.popChild();
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.popTo(targetFragmentClass, includeTargetFragment);
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        super.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    @Override
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        super.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.popToChild(targetFragmentClass, includeTargetFragment);
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        super.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        super.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    @Override
    public void startWithPop(ISupportFragment toFragment) {
        super.startWithPop(toFragment);
    }

    @Override
    public void start(ISupportFragment toFragment) {
        super.start(toFragment);
    }

    @Override
    public void start(ISupportFragment toFragment, int launchMode) {
        super.start(toFragment, launchMode);
    }

    @Override
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        super.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        super.startForResult(toFragment, requestCode);
    }

    @Override
    public String getOtherTag() {
        return super.getOtherTag();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return super.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(enable);
    }

    @Override
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
        super.setEdgeLevel(edgeLevel);
    }

    @Override
    public void setEdgeLevel(int widthPixel) {
        super.setEdgeLevel(widthPixel);
    }

    @Override
    public void setParallaxOffset(float offset) {
        super.setParallaxOffset(offset);
    }

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return super.getSupportDelegate();
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return super.extraTransaction();
    }

    @Override
    public void post(Runnable runnable) {
        super.post(runnable);
    }

    @Override
    public void onChildSupportVisible() {
        super.onChildSupportVisible();
    }

    @Override
    public void onChildSupportInvisible() {
        super.onChildSupportInvisible();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return super.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        super.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        super.setFragmentResult(resultCode, bundle);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void putNewBundle(Bundle newBundle) {
        super.putNewBundle(newBundle);
    }

    @Override
    protected void hideSoftInput() {
        super.hideSoftInput();
    }

    @Override
    protected void showSoftInput(View view) {
        super.showSoftInput(view);
    }

    @Override
    public void loadRootFragment(int containerId, ISupportFragment toFragment) {
        super.loadRootFragment(containerId, toFragment);
    }

    @Override
    public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
        super.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim);
    }

    @Override
    public void loadMultipleRootFragment(int containerId, int showPosition, ISupportFragment... toFragments) {
        super.loadMultipleRootFragment(containerId, showPosition, toFragments);
    }

    @Override
    public void showHideFragment(ISupportFragment showFragment) {
        super.showHideFragment(showFragment);
    }

    @Override
    public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
        super.showHideFragment(showFragment, hideFragment);
    }

    @Override
    public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
        super.replaceFragment(toFragment, addToBackStack);
    }

    @Override
    public ISupportFragment getTopFragment() {
        return super.getTopFragment();
    }

    @Override
    public ISupportFragment getTopChildFragment() {
        return super.getTopChildFragment();
    }

    @Override
    public ISupportFragment getPreFragment() {
        return super.getPreFragment();
    }

    @Override
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return super.findFragment(fragmentClass);
    }

    @Override
    public <T extends ISupportFragment> T findChildFragment(Class<T> fragmentClass) {
        return super.findChildFragment(fragmentClass);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @NonNull
    @Override
    public LifecycleOwner getViewLifecycleOwner() {
        return super.getViewLifecycleOwner();
    }

    @NonNull
    @Override
    public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData() {
        return super.getViewLifecycleOwnerLiveData();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return super.getViewModelStore();
    }

    @NonNull
    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return super.getDefaultViewModelProviderFactory();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void setInitialSavedState(@Nullable SavedState state) {
        super.setInitialSavedState(state);
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @NonNull
    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        return super.onGetLayoutInflater(savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void registerForContextMenu(@NonNull View view) {
        super.registerForContextMenu(view);
    }

    @Override
    public void unregisterForContextMenu(@NonNull View view) {
        super.unregisterForContextMenu(view);
    }

    @Override
    public void setEnterSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    @Override
    public void setExitSharedElementCallback(@Nullable SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
    }

    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Nullable
    @Override
    public Object getEnterTransition() {
        return super.getEnterTransition();
    }

    @Override
    public void setReturnTransition(@Nullable Object transition) {
        super.setReturnTransition(transition);
    }

    @Nullable
    @Override
    public Object getReturnTransition() {
        return super.getReturnTransition();
    }

    @Override
    public void setExitTransition(@Nullable Object transition) {
        super.setExitTransition(transition);
    }

    @Nullable
    @Override
    public Object getExitTransition() {
        return super.getExitTransition();
    }

    @Override
    public void setReenterTransition(@Nullable Object transition) {
        super.setReenterTransition(transition);
    }

    @Nullable
    @Override
    public Object getReenterTransition() {
        return super.getReenterTransition();
    }

    @Override
    public void setSharedElementEnterTransition(@Nullable Object transition) {
        super.setSharedElementEnterTransition(transition);
    }

    @Nullable
    @Override
    public Object getSharedElementEnterTransition() {
        return super.getSharedElementEnterTransition();
    }

    @Override
    public void setSharedElementReturnTransition(@Nullable Object transition) {
        super.setSharedElementReturnTransition(transition);
    }

    @Nullable
    @Override
    public Object getSharedElementReturnTransition() {
        return super.getSharedElementReturnTransition();
    }

    @Override
    public void setAllowEnterTransitionOverlap(boolean allow) {
        super.setAllowEnterTransitionOverlap(allow);
    }

    @Override
    public boolean getAllowEnterTransitionOverlap() {
        return super.getAllowEnterTransitionOverlap();
    }

    @Override
    public void setAllowReturnTransitionOverlap(boolean allow) {
        super.setAllowReturnTransitionOverlap(allow);
    }

    @Override
    public boolean getAllowReturnTransitionOverlap() {
        return super.getAllowReturnTransitionOverlap();
    }

    @Override
    public void postponeEnterTransition() {
        super.postponeEnterTransition();
    }

    @Override
    public void startPostponedEnterTransition() {
        super.startPostponedEnterTransition();
    }

    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
