package cn.kuwo.pp.ui.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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


import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.util.CircleTextProgressbar;
import cn.kuwo.pp.util.IconFountTextView;

public class ShareVoiceDialog extends ShareCardDialog {
    private VoiceInfo mVoiceInfo;

    public static ShareVoiceDialog newInstance(VoiceInfo model) {
        ShareVoiceDialog dialog = new ShareVoiceDialog(model);
        return dialog;
    }

    public ShareVoiceDialog(VoiceInfo model) {
        mVoiceInfo = model;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_voice, container, false);
        _initView(view);
        return view;
    }

    @Override
    public String getShareId() {
        return mVoiceInfo.getVid();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.view_share_voice;
    }

    @Override
    public void setPreviewInfo(View rootView, boolean sharePic) {
        ImageView imgUserHeadIcon = rootView.findViewById(R.id.voice_edit_user_icon);
        TextView tvUserName = rootView.findViewById(R.id.voice_edit_user_name);
        TextView tvUserTimbre = rootView.findViewById(R.id.voice_edit_timbre);
        TextView tvResultContent = rootView.findViewById(R.id.voice_edit_result_content);
        TextView tvMatchContent = rootView.findViewById(R.id.voice_edit_match_content);
        ProgressBar pbSexProgress = rootView.findViewById(R.id.voice_sex_progress);
        ProgressBar pbSameprogress = rootView.findViewById(R.id.voice_same_progress);
        IconFountTextView tvPlayState = rootView.findViewById(R.id.voice_edit_play_state);
        CircleTextProgressbar scoreProgressBar = rootView.findViewById(R.id.voice_edit_score);

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

        if (!TextUtils.isEmpty(mVoiceInfo.getImg())) {
            if (sharePic) {
                ImageView ivDialogHeaderIcon = getView().getRootView().findViewById(R.id.voice_edit_user_icon);

                imgUserHeadIcon.getLayoutParams().height = ivDialogHeaderIcon.getMeasuredHeight();
                imgUserHeadIcon.getLayoutParams().width = ivDialogHeaderIcon.getMeasuredWidth();
                Bitmap bgBitmap = Bitmap.createBitmap(ivDialogHeaderIcon.getMeasuredWidth(), ivDialogHeaderIcon.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                ivDialogHeaderIcon.draw(new Canvas(bgBitmap));
                imgUserHeadIcon.setBackground(UtilsCode.INSTANCE.bitmap2Drawable(bgBitmap));
            } else {
                ImageLoader.showImage(imgUserHeadIcon, mVoiceInfo.getImg(), -1);
            }
        }
    }
}
