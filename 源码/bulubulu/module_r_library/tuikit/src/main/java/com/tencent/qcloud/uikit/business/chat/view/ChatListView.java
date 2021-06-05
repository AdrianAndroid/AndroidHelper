package com.tencent.qcloud.uikit.business.chat.view;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.qcloud.uikit.business.chat.view.widget.ChatAdapter;

import java.util.Objects;

/**
 * 整个聊天信息界面
 */
public class ChatListView extends RecyclerView {
    public static final int DATA_CHANGE_TYPE_REFRESH = 0;
    public static final int DATA_CHANGE_TYPE_LOAD = 1;
    public static final int DATA_CHANGE_TYPE_ADD_FRONT = 2;
    public static final int DATA_CHANGE_TYPE_ADD_BACK = 3;
    public static final int DATA_CHANGE_TYPE_UPDATE = 4;
    public static final int DATA_CHANGE_TYPE_DELETE = 5;
    public static final int DATA_CHANGE_TYPE_CLEAR = 6;


    private DynamicChatUserIconView mChatIcon;
    private int mSelfBubbleId, mOppositeBubbleId, mTipsMessageBubbleId, mChatTimeBubbleId;
    private int mNameSize, mContextSize, mTipsMessageSize, mChatTimeSize;
    private int mNameColor, mSelfContentColor, mOppositeContentColor, mTipsMessageColor, mChatTimeColor;

    private boolean divided = true;

    private OnLoadMoreHandler mHandler;
    private OnEmptySpaceClickListener mEmptySpaceClickListener;


    public ChatListView(Context context) {

        super(context);
        init();
    }

    public ChatListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChatListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutFrozen(false);
        setItemViewCacheSize(0); //复用问题
        setHasFixedSize(true); //
        setFocusableInTouchMode(false); //获取焦点
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager); //方向
    }

    // 是否阻拦事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            View child = findChildViewUnder(e.getX(), e.getY());// 找到点击事件的那个View
            if (child == null) {
                if (mEmptySpaceClickListener != null)
                    mEmptySpaceClickListener.onClick(); //没有相关的View
            } else if (child instanceof ViewGroup) { // 找到一个容器
                ViewGroup group = (ViewGroup) child;
                final int count = group.getChildCount();
                float x = e.getRawX();
                float y = e.getRawY();
                View touchChild = null; //没找到这个view就回调了， 找到了似乎啥也没做
                for (int i = count - 1; i >= 0; i--) {
                    final View innerChild = group.getChildAt(i);
                    int position[] = new int[2];
                    innerChild.getLocationOnScreen(position); //获取它在屏幕上的坐标
                    if (x >= position[0]
                            && x <= position[0] + innerChild.getMeasuredWidth()
                            && y >= position[1]
                            && y <= position[1] + innerChild.getMeasuredHeight()) {
                        touchChild = innerChild;
                        break;
                    }
                }
                if (touchChild == null)
                    if (mEmptySpaceClickListener != null)
                        mEmptySpaceClickListener.onClick();
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    public DynamicChatUserIconView getUserChatIcon() {
        return mChatIcon;
    }

    public void setUserChatIcon(DynamicChatUserIconView mUserIcon) {
        this.mChatIcon = mChatIcon;
    }

    public int getSelfBubbleId() {
        return mSelfBubbleId;
    }

    public void setSelfBubbleId(int selfBubbleId) {
        mSelfBubbleId = selfBubbleId;
    }

    public int getOppositeBubbleId() {
        return mOppositeBubbleId;
    }

    public void setOppositeBubbleId(int oppositeBubbleId) {
        mOppositeBubbleId = oppositeBubbleId;
    }

    public int getTipsMessageBubbleId() {
        return mTipsMessageBubbleId;
    }

    public void setTipsMessageBubbleId(int tipsMessageBubbleId) {
        mTipsMessageBubbleId = tipsMessageBubbleId;
    }

    public int getChatTimeBubbleId() {
        return mChatTimeBubbleId;
    }

    public void setChatTimeBubbleId(int chatTimeBubbleId) {
        mChatTimeBubbleId = chatTimeBubbleId;
    }

    public int getNameSize() {
        return mNameSize;
    }

    public void setNameSize(int mSelfNameSize) {
        this.mNameSize = mSelfNameSize;
    }

    public int getNameColor() {
        return mNameColor;
    }

    public void setNameColor(int mSelfNameColor) {
        this.mNameColor = mSelfNameColor;
    }


    public int getContextSize() {
        return mContextSize;
    }

    public void setContextSize(int mSelfContextSize) {
        this.mContextSize = mSelfContextSize;
    }


    public int getSelfContentColor() {
        return mSelfContentColor;
    }

    public void setSelfContentColor(int mSelfContentColor) {
        this.mSelfContentColor = mSelfContentColor;
    }

    public int getOppositeContentColor() {
        return mOppositeContentColor;
    }

    public void setOppositeContentColor(int mOppositeContentColor) {
        this.mOppositeContentColor = mOppositeContentColor;
    }

    public static int getDataChangeTypeRefresh() {
        return DATA_CHANGE_TYPE_REFRESH;
    }

    public int getTipsMessageSize() {
        return mTipsMessageSize;
    }

    public void setTipsMessageSize(int mTipsMessageSize) {
        this.mTipsMessageSize = mTipsMessageSize;
    }

    public int getTipsMessageColor() {
        return mTipsMessageColor;
    }

    public void setTipsMessageColor(int mTipsMessageColor) {
        this.mTipsMessageColor = mTipsMessageColor;
    }

    public static int getDataChangeTypeLoad() {
        return DATA_CHANGE_TYPE_LOAD;
    }

    public int getChatTimeSize() {
        return mChatTimeSize;
    }

    public void setChatTimeSize(int mChatTimeSize) {
        this.mChatTimeSize = mChatTimeSize;
    }

    public int getChatTimeColor() {
        return mChatTimeColor;
    }

    public void setChatTimeColor(int mChatTimeColor) {
        this.mChatTimeColor = mChatTimeColor;
    }


    public boolean isDivided() {
        return divided;
    }

    public void setDivided(boolean divided) {
        this.divided = divided;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) { // 停下来之后
            if (mHandler != null) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                assert layoutManager != null;
                int firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (firstPosition == 0 && ((lastPosition - firstPosition + 1) < Objects.requireNonNull(getAdapter()).getItemCount())) { // 没加载完全
                    if (getAdapter() instanceof ChatAdapter) {
                        ((ChatAdapter) getAdapter()).showLoading();
                    }
                    mHandler.loadMore();
                }
            }
        }
    }


    public void scrollToEnd() {
        if (getAdapter() != null)
            scrollToPosition(getAdapter().getItemCount() - 1);
    }

    public OnLoadMoreHandler getLoadMoreHandler() {
        return mHandler;
    }

    public void setMLoadMoreHandler(OnLoadMoreHandler mHandler) {
        this.mHandler = mHandler;
    }

    public OnEmptySpaceClickListener getEmptySpaceClickListener() {
        return mEmptySpaceClickListener;
    }

    public void setEmptySpaceClickListener(OnEmptySpaceClickListener mEmptySpaceClickListener) {
        this.mEmptySpaceClickListener = mEmptySpaceClickListener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    public interface OnLoadMoreHandler {
        void loadMore();
    }

    public interface OnEmptySpaceClickListener {
        void onClick();
    }

}
