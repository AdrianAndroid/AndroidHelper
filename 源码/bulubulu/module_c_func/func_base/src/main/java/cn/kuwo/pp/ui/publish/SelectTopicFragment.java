package cn.kuwo.pp.ui.publish;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.ui.publish.adapter.SelectTopicAdapter;

/**
 * 添加话题
 */
public class SelectTopicFragment extends BaseFragment {
    EditText et_topic;
    TextView tv_hot;
    RecyclerView recycler_view;

    private void _initView(View view) {
        et_topic = view.findViewById(R.id.et_topic);
        tv_hot = view.findViewById(R.id.tv_hot);
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    private SelectTopicAdapter mAdapter;
    private SelectTopicPresenter mPresenter;
    private ArrayList<TopicItemBean> mSearchTopicList = new ArrayList();
    private ArrayList<TopicItemBean> mHotTopicList = new ArrayList();

    public static SelectTopicFragment newInstance() {
        SelectTopicFragment fragment = new SelectTopicFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_topic, container, false);
        _initView(view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, "添加话题", true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);

        mPresenter = new SelectTopicPresenter(this);

        mAdapter = new SelectTopicAdapter(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(mAdapter);

        addListener();
        requestHotTopics();
        showSoftInput(et_topic);
    }

    private void addListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TopicItemBean bean = (TopicItemBean) adapter.getItem(position);
                mPresenter.createTopic(bean.getName());
            }
        });


        et_topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    mAdapter.setNewData(mHotTopicList);
                } else {
                    if (mSearchTopicList.size() > 0) {
                        mSearchTopicList.remove(0);
                    }

                    TopicItemBean itemBean = new TopicItemBean();
                    itemBean.setName(charSequence.toString());
                    mSearchTopicList.add(0, itemBean);

                    mAdapter.setNewData(mSearchTopicList);

                    mPresenter.searchTopic(charSequence.toString());
                }
                tv_hot.setVisibility(charSequence.toString().isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void requestHotTopics() {
        mPresenter.requestHotTopicList(1, 30);
    }

    public void onSelectedTopic(TopicItemBean bean) {
        getActivityViewModel(PublishViewModel.class).addTopicList(bean);
        pop();
    }

    public void onSearchTopicComplete(List<TopicItemBean> topics) {
        mSearchTopicList.clear();

        String edText = et_topic.getText().toString();
        boolean isNew = true;
        for (int i = 0; i < topics.size(); i++) {
            if (edText.equalsIgnoreCase(topics.get(i).getName())) {
                isNew = false;
                break;
            }
        }
        if (isNew) {
            TopicItemBean itemBean = new TopicItemBean();
            itemBean.setName(et_topic.getText().toString());
            itemBean.setNew(true);
            mSearchTopicList.add(0, itemBean);
        }

        mSearchTopicList.addAll(topics);
        mAdapter.setNewData(mSearchTopicList);
        tv_hot.setVisibility(View.GONE);
    }

    public void onGetHotTopicComplete(List topics) {
        mHotTopicList.addAll(topics);
        mAdapter.setNewData(mHotTopicList);
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
