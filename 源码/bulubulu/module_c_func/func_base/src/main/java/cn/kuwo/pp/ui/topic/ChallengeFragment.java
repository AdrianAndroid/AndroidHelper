package cn.kuwo.pp.ui.topic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.recyclerview.EndlessRecyclerOnScrollListener;
import cn.kuwo.common.recyclerview.SnapRecyclerView;
import cn.kuwo.common.textview.CountDownView;
import cn.kuwo.common.util.L;
import cn.kuwo.common.util.PnHelper;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.func_base.utils.PlayBgMusic;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.WebQuestionModelBean;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.mine.UserInfoFragment;
import cn.kuwo.pp.ui.share.ShareCardDialog;
import cn.kuwo.pp.ui.topic.adapter.ChallengeAdapter;
import cn.kuwo.pp.ui.world.CommentDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static cn.kuwo.pp.http.ConstantUrls.URL_CHALLENGE_H5;

public class ChallengeFragment extends BaseFragment {

    private SnapRecyclerView recyclerView;
    private TextView tvTitle;
    private CountDownView tvTimeRemain;
    private View titleBackground;
    private TextView challengeToast; // 已经完成了一半了，加油哦😊

    public static String TAG = "CHALLENGE";
    public static String PLAY_BG_MUSIC = "PLAY_CHALLENGE_BG_MUSIC";
    public static String WEB_RESULT_KEY = "challengeResult";
    public static String TOPIC_ITEM = "topic_item";

    private ChallengeAdapter mAdapter;

    private boolean mIsMusicAutoStop = false;

    private KwTimer mRemainTimer;
    private final int TOTAL_SECOND = 30;
    private int mRemainSecond = TOTAL_SECOND;
    private Random random = new Random();

    //    private ChallengeLoadingDialog mLoadingDialog;
//    private MaterialDialog mDialogTip;

    private TopicItemBean mTopic;

    private boolean isLoadingQuestions = false; //是否正在加载数据

    private int pn = PnHelper.INSTANCE.getPN_FIRST();

    public ChallengeFragment() {
        //重新挑战数据
        String topicItem = SP.decodeString(TOPIC_ITEM);
        mTopic = TopicItemBean.fromJson(topicItem);
    }

    public ChallengeFragment(TopicItemBean topicId) {
        super();
        mTopic = topicId;
        SP.encode(TOPIC_ITEM, topicId.toString());
    }

    public static ChallengeFragment newInstance() {
        return new ChallengeFragment();
    }

    public static ChallengeFragment newInstance(TopicItemBean topicId) {
        return new ChallengeFragment(topicId);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
//        _initView(view);
        initView(view);
        return attachToSwipeBack(view);
    }


    private void initView(View view) {
//        if (App.DEBUG) {
//            mRemainSecond = 14;
//        }
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTimeRemain = view.findViewById(R.id.tvTimeRemain);
        titleBackground = view.findViewById(R.id.titleBackground);
        challengeToast = view.findViewById(R.id.challegeToast);
    }

//
//    private void showChallengeFirstToast(int position) {
//        //挑战开始啦～测测你是哪种圈内人ヽ(•̀ω•́ )ゝ？
//        //马上就要完成了～加油( ง •̀_•́)ง
//        // 显示挑战时间的页面
//        challengeToast.setText("挑战开始啦～测测你是哪种圈内人ヽ(•̀ω•́ )ゝ？");
//        ObjectAnimator.ofFloat(challengeToast, "alpha", 0F, 0.8F).setDuration(1000).start();
//        challengeToast.postDelayed(() -> ObjectAnimator.ofFloat(challengeToast, "alpha", 0.8F, 0F).setDuration(2000).start(), 2000);
//    }


    // 时间显示到一半的时候，弹出来
    private void showChallengeToast(int position) {
        //挑战开始啦～测测你是哪种圈内人ヽ(•̀ω•́ )ゝ？
        //马上就要完成了～加油( ง •̀_•́)ง
        // 显示挑战时间的页面
        boolean isFirst = position == 0;
        boolean isEnd = mRemainSecond < 10 && challengeToast.getTag() instanceof Boolean && (Boolean) challengeToast.getTag();

        if (isFirst) {
            // 第一次
            challengeToast.setText("挑战开始啦～测测你是哪种圈内人ヽ(•̀ω•́ )ゝ？");
            challengeToast.setTag(true);
        } else if (isEnd) {
            challengeToast.setText("马上就要完成了～加油( ง •̀_•́)ง");
            challengeToast.setTag(false);
        }
        if (isFirst || isEnd) {
            float orgAlpha = 0.8f;
//            challengeToast.setVisibility(View.VISIBLE);
            challengeToast.setAlpha(orgAlpha);
            //ObjectAnimator.ofFloat(challengeToast, "alpha", 0F, orgAlpha).setDuration(1000).start();
//            challengeToast.postDelayed(() -> ObjectAnimator.ofFloat(challengeToast, "alpha", orgAlpha, 0F).setDuration(2000).start(), 2000);
            challengeToast.postDelayed(() -> challengeToast.setAlpha(0f), 2000);
        }
    }

