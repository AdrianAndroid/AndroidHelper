package cn.kuwo.common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import cn.kuwo.common.R;
import cn.kuwo.common.view.NoScrollViewPager;


/**
 * 集成了ViewPager和 TabLayout 的基类，封装了adapter，包含ViewPager的界面可继承该类实现
 * 如果布局和默认不同，请重写 {@link #onCreateView} 方法;
 * 如果控件id不同，请重写 {@link #getTabLayoutId} 和 {@link #getViewPagerId}
 * 如需要修改TabLayout样式，请看 {@link #initTabLayout} 方法说明
 * <p>
 * 子类自定义布局，ViewPager 务必使用 NoScrollViewPager，不然崩给你看
 */
public abstract class ViewPagerFragment extends BaseFragment {

    protected static String CURRENT_PAGE = "mCurrentPage";

    protected int mCurrentPage = 0;

    protected TabLayout mTabLayout;

    public NoScrollViewPager mViewPager;

    protected Map<Integer, BaseFragment> mFragments;

    private FragmentStatePagerAdapter mPagerAdapter;

    private TabLayout.ViewPagerOnTabSelectedListener mTabSelectedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_base_view_pager, container, false);
        setHasOptionsMenu(true);
        return inflate;
    }

    /**
     * 是否可以手势滑动，默认可以，需要禁止滑动请重写该方法，并返回false
     *
     * @return
     */
    protected boolean canScroll() {
        return true;
    }

    protected int getTabLayoutId() {
        return R.id.tab_layout;
    }

    protected int getViewPagerId() {
        return R.id.view_pager;
    }

    protected void onTabSelected(TabLayout.Tab tab) {
        mCurrentPage = tab.getPosition();
    }

    protected void onTabUnselected(TabLayout.Tab tab) {
    }

    protected abstract String[] getTitles();

    protected abstract BaseFragment initFragment(int position);

    /**
     * 实现 TabLayout 和 ViewPager 的自动关联，如果需要定制 TabLayout 的样式，请重写该方法
     * 并调用super.initTabLayout(mTabLayout, selectedPosition); mTabLayout.removeAllTabs();
     * 然后添加 TabView
     *
     * @param tabLayout
     * @param selectedPosition
     */
    protected void initTabLayout(TabLayout tabLayout, int selectedPosition) {
        //mTabLayout 与 ViewPager 绑在一起，自动关联相应
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(selectedPosition).select();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(CURRENT_PAGE)) {
            mCurrentPage = getArguments().getInt(CURRENT_PAGE);
        }
        if (savedInstanceState != null) {
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
        }
        //初始化 mTabLayout,添加 newTab
        mTabLayout = view.findViewById(getTabLayoutId());
        //实例化ViewPager， 然后给ViewPager设置Adapter
        mViewPager = view.findViewById(getViewPagerId());
        mViewPager.setCanScroll(canScroll());
        mPagerAdapter = new TabPageIndicatorAdapter(getChildFragmentManager());
        mTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                ViewPagerFragment.this.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                ViewPagerFragment.this.onTabUnselected(tab);
            }
        };
        mTabLayout.addOnTabSelectedListener(mTabSelectedListener);
        if (!lazyInitAdapter()) {
            setAdapter();
        }
    }

    /**
     * 是否需要延迟初始化adapter，如果子界面需要前置操作，该方法返回true，然后自行设置adapter
     *
     * @return
     */
    protected boolean lazyInitAdapter() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE, mCurrentPage);
    }


    protected void setAdapter() {
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(getTitles().length);
        initTabLayout(mTabLayout, mCurrentPage);
        onTabSelected(mTabLayout.getTabAt(mCurrentPage));
    }

    @Override
    public void onDestroyView() {
        if (mViewPager != null) {
            mViewPager.setAdapter(null);
        }
        if (mPagerAdapter != null) {
            mPagerAdapter = null;
        }
        super.onDestroyView();
        mFragments.clear();
        mTabLayout.removeOnTabSelectedListener(mTabSelectedListener);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    /**
     * 定义ViewPager的适配器
     */
    class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment baseFragment = initFragment(position);
            mFragments.put(position, baseFragment);
            return baseFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTitles()[position % getTitles().length];
        }

        @Override
        public int getCount() {
            return getTitles().length;
        }
    }
}
