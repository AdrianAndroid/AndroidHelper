package cn.kuwo.pp.ui.discover;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.elbbbird.android.analytics.AnalyticsUtils;
import com.elbbbird.android.analytics.UmengEventId;
import com.tencent.qcloud.uikit.common.UIKitConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.util.L;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.IPlayCallback;
import cn.kuwo.player.PlayError;
import cn.kuwo.player.PlayState;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.discover.view.BallMoreDialog;
import cn.kuwo.pp.ui.discover.view.BalloonView;
import cn.kuwo.pp.ui.discover.view.CenterBalloonView;
import cn.kuwo.pp.ui.login.LoginFragment;
import cn.kuwo.pp.ui.report.ReportDialog;
import cn.kuwo.pp.ui.share.ShareVoiceDialog;

import static cn.kuwo.pp.ui.discover.view.CenterBalloonView.MOVE_OUT_LEFT;

;
;

public class FindFriendFragment extends BaseFragment {

    private FindFriendPresenter mPresenter;
    private ArrayList<BalloonView> mBallList = new ArrayList();
    private ArrayList<VoiceInfo> mUserList = new ArrayList();

    private static int SWIPE_VERTICAL_MINI = 100;
    private static int SWIPE_MINI_VELOCITY = 10;
    private static int LOAD_MORE_NUM = 3;

    private BalloonView mMovingBall;
    private GestureDetector mWholeViewGesDetector;
    private String mPlayingUrl;
    private Boolean mIsAutoPause = false;
    private Boolean mAutoPlayFlag = false;
//    private MaterialDialog mDialogHint;

    ConstraintLayout layoutBalls;
    CenterBalloonView mCenterBalloonView;

    BalloonView ballUserOne;
    BalloonView ballUserTwo;
    BalloonView ballUserThree;
    BalloonView ballUserFour;
    BalloonView ballUserFive;

    MultipleStatusView mStatusView;

    ImageView ivGuideLike;
    ImageView ivGuideUnlike;


    private void _initView(View view) {
        layoutBalls = view.findViewById(R.id.layoutBalls);
        mCenterBalloonView = view.findViewById(R.id.ballUserCurrent);
        ballUserOne = view.findViewById(R.id.ballUserOne);
        ballUserTwo = view.findViewById(R.id.ballUserTwo);
        ballUserThree = view.findViewById(R.id.ballUserThree);
        ballUserFour = view.findViewById(R.id.ballUserFour);
        ballUserFive = view.findViewById(R.id.ballUserFive);
        mStatusView = view.findViewById(R.id.multipleStatusView);
        ivGuideLike = view.findViewById(R.id.ivGuideLike);
        ivGuideUnlike = view.findViewById(R.id.ivGuideUnlike);
    }

    LottieAnimationView mBalloonUpAnim;
    LottieAnimationView mHeartAnim;
    LottieAnimationView mLikeBothAnim;

    private MediaPlayer hintPlayer;   //喜欢用户提示音播放器

    public static FindFriendFragment newInstance() {
        return new FindFriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_friend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new FindFriendPresenter(this);

        BuluPlayer.getInstance().addPlayCallback(playerCallback);

        initListeners();
        loadMoreUser();

        mStatusView.showLoading();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (hintPlayer != null) {
            hintPlayer.stop();
            hintPlayer.release();
            hintPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mIsAutoPause) {
            BuluPlayer.getInstance().resume();
            mIsAutoPause = false;
        }
    }

    @Override
    public void onPause() {
        if (BuluPlayer.getInstance().isPlayingUrl(mPlayingUrl) && BuluPlayer.getInstance().getCurrentState() != PlayState.PAUSE) {
            BuluPlayer.getInstance().pause();
            mIsAutoPause = true;
            updatePlayState(false);
        }
        super.onPause();
    }

    IPlayCallback playerCallback = new IPlayCallback() {
        @Override
        public void onPlayStart() {
            if (isSupportVisible()) {
                updatePlayState(true);
            }
        }

        @Override
        public void onBuffering(int bufPercent) {

        }

        @Override
        public void onPlaying() {
            if (TextUtils.equals(BuluPlayer.getInstance().getDataSource(), mPlayingUrl)) {
                if (!isSupportVisible()) {
                    BuluPlayer.getInstance().pause();
                    updatePlayState(false);
                }
            }
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
        }

        @Override
        public void onSeekCompleted() {

        }

        @Override
        public void onError(PlayError code, String errExtMsg) {
            updatePlayState(false);
            UtilsCode.INSTANCE.showShort(errExtMsg);
        }
    };

