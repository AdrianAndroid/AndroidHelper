package cn.kuwo.pp.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.IPlayCallback;
import cn.kuwo.player.PlayError;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.login.EditProfileFragment;
import cn.kuwo.pp.ui.main.MainActivity;
import cn.kuwo.pp.ui.main.MainFragment;
import cn.kuwo.pp.ui.record.RecordMyVoiceFragment;
import cn.kuwo.pp.ui.share.ShareVoiceDialog;
import cn.kuwo.pp.util.CircleTextProgressbar;
import cn.kuwo.pp.util.IconFountTextView;
import io.reactivex.Observable;
import me.yokeyword.fragmentation.ISupportFragment;

@Deprecated
public class VoiceEditFragment extends BaseFragment {
    public static final String KEY_USER = "user";
    public static final String KEY_IS_LOGIN_MODE = "is_login_mode";
    public static final String USER_RESET_TEST_DATE = "User_Reset_Test_Date";
    public static final String USER_RESET_TEST_VOICE = "User_Reset_Test_Voice";
    public static final String SHARE_BASE_URL = "http://bulubulu.kuwo.cn/m/bulubulu/index.html?uid=";
    private VoiceInfo mVoiceInfo;
    private boolean mIsLoginMode;


    ViewGroup scrollLayout;
    ImageView imgUserHeadIcon;
    TextView tvUserName;
    TextView tvUserTimbre;
    TextView tvResultContent;
    TextView tvMatchContent;
    ProgressBar pbSexProgress;
    ProgressBar pbSameprogress;
    IconFountTextView tvPlayState;
    CircleTextProgressbar scoreProgressBar;
    View mSaveBtn;
    View mShareBtn;


