package cn.kuwo.pp.ui.publish;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.elbbbird.android.analytics.AnalyticsUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.UserPublishEvent;
import cn.kuwo.pp.event.UserPublishSuccessEvent;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import com.elbbbird.android.analytics.UmengEventId;
import cn.kuwo.pp.ui.publish.view.SegmentView;
import cn.kuwo.pp.util.CommonViewPageAdapter;

;

public class PublishMainFragment extends BaseFragment {

    public static int PAGE_PICTURE_VOTE = 1;
    public static int PAGE_TEXT_VOTE = 2;
    public static int PAGE_USER_TREND = 3;

    public static int FROM_USER_PAGE = 1;
    public static int FROM_TREND_PAGE = 2;
    public static int FROM_TREND_H5 = 3;
    private int mFromPage = 0;

    private CommonViewPageAdapter mMainAdapter;

    private SegmentView segmentView;
    private ViewPager viewPager;
    private TextView tvConfirm;
    private FlexboxLayout flexboxLayout;

    private void _initView(View view) {
        segmentView = view.findViewById(R.id.segment_view);
        viewPager = view.findViewById(R.id.viewpager);
        tvConfirm = view.findViewById(R.id.tvConfirm);
        flexboxLayout = view.findViewById(R.id.flexBoxLayout);
    }

    public static PublishMainFragment newInstance(int fromPage) {
        Bundle args = new Bundle();
        PublishMainFragment fragment = new PublishMainFragment();
        args.putInt("from", fromPage);
        fragment.setArguments(args);
        return fragment;
    }

    // 携带了一个TopicName
    public static PublishMainFragment newInstance(int fromPage, String topicName, String topicId) {
        Bundle args = new Bundle();
        PublishMainFragment fragment = new PublishMainFragment();
        args.putInt("from", fromPage);
        args.putString("topicName", topicName);
        args.putString("topicId", topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_main, container, false);
        _initView(view);
        return attachToSwipeBack(view);
    }

    private PublishViewModel activityViewModel;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);

        assert getArguments() != null;
        mFromPage = getArguments().getInt("from", FROM_TREND_PAGE);

        // 最后添加标题 ,不参与点击事件
        addTopicToFlexLayout(getString(R.string.publish_addtitle), -1, true);

        activityViewModel = getActivityViewModel(PublishViewModel.class);
        activityViewModel.topicItemChanged.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                // 添加话题
                // 更新FlexBoxLayout
                flexboxLayout.removeAllViews();
                // 为了保证position的正确
                for (int i = 0; i < activityViewModel.mTopicList.size(); i++) {
                    addTopicToFlexLayout(activityViewModel.mTopicList.get(i).getName(), i, false);
                }
                // 最后添加标题 ,不参与点击事件
                addTopicToFlexLayout(getString(R.string.publish_addtitle), -1, true);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载

        segmentView.setSegmentText(getString(R.string.publish_vote), 0);
        segmentView.setSegmentText(getString(R.string.publish_share), 1);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(VoteMainFragment.newInstance());
        fragments.add(PublishTrendFragment.newInstance());
        List<String> titles = new ArrayList<>();
        titles.add("投票");
        titles.add("心情");

        mMainAdapter = new CommonViewPageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(mMainAdapter);

        initListeners();


        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                // 在创建之后
                assert getArguments() != null;
                String topicName = getArguments().getString("topicName", "");
                String topicId = getArguments().getString("topicId", "");
                if (!TextUtils.isEmpty(topicName) && !TextUtils.isEmpty(topicId)) {
                    // 设置标题
                    TopicItemBean b = new TopicItemBean();
                    b.setName(topicName);
                    b.setId(topicId);
                    activityViewModel.addTopicList(b);
                }
                return false;
            }
        });
    }

    private void initListeners() {
        segmentView.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                segmentView.setSelect(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 确认
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoteMainFragment voteMainFragment = (VoteMainFragment) mMainAdapter.getItem(0);
                int page = voteMainFragment.mViewPager.getCurrentItem() == 0 ? PAGE_PICTURE_VOTE : PAGE_TEXT_VOTE;
                if (viewPager.getCurrentItem() == 1) {
                    page = PAGE_USER_TREND;
                }
                EventBus.getDefault().post(new UserPublishEvent(page));
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserPublicSuccess(UserPublishSuccessEvent event) {
        pop();

        if (mFromPage == FROM_USER_PAGE) { // 从User返回过来
            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PUBLISH_FROM_USER, "");
        } else if (mFromPage == FROM_TREND_PAGE) { // 从创建投票过来
            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PUBLISH_FROM_TREND, "");
        } else if (mFromPage == FROM_TREND_H5) {
            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PUBLISH_FROM_TREND, "");
        }
    }

    private void addTopicToFlexLayout(String name, final int position, boolean isAddTitle) {
        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_topic_item, flexboxLayout, false);
        view.setText(new StringBuilder().append("#").append(name).append("#"));

        if (isAddTitle) { //标题
            view.setCompoundDrawables(null, null, null, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFromMain(SelectTopicFragment.newInstance());
                }
            });
        } else {
            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //flexboxLayout.removeView(v); // 只有事件可以添加点击事件
                    activityViewModel.removeTopicItem((Integer) v.getTag());
                }
            });
        }
        flexboxLayout.addView(view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (activityViewModel != null) {
            activityViewModel.onCleared();
        }
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
