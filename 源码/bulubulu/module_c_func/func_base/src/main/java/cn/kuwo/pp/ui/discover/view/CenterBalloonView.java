package cn.kuwo.pp.ui.discover.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;

public class CenterBalloonView extends RelativeLayout {

    public static int MOVE_OUT_LEFT = 1;
    public static int MOVE_OUT_RIGHT = 2;

    private View mBallView;
    private VoiceInfo mVoiceInfo;
    private onCenterBalloonViewListener mCenterBalloonListener;
    private LottieAnimationView mPlayingAnim;
    private LottieAnimationView mBackgroundAnim;
    private ConstraintLayout mLayoutResume;

    public CenterBalloonView(Context context){
        super(context);
        initView();
    }

    public CenterBalloonView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public CenterBalloonView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mBallView = LayoutInflater.from(getContext()).inflate(R.layout.view_center_balloon, this);
        mPlayingAnim = mBallView.findViewById(R.id.anim_playing_voice);
        mBackgroundAnim = mBallView.findViewById(R.id.anim_background);

        ImageView ivMore = mBallView.findViewById(R.id.buttonBallMore);
        ivMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCenterBalloonListener != null){
                    mCenterBalloonListener.onClickMore();
                }
            }
        });

        mLayoutResume = mBallView.findViewById(R.id.layout_resume);
        mLayoutResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCenterBalloonListener != null){
                    mCenterBalloonListener.onResumePlay();
                }
            }
        });
    }

    public void setVoiceInfo(VoiceInfo voiceInfo){
        if(voiceInfo == null){
            return;
        }

        mVoiceInfo = voiceInfo;

        mPlayingAnim.setAnimation(voiceInfo.isGirl() ? "balloon_playing_yellow.json" : "balloon_playing_blue.json");
        mBackgroundAnim.setAnimation(voiceInfo.isGirl() ? "balloon_background_yellow.json" : "balloon_background_blue.json");
        mBackgroundAnim.playAnimation();

        ImageView centerImageView = mBallView.findViewById(R.id.centerUserFace);
        ImageLoader.showCircleImage(centerImageView, voiceInfo.getHeadImg());

        TextView tvUserName = mBallView.findViewById(R.id.userName);
        tvUserName.setText(voiceInfo.getName());
    }
    public VoiceInfo getVoiceInfo(){
        return mVoiceInfo;
    }

    public interface onCenterBalloonViewListener{
        void onStartMoveOut();
        void onClickMore();
        void onResumePlay();
    }

    public void setCenterBalloonListener(onCenterBalloonViewListener listener){
        mCenterBalloonListener = listener;
    }

    public void showPlayingAnim(boolean playing){
        if(playing){
            mPlayingAnim.playAnimation();
            mLayoutResume.setVisibility(GONE);
        }else{
            mPlayingAnim.pauseAnimation();
            mLayoutResume.setVisibility(VISIBLE);
        }
    }

    public void scaleSmall(){
        mPlayingAnim.pauseAnimation();

        ObjectAnimator sizeX = ObjectAnimator.ofFloat(this,"scaleX",1,(float) 0.0).setDuration(200);
        ObjectAnimator sizeY = ObjectAnimator.ofFloat(this,"scaleY",1,(float) 0.0).setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(sizeX, sizeY);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(mCenterBalloonListener != null){
                    mCenterBalloonListener.onStartMoveOut();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
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

    public void moveOut(int direction){
        mPlayingAnim.pauseAnimation();

        int location[] = new int[2];
        getLocationInWindow(location);

        int offset = location[0] + getWidth();
        if(direction == MOVE_OUT_LEFT){
            offset = 0 - offset;
        }
        ObjectAnimator animator =  ObjectAnimator.ofFloat(this,"translationX",0f,offset).setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(mCenterBalloonListener != null){
                    mCenterBalloonListener.onStartMoveOut();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}
