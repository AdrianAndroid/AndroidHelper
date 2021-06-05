package cn.kuwo.pp.ui.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Random;

import cn.kuwo.common.dialog.BaseBottomDialog;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.common.view.UseHeadImageLayout;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.ui.discover.view.PolygonProgressView;
import cn.kuwo.pp.ui.world.util.UserPicItemDecoration;

public class ShareCardDialog extends BaseBottomDialog {

    RecyclerView recyclerView;
    ImageView iv_close;

    protected void _initView(View view) {
        recyclerView = view.findViewById(R.id.rv_grids);
        iv_close = view.findViewById(R.id.iv_close);
    }

    private QuestionModel mModel;
    private ShareAdapter mAdapter;
    private int mAnswerProgress;
    private int mMatchCount;

    public static ShareCardDialog newInstance(QuestionModel model, int answerProgress, int matchCount) {
        ShareCardDialog dialog = new ShareCardDialog(model, answerProgress, matchCount);

        return dialog;
    }


    public ShareCardDialog(QuestionModel model, int answerProgress, int matchCount) {
        mModel = model;
        mAnswerProgress = answerProgress;
        mMatchCount = matchCount;
    }

    public ShareCardDialog() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_pk, container, false);
        _initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPreviewInfo(view.getRootView(), false);

        mAdapter = new ShareAdapter(ShareUtil.buildShareItem());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new UserPicItemDecoration(UtilsCode.INSTANCE.dp2px(5f), Color.TRANSPARENT));

        addListeners();
        setVerticalDragListener(view);
    }


    public String getShareId() {
        return mModel.getId();
    }

    public int getLayoutResId() {
        return R.layout.view_share_friend_test;
    }

    private void addListeners() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShareUtil.onItemClick(ShareCardDialog.this, adapter, view, position);
            }
        });
    }

    public void setPreviewInfo(View rootView, boolean sharePic) {
        TextView tvTitle = rootView.findViewById(R.id.tvTitle);
        ImageView ivOptionOne = rootView.findViewById(R.id.ivOptionOne);
        ImageView ivOptionTwo = rootView.findViewById(R.id.ivOptionTwo);
        RelativeLayout viewOptionOneBg = rootView.findViewById(R.id.viewOptionOneBg);
        RelativeLayout viewOptionTwoBg = rootView.findViewById(R.id.viewOptionTwoBg);
        TextView tvOptionOne = rootView.findViewById(R.id.tvOptionOne);
        TextView tvOptionTwo = rootView.findViewById(R.id.tvOptionTwo);
        TextView tvUserName = rootView.findViewById(R.id.tvUserName);
        ImageView ivUserHeader = rootView.findViewById(R.id.ivUserHeader);
        View viewAnswerOnePercent = rootView.findViewById(R.id.viewAnswerOnePercent);
        TextView tvAnswerOnePercent = rootView.findViewById(R.id.tvAnswerOnePercent);
        View viewAnswerTwoPercent = rootView.findViewById(R.id.viewAnswerTwoPercent);
        TextView tvAnswerTwoPercent = rootView.findViewById(R.id.tvAnswerTwoPercent);
        UseHeadImageLayout layoutAnswerOneUser = rootView.findViewById(R.id.layoutAnswerOneUser);
        UseHeadImageLayout layoutAnswerTwoUser = rootView.findViewById(R.id.layoutAnswerTwoUser);
        ImageView ivAnswerOneCheck = rootView.findViewById(R.id.ivAnswerOneCheck);
        ImageView ivAnswerTwoCheck = rootView.findViewById(R.id.ivAnswerTwoCheck);
        CardView cvUsers = rootView.findViewById(R.id.cvUsers);
        TextView tvGroupOne = rootView.findViewById(R.id.tvGroupOne);
        TextView tvGroupTwo = rootView.findViewById(R.id.tvGroupTwo);
        PolygonProgressView viewAnswerProgress = rootView.findViewById(R.id.viewAnswerProgress);
        ImageView ivMatch = rootView.findViewById(R.id.ivMatch);


        tvTitle.setText(mModel.getQuestion());

        ivOptionOne.setVisibility(mModel.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);
        ivOptionTwo.setVisibility(mModel.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);
        viewOptionOneBg.setVisibility(!mModel.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);
        viewOptionTwoBg.setVisibility(!mModel.isPicQuestion() ? View.VISIBLE : View.INVISIBLE);

        if (mModel.isPicQuestion()) {
            if (sharePic) {
                ImageView ivDialogOptionOne = getView().getRootView().findViewById(R.id.ivOptionOne);
                Bitmap bgBitmap = Bitmap.createBitmap(ivDialogOptionOne.getMeasuredWidth(), ivDialogOptionOne.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                ivDialogOptionOne.draw(new Canvas(bgBitmap));
                ivOptionOne.setBackground(UtilsCode.INSTANCE.bitmap2Drawable(bgBitmap));

                ImageView ivDialogOptionTwo = getView().getRootView().findViewById(R.id.ivOptionTwo);
                bgBitmap = Bitmap.createBitmap(ivDialogOptionTwo.getMeasuredWidth(), ivDialogOptionTwo.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                ivDialogOptionTwo.draw(new Canvas(bgBitmap));
                ivOptionTwo.setBackground(UtilsCode.INSTANCE.bitmap2Drawable(bgBitmap));
            } else {
                ImageLoader.showImage(ivOptionOne, mModel.getOptionOne(), R.drawable.image_rounded_placeholder);
                ImageLoader.showImage(ivOptionTwo, mModel.getOptionTwo(), R.drawable.image_rounded_placeholder);
            }
        } else {
            SetTextQuestionBgColor(mModel, viewOptionOneBg, viewOptionTwoBg, tvOptionOne, tvOptionTwo);
            tvOptionOne.setText(mModel.getOptionOne());
            tvOptionTwo.setText(mModel.getOptionTwo());
        }

        if (mModel.getUser() != null) {
            tvUserName.setText(mModel.getUser().getName());
            ImageLoader.showCircleImage(ivUserHeader, mModel.getUser().getHeadImg(), mModel.getUser().getNewDefaultHeadImage());
        }

        if (mModel.getAnswerModel() != null) {
            viewAnswerOnePercent.setVisibility(View.VISIBLE);
            tvAnswerOnePercent.setVisibility(View.VISIBLE);
            viewAnswerTwoPercent.setVisibility(View.VISIBLE);
            tvAnswerTwoPercent.setVisibility(View.VISIBLE);
            cvUsers.setVisibility(View.VISIBLE);
            layoutAnswerOneUser.setVisibility(mModel.getAnswerModel().getOptionOneUser().size() > 0 ? View.VISIBLE : View.INVISIBLE);
            layoutAnswerTwoUser.setVisibility(mModel.getAnswerModel().getOptionTwoUser().size() > 0 ? View.VISIBLE : View.INVISIBLE);
            ivAnswerOneCheck.setVisibility(mModel.getAnswerModel().isAnswerOne() ? View.VISIBLE : View.GONE);
            ivAnswerTwoCheck.setVisibility(!mModel.getAnswerModel().isAnswerOne() ? View.VISIBLE : View.GONE);

            viewAnswerOnePercent.setBackgroundColor(mModel.getAnswerModel().isAnswerOne() ? Color.parseColor("#E5FFE43F") : Color.parseColor("#CCE0E0E0"));
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewAnswerOnePercent.getLayoutParams();
            layoutParams.matchConstraintPercentHeight = ((float) getMiniPercent(mModel.getAnswerModel().getOptionOnePer()) / (float) 100.0);
            viewAnswerOnePercent.setLayoutParams(layoutParams);

            viewAnswerTwoPercent.setBackgroundColor(mModel.getAnswerModel().isAnswerOne() ? Color.parseColor("#CCE0E0E0") : Color.parseColor("#E5FFE43F"));
            layoutParams = (ConstraintLayout.LayoutParams) viewAnswerTwoPercent.getLayoutParams();
            layoutParams.matchConstraintPercentHeight = ((float) getMiniPercent(mModel.getAnswerModel().getOptionTwoPer()) / (float) 100.0);
            viewAnswerTwoPercent.setLayoutParams(layoutParams);

            tvAnswerOnePercent.setText(mModel.getAnswerModel().getOptionOnePer() + "%");
            tvAnswerTwoPercent.setText(mModel.getAnswerModel().getOptionTwoPer() + "%");
            tvGroupOne.setText(mModel.getAnswerModel().getScoreOne() + "人");
            tvGroupTwo.setText(mModel.getAnswerModel().getScoreTwo() + "人");

            ivMatch.setImageResource(mMatchCount > 0 ? R.drawable.friend_test_match_highlight : R.drawable.friend_test_match);
            viewAnswerProgress.setProgress(mAnswerProgress);

            ArrayList oneUserPicList = new ArrayList();
            for (int i = 0; i < mModel.getAnswerModel().getOptionOneUser().size(); i++) {
                if (i >= 4) {
                    break;
                }
                oneUserPicList.add(mModel.getAnswerModel().getOptionOneUser().get(i).getHeadImg());
            }

            layoutAnswerOneUser.removeAllUrls();
            if (!sharePic) {
                layoutAnswerOneUser.setSmallMode();
            }
            layoutAnswerOneUser.setUrls(oneUserPicList);

            ArrayList twoUserPicList = new ArrayList();
            for (int i = 0; i < mModel.getAnswerModel().getOptionTwoUser().size(); i++) {
                if (i >= 4) {
                    break;
                }
                twoUserPicList.add(mModel.getAnswerModel().getOptionTwoUser().get(i).getHeadImg());
            }

            layoutAnswerTwoUser.removeAllUrls();
            if (!sharePic) {
                layoutAnswerTwoUser.setSmallMode();
            }
            layoutAnswerTwoUser.setUrls(twoUserPicList);

            layoutAnswerTwoUser.post(() -> {
                layoutAnswerOneUser.bringToFront();
                layoutAnswerTwoUser.bringToFront();
            });
        } else {
            viewAnswerOnePercent.setVisibility(View.INVISIBLE);
            tvAnswerOnePercent.setVisibility(View.INVISIBLE);
            viewAnswerTwoPercent.setVisibility(View.INVISIBLE);
            tvAnswerTwoPercent.setVisibility(View.INVISIBLE);
            layoutAnswerOneUser.setVisibility(View.INVISIBLE);
            layoutAnswerTwoUser.setVisibility(View.INVISIBLE);
            ivAnswerOneCheck.setVisibility(View.GONE);
            ivAnswerTwoCheck.setVisibility(View.GONE);
            cvUsers.setVisibility(View.INVISIBLE);
        }
    }

    private void SetTextQuestionBgColor(QuestionModel model, View left, View right, TextView leftTextView, TextView rightTextView) {
        Random random = new Random();

        if (model.getColorIndex() < 0) {
            model.setColorIndex(random.nextInt(3));
        }

        switch (model.getColorIndex()) {
            case 0: {
                left.setBackgroundColor(Color.parseColor("#C273DE"));
                right.setBackgroundColor(Color.parseColor("#6E96FF"));
                leftTextView.setTextColor(Color.parseColor("#4A2657"));
                rightTextView.setTextColor(Color.parseColor("#223056"));
            }
            return;
            case 1: {
                left.setBackgroundColor(Color.parseColor("#FFC058"));
                right.setBackgroundColor(Color.parseColor("#3DD6D9"));
                leftTextView.setTextColor(Color.parseColor("#4E3B1B"));
                rightTextView.setTextColor(Color.parseColor("#144647"));
            }
            return;
            case 2: {
                left.setBackgroundColor(Color.parseColor("#6E96FF"));
                right.setBackgroundColor(Color.parseColor("#DD5C4E"));
                leftTextView.setTextColor(Color.parseColor("#223056"));
                rightTextView.setTextColor(Color.parseColor("#56221C"));
            }
            return;
        }

        left.setBackgroundColor(Color.parseColor("#C273DE"));
        right.setBackgroundColor(Color.parseColor("#6E96FF"));
        leftTextView.setTextColor(Color.parseColor("#4A2657"));
        rightTextView.setTextColor(Color.parseColor("#223056"));
    }

    private int getMiniPercent(int percent) {
        if (percent < 30) {
            percent = 30;
        }

        return percent;
    }
}
