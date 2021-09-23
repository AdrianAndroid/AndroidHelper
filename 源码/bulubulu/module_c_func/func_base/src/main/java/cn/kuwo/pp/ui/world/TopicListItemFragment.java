package cn.kuwo.pp.ui.world;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.zhl.userguideview.AnimGuideView;
import com.zhl.userguideview.GuideUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.edittext.TextWatcherImp;
import cn.kuwo.common.event.ConnectStatusEvent;
import cn.kuwo.common.util.ArrayUtils;
import cn.kuwo.common.util.CustomLoadMoreView;
import cn.kuwo.common.util.L;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.func_base.utils.SPC;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.ui.topic.ChallengeFragment;
import cn.kuwo.pp.ui.topic.TopicDetailFragment;
import cn.kuwo.pp.ui.world.adapter.TopicItemAdapter;
import kotlin.jvm.functions.Function1;

public class TopicListItemFragment extends BaseFragment {

    //    public enum TopicListType {
//        TOPIC_HOT_LIST, // 最热
//        TOPIC_SUB_LIST  // 最新
//    }
    public static final int TOPIC_HOT_LIST = 0; // 最热
    public static final int TOPIC_SUB_LIST = 1; // 最新

    public static TopicListItemFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        TopicListItemFragment topicListItemFragment = new TopicListItemFragment();
        topicListItemFragment.setArguments(args);
        return topicListItemFragment;
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MultipleStatusView statusView;
    private View searchContainer;
    private EditText et_search; // 搜索框
    private ImageView closeSearch; //关闭搜索模式

    private void _initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        statusView = view.findViewById(R.id.multipleStatusView);
        et_search = view.findViewById(R.id.et_search);
        searchContainer = view.findViewById(R.id.searchContainer);
        closeSearch = view.findViewById(R.id.closeSearch);
    }

    protected int mType;

    protected TopicPresenter mPresenter;
    protected TopicItemAdapter mAdapter;

    protected int startPage = 1; // 页数
//    protected int pageCount = 10;
//    protected int retryCount = 0;

    //    private boolean mSearchMode = false;
    private ArrayList<TopicItemBean> searchList = new ArrayList<TopicItemBean>();
    private ArrayList<TopicItemBean> dataList = new ArrayList<TopicItemBean>(); // 临时变量

    private HashSet<String> hashSet = new HashSet<>(); //  去重用

//    private BaseQuickAdapter.RequestLoadMoreListener mLoadMoreListener;

