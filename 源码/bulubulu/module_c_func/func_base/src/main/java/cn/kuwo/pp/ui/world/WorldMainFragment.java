package cn.kuwo.pp.ui.world;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.util.ArrayUtils;
import cn.kuwo.common.view.NoScrollViewPager;
import cn.kuwo.pp.R;
import cn.kuwo.pp.ui.publish.PublishMainFragment;
import cn.kuwo.pp.util.CommonViewPageAdapter;
import cn.kuwo.pp.util.magictabview.ColorTransitionPagerTitleView;

public class WorldMainFragment extends BaseFragment {
    private CommonViewPageAdapter mMainAdapter;

    MagicIndicator mTabLayout;
    NoScrollViewPager mViewPager;

    public static WorldMainFragment newInstance() {
        return new WorldMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world_main, container, false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);

        initNewTopic(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // 记录世界
        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.WORLD, "WORLD");
        List<String> titles = ArrayUtils.INSTANCE.asList("热门", "最新", "喜欢");
        List<Fragment> fragments = ArrayUtils.INSTANCE.asList(
                HotListFragment.newInstance(),
                LatestListFragment.newInstance(),
                FavorListFragment.newInstance()
        );
        mMainAdapter = new CommonViewPageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mMainAdapter);

        initTabLayout(titles);
    }

    //  制作发布
    private void initNewTopic(View view) {
        View ivPublish = view.findViewById(R.id.ivPublish);
        if (ivPublish == null) return;
        ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFromMain(PublishMainFragment.newInstance(PublishMainFragment.FROM_TREND_PAGE));
            }
        });
    }


    private void initTabLayout(List<String> titleList) {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText((String) titleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FFE43F"));
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
                indicator.setLineHeight(UIUtil.dip2px(context, 0));
                indicator.setLineWidth(UIUtil.dip2px(context, 0));
                indicator.setRoundRadius(UIUtil.dip2px(context, 0));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(0.0f));
                indicator.setColors(Color.parseColor("#FFDF1F"));
                indicator.setYOffset(0);
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