    private void setBgColor(View view) {
        if (view == null) {
            return;
        }

        switch (random.nextInt(3)) {
            case 0:
                view.setBackground(getDrawable(R.drawable.shape_rectangle_3dd6d9_c779d3));
                break;
            case 1:
                view.setBackground(getDrawable(R.drawable.shape_rectangle_6e96ff_3dd6d9));
                break;
            case 2:
                view.setBackground(getDrawable(R.drawable.shape_rectangle_c273de_ffbd50));
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableToolbar(R.id.toolbar, "入圈挑战", true);
        mToolbar.getTvTitle().setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
        if (mTopic != null) {
            tvTitle.setText("#" + mTopic.getName() + "#");
        }

        initRecyclerView(); // 初始化横向的RecyclerView

        startRemainTimer();

        requestQuestionList();

        if (SP.decodeBool(PLAY_BG_MUSIC, true)) {
            playBgMusic();
        }

        //重置计分mmkv
        SP.remove(WEB_RESULT_KEY);

        setBgColor(titleBackground);
    }

    private void initRecyclerView() {
        mAdapter = new ChallengeAdapter(this, null);

        recyclerView.setAdapter(mAdapter);
        // 监听滑动到最后几页
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(3, new Function1<RecyclerView, Unit>() {
            @Override
            public Unit invoke(RecyclerView recyclerView) {
                // 加载下一页
                requestQuestionList();
                return null;
            }
        }));


        addListener(); // 添加每个条目的点击事件
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        if (mIsMusicAutoStop) { //显示的时候播放音乐
            playBgMusic();
        }
        mIsMusicAutoStop = false;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

        if (isPlayingBgMusic()) { // 不显示的时候停止播放
            stopBgMusic();
            mIsMusicAutoStop = true;
        }
        DialogUtils.INSTANCE.dismissDialog();
    }

    // 开始倒计时
    private void startRemainTimer() {
        mRemainTimer = new KwTimer(new KwTimer.Listener() {
            @Override
            public void onTimer(KwTimer timer) {
                mRemainSecond -= 1;
                if (tvTimeRemain != null) {
                    tvTimeRemain.setSecond(mRemainSecond);
                }
//                if (tvTimeRemain != null) {
//                    tvTimeRemain.setText(String.format("%02d:%02d", mRemainSecond / 60, mRemainSecond % 60));
//                }
//                if (mRemainSecond == 10) {
//                    tvTimeRemain.setTextColor(Color.parseColor("#FF6565"));
//                }

                if (mRemainSecond == 0) {
                    mRemainTimer.stop();

                    alertDialog();
                }
            }
        });

        mRemainTimer.start(1000);
    }

    // 显示结束的弹窗
    private void alertDialog() {
        DialogUtils.INSTANCE.showChallengeSuccessDialog(new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean) {
                // 查看结果
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CHANNLEGE_INTO_CHANNLEGE_RESULT, ChallengeFragment.class.getSimpleName());
                startWithPopFromMain(ChallengeResultWebFragment.newInstance(URL_CHALLENGE_H5, "结果", false, "", mTopic));
                return null;
            }
        }, this);

