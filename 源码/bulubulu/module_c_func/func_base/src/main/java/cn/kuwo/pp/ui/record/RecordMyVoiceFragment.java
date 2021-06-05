package cn.kuwo.pp.ui.record;

import android.Manifest;
import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.text.SimpleDateFormat;

import cn.kuwo.base.KwTimer;
import cn.kuwo.common.base.BaseFragment;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.MultipleStatusView;
import cn.kuwo.daynight.TypefaceUtil;
import cn.kuwo.player.BuluPlayer;
import cn.kuwo.player.IPlayCallback;
import cn.kuwo.player.PlayError;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.GuideBean;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.mine.VoiceEditFragment;
import cn.kuwo.pp.util.IconFountTextView;
import cn.kuwo.pp.util.LongPressProgressbar;
import cn.kuwo.pp.util.cardlayoutmgr.CardItemTouchHelperCallback;
import cn.kuwo.pp.util.cardlayoutmgr.CardLayoutManager;
import cn.kuwo.pp.util.cardlayoutmgr.OnSwipeListener;
import permissions.dispatcher.NeedsPermission;

public class RecordMyVoiceFragment extends BaseFragment implements RecordMyVoiceView, KwTimer.Listener, IPlayCallback {

    private String mWavFilePath;
    private LongPressProgressbar.OnCountdownProgressListener startBtnPressListener = new LongPressProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgressStart() {
            onPressStart();
        }

        @Override
        public void onProgress(int what, int progress) {
        }

        @Override
        public void onProgressEnd(boolean isUser, long runTime) {
            if (mPresenter.isRecording()) {
                mPresenter.stopRecording();
            }
        }

        @Override
        public void onProgressCancel() {
            Log.d("pressRecord", "---onProgressCancel");
            if (mPresenter.isRecording()) {
                mPresenter.cancelRecording();
            }
        }

