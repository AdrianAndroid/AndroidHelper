package cn.kuwo.pp.ui.mine;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.google.gson.JsonObject;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.util.CustomLoadMoreView;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.IPlayCallback;
import cn.kuwo.player.PlayError;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.UserPublishSuccessEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.EmptyTrendBean;
import cn.kuwo.pp.http.bean.UserOnlineBean;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.chat.C2CChatFragment;
import cn.kuwo.pp.ui.login.EditProfileFragment;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.mine.adapter.UserInfoAdapter;
import cn.kuwo.pp.ui.publish.PublishMainFragment;
import cn.kuwo.pp.ui.share.ShareCardDialog;
import cn.kuwo.pp.ui.share.ShareTrendDialog;
import cn.kuwo.pp.ui.topic.TopicDetailFragment;
import cn.kuwo.pp.ui.world.CommentDialog;
import cn.kuwo.pp.ui.world.adapter.HotListAdapter;
import cn.kuwo.pp.util.GlideSimpleLoader;
import cn.kuwo.pp.util.IconFountTextView;
import io.reactivex.Observable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import pl.droidsonroids.gif.GifImageView;

;
;

/**
 * 个人信息页
 */
public class UserInfoFragment extends BaseFragment {
    public static String KEY_VOICE_INFO = "voice_info";
    public static String KEY_USER_INFO = "user_info";
    public static String TAG = "USER";

    TextView tvUserName;
    RecyclerView recyclerView;
    LinearLayout topHoverLayout;
    IconFountTextView btnManager;
    ImageView ivChat;
    ImageView ivFollow;
    ImageView ivPublish;
    GifImageView iv_gif_bg;

    private void _initView(View view) {
        tvUserName = view.findViewById(R.id.tvToolBarUserName);
        recyclerView = view.findViewById(R.id.recyclerView);
        topHoverLayout = view.findViewById(R.id.top_hover_layout);
        btnManager = view.findViewById(R.id.btnManager);
        ivChat = view.findViewById(R.id.ivChat);
        ivFollow = view.findViewById(R.id.ivFollow);
        ivPublish = view.findViewById(R.id.ivPublish);
        iv_gif_bg = view.findViewById(R.id.iv_gif_bg);
    }

    private VoiceInfo mVoiceInfo;
    private UserInfo mUserInfo;
    private boolean mIsMe = false;
    private String mPlayingUrl;

    private UserInfoPresenter mPresenter;
    private UserInfoAdapter mAdapter;
    private int mStartPage = 1;
    private int mPageCount = 10;
    private int mScrollPosition = 0;

    private ImageWatcherHelper mIWHelper;

    public static UserInfoFragment newInstance(VoiceInfo voiceInfo, UserInfo userInfo) {
        Bundle args = new Bundle();

        if (voiceInfo != null) {
            args.putParcelable(KEY_VOICE_INFO, voiceInfo);
        }

        if (userInfo != null) {
            //String str = userInfo.toString();
            args.putString(KEY_USER_INFO, userInfo.toString());
        }

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        _initView(view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, true);
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);

        mVoiceInfo = getArguments().getParcelable(KEY_VOICE_INFO);
        String userJson = getArguments().getString(KEY_USER_INFO);
        if (userJson != null) {
            mUserInfo = UserInfo.Companion.fromJson(userJson);
        } else if (mVoiceInfo != null) {
            mUserInfo = new UserInfo(mVoiceInfo.getCity(), "", mVoiceInfo.getAge(), 0, "", "", mVoiceInfo.getName(), "",
                    mVoiceInfo.getHeadImg(), mVoiceInfo.isGirl() ? 2 : 1, 0, 0, mVoiceInfo.getUid(), 10, 0, "", new JsonObject(), null);
        }

        tvUserName.setText(mUserInfo.getName());

        if (mUserInfo.getUid() == null) {
            UtilsCode.INSTANCE.showLong("没有用户信息");
            pop();
            return;
        }

