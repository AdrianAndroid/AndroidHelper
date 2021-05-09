package cn.kuwo.pp.ui.topic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.barlibrary.ImmersionBar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.NoScrollViewPager;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.ui.publish.PublishMainFragment;
import cn.kuwo.pp.ui.share.ShareChallengeDialog;
import cn.kuwo.pp.util.CommonViewPageAdapter;
import cn.kuwo.pp.util.magictabview.ScaleTransitionPagerTitleView;


public class TopicDetailFragment extends BaseFragment {
    private MagicIndicator mTabLayout;
    private NoScrollViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View ivTopicStart;
    private ImageView ivShare;
    private TextView tvTopTitle;
    private TextView tvTopicTitle;
    private ImageView ivUser1;
    private ImageView ivUser2;
    private ImageView ivUser3;
    private TextView tvNumbers;
    private TextView tvSubscribe;
    private ConstraintLayout layout_bg;
//    View ivPublish;

    private void initViews(View view) {
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        ivTopicStart = view.findViewById(R.id.ivTopicStart);
        ivShare = view.findViewById(R.id.ivShare);
        tvTopTitle = view.findViewById(R.id.tvTopTitle);
        tvTopicTitle = view.findViewById(R.id.tvTopicTitle);
        ivUser1 = view.findViewById(R.id.ivUser1);
        ivUser2 = view.findViewById(R.id.ivUser2);
        ivUser3 = view.findViewById(R.id.ivUser3);
        tvNumbers = view.findViewById(R.id.tvNumbers);
        tvSubscribe = view.findViewById(R.id.tvSubscribe);
        layout_bg = view.findViewById(R.id.layout_bg);
    }


    private CommonViewPageAdapter mMainAdapter;
    private TopicHotItemFragment mHotFragment;
    //private TopicLatestItemFragment mLatestFragment;

    private TopicItemBean mTopic;

    public static TopicDetailFragment newInstance(TopicItemBean topic) {
        TopicDetailFragment fragment = new TopicDetailFragment(topic);
        return fragment;
    }

    public TopicDetailFragment(TopicItemBean topic) {
        super();
        mTopic = topic;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);
        initViews(view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);

        float minimumHeight = getDimension(R.dimen.tab_layout_height) + getDimension(R.dimen.toolbar_height) + ImmersionBar.getStatusBarHeight(_mActivity);
        collapsingToolbarLayout.setMinimumHeight((int) minimumHeight);

        setRandomImage(); //随机背景图


        mHotFragment = TopicHotItemFragment.newInstance(mTopic.getId());
        //mLatestFragment = TopicLatestItemFragment.newInstance(mTopic.getId());
        List<Fragment> fragments = Arrays.asList(
                TopicLatestItemFragment.newInstance(mTopic.getId())
                , mHotFragment
        );

        List<String> titles = Arrays.asList(
                "最新"
                , "最热"
        );

        mMainAdapter = new CommonViewPageAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mMainAdapter);

        initHeadUI();
        initTabLayout(titles);
        initListeners();
        initNewTopic(view);

        if (mTopic.getUserList() == null) {
            TopicChallengePresenter.requestTopicDetail(this, mTopic.getId());
        }
    }

    private void setRandomImage() {
        List<Integer> integers = Arrays.asList(
                R.drawable.topic_title_bg_1,
                R.drawable.topic_title_bg_2,
                R.drawable.topic_title_bg_3
        );
        int index = new Random().nextInt(3);
        if (App.DEBUG) Log.i("Random", "" + index);
        Integer integer = integers.get(index);
        layout_bg.setBackgroundResource(integer);
    }

    //  制作发布
    private void initNewTopic(View view) {
        View ivPublish = view.findViewById(R.id.ivPublish);
        if (ivPublish == null) {
            return;
        }
        ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishMainFragment publishMainFragment = PublishMainFragment.newInstance(PublishMainFragment.FROM_TREND_PAGE, mTopic.getName(), mTopic.getId());
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.TOPIC_DETAIL_INTO_PUBLISH, TopicDetailFragment.class.getSimpleName());
                startFromMain(publishMainFragment); // setValue 主线程调用
            }
        });
    }

    private void initHeadUI() {
        if (mTopic.getUserList() == null) {
            return;
        }

        tvTopTitle.setText(mTopic.getName());
        tvTopicTitle.setText(mTopic.getName());
        tvNumbers.setText(mTopic.getUserCnt() + "人参与");

//        if (mTopic.getSubed() == 1) {
//            tvSubscribe.setTextColor(Color.parseColor("#333333"));
//            tvSubscribe.setText("已订阅");
//        } else {
//            tvSubscribe.setTextColor(Color.WHITE);
//            tvSubscribe.setText("+订阅");
//        }
        changeSubscribeTextView(mTopic.getSubed() == 1);

        if (mTopic.getUserList() != null) {
            for (int i = 0; i < mTopic.getUserList().size(); i++) {
                switch (i) {
                    case 0:
                        ImageLoader.showCircleImage(ivUser1, mTopic.getUserList().get(i).getHeadImg(), mTopic.getUserList().get(i).getDefaultHeadImage());
                        break;
                    case 1:
                        ImageLoader.showCircleImage(ivUser2, mTopic.getUserList().get(i).getHeadImg(), mTopic.getUserList().get(i).getDefaultHeadImage());
                        break;
                    case 2:
                        ImageLoader.showCircleImage(ivUser3, mTopic.getUserList().get(i).getHeadImg(), mTopic.getUserList().get(i).getDefaultHeadImage());
                        break;
                }
            }
        }
    }

    private void initListeners() {
        ivTopicStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 入圈挑战
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.TOPIC_DETAIL_INTO_CHANLLEGE, TopicDetailFragment.class.getSimpleName());
                startFromMain(ChallengeFragment.newInstance(mTopic));
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareChallengeDialog.newInstance(getView(), mHotFragment.getDataList()).show(getChildFragmentManager());
            }
        });

        tvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopicChallengePresenter.subscribeTopic(TopicDetailFragment.this, mTopic.getSubed() == 0, mTopic.getId());
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
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setNormalColor(Color.parseColor("#B3B3B3"));
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

    public void onSubscribeResult(boolean subscribe, boolean success) {
        if (success) {
            mTopic.setSubed(subscribe ? 1 : 0);
            changeSubscribeTextView(subscribe);
//            if (subscribe) {
//                tvSubscribe.setTextColor(Color.parseColor("#333333"));
//                tvSubscribe.setText("已订阅");
//            } else {
//                tvSubscribe.setTextColor(Color.WHITE);
//                tvSubscribe.setText("+订阅");
//            }
        }

        if (success) {
            UtilsCode.INSTANCE.showShort(subscribe ? "订阅成功" : "取消订阅成功");
        } else {
            UtilsCode.INSTANCE.showShort(subscribe ? "订阅失败" : "取消订阅失败");
        }
    }

    public void onGetTopicDetail(TopicItemBean bean) {
        mTopic = bean;
        initHeadUI();
    }

    private void changeSubscribeTextView(boolean subscribe) {
        if (subscribe) {
            tvSubscribe.setBackground(null);
            tvSubscribe.setTextColor(Color.parseColor("#333333"));
            tvSubscribe.setText("已订阅");
        } else {
            tvSubscribe.setBackgroundResource(R.drawable.shape_4dp_strok1_ff979797);
            tvSubscribe.setTextColor(Color.WHITE);
            tvSubscribe.setText("+订阅");
        }
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