//        if (mDialogTip != null && mDialogTip.isShowing()) mDialogTip.dismiss();
//        return AlertDialog.getSingleBtnInstance(_mActivity, "恭喜挑战成功哦～", "查看结果", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialogTip.dismiss();
//                // 查看结果
//                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CHANNLEGE_INTO_CHANNLEGE_RESULT, ChallengeFragment.class.getSimpleName());
//                startWithPopFromMain(ChallengeResultWebFragment.newInstance(URL_CHALLENGE_H5, "结果", false, "", mTopic));
//            }
//        });
    }

    /*
    MainActivity activity = (MainActivity)getOwnerActivity();
    activity.start(ChallengeResultWebFragment.newInstance("https://h5app.kuwo.cn/m/meetChallenge/index.html","结果", false, "", mTopicItem), SINGLETASK);
     */

    // 添加监听事件
    private void addListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                QuestionModel model = (QuestionModel) mAdapter.getItem(position);

                if (view.getId() == R.id.ivShare) { // 分享
                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        start(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    ShareCardDialog.newInstance(model, 0, 0).show(getChildFragmentManager());
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_SHARE, TAG);
                } else if (view.getId() == R.id.ivComment) { // 评论
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_COMMENT, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        start(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    if (model != null) {
                        CommentDialog.newInstance(model, ChallengeFragment.this).show(getChildFragmentManager());
                    }
                } else if (view.getId() == R.id.ivMusic) { // 音乐
                    if (!isPlayingBgMusic()) {
                        playBgMusic();
                        SP.encode(PLAY_BG_MUSIC, true);
                    } else {
                        stopBgMusic();
                        SP.encode(PLAY_BG_MUSIC, false);
                    }
                    mAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.ivPraise) { // 点赞
                    AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.CLICK_PRAISE, TAG);

                    if (!UserInfoManager.INSTANCE.isLogin()) {
                        start(LoginFragment.Companion.newInstance(false));
                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                        return;
                    }

                    TopicChallengePresenter.likeQuestion(ChallengeFragment.this, model.getLiked() == 0 ? true : false, model);
                } else if (view.getId() == R.id.ivSkip) { //跳过
                    recyclerView.skipToNext();
                } else { // 选择
                    if (view.getId() == R.id.ivOptionOne || view.getId() == R.id.ivOptionTwo
                            || view.getId() == R.id.viewOptionOneBg || view.getId() == R.id.viewOptionTwoBg) {
                        if (!mRemainTimer.isRunnig()) {
                            alertDialog();
                            return;
                        } else if (model.getAnswerModel() != null) {
                            // 如果选择已经选择了
                            recyclerView.skipToNext();
                            return;
                        }

                        String answer = "optionOne";
                        if (view.getId() == R.id.ivOptionTwo || view.getId() == R.id.viewOptionTwoBg) {
                            answer = "optionTwo";
                        }

                        TopicChallengePresenter.answerQuestion(ChallengeFragment.this, model, position, answer, mTopic.getId());
                        Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                        vibrator.vibrate(100);

                        AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.PK_VOTE, TAG);
                        showChallengeToast(position);
                    } else if (view.getId() == R.id.tvUserName || view.getId() == R.id.ivUserHeader) {
                        if (!UserInfoManager.INSTANCE.isLogin()) {
                            start(LoginFragment.Companion.newInstance(false));
                            AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, TAG);
                            return;
                        }

                        startFromMain(UserInfoFragment.newInstance(null, model.getUser()));
                    }
                }
            }
        });
    }

    // 更新
    public void updateAnswer(QuestionModel questionModel, int position, QuestionAnswerModel answerModel) {
        if (questionModel.getAnswerModel() != null) {
            return;
        }
        questionModel.setEnableClick(false);
        questionModel.setAnswerModel(answerModel);
        mAdapter.notifyItemChanged(position);
        recyclerView.skipToNext();
        appendResult(questionModel);
    }


    // 加载数据
    private void requestQuestionList() {
        if (!isLoadingQuestions && mRemainTimer.isRunnig()) {
            isLoadingQuestions = true;
            TopicChallengePresenter.requestQuestionList(ChallengeFragment.this, mTopic.getId(), pn);
        }
    }


    public void addQuestions(List<QuestionModel> list, int pn) {
        isLoadingQuestions = false; //无论成功失败都返回这里
//        mAdapter.setNewData(list);

        this.pn = pn;
        mAdapter.addData(list);
        mAdapter.notifyDataSetChanged();
    }

    // 播放音乐
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


    public void onLikeResult(QuestionModel model, boolean like, boolean success) {
        if (success) {
            model.setLiked(like ? 1 : 0);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(model));
        }

        if (success) {
            UtilsCode.INSTANCE.showShort(like ? "点赞成功" : "取消点赞成功");
        } else {
            UtilsCode.INSTANCE.showShort(like ? "点赞失败" : "取消点赞失败");
        }
    }

    @Override
    protected void onBackBtnClicked(View view) {
        mRemainTimer.stop();
        if (getPreFragment() == null) {
            _mActivity.finish();
        } else {
            pop();
        }
    }

    private void appendResult(QuestionModel questionModel) {
        //挑战计分,追加结果
        WebQuestionModelBean webQuestionModelBean = new WebQuestionModelBean();
        webQuestionModelBean = webQuestionModelBean.fromQuestion(questionModel);
        String resultKey = WEB_RESULT_KEY;
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<WebQuestionModelBean>>() {
        }.getType();
        if (SP.contains(resultKey)) {
            String challengeResult = SP.decodeString(resultKey);
            List<WebQuestionModelBean> challengeResultList = gson.fromJson(challengeResult, jsonType);
            challengeResultList.add(webQuestionModelBean);
            challengeResult = gson.toJson(challengeResultList, jsonType);
            try {
                SP.encode(resultKey, challengeResult);
            } catch (Exception e) {
                //LogUtils.d("记录失败");
            }
        } else {
            List<WebQuestionModelBean> challengeResultList = new ArrayList<>();
            challengeResultList.add(webQuestionModelBean);
            String challengeResult = gson.toJson(challengeResultList, jsonType);
            SP.encode(resultKey, challengeResult);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRemainTimer != null) {
            mRemainTimer.stop();
        }
        stopBgMusic();
    }

    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
