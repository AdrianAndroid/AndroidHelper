package cn.kuwo.pp.ui.discover;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import cn.kuwo.common.dialog.FullScreenDialog;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.CustomToolbar;
import cn.kuwo.common.viewmodel.MainViewModel;
import cn.kuwo.pp.R;
import cn.kuwo.pp.event.MatchSuccessEvent;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.chat.C2CChatFragment;
import io.reactivex.Observable;

public class FriendMatchingDialog extends FullScreenDialog {
    public FriendMatchingDialog(Activity context) {
        super(context, R.style.dialog);
    }

    private VoiceInfo mVoiceInfo = null;
    private boolean isMatchFinish = false;
    private boolean isAnimFinish = false;

    public static FriendMatchingDialog newInstance(Activity context) {
        return new FriendMatchingDialog(new WeakReference<>(context).get());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        matchUsers();
    }

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.dialog_friend_matching);
    }

    private void initUI() {
        CustomToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("匹配中…");
        toolbar.getToolbar().setNavigationIcon(null);

        LottieAnimationView anim_bg = findViewById(R.id.anim_bg);
        anim_bg.setImageAssetsFolder("json_image_3");
        anim_bg.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                isAnimFinish = true;
                onMatchFinish();
            }
        });
        anim_bg.playAnimation();

        ImageView ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void onMatchFinish() {
        if (!isMatchFinish) {
            return;
        }
        if (!isAnimFinish) {
            return;
        }

        EventBus.getDefault().post(new MatchSuccessEvent());
        C2CChatFragment fragment = C2CChatFragment.Companion.newInstance("", mVoiceInfo.getUid(), mVoiceInfo);
        fragment.setFromMatch();

//        MainActivity activity = (MainActivity) getOwnerActivity();
//        activity.start(fragment, SINGLETASK);
        if (getOwnerActivity() instanceof AppCompatActivity) { // 其实就是MainActivity
            new ViewModelProvider((AppCompatActivity) getOwnerActivity()).get(MainViewModel.class)
                    .startSingleTask.setValue(fragment);
        }

        dismiss();
    }

    private void matchUsers() {
        Observable<BaseHttpResult<BaseListData<VoiceInfo>>> observable = RetrofitClient.getApiService().matchUsers(UserInfoManager.INSTANCE.getUid()).compose(bindUntilEvent(FragmentEvent.STOP));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<BaseListData<VoiceInfo>>() {
                    @Override
                    public void onNext(BaseListData<VoiceInfo> o) {
                        if (o != null && o.getList() != null && o.getList().size() > 0) {
                            isMatchFinish = true;
                            mVoiceInfo = o.getList().get(0);
                            onMatchFinish();
                        } else {
                            UtilsCode.INSTANCE.showShort("未能匹配到用户");
                            dismiss();
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showShort("匹配请求失败: " + e.getMessage());
                        dismiss();
                    }
                });
    }
}
