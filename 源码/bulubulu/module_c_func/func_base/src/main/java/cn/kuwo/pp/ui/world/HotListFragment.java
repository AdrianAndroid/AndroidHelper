package cn.kuwo.pp.ui.world;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.event.ConnectStatusEvent;
import cn.kuwo.common.util.CustomLoadMoreView;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.EditProfileEvent;
import cn.kuwo.pp.event.FriendChangeEvent;
import cn.kuwo.pp.event.UserPublishSuccessEvent;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.mine.UserInfoFragment;
import cn.kuwo.pp.ui.share.ShareCardDialog;
import cn.kuwo.pp.ui.share.ShareTrendDialog;
import cn.kuwo.pp.ui.topic.TopicDetailFragment;
import cn.kuwo.pp.ui.world.adapter.HotListAdapter;
import cn.kuwo.pp.util.GlideSimpleLoader;

/**
 * 热门
 * 最新
 * 喜欢
 */
public class HotListFragment extends BaseFragment {
    public static String TAG = "TREND";

    public static HotListFragment newInstance() {
        return new HotListFragment();
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MultipleStatusView statusView;

    protected HotListAdapter mAdapter;
    protected HotListPresenter mPresenter;
    protected int startPage = 1;
    protected int pageCount = 10;
    protected int retryCount = 0;
    private ImageWatcherHelper mIWHelper;


    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        statusView = view.findViewById(R.id.multipleStatusView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_list, container, false);
        //_initView(view);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new HotListAdapter(getContext(), null);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        statusView.setVisibility(View.VISIBLE);
        statusView.showLoading();
        mPresenter = new HotListPresenter(this);
        queryData();

        initListener();
    }

    protected void queryData() {
        mPresenter.requestHotTrendList(startPage, pageCount);
    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (position >= mAdapter.getData().size()) {
                    return;
                }
                UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);