//    public TopicListItemFragment(TopicListType type) {
//        super();
//        mType = type;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_item, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mType = getArguments().getInt("type", TOPIC_HOT_LIST);

        initRecyclerView();
        initStatusView();

        mPresenter = new TopicPresenter(this);

        initSearch();
        addListener();
        queryFirst();
    }


    private void initStatusView() {
        statusView.setVisibility(View.VISIBLE);
        statusView.showLoading();
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重新加载数据
                queryFirst();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new TopicItemAdapter(null);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int state = RecyclerView.SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (App.DEBUG) L.m(getPrintClass(), "ScrollingListener", newState);
                state = newState;
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    showGuideView(); // 状态为闲置的时候才显示
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (App.DEBUG) L.m(getPrintClass(), "ScrollingListener", dy);
                if (dy == 0 && RecyclerView.SCROLL_STATE_IDLE == state) {
                    showGuideView();
                }
            }
        });
    }

    void test() {
        RecyclerView.ViewHolder viewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(0);
    }


    private void initSearch() {
        if (mType == TOPIC_HOT_LIST) {
            // 显示
            searchContainer.setVisibility(View.VISIBLE);
            initSearchListener();
        } else {
            // 不显示
            searchContainer.setVisibility(View.GONE);
        }
    }

    // EditText 监听事件
    private void initSearchListener() {
        et_search.addTextChangedListener(new TextWatcherImp() {

            @Override
            public void onTextChanged(@org.jetbrains.annotations.Nullable CharSequence charSequence, int start, int before, int count) {
                //super.onTextChanged(charSequence, start, before, count);
                setSearchMode();
            }
        });
        closeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
                setSearchMode(); //设置列表
            }
        });
    }

    private void queryFirst() {
        startPage = 1;
//        dataList.clear();
//        searchList.clear();
//        mAdapter.getData().clear();
        //mAdapter.notifyDataSetChanged();
        queryData();
    }

    protected void queryData() {
        if (mType == TOPIC_HOT_LIST) {
            mPresenter.requestHotTopicList(startPage);
        } else if (mType == TOPIC_SUB_LIST) {
            mPresenter.requestSubTopicList(startPage);
        }
    }

    private void addListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 跳转到相亲页面
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.HOT_INTO_TOPIC_DETAIL, TopicListItemFragment.class.getSimpleName());
                startFromMain(TopicDetailFragment.newInstance(mAdapter.getItem(position)));
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ivTopicStart) {
                    // 跳转到挑战页面
                    startFromMain(ChallengeFragment.newInstance((TopicItemBean) adapter.getItem(position)));
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                // 此时不允许下拉刷新了
                //swipeRefreshLayout.setEnabled(false);
                // 编辑模式不能刷新数据
                if (!isSearchMode()) {
                    queryData();
                } else {
                    mAdapter.loadMoreComplete(); //直接消失
                }
            }
        }, recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 此时不允许加载更多了
                //mAdapter.setEnableLoadMore(false);
                queryFirst();
            }
        });
    }

    // 是否是搜索模式
    private boolean isSearchMode() {
        return et_search != null && !TextUtils.isEmpty(et_search.getText());
    }

    public void onAddTopics(List<TopicItemBean> topicList) {

        // 先恢复一下
        if (!swipeRefreshLayout.isEnabled()) {
            swipeRefreshLayout.setEnabled(true);
        }
        if (!mAdapter.isLoadMoreEnable()) {
            mAdapter.setEnableLoadMore(true);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (mAdapter.isLoading()) {
            mAdapter.loadMoreComplete();
        }

        statusView.setVisibility(View.GONE);

        if (startPage == 1) { // 首页
            dataList.clear();
            searchList.clear();
            mAdapter.getData().clear();
        }

        if (dataList.isEmpty() && topicList.isEmpty()) {
            // 暂时没有数据
            statusView.setVisibility(View.VISIBLE);
            statusView.showEmpty("暂时没有内容");
            mAdapter.notifyDataSetChanged();
        } else if (!dataList.isEmpty() && topicList.isEmpty()) {
            // 没有更多数据了
            mAdapter.loadMoreEnd(); // 没有更多数据
            mAdapter.notifyDataSetChanged();
        } else { //正常加载
            startPage++; //成功
//            if (mAdapter.getData().isEmpty()) { // 容易引起崩溃
//                mAdapter.notifyDataSetChanged(); //先清
//            }
            // 添加数据 , 第一页的时候已经清空数据，所以直接加就行了
            dataList.addAll(topicList); //添加数据
            // 这里添加去重
            //distinct(dataList);
            ArrayUtils.INSTANCE.distinctBy(hashSet, dataList, new Function1<TopicItemBean, String>() {
                @Override
                public String invoke(TopicItemBean topicItemBean) {
                    return topicItemBean.getId();
                }
            });
            mAdapter.replaceData(dataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    /*
public inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
val set = HashSet<K>()
val list = ArrayList<T>()
for (e in this) {
    val key = selector(e)
    if (set.add(key))
        list.add(e)
}
return list
}
 */


//    private void distinct(List<TopicItemBean> list) {
//        if (list == null && list.isEmpty()) {
//            return;
//        }
//        List<TopicItemBean> tmpList = new ArrayList<>();
//        hashSet.clear();
//        for (TopicItemBean topicItemBean : list) {
//            if (hashSet.add(topicItemBean.getId())) {
//                tmpList.add(topicItemBean);
//            }
//        }
//        list.clear();
//        list.addAll(tmpList);
//    }

    public void onGetTopicFail() {
        if (startPage == 1) {
            statusView.setVisibility(View.VISIBLE);
            statusView.showError();
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    /*
public inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val list = ArrayList<T>()
    for (e in this) {
        val key = selector(e)
        if (set.add(key))
            list.add(e)
    }
    return list
}
     */

    public void setSearchMode() {
        boolean searchMode = isSearchMode();
        // 不允许更新数据
        mAdapter.setEnableLoadMore(!searchMode);
        swipeRefreshLayout.setEnabled(!searchMode);

        if (searchMode) {
            closeSearch.setVisibility(View.VISIBLE);
        } else {
            closeSearch.setVisibility(View.GONE);
        }

        if (searchMode) {
            // 显示关闭按钮
            String text = et_search.getText().toString();
            searchList.clear(); //先清空原先的
            for (TopicItemBean topicItemBean : dataList) {
                if (topicItemBean.getName().contains(text)) {
                    searchList.add(topicItemBean);
                }
            }
            mAdapter.replaceData(searchList);
        } else {
            //恢复
            searchList.clear();
            mAdapter.replaceData(dataList);
        }
        mAdapter.notifyDataSetChanged();


    }

    public void onSearchTopicComplete(List<TopicItemBean> topics) {
        statusView.setVisibility(View.GONE);
        mAdapter.setNewData(topics);
    }


    public void showGuideView() {
//        if (App.DEBUG) SP.encode(SPC.TOPIC_HOT_LIST_HINT, true); //点击了之后才记录
        if (
                mType == TOPIC_HOT_LIST
                        && isVisible()
                        && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                        && UtilsCode.INSTANCE.getTopShow(getParentFragmentManager()) instanceof TopicListItemFragment
                        && SP.decodeBool(SPC.TOPIC_HOT_LIST_HINT, true)
        ) { // 热门的才显示

            try {
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                final View anchor = recyclerView.getLayoutManager().getChildAt(position); // 整个item
                if (getParentFragment().getView() instanceof FrameLayout && anchor != null) {
                    GuideUtils.showGuideAtTop(
                            _mActivity /*(FrameLayout) getParentFragment().getView()*/
                            , anchor.findViewById(R.id.ivTopicStart)
                            , new AnimGuideView.OnGuideListener() {
                                @Override
                                public void onTouched(AnimGuideView view, AnimGuideView.TYPE type) {
                                    if (type == AnimGuideView.TYPE.TARGET) {
                                        anchor.findViewById(R.id.ivTopicStart).performClick();// 模拟点击事件
                                        GuideUtils.dismissGuideView(view);
                                        SP.encode(SPC.TOPIC_HOT_LIST_HINT, false); //点击了之后才记录
                                    } else if (type == AnimGuideView.TYPE.HINT) {
                                        GuideUtils.dismissGuideView(view);
                                        SP.encode(SPC.TOPIC_HOT_LIST_HINT, false); //点击了之后才记录
                                    }  //GuideUtils.dismissGuideView(view);

                                }
                            }
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConnectedChanged(ConnectStatusEvent event) {
        super.onConnectedChanged(event);
        if (mAdapter.getData().isEmpty() && UtilsCode.INSTANCE.isConnected()) {
            queryData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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

    @Override
    public String getOtherTag() {
        if (mType == TOPIC_HOT_LIST) {
            return "TOPIC_HOT_LIST";
        } else if (mType == TOPIC_SUB_LIST) {
            return "TOPIC_SUB_LIST";
        } else {
            return null;
        }
    }


}
