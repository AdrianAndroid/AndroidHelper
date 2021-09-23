package cn.kuwo.pp.ui.discover;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.app.App;
import cn.kuwo.common.util.L;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.func_base.utils.PlayBgMusic;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.MainTabSelectChangeEvent;
import cn.kuwo.pp.event.MatchSuccessEvent;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.discover.adapter.FriendTestListAdapter2;
import cn.kuwo.pp.ui.discover.view.PolygonProgressView;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.mine.UserInfoFragment;
import cn.kuwo.pp.ui.share.ShareCardDialog;
import cn.kuwo.pp.ui.world.CommentDialog;
import cn.kuwo.pp.util.UserActionLog;

public class FriendTestFragment extends FingerAnimFragment {

    private RecyclerView recyclerView;
    private ImageView ivMatch;
    private PolygonProgressView viewAnswerProgress;
    private LottieAnimationView animFirstGuide;
    private TextView tvBadgeView;

    private FriendTestListAdapter2 mAdapter;
    private FriendTestPresenter mPresenter;
    private ArrayList mQuestionList = new ArrayList();
    private boolean isLoadingMore = false;
    private KwTimer mAutoNextTimer; //倒计时
    private KwTimer mSkipTimer; // 倒计时

    private long mLastPageTime = System.currentTimeMillis();
    private int mLastQuestion = 0;
    private int mAnswerCount = 0;
    private int mCanMatchCount = 0;
    private static int MATCH_FOR_ANSWER = 10;

    public static String TAG = "PK";
    public static String MATCH_COUNT_KEY = "MATCH_COUNT_KEY";
    public static String ANSWER_COUNT_KEY = "ANSWER_COUNT_KEY";
    public static String PLAY_BG_MUSIC = "PLAY_BG_MUSIC";

    private FriendMatchingDialog mMatchingDialog;

    private boolean mIsMusicAutoStop = false;