    @Override
    public void onSupportVisible() {
        if (mIsAutoPause && BuluPlayer.getInstance().isPlayingUrl(mPlayingUrl)) {
            BuluPlayer.getInstance().resume();
            mIsAutoPause = false;
        }

        if (mPlayingUrl == null && mAutoPlayFlag == false) {
            if (mCenterBalloonView.getVoiceInfo() != null) {
                startPlayVoice();
            }
        }

        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        if (BuluPlayer.getInstance().isPlayingUrl(mPlayingUrl) && BuluPlayer.getInstance().getCurrentState() != PlayState.PAUSE) {
            innerPause(true);
        }

        super.onSupportInvisible();
    }

    private void innerPause(boolean autoPause) {
        mIsAutoPause = autoPause;
        BuluPlayer.getInstance().pause();
        updatePlayState(false);
    }

    private void addFloatBallListener(BalloonView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovingBall != null) {
                    return;
                }
                int index = mBallList.indexOf(view);
                if (index != -1) {
                    mBallList.remove(index);
                }
                mMovingBall = (BalloonView) view;
                mCenterBalloonView.moveOut(MOVE_OUT_LEFT);
            }
        });
    }

    private void createFloatBallView(BalloonView sourceView) {
        if (sourceView == null) {
            return;
        }
        if (mUserList.size() == 0) {
            return;
        }

        VoiceInfo voiceInfo = mUserList.get(0);
        mUserList.remove(0);
        if (mUserList.size() <= LOAD_MORE_NUM) {
            loadMoreUser();
        }

        BalloonView view = new BalloonView(getContext());
        view.setLayoutParams(sourceView.getLayoutParams());

        ConstraintLayout fragmentView = (ConstraintLayout) getView();
        fragmentView.addView(view);

        view.setVoiceInfo(voiceInfo);
        view.startFadeInAnimation();

        addFloatBallListener(view);
        mBallList.add(view);
    }

    public void createCenterBallView(BalloonView sourceView) {
        if (sourceView == null) {
            return;
        }

        CenterBalloonView centerBall = new CenterBalloonView(getContext());
        centerBall.setId(mCenterBalloonView.getId());
        centerBall.setLayoutParams(mCenterBalloonView.getLayoutParams());

        ConstraintLayout fragmentView = (ConstraintLayout) getView();
        fragmentView.addView(centerBall);
        centerBall.setVoiceInfo(sourceView.getVoiceInfo());

        View oldCenterView = mCenterBalloonView;
        mCenterBalloonView = centerBall;

        setCenterBallListener();
        startPlayVoice();

        post(new Runnable() {
            @Override
            public void run() {
                fragmentView.removeView(sourceView);
                fragmentView.removeView(oldCenterView);
            }
        });
    }

    private void loadMoreUser() {
        String card = "";
        String sex = "";
        if (UserInfoManager.INSTANCE.getVoiceInfo() != null) {
            card = UserInfoManager.INSTANCE.getVoiceInfo().getCard();
            sex = UserInfoManager.INSTANCE.getVoiceInfo().getSex();
        }
        mPresenter.getRecommendUsers(card, sex);
    }

    public void showUserList(List<VoiceInfo> userList) {
        mStatusView.setVisibility(View.GONE);

        if (userList.size() == 0) {
            return;
        }

        mUserList.addAll(userList);

        if (mCenterBalloonView.getVoiceInfo() == null) {
            VoiceInfo voiceInfo = mUserList.get(0);
            mCenterBalloonView.setVoiceInfo(voiceInfo);
            mUserList.remove(0);
            startPlayVoice();
        }

        for (int i = 0; i < mUserList.size(); i++) {
            if (i >= mBallList.size()) {
                break;
            }

            VoiceInfo voiceInfo = mUserList.get(i);
            BalloonView balloonView = mBallList.get(i);
            if (balloonView.getVoiceInfo() == null) {
                balloonView.setVoiceInfo(voiceInfo);
                balloonView.startFloatAnimation();

                mUserList.remove(i);
            }
        }

        if (mUserList.size() <= LOAD_MORE_NUM) {
            loadMoreUser();
        }
    }

    public void showUserListFail(String errMsg) {
        mStatusView.setVisibility(View.VISIBLE);
        mStatusView.showError(errMsg);
    }

    private void initListeners() {
        mBallList.add(ballUserOne);
        mBallList.add(ballUserTwo);
        mBallList.add(ballUserThree);
        mBallList.add(ballUserFour);
        mBallList.add(ballUserFive);
        addFloatBallListener(ballUserOne);
        addFloatBallListener(ballUserTwo);
        addFloatBallListener(ballUserThree);
        addFloatBallListener(ballUserFour);
        addFloatBallListener(ballUserFive);

        setCenterBallListener();

        mWholeViewGesDetector = new GestureDetector(_mActivity, new WholeViewGestureListener());
        layoutBalls.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mWholeViewGesDetector.onTouchEvent(event);
            }
        });

        mStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreUser();
                mStatusView.showLoading();
            }
        });

        mLikeBothAnim = getView().findViewById(R.id.anim_match_both);
        mLikeBothAnim.setImageAssetsFolder("json_image_1");
        mLikeBothAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLikeBothAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mHeartAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mHeartAnim = getView().findViewById(R.id.anim_heart_scale);
        mHeartAnim.setImageAssetsFolder("json_image");
        mHeartAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHeartAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mHeartAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mBalloonUpAnim = getView().findViewById(R.id.anim_balloon_up);
        mBalloonUpAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mBalloonUpAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mBalloonUpAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void setCenterBallListener() {
        mCenterBalloonView.setCenterBalloonListener(new CenterBalloonView.onCenterBalloonViewListener() {
            @Override
            public void onStartMoveOut() {
                nextMoveBall();
                mMovingBall.moveToCenter(mCenterBalloonView);
                createFloatBallView(mMovingBall);
            }

            @Override
            public void onResumePlay() {
                startPlayVoice();
            }

            @Override
            public void onClickMore() {
                BallMoreDialog dialog = BallMoreDialog.newInstance();
                dialog.setDialogListener(new BallMoreDialog.BallMoreDialogListener() {
                    @Override
                    public void onClickIndex(int index) {
                        if (index == 0) {
                            ShareVoiceDialog.newInstance(mCenterBalloonView.getVoiceInfo()).show(getFragmentManager());
                        } else if (index == 1) {
                            ReportDialog.newInstance(ReportDialog.TYPE_REPORT_VOICE, mCenterBalloonView.getVoiceInfo().getVid()).show(getFragmentManager());
                        }
                    }
                });
                dialog.show(getFragmentManager());
            }
        });
    }

    private void nextMoveBall() {
        if (mMovingBall == null) {
            Random random = new Random();
            int next = random.nextInt(mBallList.size());
            mMovingBall = mBallList.get(next);
            mBallList.remove(next);
        }

        mMovingBall.setBalloonViewListener(new BalloonView.onBalloonViewListener() {
            @Override
            public void onMoveCenterFinish() {
                createCenterBallView(mMovingBall);
                mMovingBall = null;
            }
        });
    }

    private class WholeViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mMovingBall != null) {
                return false;
            }
            if (e1.getX() - e2.getX() > SWIPE_VERTICAL_MINI && Math.abs(velocityX) > SWIPE_MINI_VELOCITY) {
                onFlingLeft();
            } else if (e2.getX() - e1.getX() > SWIPE_VERTICAL_MINI && Math.abs(velocityX) > SWIPE_MINI_VELOCITY) {
                onFlingRight();
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (BuluPlayer.getInstance().isPlaying()) {
                BuluPlayer.getInstance().pause();
                mIsAutoPause = false;
            } else {
                startPlayVoice();
            }

            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onFlingRight();
            return super.onDoubleTap(e);
        }
    }

    private void onFlingLeft() {
        mCenterBalloonView.moveOut(MOVE_OUT_LEFT);
        likeUser(false);
    }

    private void onFlingRight() {
        if (mMovingBall != null) {
            return;
        }

        likeUser(true);

        if (!UserInfoManager.INSTANCE.isLogin()) {
            return;
        }

        playHintSound();

        mCenterBalloonView.scaleSmall();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBalloonUpAnim.setVisibility(View.VISIBLE);
                mBalloonUpAnim.bringToFront();
                mBalloonUpAnim.playAnimation();

                mHeartAnim.setVisibility(View.VISIBLE);
                mHeartAnim.bringToFront();
                mHeartAnim.setImageAssetsFolder("json_image");
                mHeartAnim.playAnimation();
            }
        }, 200);
    }

    private void startPlayVoice() {
        if (mCenterBalloonView.getVoiceInfo() == null) {
            return;
        }

        if (BuluPlayer.getInstance().getCurrentState() == PlayState.PAUSE && BuluPlayer.getInstance().getDataSource().equals(mPlayingUrl)) {
            BuluPlayer.getInstance().resume();
        } else {
            VoiceInfo voiceInfo = mCenterBalloonView.getVoiceInfo();
            mPlayingUrl = voiceInfo.getUrl();
            BuluPlayer.getInstance().play(voiceInfo.getUrl());
        }
    }

    private void updatePlayState(Boolean isPlayed) {
        if (mCenterBalloonView != null) {
            mCenterBalloonView.showPlayingAnim(isPlayed);
        }
    }

    private void likeUser(boolean isLike) {
        if (App.DEBUG) L.L(getClass(), "likeUser");
        if (!UtilsCode.INSTANCE.isConnected()) {

            DialogUtils.INSTANCE.showNetworkErrorDialog(this);

//            String dlgContent = "网络受到神秘力量干扰";
//            mDialogHint = AlertDialog.getSingleBtnInstance(_mActivity, dlgContent, "知道了", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialogHint.dismiss();
//                    mDialogHint = null;
//                }
//            });
//            mDialogHint.show();
            return;
        }

        VoiceInfo voiceInfo = mCenterBalloonView.getVoiceInfo();
        if (voiceInfo == null) {
            return;
        }

        if (isLike) {
            if (UserInfoManager.INSTANCE.isLogin()) {
                FriendListManager.getInstance().follow(voiceInfo, this, new FriendListManager.IFriendListManagerResult() {
                    @Override
                    public void IFriendListManagerResult_success(int result) {
                        if (App.DEBUG)
                            L.L(getClass(), "likeUser", "IFriendListManagerResult_success");
                    }

                    @Override
                    public void IFriendListManagerResult_failed(String msg) {
                        //UtilsCode.INSTANCE.showShort("关注失败: "+msg);
                        if (App.DEBUG)
                            L.L(getClass(), "likeUser", "IFriendListManagerResult_failed");
                    }
                });
            } else {
                innerPause(true);
                AnalyticsUtils.INSTANCE.onEvent(_mActivity, UmengEventId.OTHER_LOGIN, "FIND_FRIEND");
                startFromMain(LoginFragment.Companion.newInstance(false));
            }
        }
    }

    private void playHintSound() {
        if (hintPlayer != null) {
            hintPlayer.stop();
            hintPlayer.release();
            hintPlayer = null;
        }
        hintPlayer = MediaPlayer.create(_mActivity, R.raw.like_user_sound);
        hintPlayer.setOnCompletionListener(mp -> {
            hintPlayer.release();
            hintPlayer = null;
        });
        hintPlayer.start();
    }

    private void playGuideAnim() {
        if (!isGuideAnim()) {
            return;
        }

        int offsetX = UtilsCode.INSTANCE.dp2px(140);
        int offsetY = UtilsCode.INSTANCE.dp2px(30);

        ObjectAnimator animOffsetX1 = ObjectAnimator.ofFloat(mCenterBalloonView, "translationX", 0f, 0 - offsetX, 0 - offsetX, 0f, 0f, offsetX, offsetX, 0f).setDuration(2000);
        ObjectAnimator animOffsetY1 = ObjectAnimator.ofFloat(mCenterBalloonView, "translationY", 0f, offsetY, offsetY, 0f, 0f, offsetY, offsetY, 0f).setDuration(2000);

        ivGuideLike.setAlpha((float) 0.0);
        ivGuideLike.setVisibility(View.VISIBLE);
        int location[] = new int[2];
        mCenterBalloonView.getLocationInWindow(location);
        int destX = location[0] + (mCenterBalloonView.getWidth() - ivGuideLike.getWidth()) / 2;
        ivGuideLike.getLocationInWindow(location);
        int posX = location[0];
        ObjectAnimator likePositionX = ObjectAnimator.ofFloat(ivGuideLike, "X", posX, destX).setDuration(1000);
        ObjectAnimator likeAlpha1 = ObjectAnimator.ofFloat(ivGuideLike, "alpha", 0, 1).setDuration(950);
        ObjectAnimator likeAlpha2 = ObjectAnimator.ofFloat(ivGuideLike, "alpha", 1, 0).setDuration(50);
        likePositionX.setStartDelay(1000);
        likeAlpha1.setStartDelay(1000);
        likeAlpha2.setStartDelay(1000 + 950);

        ObjectAnimator likeSizeX1 = ObjectAnimator.ofFloat(ivGuideLike, "scaleX", 1, 0).setDuration(50);
        ObjectAnimator likeSizeY1 = ObjectAnimator.ofFloat(ivGuideLike, "scaleY", 1, 0).setDuration(50);
        ObjectAnimator likeSizeX2 = ObjectAnimator.ofFloat(ivGuideLike, "scaleX", 0, 1).setDuration(950);
        ObjectAnimator likeSizeY2 = ObjectAnimator.ofFloat(ivGuideLike, "scaleY", 0, 1).setDuration(950);
        likeSizeX2.setStartDelay(50);
        likeSizeY2.setStartDelay(50);

        ivGuideUnlike.setAlpha((float) 0.0);
        ivGuideUnlike.setVisibility(View.VISIBLE);
        ivGuideUnlike.getLocationInWindow(location);
        posX = location[0];
        ObjectAnimator unlikePositionX = ObjectAnimator.ofFloat(ivGuideUnlike, "X", posX, destX).setDuration(1000);
        ObjectAnimator unlikeAlpha1 = ObjectAnimator.ofFloat(ivGuideUnlike, "alpha", 0, 1).setDuration(950);
        ObjectAnimator unlikeAlpha2 = ObjectAnimator.ofFloat(ivGuideUnlike, "alpha", 1, 0).setDuration(50);
        unlikeAlpha2.setStartDelay(950);

        ObjectAnimator unlikeSizeX1 = ObjectAnimator.ofFloat(ivGuideUnlike, "scaleX", 1, 0).setDuration(50);
        ObjectAnimator unlikeSizeY1 = ObjectAnimator.ofFloat(ivGuideUnlike, "scaleY", 1, 0).setDuration(50);
        ObjectAnimator unlikeSizeX2 = ObjectAnimator.ofFloat(ivGuideUnlike, "scaleX", 0, 1).setDuration(950);
        ObjectAnimator unlikeSizeY2 = ObjectAnimator.ofFloat(ivGuideUnlike, "scaleY", 0, 1).setDuration(950);
        likeSizeX2.setStartDelay(50);
        likeSizeY2.setStartDelay(50);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animOffsetX1, animOffsetY1,
                likePositionX, likeAlpha1, likeAlpha2, likeSizeX1, likeSizeY1, likeSizeX2, likeSizeY2,
                unlikePositionX, unlikeAlpha1, unlikeAlpha2, unlikeSizeX1, unlikeSizeY1, unlikeSizeX2, unlikeSizeY2);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ivGuideLike.setVisibility(View.GONE);
                ivGuideUnlike.setVisibility(View.GONE);
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

    public void onSelectTab(boolean select) {
        if (select) {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playGuideAnim();
                }
            }, 1500);
        }
    }

    private boolean isGuideAnim() {
        SharedPreferences preferences = getContext().getSharedPreferences(UIKitConstants.UI_PARAMS, Context.MODE_PRIVATE);
        boolean guideAnim = preferences.getBoolean("findFriendAnim", true);
        if (guideAnim) {
            preferences.edit().putBoolean("findFriendAnim", false).commit();
        }

        return guideAnim;
    }


    @Override
    public Class<?> getPrintClass() {
        return getClass();
    }
}