                if (view.getId() == R.id.ivUserHeader || view.getId() == R.id.tvUserName) {
                    startFromMain(UserInfoFragment.newInstance(null, model.getUser()));
                    AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.CLICK_USER_HEADER, TAG);
                } else if (view.getId() == R.id.ivFavor) {
                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }
                    if (UserInfoManager.INSTANCE.getUid().equals(model.getUser().getUid())) {
                        return;
                    }
                    if (!FriendListManager.getInstance().isFollowedUser(model.getUser().getUid())) {
                        FriendListManager.getInstance().follow(model.getUser(), HotListFragment.this, new FriendListManager.IFriendListManagerResult() {
                            @Override
                            public void IFriendListManagerResult_success(int result) {
                                mAdapter.notifyItemChanged(position);
                                UtilsCode.INSTANCE.showShort("关注成功");
                                if (App.DEBUG) L.L(getClass(), "关注成功");
                            }

                            @Override
                            public void IFriendListManagerResult_failed(String msg) {
                                UtilsCode.INSTANCE.showShort("关注失败: " + msg);
                                if (App.DEBUG) L.L(getClass(), "关注失败");
                            }
                        });
                    } else {
                        FriendListManager.getInstance().delete(model.getUser().getUid(), HotListFragment.this, new FriendListManager.IFriendListManagerResult() {
                            @Override
                            public void IFriendListManagerResult_success(int result) {
                                mAdapter.notifyItemChanged(position);
                                UtilsCode.INSTANCE.showShort("取消关注成功");
                                if (App.DEBUG) L.L(getClass(), "取消关注成功");
                            }

                            @Override
                            public void IFriendListManagerResult_failed(String msg) {
                                UtilsCode.INSTANCE.showShort("取消关注失败: " + msg);
                                if (App.DEBUG) L.L(getClass(), "取消关注失败");
                            }
                        });
                    }
                } else if (view.getId() == R.id.ivPraise) {
                    AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.CLICK_PRAISE, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    if (model.getLiked() == 0) {
                        mPresenter.likeTrend(true, model);
                    } else {
                        mPresenter.likeTrend(false, model);
                    }
                } else if (view.getId() == R.id.ivShare) {
                    if (model.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH) {
                        ShareTrendDialog.newInstance(model).show(getChildFragmentManager());
                    } else {
                        ShareCardDialog.newInstance(model.toQuestionModel(), 0, 0).show(getChildFragmentManager());
                    }
                    AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.CLICK_SHARE, TAG);
                } else if (view.getId() == R.id.ivComment) {
                    CommentDialog.newInstance(model, HotListFragment.this).show(getChildFragmentManager());
                    AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.CLICK_COMMENT, TAG);
                } else if (view.getId() == R.id.ivOptionOne || view.getId() == R.id.ivOptionTwo || view.getId() == R.id.viewOptionOneBg || view.getId() == R.id.viewOptionTwoBg) {
                    if (model.getPicked().isEmpty()) {
                        model.setPicked(UserTrendBean.ANSWER_ONE);
                        if (view.getId() == R.id.ivOptionTwo || view.getId() == R.id.viewOptionTwoBg) {
                            model.setPicked(UserTrendBean.ANSWER_TWO);
                        }

                        mPresenter.answerQuestion(model.getQid(), model.getPicked());

                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyItemChanged(position);
                            }
                        }, 100);

                        AnalyticsUtils.INSTANCE.onEvent(getActivity(), UmengEventId.PK_VOTE, TAG);
                    }
                }
            }
        });

        mIWHelper = ImageWatcherHelper.with(_mActivity, new GlideSimpleLoader());
        mAdapter.hotListItemClickListener = new HotListAdapter.onHotListItemClickListener() {
            @Override
            public void onClickImage(List viewList, List dataList, int position) {
                ArrayList imageUrls = new ArrayList();
                SparseArray<ImageView> mappingArray = new SparseArray<ImageView>();
                for (int i = 0; i < viewList.size(); i++) {
                    mappingArray.put(i, (ImageView) viewList.get(i));
                    imageUrls.add(Uri.parse((String) dataList.get(i)));
                }

                mIWHelper.show((ImageView) viewList.get(position), mappingArray, imageUrls);
            }

            @Override
            public void onClickTopic(TopicItemBean topic) {
                startFromMain(TopicDetailFragment.newInstance(topic));
            }
        };

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!swipeRefreshLayout.isRefreshing()) {
                    if (startPage > 1) {
                        queryData();
                    }
                } else {
                    mAdapter.loadMoreComplete();
                }
            }
        }, recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mAdapter.isLoading()) { //不加载的时候才下拉
                    startPage = 1;
                    retryCount = 0;
                    queryData();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });
    }

    public void onLikeResult(UserTrendBean userTrendBean, boolean like, boolean success) {
        if (success) {
            userTrendBean.setLiked(like ? 1 : 0);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(userTrendBean));
        }

        if (success) {
            UtilsCode.INSTANCE.showShort(like ? "点赞成功" : "取消点赞成功");
        } else {
            UtilsCode.INSTANCE.showShort(like ? "点赞失败" : "取消点赞失败");
        }
    }

    public void onGetTrendFail() {
        if (startPage == 1) {
            statusView.setVisibility(View.VISIBLE);
            statusView.showError();
        }
    }

    public List<UserTrendBean> getDataList() {
        return mAdapter.getData();
    }

    public void onAddTrends(List<UserTrendBean> userTrendList) {
        startPage++;
        if (userTrendList.isEmpty() && retryCount < 3) {
            retryCount++;
            queryData();
            return;
        } else {
            retryCount = 0;
        }

        statusView.setVisibility(View.GONE);

        if (userTrendList.isEmpty() && mAdapter.getData().isEmpty()) {
            statusView.setVisibility(View.VISIBLE);
            statusView.showEmpty(R.drawable.empty_chat, "暂时没有内容");

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            return;
        }

        if (mAdapter.isLoading()) {
            mAdapter.loadMoreComplete();

            if (userTrendList.size() == 0) {
                mAdapter.loadMoreEnd();
            }
        }

        if (swipeRefreshLayout.isRefreshing()) {
            // 加载第一页的数据
            swipeRefreshLayout.setRefreshing(false);
            mAdapter.replaceData(userTrendList);
            mAdapter.setLoadMoreView(new CustomLoadMoreView());
        } else {
            //去掉重复的数据
            ArrayList temp = new ArrayList();
            for (UserTrendBean bean : userTrendList) {
                if (bean.getTrendType() != UserTrendBean.TrendType.TREND_NULL) {
                    boolean ignoreItem = false;
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        if (bean.getId().equalsIgnoreCase(mAdapter.getData().get(i).getId())) {
                            ignoreItem = true;
                            break;
                        }
                    }

                    if (!ignoreItem) {
                        temp.add(bean);
                    }
                }
            }

            mAdapter.addData(temp);
        }
        if (App.DEBUG) {
            for (UserTrendBean datum : mAdapter.getData()) {
                Log.i("HotListFragment", datum.toString());
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoChange(EditProfileEvent event) {
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFriendChange(FriendChangeEvent event) {
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserPublicSuccess(UserPublishSuccessEvent event) {
        statusView.setVisibility(View.INVISIBLE);
        mAdapter.addData(0, event.getBean());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectedChanged(ConnectStatusEvent event) {
        super.onConnectedChanged(event);
        if (mAdapter.getData().isEmpty() && UtilsCode.INSTANCE.isConnected()) {
            queryData();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mIWHelper != null && mIWHelper.handleBackPressed()) {
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