        @Override
        public void onClick() {
            onClickPlay();
        }
    };
    private boolean isLoading;  //是否上传中

    private void onClickPlay() {
        if ((recordFilepath != null && recordFilepath.startsWith("http")) || UtilsCode.INSTANCE.isFileExists(recordFilepath)) {
            if (mPresenter.isRecording()) {
                return;
            }
            if (BuluPlayer.getInstance().isPlaying() && BuluPlayer.getInstance().isPlayingUrl(recordFilepath)) {
                BuluPlayer.getInstance().pause();
            } else {
                BuluPlayer.getInstance().play(recordFilepath);
            }
        }
    }


    public static RecordMyVoiceFragment newInstance() {
        return new RecordMyVoiceFragment();
    }

    LottieAnimationView animationCircleView;
    View loadingPanel;
    RecyclerView mRecyclerView;
    LongPressProgressbar btnStartRecord;
    TextView tvTitle;
    View btnEndRecord;
    View btnCancelRecord;
    IconFountTextView imgStartRecord;
    View imgEndRecord;
    View imgCancelRecord;
    TextView tvDesc;
    TextView tvTime;
    MultipleStatusView mStatusView;

    KwTimer kwTimer;
    SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
    String recordFilepath;

    private void _initView(View view) {
        animationCircleView = view.findViewById(R.id.anim_record_uploading);
        loadingPanel = view.findViewById(R.id.record_loading_group);
        mRecyclerView = view.findViewById(R.id.record_recyclerview);
        btnStartRecord = view.findViewById(R.id.record_start);
        tvTitle = view.findViewById(R.id.record_title);
        btnEndRecord = view.findViewById(R.id.record_finish);
        btnCancelRecord = view.findViewById(R.id.record_cancel);
        imgStartRecord = view.findViewById(R.id.record_start_icon);
        imgEndRecord = view.findViewById(R.id.record_finish_icon);
        imgCancelRecord = view.findViewById(R.id.record_cancel_icon);
        tvDesc = view.findViewById(R.id.record_desc);
        tvTime = view.findViewById(R.id.record_time);
        mStatusView = view.findViewById(R.id.record_status_view);

        btnCancelRecord.setOnClickListener(listener);
        imgCancelRecord.setOnClickListener(listener);
        btnEndRecord.setOnClickListener(listener);
        imgEndRecord.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //@OnClick({R.id.record_cancel,R.id.record_cancel_icon})
            //@OnClick({R.id.record_finish,R.id.record_finish_icon})
            if (v.getId() == R.id.record_cancel || v.getId() == R.id.record_cancel_icon) {
                onCancelClick(v);
            } else if (v.getId() == R.id.record_finish || v.getId() == R.id.record_finish_icon) {
                onUploadFinishClick(v);
            }
        }
    };

    public void onCancelClick(View view) {
        if (BuluPlayer.getInstance().isPlayingUrl(recordFilepath)) {
            BuluPlayer.getInstance().stop();
        }
        changeToNormalState();
        if (!TextUtils.isEmpty(recordFilepath)) {
            if (UtilsCode.INSTANCE.deleteFile(recordFilepath)) {
                UtilsCode.INSTANCE.deleteFile(recordFilepath.replace(".mp3", ".wav"));
            }
            recordFilepath = null;
        }
    }

    public void onUploadFinishClick(View view) {
        if (isLoading) {
            UtilsCode.INSTANCE.showShort("正在上传分析中，请稍等");
            return;
        }
        if (BuluPlayer.getInstance().isPlayingUrl(recordFilepath)) {
            BuluPlayer.getInstance().stop();
        }
        if (UtilsCode.INSTANCE.isFileExists(mWavFilePath)) {
            isLoading = true;
            mPresenter.uploadAndAnalysis(recordFilepath, mWavFilePath);
        } else {
            UtilsCode.INSTANCE.showShort("录制文件不存在");
        }
    }

    private void onPressStart() {
        if (mPresenter.isRecording()) {
            UtilsCode.INSTANCE.showShort("正在录音中");
        } else {
            startRecording();
        }
    }

    private void changePlayingState(boolean isPlaying) {
        if (recordFilepath != null && recordFilepath.equals(BuluPlayer.getInstance().getDataSource())) {
            if (isPlaying) {
                imgStartRecord.setIconText("&#xe6d2;");
            } else {
                imgStartRecord.setIconText("&#xe6d3;");
            }
        }
    }

    private String getUploadingAnimFileName(boolean isFirstPart) {
        boolean isNightMode = false;
        if ("2".equals(UserInfoManager.INSTANCE.getUserGender())) {
            isNightMode = true;
        }
        if (isFirstPart) {
            if (isNightMode) {
                return "record_uploading_women_1.json";
            } else {
                return "record_uploading_man_1.json";
            }
        } else {
            if (isNightMode) {
                return "record_uploading_women_2.json";
            } else {
                return "record_uploading_man_2.json";
            }
        }
    }

    /**
     * 切换界面控件到未开始录制状态
     */
    private void changeToNormalState() {
        tvTime.setVisibility(View.INVISIBLE);
        tvDesc.setVisibility(View.VISIBLE);
        btnEndRecord.setVisibility(View.INVISIBLE);
        imgEndRecord.setVisibility(View.INVISIBLE);
        btnCancelRecord.setVisibility(View.INVISIBLE);
        imgCancelRecord.setVisibility(View.INVISIBLE);
        imgStartRecord.setIconText("&#xe6d4;");
        tvTitle.setText("录制声音");
        tvDesc.setText(getResources().getText(R.string.record_desc));
        stopTimer();
        btnStartRecord.changeToNormal();
    }

    /**
     * 切换界面控件到录制结束状态
     */
    private void changeToFinishState() {
        stopTimer();
        mRecyclerView.setVisibility(View.VISIBLE);
        tvTime.setVisibility(View.VISIBLE);
        btnEndRecord.setVisibility(View.VISIBLE);
        imgEndRecord.setVisibility(View.VISIBLE);
        btnCancelRecord.setVisibility(View.VISIBLE);
        imgCancelRecord.setVisibility(View.VISIBLE);
        imgStartRecord.setIconText("&#xe6d3;");
        tvTitle.setText("录制完成");
        tvDesc.setText("");
    }

    /**
     * 切换界面控件到录制中状态
     */
    private void changeToRecordingState() {
        tvTime.setVisibility(View.VISIBLE);
        tvTitle.setText("正在录制");
        tvDesc.setText("可以录制5秒-1分钟的音频哦");
        btnEndRecord.setVisibility(View.INVISIBLE);
        imgEndRecord.setVisibility(View.INVISIBLE);
        btnCancelRecord.setVisibility(View.INVISIBLE);
        imgCancelRecord.setVisibility(View.INVISIBLE);
        startTimer();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
    private void startRecording() {
        mPresenter.startRecording();
    }

    private RecordMyVoicePresenter mPresenter;
    private RecordGuideAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_my_voice, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        animationCircleView.cancelAnimation();
        animationCircleView.setAnimation(getUploadingAnimFileName(true));
        animationCircleView.setRepeatCount(-1);  //无限循环
//        changeToNormalState();
//        loadingPanel.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        if (BuluPlayer.getInstance().isPlayingUrl(recordFilepath)) {
            BuluPlayer.getInstance().pause();
        }
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BuluPlayer.getInstance().addPlayCallback(this);
        TypefaceUtil.setTypeface(tvTime, TypefaceUtil.Akrobat_kuwo_Regular);
        mPresenter = new RecordMyVoicePresenter(this);
        mPresenter.requestGuideList();
        mStatusView.setEmptyViewResId(R.layout.look_for_friend_empty);
        mStatusView.showLoading();
        mStatusView.setOnRetryClickListener(v -> {
            if (mAdapter != null) {
                mAdapter.setNewData(null);
            }
            mPresenter.requestGuideList();
            mStatusView.showLoading();
        });
        btnStartRecord.setProgressColor(getResources().getColor(R.color.colorCommonHighlight));
        btnStartRecord.setOutLineColor(Color.TRANSPARENT);
        btnStartRecord.setInCircleColor(getResources().getColor(R.color.colorCommonHighlightAlpha20));
        btnStartRecord.setCountdownProgressListener(1, startBtnPressListener);
    }

    @Override
    public void onDestroyView() {
        BuluPlayer.getInstance().removePlayCallback(this);
        super.onDestroyView();
    }

    @Override
    public void onGuideListSuccess(BaseListData<GuideBean> data) {
        if (mRecyclerView == null) {
            return;
        }
        if (data != null && data.getList() != null && data.getList().size() > 0) {
            if (mAdapter == null) {
                mAdapter = new RecordGuideAdapter(data.getList());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(), data.getList());
                cardCallback.setOnSwipedListener(new OnSwipeListener<GuideBean>() {

                    @Override
                    public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, GuideBean bean, int direction) {
                        mAdapter.addData(bean);
                    }

                    @Override
                    public void onSwipedClear() {
                        mStatusView.showEmpty();
                    }
                });
                final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
                final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
                mRecyclerView.setLayoutManager(cardLayoutManager);
                touchHelper.attachToRecyclerView(mRecyclerView);
            } else {
                mAdapter.setNewData(data.getList());
            }
            mStatusView.showContent();
        } else {
            mStatusView.showEmpty();
        }
    }

    @Override
    public void onGuideListFailed(int code, String msg) {
        mStatusView.showError(msg);
    }

    @Override
    public void onRecordStart(String filePath) {
        changeToRecordingState();
        recordFilepath = filePath;
    }

    @Override
    public void onRecordSuccess(String mp3File, String wavFile, boolean isUserStop) {
        changeToFinishState();
        mWavFilePath = wavFile;
    }

    @Override
    public void onRecordFailed(int code, String msg) {
        changeToNormalState();
        if (TextUtils.isEmpty(recordFilepath) == false) {
            if (UtilsCode.INSTANCE.deleteFile(recordFilepath)) {
                UtilsCode.INSTANCE.deleteFile(recordFilepath.replace(".mp3", ".wav"));
            }
            recordFilepath = null;
        }
        UtilsCode.INSTANCE.showShort(msg);
    }

    @Override
    public void onUploadStart() {
        changeToUploadingState();
    }

    private void changeToUploadingState() {
        animationCircleView.playAnimation();
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUploadProcess(long percent, long total) {
        updateUploadingProcess(percent, total);
    }

    private void updateUploadingProcess(long percent, long total) {

    }

    @Override
    public void onUploadFailed(int code, String msg) {
        isLoading = false;
        changeToFinishState();
        loadingPanel.setVisibility(View.GONE);
        if (code == -1001) {
            //TODO 这个应该是不用了
//            post(() -> {
//                dlgHint = AlertDialog.getSingleBtnInstance(
//                        _mActivity,
//                        msg, "确定",
//                        v -> {
//                            if (dlgHint != null) {
//                                dlgHint.dismiss();
//                                dlgHint = null;
//                            }
//                            pop();
//                        }
//                );
//                dlgHint.show();
//            });
        } else {
            UtilsCode.INSTANCE.showShort(msg);
        }
    }

    @Override
    public void onUploadSuccess(VoiceInfo voiceInfo) {
        isLoading = false;
        if (animationCircleView == null) {
            return;
        }
        if (TextUtils.isEmpty(recordFilepath) == false) {
            if (UtilsCode.INSTANCE.deleteFile(recordFilepath)) {
                UtilsCode.INSTANCE.deleteFile(recordFilepath.replace(".mp3", ".wav"));
            }
            recordFilepath = null;
        }
        animationCircleView.setAnimation(getUploadingAnimFileName(false));
        animationCircleView.addAnimatorListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //打开新的展示界面
                startWithPop(VoiceEditFragment.newInstance(voiceInfo, true));

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationCircleView.setRepeatCount(0);
        animationCircleView.playAnimation();
    }

    /**
     * 启动定时器，参数是毫秒
     */
    private void startTimer() {
        stopTimer();
        tvTime.setText("00:00");
        if (kwTimer == null) {
            kwTimer = new KwTimer(this);
            kwTimer.start(1000);
        }
    }

    private void stopTimer() {
        if (kwTimer != null && kwTimer.isRunnig()) {
            kwTimer.stop();
            kwTimer = null;
        }
    }

    @Override
    public void onTimer(KwTimer timer) {
        int tickTime = timer.getTickTimes() * 1000;  //把响应次数转为秒
        tvTime.setText(UtilsCode.INSTANCE.millis2String(tickTime, timeFormat));
    }

    @Override
    public void onPlayStart() {
        changePlayingState(true);
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
        changePlayingState(false);

    }

    @Override
    public void onPlayStop() {
        changePlayingState(false);

    }

    @Override
    public void onSeekCompleted() {

    }

    @Override
    public void onError(PlayError code, String errExtMsg) {
        changePlayingState(false);
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
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