    public static FriendTestFragment newInstance() {
        return new FriendTestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_test, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        ivMatch = view.findViewById(R.id.ivMatch);
        viewAnswerProgress = view.findViewById(R.id.viewAnswerProgress);
        animFirstGuide = view.findViewById(R.id.animFirstGuide);
        tvBadgeView = view.findViewById(R.id.tvBadgeView);
        return attachBackgroundView(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initRecyclerView(); // Recycler

        animFirstGuide.setImageAssetsFolder("json_image_4");

        mPresenter = new FriendTestPresenter(this);
        mPresenter.requestQuestionList(); // 请求数据

        addListener();
        initAnswerData();

        if (SP.decodeBool(PLAY_BG_MUSIC, true)) {
            playBgMusic();
        }

//        // 显示隐私政策
//        PrivacyDialogUtil.showPrivacy(view.findViewById(R.id.privacy), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startFromMain(WebFragment.newInstance("https://h5app.kuwo.cn/m/3dab9c3a/server.html", "用户协议", ""));
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startFromMain(WebFragment.newInstance("https://h5app.kuwo.cn/m/3d724391/secret.html", "隐私政策", ""));
//            }
//        });
    }

    private void initRecyclerView() {
        mAdapter = new FriendTestListAdapter2(this, null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(recyclerView); // 为了找到recyclerView中某个位置
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopBgMusic();
    }

//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//
//        if (mIsMusicAutoStop) {
//            playBgMusic();
//        }
//        mIsMusicAutoStop = false;
//    }
//
//    @Override
//    public void onSupportInvisible() {
//        super.onSupportInvisible();
//
//        if (isPlayingBgMusic()) {
//            stopBgMusic();
//            mIsMusicAutoStop = true;
//        }
//    }

    @Override
    public void onChildSupportVisible() {
        super.onChildSupportVisible();
        if (mIsMusicAutoStop) {
            playBgMusic();
        }
        mIsMusicAutoStop = false;
    }

    @Override
    public void onChildSupportInvisible() {
        super.onChildSupportInvisible();

        if (isPlayingBgMusic()) {
            stopBgMusic();
            mIsMusicAutoStop = true;
        }
    }

    // 初始化问题的数据
    private void initAnswerData() {
        if (UserInfoManager.INSTANCE.isLogin()) {
            mPresenter.getMatchCount();
        } else {
            mCanMatchCount = SP.decodeInt(MATCH_COUNT_KEY, 0);
            mAnswerCount = SP.decodeInt(ANSWER_COUNT_KEY, 0);
            updateMatchUI();
        }
    }

    // 更新匹配的数字
    public void updateMatchCount(int canMatchCount, int answerCount) {
        mCanMatchCount = canMatchCount;
        mAnswerCount = answerCount;
        updateMatchUI();
    }

    private void updateMatchUI() {
        ivMatch.setImageResource(mCanMatchCount > 0 ? R.drawable.friend_test_match_highlight : R.drawable.friend_test_match);
        // 显示匹配的个数
        if (mCanMatchCount > 0) {
            tvBadgeView.setVisibility(View.VISIBLE);
            tvBadgeView.setText(mCanMatchCount + ""); //显示的个数
        } else {
            tvBadgeView.setText("");
            tvBadgeView.setVisibility(View.INVISIBLE);
        }

        // 答对了几道题
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewAnswerProgress.setProgress(mAnswerCount);
            }
        }, 200);
    }

    private void addListener() {
        // 添加Adapter的点击事件
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (position >= mQuestionList.size()) {
                    return;
                }
                QuestionModel model = (QuestionModel) mQuestionList.get(position);

                int viewId = view.getId();
                if (viewId == R.id.ivShare) { // 点击分享
                    // 分享
                    stopAutoScroll();

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    ShareCardDialog.newInstance(model, mAnswerCount, mCanMatchCount).show(getChildFragmentManager());
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_SHARE, TAG);
                } else if (viewId == R.id.ivComment) { // 点击评论
                    // 评论
                    stopAutoScroll();
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_COMMENT, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    if (model != null) {
                        CommentDialog.newInstance(model, FriendTestFragment.this).show(getChildFragmentManager());
                    }
                } else if (viewId == R.id.ivMusic) { // 点击播放音乐
                    // 播放/停止音乐
                    if (!isPlayingBgMusic()) {
                        playBgMusic();
                        SP.encode(PLAY_BG_MUSIC, true);
                    } else {
                        stopBgMusic();
                        SP.encode(PLAY_BG_MUSIC, false);
                    }
                    mAdapter.changeMusic(position);
                } else if (viewId == R.id.ivPraise) { // 点击点赞
                    // 喜欢
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_PRAISE, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    mPresenter.likeQuestion(model.getLiked() == 0, model);
                } else if (viewId == R.id.ivSkip) { // 点击跳过
                    skipToNext(); // 跳转到下一个
                } else if (viewId == R.id.optionOne || viewId == R.id.optionTwo) {
                    // 震动一下，每次都给他震动一下
                    Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                    vibrator.vibrate(100);

                    if (model.getAnswerModel() != null) {
                        skipToNext();// 跳转到下一页
                        return;
                    }
                    String answer = "optionOne"; // 选项的答案
                    if (viewId == R.id.optionTwo) {
                        answer = "optionTwo";
                    }
                    // 设置回答问题的Model
                    model.setAnswerModel(new QuestionAnswerModel(answer));

                    // 网络请求，回答问题
                    mPresenter.answerQuestion(model, position);
                    // 统计数据
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PK_VOTE, TAG);
                    sendQuestionAnswerLog();
                } else if (viewId == R.id.tvUserName || viewId == R.id.ivUserHeader) {
                    // 跳转到用户详情页面
                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        startFromMain(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    startFromMain(UserInfoFragment.newInstance(null, model.getUser()));
                } else {
                    if (App.DEBUG) L.m(FriendTestFragment.this.getClass(), "没有找到你想要的点击事件");
                }
            }
        });
        // 自动加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx < 0 || isLoadingMore) {
                    return;
                }
                // 到达某个程度后，自动加载下一页
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager.findFirstVisibleItemPosition() + 3 >= linearLayoutManager.getItemCount()) {
                    isLoadingMore = true;
                    mPresenter.requestQuestionList(); // 加载更多
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (App.DEBUG)
                    L.m(FriendTestFragment.this.getClass(), "newState", newState);
                // 监听滚动的状态
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    stopAutoScroll(); // 停止自动滚动
                    setSkipTimer(); // 设置倒计时

                    sendPageRemainLog();
                    checkPassQuestion();
                    mLastQuestion = getCurrentPosition();
                    // 请求评论 // 获取评论， 但是获取成功之后，就不重复获取了
                    QuestionModel item = mAdapter.getItem(mLastQuestion);
                    if (item != null && !item.isHadComment()) { // 没有评论的时候才加载第一页
                        mPresenter.getComment(item, 1);
                    }
                }
            }
        });

        ivMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PK_MATCH, TAG);

                if (!UserInfoManager.INSTANCE.isLogin()) {
                    startFromMain(LoginFragment.Companion.newInstance(false));
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.MATCH_LOGIN, "");
                    return;
                }

                if (mCanMatchCount == 0) {
                    UtilsCode.INSTANCE.showShort("需要答10道题才能匹配哦");
                    return;
                }

                mMatchingDialog = FriendMatchingDialog.newInstance(_mActivity);
                mMatchingDialog.show();
            }
        });
    }

    private void checkPassQuestion() {
        // 检查题目是否正确
        if (mLastQuestion >= mAdapter.getItemCount()) {
            return;
        }

        QuestionModel model = (QuestionModel) mQuestionList.get(mLastQuestion);
        if (model.getAnswerModel() == null) {
            mPresenter.passQuestion(model);
        }
    }

    // 匹配成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMatchSuccess(MatchSuccessEvent event) {
        // 匹配成功
        if (mCanMatchCount > 0) {
            mCanMatchCount--; // 本地计算匹配次数
            SP.encode(MATCH_COUNT_KEY, mCanMatchCount);
        }

        tvBadgeView.setVisibility(View.VISIBLE);
        tvBadgeView.setText(mCanMatchCount + "");
        ivMatch.setImageResource(mCanMatchCount > 0 ? R.drawable.friend_test_match_highlight : R.drawable.friend_test_match);
        if (mCanMatchCount == 0) {
            tvBadgeView.setVisibility(View.INVISIBLE);
        }
    }

    private QuestionModel getCurrentModel() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        if (position < 0 || position >= mAdapter.getItemCount()) {
            return null;
        }

        return mAdapter.getItem(position);
    }

    private int getCurrentPosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        if (position < 0 || position >= mAdapter.getItemCount()) {
            return 0;
        }

        return position;
    }

    // 添加问题列表
    public void addQuestions(List list) {
        playFirstGuideAnim(); // 向左边抖动一下
        isLoadingMore = false;
        if (mQuestionList.isEmpty() && !list.isEmpty() && list.get(0) instanceof QuestionModel) {
            mPresenter.getComment((QuestionModel) list.get(0), 1);
        }
        mQuestionList.addAll(list);
        mAdapter.setNewData(mQuestionList);
        mAdapter.notifyDataSetChanged();
    }

    // 回答成功
    public void updateAnswer(QuestionModel questionModel, int position, QuestionAnswerModel answerModel) {
//        if (questionModel.getAnswerModel() != null) {
//            return;
//        }

        questionModel.setAnswerModel(answerModel); // 讲答题结果记录下来
        mAdapter.showPkResultAnimator(position, questionModel); // 开始播放动画
        //mAdapter.notifyItemChanged(position); // 更新整个界面

        //mPresenter.getComment(questionModel, 1);

        onSetMatch(answerModel); // 显示匹配的动画
        setAutoScroll(); // 开始自动滚动
        stopSkipTimer(); //
    }

    private void onSetMatch(QuestionAnswerModel answerModel) {
        if (UserInfoManager.INSTANCE.isLogin()) {
            mAnswerCount = answerModel.getCnt();
            if (answerModel.getMatchTimes() > mCanMatchCount) {
                mCanMatchCount = answerModel.getMatchTimes();
                setBadgeNumber();
            } else {
                mCanMatchCount = answerModel.getMatchTimes();
            }
        } else {
            if (++mAnswerCount == MATCH_FOR_ANSWER) {
                mCanMatchCount++;
                mAnswerCount = 0;
                setBadgeNumber();
            }
        }

        SP.encode(MATCH_COUNT_KEY, mCanMatchCount);
        SP.encode(ANSWER_COUNT_KEY, mAnswerCount);

        ivMatch.setImageResource(mCanMatchCount > 0 ? R.drawable.friend_test_match_highlight : R.drawable.friend_test_match);
        viewAnswerProgress.setProgress(mAnswerCount);
    }

    private void setBadgeNumber() {
        if (isNeedMatchTipAnim()) {
            playMatchAnimation();
        } else {
            tvBadgeView.setVisibility(View.VISIBLE);
            tvBadgeView.setText(mCanMatchCount + "");
            playMatchNumberAnim();
        }
    }

    private void setSkipTimer() {
        if (mSkipTimer != null) {
            return;
        }
        if (getCurrentPosition() == 0) {
            return;
        }
        if (!isNeedSkipTimer()) {
            return;
        }

        mSkipTimer = new KwTimer(new KwTimer.Listener() {
            @Override
            public void onTimer(KwTimer timer) {
                try {
                    mAdapter.playSkipAnimation(getCurrentPosition());
                    //mAdapter.notifyItemChanged(getCurrentPosition());
                    stopSkipTimer();
                    saveSkipCount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mSkipTimer.start(3 * 1000, 1); // 三秒钟不操作就跳动一下
    }

    private boolean isNeedSkipTimer() {
        int skipCount = SP.getInt("skipCount", 0);
        return skipCount < 3;
    }

    private void saveSkipCount() {
        int skipCount = SP.getInt("skipCount", 0);
        skipCount++;
        SP.putInt("skipCount", skipCount).commit();
    }

    private void stopSkipTimer() {
        if (mSkipTimer != null) {
            mSkipTimer.stop();
            mSkipTimer = null;
        }
    }

    private void setAutoScroll() {
        mAutoNextTimer = new KwTimer(new KwTimer.Listener() {
            @Override
            public void onTimer(KwTimer timer) {
                skipToNext();
            }
        });
        mAutoNextTimer.start(2 * 1000, 1);
    }

    private void stopAutoScroll() {
        if (mAutoNextTimer != null) {
            mAutoNextTimer.stop();
        }
    }

    private void skipToNext() {
        if (recyclerView.canScrollHorizontally(1)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() + 1);
        }
    }

    @Subscribe
    public void onMainTabSelectChange(MainTabSelectChangeEvent event) {
        if (event.getCurrentTabIndex() != MainTabSelectChangeEvent.MainTabIndex.TAB_FRIEND.ordinal()) {
            stopAutoScroll();

            if (isPlayingBgMusic()) {
                stopBgMusic();
                mIsMusicAutoStop = true;
            }
        } else {
            if (mIsMusicAutoStop) {
                playBgMusic();
            }
            mIsMusicAutoStop = false;
        }
    }

    public void onSelectTab(boolean select) {
        if (!select) {
            stopAutoScroll();
        }
    }

    public void showUserInfo(VoiceInfo voiceInfo, UserInfo userInfo) {
        if (!UserInfoManager.INSTANCE.isLogin()) {
            startFromMain(LoginFragment.Companion.newInstance(false));
            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
            return;
        }

        startFromMain(UserInfoFragment.newInstance(voiceInfo, userInfo));
    }

    public void onGetComment(QuestionModel model, CommentBean o, int pn) {
        if (App.DEBUG) L.m(getClass(), o.getComments().size());
        if (o.getComments() != null && o.getComments().size() > 0) {
            if (getCurrentPosition() == mAdapter.getData().indexOf(model)) {
                if (model.getCommentBean() == null) {
                    model.setCommentBean(o);
                } else {
                    model.getCommentBean().getComments().addAll(o.getComments());
                }

                if (o.isMore()) {
                    mPresenter.getComment(model, pn + 1);
                } else {
                    model.setShowComment(true);
                    //mAdapter.notifyItemChanged(getCurrentPosition());
                    mAdapter.showDanmu(getCurrentPosition(), model);
                }
            }
        }
    }

    public boolean isPlayFirstGuide() {
        boolean first = SP.getBoolean("playFirstGuide", true);
        if (first) {
            SP.putBoolean("playFirstGuide", false).commit();
        }

        return first;
    }

    private void playFirstGuideAnim() {
        if (mAdapter.getItemCount() == 0 && isPlayFirstGuide()) {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animFirstGuide.playAnimation();
                }
            }, 2000);
        }
    }

    // 匹配上了动画
    private void playMatchNumberAnim() {
        tvBadgeView.setVisibility(View.VISIBLE);

        ObjectAnimator sizeX = ObjectAnimator.ofFloat(ivMatch, "scaleX", 1f, 1.2f, 1.2f, 1f, 1f, 1.2f, 1.2f, 1f).setDuration(1500);
        ObjectAnimator sizeY = ObjectAnimator.ofFloat(ivMatch, "scaleY", 1f, 1.2f, 1.2f, 1f, 1f, 1.2f, 1.2f, 1f).setDuration(1500);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivMatch, "rotation", 0f, -30f, -30f, 30f, 30f, 0f).setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(sizeX, sizeY, rotation);
        animatorSet.start();
    }

    private boolean isNeedMatchTipAnim() {
        return true;
    }

    // 播放匹配之后的动画
    private void playMatchAnimation() {
        View view = getView().findViewById(R.id.ivHeardAnim);

        ObjectAnimator sizeX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f).setDuration(10);
        ObjectAnimator sizeY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f).setDuration(10);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(sizeX, sizeY);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.VISIBLE);

                ObjectAnimator bigSizeX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f).setDuration(600);
                ObjectAnimator bigSizeY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f).setDuration(600);
                ObjectAnimator offset1 = ObjectAnimator.ofFloat(view, "translationY", 0f, -700f).setDuration(600);

                ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 45f, 45f, 0f, 0f, 45f, 45f, 0f).setDuration(900);
                rotation.setStartDelay(550);

                ObjectAnimator offset2 = ObjectAnimator.ofFloat(view, "translationY", -700f, 0f).setDuration(500);
                ObjectAnimator offset3 = ObjectAnimator.ofFloat(view, "translationX", 0f, 50f).setDuration(500);
                ObjectAnimator smallSizeX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f).setDuration(500);
                ObjectAnimator smallSizeY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f).setDuration(500);
                offset2.setStartDelay(600 + 900);
                offset3.setStartDelay(600 + 900);
                smallSizeX.setStartDelay(600 + 900);
                smallSizeY.setStartDelay(600 + 900);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(bigSizeX, bigSizeY, offset1, rotation, offset2, offset3, smallSizeX, smallSizeY);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        tvBadgeView.setVisibility(View.VISIBLE);
                        tvBadgeView.setText(mCanMatchCount + "");
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(100);
            }
        }, 1000);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                vibrator.vibrate(200);
            }
        }, 1500);
    }

    public void onLikeResult(QuestionModel model, boolean like, boolean success) {
        if (success) {
            model.setLiked(like ? 1 : 0);
            mAdapter.changePraise(getCurrentPosition(), model);
        }

        if (success) {
            UtilsCode.INSTANCE.showShort(like ? "点赞成功" : "取消点赞成功");
        } else {
            UtilsCode.INSTANCE.showShort(like ? "点赞失败" : "取消点赞失败");
        }
    }

    private void sendPageRemainLog() {
        long duration = System.currentTimeMillis() - mLastPageTime;
        mLastPageTime = System.currentTimeMillis();
        UserActionLog.reportAction(UserActionLog.questionPageRemainConsume, duration + "");
    }

    private void sendQuestionAnswerLog() {
        long duration = System.currentTimeMillis() - mLastPageTime;
        UserActionLog.reportAction(UserActionLog.questionAnswerConsume, duration + "");
    }

    @Override
    protected void onLoginSuccess() {
        super.onLoginSuccess();

        if (mCanMatchCount > 0 || mAnswerCount > 0) {
            mPresenter.syncMatchCount(mCanMatchCount, mAnswerCount);
        } else {
            initAnswerData();
        }
    }

    @Override
    protected void onLogoutSuccess() {
        super.onLogoutSuccess();
        mCanMatchCount = 0;
        mAnswerCount = 0;
        SP.encode(MATCH_COUNT_KEY, mCanMatchCount);
        SP.encode(ANSWER_COUNT_KEY, mAnswerCount);
        initAnswerData();
    }

    @Override
    public void onConnectedChanged(cn.kuwo.common.event.ConnectStatusEvent event) {
        super.onConnectedChanged(event);
        if (App.DEBUG) L.m(getPrintClass(), event.toString());
        if ((mAdapter.getData() == null || mAdapter.getData().isEmpty()) && UtilsCode.INSTANCE.isConnected()) {
            // 刷新列表数据
            mPresenter.requestQuestionList();// 刷新网络
        }
    }

    private void playBgMusic() {
        L.m3();
        PlayBgMusic.playBgMusic();
    }

    private void stopBgMusic() {
        L.m3();
        PlayBgMusic.stopBgMusic();
    }

    public boolean isPlayingBgMusic() {
        return PlayBgMusic.isPlaying();
    }


    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
