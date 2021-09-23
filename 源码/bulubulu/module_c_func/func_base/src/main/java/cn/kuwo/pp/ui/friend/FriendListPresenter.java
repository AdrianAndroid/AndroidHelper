package cn.kuwo.pp.ui.friend;

import android.widget.ImageView;

import com.tencent.qcloud.uikit.common.utils.UIUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.PlayCallback;
import cn.kuwo.player.PlayError;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.FriendList.FriendListItem;

public class FriendListPresenter {
    private static final int DEFAULT_PAGE_SIZE = 20;

    public FriendListPresenter(BaseFragment fragment) {
        this.fragment = fragment;
    }
    private BaseFragment fragment;

    private class PlayBtnCallBack extends PlayCallback {
        @Override
        public void onPlayStop() {
            setButtonStopped();
        }

        @Override
        public void onError(PlayError code, String errExtMsg) {
            setButtonStopped();
        }

        public void setButtonPlaying(ImageView btn) {
            if (currentButton != null) {
                currentButton.setImageResource(R.drawable.voice_play);
            } else {
                BuluPlayer.getInstance().addPlayCallback(this);
            }
            currentButton = btn;
            currentButton.setImageResource(R.drawable.voice_playing);
        }
        public void setButtonStopped() {
            BuluPlayer.getInstance().removePlayCallback(this);
            if (currentButton != null) {
                currentButton.setImageResource(R.drawable.voice_play);
                currentButton = null;
            }
        }
        public ImageView currentButton;
    };

    private PlayBtnCallBack playCallback = new PlayBtnCallBack();

    public void onPlayButtonClicked(final FriendListItem item, final ImageView view) {
        if (view == playCallback.currentButton) {
            BuluPlayer.getInstance().stop();
            return;
        }

        BuluPlayer.getInstance().stop();
        playCallback.setButtonPlaying(view);

        RetrofitClient.getInstance().execute(RetrofitClient.getApiService().getUserDetail(item.user.getUid()).compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW)),
                new CustomObserver<VoiceInfo>() {
                    @Override
                    public void onNext(VoiceInfo o) {
                        if (o != null) {
                            BuluPlayer.getInstance().play(o.getUrl());
                        } else {
                            playCallback.setButtonStopped();
                            UIUtils.toastLongMessage("声音数据请求错误");
                        }
                    }

                    @Override
                    public void _onError(ApiException e) {
                        playCallback.setButtonStopped();
                        UIUtils.toastLongMessage("声音数据请求失败");
                    }
                });
    }
}