        String UserId = mUserInfo.getUid();
        if (UserInfoManager.INSTANCE.isLogin() && UserId.equals(UserInfoManager.INSTANCE.getUid())) {
            mIsMe = true;
        } else {
            ivChat.setVisibility(View.VISIBLE);
            ivFollow.setVisibility(View.VISIBLE);
            ivFollow.setImageResource(FriendListManager.getInstance().isFollowedUser(UserId) ? R.drawable.mine_followed : R.drawable.mine_follow);
        }

        ImageLoader.showGif(iv_gif_bg, getUriFromDrawableRes(getContext(), getGifResourceId()).toString(), 0);

        ArrayList dataList = new ArrayList();
        UserTrendBean userBean = new UserTrendBean();
        userBean.setUser(mUserInfo == null ? UserInfoManager.INSTANCE.getUserInfo() : mUserInfo);
        dataList.add(userBean);

        mAdapter = new UserInfoAdapter(getContext(), dataList, mVoiceInfo, mIsMe);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        initListener();

        mPresenter = new UserInfoPresenter(this);
        queryData();

        if (mVoiceInfo == null) {
            requestVoiceInfo(mUserInfo.getUid());
        }

        if (!mIsMe) {
            ivPublish.setVisibility(View.INVISIBLE);
        }
    }

    public void queryData() {
        if (mIsMe) { // 自己
            mPresenter.requestMyTrendList(mUserInfo.getUid(), mStartPage, mPageCount);
        } else { //他人
            if (UserInfoManager.INSTANCE.isLogin()) {
                mPresenter.getMatchValue(mUserInfo.getUid());
                mPresenter.requestUserTrendList(UserInfoManager.INSTANCE.getUserInfo().getUid(), mUserInfo.getUid(), mStartPage, mPageCount);
            }
        }
    }

    @Override
    public void onSupportInvisible() {
        BuluPlayer.getInstance().stop();
    }

    public void onDeleteTrend(UserTrendBean bean) {
        int position = mAdapter.getData().indexOf(bean);
        boolean isLast = (position == mAdapter.getData().size() - 1);
        if (position >= 0) {
            mAdapter.remove(position);

            if (isLast) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemChanged(position);
            }
        }

        if (mAdapter.getData().size() < 2) {
            mStartPage = 0;
            onNoTrends(false);
        }
    }

    public void onNoTrends(boolean isFail) {
        if (isFail) {
            return;
        }

        if (mStartPage == 1) {
            if (mAdapter.getData().size() < 2) {
                EmptyTrendBean bean = new EmptyTrendBean();
                mAdapter.addData(bean);
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.setEnableLoadMore(false);
        } else {
            if (mAdapter.isLoading()) {
                mAdapter.loadMoreEnd();
            }
        }
    }

    public void onAddTrends(List<UserTrendBean> userTrendList) {
        mStartPage++;
        mAdapter.setEnableLoadMore(true);
        if (mAdapter.isLoading()) {
            mAdapter.loadMoreComplete();

            if (userTrendList.size() == 0) {
                mAdapter.loadMoreEnd();
            }
        }

        //去掉错误的数据
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

        if (temp.size() > 0) {
            mAdapter.addData(temp);
            mAdapter.notifyDataSetChanged();
        } else {
            ivPublish.setVisibility(View.INVISIBLE);

            EmptyTrendBean bean = new EmptyTrendBean();
            mAdapter.addData(bean);
        }
    }

    private void initListener() {
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFromMain(new SettingFragment());
            }
        });

        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIMConversation conv = TIMManager.getInstance().getConversation(TIMConversationType.C2C, mUserInfo.getUid());
                if (App.DEBUG) L.L(getClass(), "ivChat", "TIMConversation");
                if (conv.getLastMsg() == null) {
                    FriendListManager.getInstance().sayHello(mUserInfo.getUid());
                }

                C2CChatFragment fragment = C2CChatFragment.Companion.newInstance("", mUserInfo.getUid(), mVoiceInfo);
                fragment.setFromMatch();
                start(fragment, SINGLETASK);
            }
        });

        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FriendListManager.getInstance().isFollowedUser(mUserInfo.getUid())) {
                    // 取消关注
                    FriendListManager.getInstance().delete(mUserInfo.getUid(), UserInfoFragment.this, new FriendListManager.IFriendListManagerResult() {
                        @Override
                        public void IFriendListManagerResult_success(int result) {
                            UtilsCode.INSTANCE.showShort("取消关注成功");
                            if (App.DEBUG) L.L(getClass(), "取消关注成功");
                            ivFollow.setImageResource(R.drawable.mine_follow);
                        }

                        @Override
                        public void IFriendListManagerResult_failed(String msg) {
                            if (App.DEBUG) L.L(getClass(), "取消关注失败");
                            UtilsCode.INSTANCE.showShort("取消关注失败: " + msg);
                        }
                    });
                } else {
                    // 关注
                    FriendListManager.getInstance().follow(mUserInfo, UserInfoFragment.this, new FriendListManager.IFriendListManagerResult() {
                        @Override
                        public void IFriendListManagerResult_success(int result) {
                            UtilsCode.INSTANCE.showShort("关注成功");
                            if (App.DEBUG) L.L(getClass(), "关注成功");
                            ivFollow.setImageResource(R.drawable.mine_followed);
                        }

                        @Override
                        public void IFriendListManagerResult_failed(String msg) {
                            UtilsCode.INSTANCE.showShort("关注失败: " + msg);
                            if (App.DEBUG) L.L(getClass(), "关注失败");
                        }
                    });
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mStartPage > 1) {
                    queryData();
                }
            }
        }, recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollPosition += dy;
                float alpha = (float) Math.abs(mScrollPosition) / (float) UtilsCode.INSTANCE.dp2px(200);
                if (alpha > 1.0) {
                    alpha = 1;
                }

                topHoverLayout.getBackground().mutate().setAlpha((int) (alpha * 255.0));
                tvUserName.setAlpha(alpha);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (view.getId() == R.id.tvMyVoice) {
//                    startFromMain(VoiceEditFragment.newInstance(mVoiceInfo, false));
//                } else
                if (view.getId() == R.id.anim_circle) {
                    if (mIsMe) {
                        startFromMain(EditProfileFragment.Companion.newInstance(1));
                    }
                } else if (view.getId() == R.id.tvVoicePlayState) {
                    if (BuluPlayer.getInstance().isPlayingUrl(mPlayingUrl)) {
                        BuluPlayer.getInstance().autoSwitch();
                    } else {
                        if (mVoiceInfo.getUrl() != null) {
                            mPlayingUrl = mVoiceInfo.getUrl();
                            BuluPlayer.getInstance().play(mPlayingUrl);
                        }
                    }
                } else if (view.getId() == R.id.ivOptionOne || view.getId() == R.id.ivOptionTwo || view.getId() == R.id.viewOptionOneBg || view.getId() == R.id.viewOptionTwoBg) {
                    if (position >= mAdapter.getData().size()) {
                        return;
                    }
                    UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);

                    if (model.getPicked().isEmpty()) {
                        model.setPicked(UserTrendBean.ANSWER_ONE);
                        if (view.getId() == R.id.ivOptionTwo || view.getId() == R.id.viewOptionTwoBg) {
                            model.setPicked(UserTrendBean.ANSWER_TWO);
                        }

                        mPresenter.answerQuestion(model.getId(), model.getPicked());

                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyItemChanged(position);
                            }
                        }, 100);

                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PK_VOTE, TAG);
                    }
                } else if (view.getId() == R.id.ivPraise) {
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_PRAISE, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        start(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    if (position >= mAdapter.getData().size()) {
                        return;
                    }
                    UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);

                    if (model.getLiked() == 0) {
                        mPresenter.likeTrend(true, model);
                    } else {
                        mPresenter.likeTrend(false, model);
                    }
                } else if (view.getId() == R.id.ivShare) {
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_SHARE, TAG);

                    UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);
                    model.setUser(mUserInfo);
                    if (model.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH) {
                        ShareTrendDialog.newInstance(model).show(getChildFragmentManager());
                    } else {
                        ShareCardDialog.newInstance(model.toQuestionModel(), 0, 0).show(getChildFragmentManager());
                    }
                } else if (view.getId() == R.id.ivComment) {
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_COMMENT, TAG);

                    UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);
                    CommentDialog.newInstance(model, UserInfoFragment.this).show(getChildFragmentManager());
                } else if (view.getId() == R.id.btn_make_trend) {
                    startFromMain(PublishMainFragment.newInstance(PublishMainFragment.FROM_USER_PAGE));
                } else if (view.getId() == R.id.ivDelete) {
                    UserTrendBean model = (UserTrendBean) mAdapter.getData().get(position);
//                    AlertDialog.getInstance(_mActivity, "", model.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH ? "确认删除该动态?" : "确认删除该投票", "确认", "取消", (dialog, which) -> {
//                        mPresenter.deleteUserTrend(mUserInfo.getUid(), model);
//                    }).show();

                    DialogUtils.INSTANCE.showDeleteDynamicOrVote(
                            model.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH,
                            new Function1<Boolean, Unit>() {
                                @Override
                                public Unit invoke(Boolean aBoolean) {
                                    mPresenter.deleteUserTrend(mUserInfo.getUid(), model);
                                    return null;
                                }
                            }, UserInfoFragment.this);
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

        BuluPlayer.getInstance().addPlayCallback(new IPlayCallback() {
            @Override
            public void onPlayStart() {
                updatePlayState(true);
            }

            @Override
            public void onBuffering(int bufPercent) {

            }

            @Override
            public void onPlaying() {

            }

            @Override
            public void onProgress(int nProgress) {

            }

            @Override
            public void onPlayPause() {
                updatePlayState(false);
            }

            @Override
            public void onPlayStop() {
                updatePlayState(false);
                if (TextUtils.equals(BuluPlayer.getInstance().getDataSource(), mPlayingUrl)) {
                    mPlayingUrl = null;
                }
            }

            @Override
            public void onSeekCompleted() {

            }

            @Override
            public void onError(PlayError code, String errExtMsg) {
                updatePlayState(false);
                mPlayingUrl = null;
            }
        });

        ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFromMain(PublishMainFragment.newInstance(PublishMainFragment.FROM_USER_PAGE));
            }
        });
    }

    private void updatePlayState(boolean playing) {
        mAdapter.setPlayState(playing);
    }

    // 用户的详细信息
    private void requestVoiceInfo(String uid) {
        if (uid == null) {
            return;
        }

        Observable<BaseHttpResult<VoiceInfo>> observable = RetrofitClient.getApiService()
                .getUserDetail(uid).compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<VoiceInfo>() {
                    @Override
                    public void onNext(VoiceInfo o) {
                        mVoiceInfo = o;
                        mAdapter.setVoiceInfo(o); //整个列表上的

                        if (mIsMe) { // 是自己的话
                            UserInfoManager.INSTANCE.updateVoice(o);
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {

                    }
                });
    }

    public void onLikeResult(UserTrendBean userTrendBean, boolean like, boolean success) {
        userTrendBean.setLiked(like ? 1 : 0);
        mAdapter.notifyItemChanged(mAdapter.getData().indexOf(userTrendBean));

        if (success) {
            UtilsCode.INSTANCE.showShort(like ? "点赞成功" : "取消点赞成功");
        } else {
            UtilsCode.INSTANCE.showShort(like ? "点赞失败" : "取消点赞失败");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserPublicSuccess(UserPublishSuccessEvent event) {
        UserTrendBean bean = mAdapter.getItem(1);
        if (bean instanceof EmptyTrendBean) {
            mAdapter.remove(1);
        }

        mAdapter.addData(1, event.getBean());
        mAdapter.notifyDataSetChanged();
    }

    public void onGetMatchValue(int value) {
        mUserInfo.setMatchValue(value);
        mAdapter.setMatchValue(value);
    }

    public void onGetUserOnline(UserOnlineBean bean) {
        if (bean.isOnline()) {
            mAdapter.setOnline();
        }
    }

    private Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

    private int getGifResourceId() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                return R.drawable.user_info_gif_0;
            case 1:
                return R.drawable.user_info_gif_1;
            default:
                return R.drawable.user_info_gif_2;
        }
    }

    @Override
    protected void onLogoutSuccess() {
        if (mIsMe) {
            pop();
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
    public void onSupportVisible() {
        super.onSupportVisible();
    }


    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
