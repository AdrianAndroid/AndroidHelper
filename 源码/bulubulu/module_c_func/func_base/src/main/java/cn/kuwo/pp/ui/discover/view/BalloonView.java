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

import java.util.ArrayList;
import java.util.Random;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;

public class BalloonView extends RelativeLayout {
    private ArrayList<ObjectAnimator> mFloatAnimatorList = new ArrayList();
    private int mAnimatorIndex = -1;
    private boolean mFloatFlag = false;
    private VoiceInfo mVoiceInfo;
    private onBalloonViewListener mBalloonViewListener;
    private View mRootView;

    public BalloonView(Context context) {
        super(context);
        initView();
    }

    public BalloonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BalloonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_balloon, this);
        initFloatAnimatorList();
    }

    public void setVoiceInfo(VoiceInfo voiceInfo) {
        mVoiceInfo = voiceInfo;

        ImageView bgImageView = mRootView.findViewById(R.id.balloonBackground);
        bgImageView.setImageResource(voiceInfo.isGirl() ? R.drawable.balloon_background_girl : R.drawable.balloon_background_boy);

        ImageView faceImageView = mRootView.findViewById(R.id.balloonUserFace);
        ImageLoader.showCircleImage(faceImageView, voiceInfo.getHeadImg());
    }

    public VoiceInfo getVoiceInfo() {
        return mVoiceInfo;
    }

    public void startFadeInAnimation() {
        float scale = getScaleParam();

        ObjectAnimator smallX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.1f).setDuration(100);
        ObjectAnimator smallY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.1f).setDuration(200);
        ObjectAnimator bigX = ObjectAnimator.ofFloat(this, "scaleX", 0.1f, scale).setDuration(800);
        ObjectAnimator bigY = ObjectAnimator.ofFloat(this, "scaleY", 0.1f, scale).setDuration(800);
        bigX.setStartDelay(200);
        bigY.setStartDelay(200);

        setVisibility(INVISIBLE);
        smallX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(smallX, smallY, bigX, bigY);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startFloatAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void startFloatAnimation() {
        if (mAnimatorIndex == -1) {
            Random random = new Random();
            mAnimatorIndex = random.nextInt(mFloatAnimatorList.size());
        } else {
            mAnimatorIndex++;
            if (mAnimatorIndex == mFloatAnimatorList.size()) {
                mAnimatorIndex = 0;
            }
        }

        getFloatAnimator().start();
        mFloatFlag = true;
    }

    private ObjectAnimator getFloatAnimator() {
        ObjectAnimator animator = mFloatAnimatorList.get(mAnimatorIndex);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                if (mFloatFlag) {
                    startFloatAnimation();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return animator;
    }

    private void initFloatAnimatorList() {
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationX", 0f, 25f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationX", 25f, 0f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationY", 0f, 25f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationY", 25f, 0f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationX", 0f, -25f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationX", -25f, 0f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationY", 0f, -25f).setDuration(4200));
        mFloatAnimatorList.add(ObjectAnimator.ofFloat(this, "translationY", -25f, 0f).setDuration(4200));
    }

    private float getScaleParam() {
        Random random = new Random();
        float scale = ((float) (random.nextInt(2))) / (float) 10;

        random = new Random();
        if (random.nextInt(10) % 2 == 0) {
            scale += 1.0;
        } else {
            scale = 1 - scale;
        }

        if (scale > 1.2 || scale < 0.8) {
            scale = 1;
        }

        return scale;
    }

    public void moveToCenter(CenterBalloonView centerView) {
        int location[] = new int[2];
        centerView.getLocationInWindow(location);
        int destX = location[0] + (centerView.getWidth() - getWidth()) / 2;
        int destY = location[1] + UtilsCode.INSTANCE.dp2px(5);

        mFloatFlag = false;
        clearAnimation();
        if (mAnimatorIndex > -1) {
            ObjectAnimator floatAnimator = mFloatAnimatorList.get(mAnimatorIndex);
            floatAnimator.cancel();
            floatAnimator.removeAllListeners();
        }

        getLocationInWindow(location);
        int posX = location[0];
        int poxY = location[1];

        ObjectAnimator positionX = ObjectAnimator.ofFloat(this, "X", posX, destX).setDuration(1000);
        ObjectAnimator positionY = ObjectAnimator.ofFloat(this, "Y", poxY, destY).setDuration(1000);

        float scale = (float) centerView.getWidth() / (float) getWidth();
        ObjectAnimator sizeX = ObjectAnimator.ofFloat(this, "scaleX", 1, scale).setDuration(1000);
        ObjectAnimator sizeY = ObjectAnimator.ofFloat(this, "scaleY", 1, scale).setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(positionX, positionY, sizeX, sizeY);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mBalloonViewListener != null) {
                    mBalloonViewListener.onMoveCenterFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                animatorSet.start();
            }
        }, 200);
    }

    public interface onBalloonViewListener {
        void onMoveCenterFinish();
    }

    public void setBalloonViewListener(onBalloonViewListener listener) {
        mBalloonViewListener = listener;
    }
}
