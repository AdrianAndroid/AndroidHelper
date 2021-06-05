package cn.kuwo.pp.ui.system;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.qcloud.uikit.business.chat.view.ChatBottomInputGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.SystemMessageEvent;
import cn.kuwo.pp.http.bean.systemmessage.SystemMessageBean;
import cn.kuwo.pp.manager.SystemMessageManager;
import cn.kuwo.pp.ui.mine.UserInfoFragment;
import cn.kuwo.pp.ui.system.adapter.SystemMessageAdapter;
import cn.kuwo.pp.ui.web.WebFragment;

/**
 * 布鲁布鲁小助手(系统消息界面)
 * ----列表
 * ----MainActivty
 */
public class SystemMessageFragment extends BaseFragment {
    RecyclerView recyclerView;
    ChatBottomInputGroup chatBottomBox;

    private void _initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        chatBottomBox = view.findViewById(R.id.chat_bottom_box);
    }

    private SystemMessagePresenter mPresenter;
    private SystemMessageAdapter mAdapter;
    protected int startPage = 1;
    protected int pageCount = 10;

    public static SystemMessageFragment newInstance() {
        return new SystemMessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_message, container, false);
        _initView(view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enableToolbar(R.id.toolbar, true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
        // 获取系统消息，显示在屏幕上
        mAdapter = new SystemMessageAdapter(_mActivity, SystemMessageManager.getInstance().getMessageList());
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(mAdapter);
        // 请求网络
        mPresenter = new SystemMessagePresenter(this);

        initListener();

        // 更新系统消息
        SystemMessageManager.getInstance().resetNewCount();
        if (SystemMessageManager.getInstance().getMessageList().size() == 0) {
            SystemMessageManager.getInstance().updateMessage(startPage, pageCount, this);
        }

        scrollToEnd();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                chatBottomBox.hideSoftInput();
                // 点击的这个歌消息
                SystemMessageBean systemMessageBean = mAdapter.getItem(position);
                if (systemMessageBean == null) return; // 省的有错误提示
                if (systemMessageBean.getItemType() == SystemMessageBean.SystemMessageCategory.CONTENT_ACTION.ordinal()) {
                    if (systemMessageBean.getFrom() != null) {
                        startFromMain(UserInfoFragment.newInstance(null, systemMessageBean.getFrom()));
                    }
                } else if (systemMessageBean.getItemType() == SystemMessageBean.SystemMessageCategory.SYSTEM_PUSH_TEXT.ordinal()) {
                    // 系统推送的消息
                    if (systemMessageBean.getMessageSource() != null) {
                        if (!TextUtils.isEmpty(systemMessageBean.getMessageSource().getUrl())) {
                            startFromMain(WebFragment.newInstance(systemMessageBean.getMessageSource().getUrl(), "", ""));
                        }
                    }
                } else if (systemMessageBean.getItemType() == SystemMessageBean.SystemMessageCategory.USER_SEND_TEXT.ordinal()) {
                    // 用户发送的消息
                    if (systemMessageBean.getUserMessageSource() != null) {
                        if (!TextUtils.isEmpty(systemMessageBean.getUserMessageSource().getUrl())) {
                            startFromMain(WebFragment.newInstance(systemMessageBean.getUserMessageSource().getUrl(), "", ""));
                        }
                    }
                } else if (systemMessageBean.getItemType() == SystemMessageBean.SystemMessageCategory.RELATION_SHIP.ordinal()) {
                    // 相关的消息
                    if (systemMessageBean.getFrom() != null) {
                        startFromMain(UserInfoFragment.newInstance(null, systemMessageBean.getFrom()));
                    }
                }
            }
        });

        // 下面的消息监听
        chatBottomBox.setSystemMessageMode(new ChatBottomInputGroup.SystemMessageHandler() {
            @Override
            public void sendMessage(String msg) {
                mPresenter.userReply(1, msg); //回复消息
            }

            @Override
            public void sendImage(Uri imageUri, boolean appPhoto) {
                mPresenter.userReply(2, mPresenter.selectImagePath(imageUri, appPhoto)); //回复图片
            }
        });

        // 预加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert layoutManager != null;
                    int firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                    int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (firstPosition == 0 && ((lastPosition - firstPosition + 1) < mAdapter.getItemCount())) {
                        // 预加载更新系统消息
                        SystemMessageManager.getInstance().updateMessage(startPage, pageCount, SystemMessageFragment.this);
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemMessageEvent(SystemMessageEvent event) {
        startPage++;
        mAdapter.replaceData(SystemMessageManager.getInstance().getMessageList());

        if (!event.isNotifyNewMessage()) {
            scrollToEnd();
        }
    }

    private void scrollToEnd() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        }, 500);
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