    private void _initView(View view) {

        scrollLayout = view.findViewById(R.id.voice_edit_scroll_con_layout);

        imgUserHeadIcon = view.findViewById(R.id.voice_edit_user_icon);

        tvUserName = view.findViewById(R.id.voice_edit_user_name);

        tvUserTimbre = view.findViewById(R.id.voice_edit_timbre);

        tvResultContent = view.findViewById(R.id.voice_edit_result_content);

        tvMatchContent = view.findViewById(R.id.voice_edit_match_content);

        pbSexProgress = view.findViewById(R.id.voice_sex_progress);

        pbSameprogress = view.findViewById(R.id.voice_same_progress);

        tvPlayState = view.findViewById(R.id.voice_edit_play_state);

        scoreProgressBar = view.findViewById(R.id.voice_edit_score);

        mSaveBtn = view.findViewById(R.id.voice_edit_save_btn);

        mShareBtn = view.findViewById(R.id.voice_edit_share_btn);

        mSaveBtn.setOnClickListener(listener);
        mShareBtn.setOnClickListener(listener);
        View voice_edit_retest = view.findViewById(R.id.voice_edit_retest);
        if (voice_edit_retest != null) {
            voice_edit_retest.setOnClickListener(listener);
        }
        tvUserTimbre.setOnClickListener(listener);
        tvPlayState.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.voice_edit_save_btn) {
                onSaveBtnClick(v);
            } else if (id == R.id.voice_edit_share_btn) {
                onShareBtnClick(v);
            } else if (id == R.id.voice_edit_retest) {
                onRetestBtnClick(v);
            } else if (id == R.id.voice_edit_timbre || id == R.id.voice_edit_play_state) {
                onPlayClick(v);
            }
        }
    };

    private IPlayCallback playCallback = new IPlayCallback() {
        @Override
        public void onPlayStart() {
            updatePlayState(true);
        }

        @Override
        public void onBuffering(int bufPercent) {

        }

        @Override
        public void onPlaying() {
            updatePlayState(true);
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
            UtilsCode.INSTANCE.showShort("" + errExtMsg);
            updatePlayState(false);
        }
    };
    private String mPlayingUrl;

    public void onSaveBtnClick(View view) {
        if (mVoiceInfo != null) {
            saveVoiceToServer();
        } else {
            UtilsCode.INSTANCE.showShort("用户信息不全，保存失败！");
        }
    }

    public void onShareBtnClick(View view) {
        if (mVoiceInfo != null) {
            ShareVoiceDialog.newInstance(mVoiceInfo).show(getFragmentManager());
        }
    }

    private void saveVoiceToServer() {
        Observable<BaseHttpResult<VoiceInfo>> observable = RetrofitClient.getApiService().saveVoiceInfo(mVoiceInfo.getUid()).compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        RetrofitClient.getInstance().execute(observable,
                new CustomObserver<VoiceInfo>() {
                    @Override
                    public void onNext(VoiceInfo o) {
                        onSaveToServerSuccess(o);
                    }

                    @Override
                    public void _onError(ApiException e) {
                        UtilsCode.INSTANCE.showShort(e.getMessage());
                    }
                });
    }

    private void onSaveToServerSuccess(VoiceInfo o) {
        if (o != null) {
            mVoiceInfo = o;
            UserInfoManager.INSTANCE.updateVoice(mVoiceInfo);
        }
        if (UserInfoManager.INSTANCE.isNotLogin()) {
            startFromMain(EditProfileFragment.Companion.newInstance(0));
            return;
        }
        if (_mActivity instanceof MainActivity) {
            start(MainFragment.Companion.newInstance(), ISupportFragment.SINGLETASK); //TODO 处理一下
        } else {
            startActivity(new Intent(_mActivity, MainActivity.class));
            _mActivity.finish();
        }
    }

    public void onRetestBtnClick(View view) {
        startWithPopTo(RecordMyVoiceFragment.newInstance(), VoiceEditFragment.class, true);
    }

    //    @OnClick({R.id.voice_edit_timbre, R.id.voice_edit_play_state})
    public void onPlayClick(View v) {
        if (mVoiceInfo != null && !TextUtils.isEmpty(mVoiceInfo.getUrl())) {
            if (mPlayingUrl == null || TextUtils.isEmpty(BuluPlayer.getInstance().getDataSource()) ||
                    !UtilsCode.INSTANCE.equals(BuluPlayer.getInstance().getDataSource(), mPlayingUrl)) {
                mPlayingUrl = mVoiceInfo.getUrl();
                BuluPlayer.getInstance().play(mPlayingUrl);
            } else {
                BuluPlayer.getInstance().autoSwitch();
            }
        }
    }

    public static VoiceEditFragment newInstance(VoiceInfo voiceInfo, boolean isLoginMode) {
        VoiceEditFragment fragment = new VoiceEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_USER, voiceInfo);
        args.putBoolean(KEY_IS_LOGIN_MODE, isLoginMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mVoiceInfo = bundle.getParcelable(KEY_USER);
            mIsLoginMode = bundle.getBoolean(KEY_IS_LOGIN_MODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_edit, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsLoginMode) {
            enableToolbar(R.id.toolbar, false);
            mSaveBtn.setVisibility(View.VISIBLE);
            mShareBtn.setVisibility(View.GONE);
        } else {
            enableToolbar(R.id.toolbar, true);
            mToolbar.getToolbar().setNavigationIcon(R.drawable.icon_back);
            mSaveBtn.setVisibility(View.INVISIBLE);
            mShareBtn.setVisibility(View.VISIBLE);
        }

        BuluPlayer.getInstance().addPlayCallback(playCallback);
        updateInfoToUI();
    }

    private void updatePlayState(boolean isPlayed) {
        if (isPlayed) {
            tvPlayState.setIconText("&#xe6d2;");
        } else {
            tvPlayState.setIconText("&#xe6d3;");
        }
    }

    private void updateInfoToUI() {
        if (mVoiceInfo != null) {
            scoreProgressBar.setProgress(mVoiceInfo.getScore());
            scoreProgressBar.setText(mVoiceInfo.getScore() + "");
            String userName = mVoiceInfo.getName();
            if (userName == null && UserInfoManager.INSTANCE.getUserInfo() != null) {
                userName = UserInfoManager.INSTANCE.getUserInfo().getName();
            }
            if (TextUtils.isEmpty(userName)) {
                tvUserName.setVisibility(View.GONE);
            } else {
                tvUserName.setText(userName);
                tvUserName.setVisibility(View.VISIBLE);
            }
            tvUserTimbre.setText(mVoiceInfo.getCard());
            tvResultContent.setText(mVoiceInfo.getText());
            tvMatchContent.setText(mVoiceInfo.getMatching());
            pbSexProgress.setProgress(mVoiceInfo.getAlikeNoScore());
            pbSameprogress.setProgress(mVoiceInfo.getAlikeScore());
            if (TextUtils.isEmpty(mVoiceInfo.getImg()) == false) {
                ImageLoader.showImage(imgUserHeadIcon, mVoiceInfo.getImg(), -1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        BuluPlayer.getInstance().pause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        BuluPlayer.getInstance().stop();
        BuluPlayer.getInstance().removePlayCallback(playCallback);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
