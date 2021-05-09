package cn.kuwo.pp.ui.publish;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;


import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.view.NoScrollViewPager;
import cn.kuwo.pp.R;
import cn.kuwo.pp.util.CommonViewPageAdapter;
import cn.kuwo.pp.util.magictabview.ScaleTransitionPagerTitleView;

public class VoteMainFragment extends BaseFragment {
    MagicIndicator mTabLayout;
    NoScrollViewPager mViewPager;

    private void _initView(View view) {
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
    }

    private CommonViewPageAdapter mMainAdapter;

    public static VoteMainFragment newInstance() {
        return new VoteMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote_main, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PictureVoteFragment.newInstance());
        fragments.add(TextVoteFragment.newInstance());
        ArrayList<String> titles = new ArrayList<>();
        titles.add("图片投票");
        titles.add("文字投票");
        mMainAdapter = new CommonViewPageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mMainAdapter);

        initTabLayout(titles);
    }

    private void initTabLayout(ArrayList titleList) {
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
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setNormalColor(Color.parseColor("#7fffffff"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
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
                indicator.setYOffset(20);
                return indicator;
            }
        });
        mTabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTabLayout, mViewPager);
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
