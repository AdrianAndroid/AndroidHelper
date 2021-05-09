package cn.kuwo.pp.ui.topic.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.util.L;
import cn.kuwo.common.app.App;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.ui.topic.ChallengeFragment;
import cn.kuwo.pp.util.UIUtil;

public class ChallengeAdapter extends BaseQuickAdapter<QuestionModel, BaseViewHolder> {

    private ChallengeFragment mView;
    final Random random = new Random(); // 随机

    public ChallengeAdapter(ChallengeFragment view, List<QuestionModel> data) {
        super(R.layout.item_challenge, data);
        mView = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionModel item) {
        helper.setText(R.id.tvTitle, item.getQuestion());

        helper.setVisible(R.id.ivOptionOne, item.isPicQuestion());
        helper.setVisible(R.id.ivOptionTwo, item.isPicQuestion());
        helper.setVisible(R.id.viewOptionOneBg, !item.isPicQuestion());
        helper.setVisible(R.id.viewOptionTwoBg, !item.isPicQuestion());
        helper.setImageResource(R.id.ivMusic, mView.isPlayingBgMusic() ? R.drawable.question_bg_music_close : R.drawable.question_bg_music);

        if (item.isPicQuestion()) {
            if(App.DEBUG) L.m(getClass(), "optionOne", item.getOptionOne());
            if(App.DEBUG) L.m(getClass(), "optionOne", item.getOptionTwo());
            ImageLoader.showImage(helper.getView(R.id.ivOptionOne), item.getOptionOne(), R.drawable.image_rounded_placeholder);
            ImageLoader.showImage(helper.getView(R.id.ivOptionTwo), item.getOptionTwo(), R.drawable.image_rounded_placeholder);
        } else {
            SetTextQuestionBgColor(item, helper.getView(R.id.layoutContentOne), helper.getView(R.id.layoutContentTwo), helper.getView(R.id.tvOptionOne), helper.getView(R.id.tvOptionTwo));
            helper.setText(R.id.tvOptionOne, item.getOptionOne());
            helper.setText(R.id.tvOptionTwo, item.getOptionTwo());
        }

        helper.setText(R.id.tvUserName, item.getUser().getName());
        helper.setImageResource(R.id.ivPraise, item.getLiked() == 0 ? R.drawable.question_prise : R.drawable.icon_praised);
        ImageLoader.showCircleImage(helper.getView(R.id.ivUserHeader), item.getUser().getHeadImg(), item.getUser().getNewDefaultHeadImage());

        helper.addOnClickListener(R.id.tvUserName);
        helper.addOnClickListener(R.id.ivUserHeader);
        helper.addOnClickListener(R.id.ivOptionOne);
        helper.addOnClickListener(R.id.ivOptionTwo);
        helper.addOnClickListener(R.id.viewOptionOneBg);
        helper.addOnClickListener(R.id.viewOptionTwoBg);
        helper.addOnClickListener(R.id.ivShare);
        helper.addOnClickListener(R.id.ivComment);
        helper.addOnClickListener(R.id.ivPraise);
        helper.addOnClickListener(R.id.ivMusic);
        helper.addOnClickListener(R.id.ivSkip);

        if (item.getAnswerModel() != null) {
            if (item.getAnswerModel().isAnswerOne()) {
                helper.setVisible(R.id.viewAnswerOnePercent, true);
                helper.setBackgroundColor(R.id.viewAnswerOnePercent, Color.parseColor("#CCFFE43F"));
                helper.setVisible(R.id.ivAnswerOneCheck, true);
                helper.setText(R.id.tvAnswerOnePercent, "你已选择");

                helper.setVisible(R.id.viewAnswerTwoPercent, false);
                helper.setGone(R.id.ivAnswerTwoCheck, false);
                helper.setText(R.id.tvAnswerTwoPercent, "");
            } else {
                helper.setVisible(R.id.viewAnswerTwoPercent, true);
                helper.setBackgroundColor(R.id.viewAnswerTwoPercent, Color.parseColor("#CCFFE43F"));
                helper.setVisible(R.id.ivAnswerTwoCheck, true);
                helper.setText(R.id.tvAnswerTwoPercent, "你已选择");

                helper.setVisible(R.id.viewAnswerOnePercent, false);
                helper.setGone(R.id.ivAnswerOneCheck, false);
                helper.setText(R.id.tvAnswerOnePercent, "");
            }
        } else {
            helper.setVisible(R.id.viewAnswerOnePercent, false);
            helper.setVisible(R.id.viewAnswerTwoPercent, false);
        }
    }

    public void SetTextQuestionBgColor(QuestionModel model, View left, View right, TextView leftTextView, TextView rightTextView) {


        if (model.getColorIndex() < 0) {
            model.setColorIndex(random.nextInt(3));
        }

        if (model.getColorIndex() == 1) {
            model.setOptionOneBgColor("#FFC058");
            model.setOptionTwoBgColor("#3DD6D9");
            model.setOptionOneTxtBgColor("#4E3B1B");
            model.setOptionTwoTxtBgColor("#144647");
        } else if (model.getColorIndex() == 2) {
            model.setOptionOneBgColor("#6E96FF");
            model.setOptionTwoBgColor("#DD5C4E");
            model.setOptionOneTxtBgColor("#223056");
            model.setOptionTwoTxtBgColor("#56221C");
        } else { //默认的颜色
            model.setOptionOneBgColor("#C273DE");
            model.setOptionTwoBgColor("#6E96FF");
            model.setOptionOneTxtBgColor("#4A2657");
            model.setOptionTwoTxtBgColor("#223056");
        }

        left.setBackgroundColor(Color.parseColor(model.getOptionOneBgColor()));
        right.setBackgroundColor(Color.parseColor(model.getOptionTwoBgColor()));
        leftTextView.setTextColor(Color.parseColor(model.getOptionOneTxtBgColor()));
        rightTextView.setTextColor(Color.parseColor(model.getOptionTwoTxtBgColor()));
    }
}
