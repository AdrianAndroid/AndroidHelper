package cn.kuwo.pp.ui.mine.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.koudai.styletextview.BaseRichTextStyle;
import com.koudai.styletextview.RichTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.world.adapter.HotListAdapter;
import cn.kuwo.pp.ui.world.adapter.PersonImageAdapter;
import cn.kuwo.pp.ui.world.util.TopicPovideStyleData;
import cn.kuwo.pp.util.AgeTagTextView;
import cn.kuwo.pp.util.HeaderRoundImageHelper;

public class UserInfoAdapter extends BaseMultiItemQuickAdapter<UserTrendBean, BaseViewHolder> {
    private Context mContext;
    private VoiceInfo mVoiceInfo;
    private boolean mIsPlaying = false;
    private boolean mIsMe = false;
    private int mMatchValue = 0;
    private boolean mIsOnline = false;

    public HotListAdapter.onHotListItemClickListener hotListItemClickListener;

    public UserInfoAdapter(Context context, List dataList, VoiceInfo voiceInfo, boolean isMe) {
        super(dataList);
        mContext = context;
        mVoiceInfo = voiceInfo;
        mIsMe = isMe;

        addItemType(UserTrendBean.TrendType.TREND_NULL.ordinal(), R.layout.item_user_info_header); // 头像
        addItemType(UserTrendBean.TrendType.TREND_EMPTY.ordinal(), R.layout.item_user_info_empty);
        addItemType(UserTrendBean.TrendType.TREND_IMAGE_QUESTION.ordinal(), R.layout.item_user_info_image_question);
        addItemType(UserTrendBean.TrendType.TREND_TEXT_QUESTION.ordinal(), R.layout.item_user_info_text_question);
        addItemType(UserTrendBean.TrendType.TREND_USER_PUBLISH.ordinal(), R.layout.item_user_info_user_publish);
    }

    public void setVoiceInfo(VoiceInfo info) {
        mVoiceInfo = info;
        notifyItemChanged(0);
    }

    public void setPlayState(boolean playing) {
        mIsPlaying = playing;
        notifyItemChanged(0);
    }

    public void setMatchValue(int value) {
        mMatchValue = value;
        notifyItemChanged(0);
    }

    public void setOnline() {
        mIsOnline = true;
        notifyItemChanged(0);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserTrendBean item) {
        if (item.getTrendType() == UserTrendBean.TrendType.TREND_NULL) {
            UserInfo userInfo = item.getUser();
            helper.setText(R.id.tvUserName, userInfo.getName());
            boolean inviteCodeUser;
            if (mIsMe)
                inviteCodeUser = UserInfoManager.INSTANCE.isInviteCodeUser();
            else
                inviteCodeUser = userInfo.isInviteCodeUser();
            HeaderRoundImageHelper.INSTANCE.showCircleImg(
                    helper.getView(R.id.anim_circle),
                    userInfo.getHeadImg(),
                    userInfo.getNewDefaultHeadImage(),
                    inviteCodeUser
            );

            AgeTagTextView tvAgeLabel = helper.getView(R.id.tvAgeLabel);
            //tvAgeLabel.setViewInfo(userInfo.getSex() + "", userInfo.getAge());
            tvAgeLabel.setSexAndAge(userInfo.getSex() + "", userInfo.getAge());

            if (mIsMe) {
                helper.setVisible(R.id.layout_match_value, false);
                helper.setVisible(R.id.btnIntegral, true);
                helper.setText(R.id.btnIntegral, item.getUser().getIntegral() + "");
            } else {
                helper.setVisible(R.id.layout_match_value, true);
                helper.setVisible(R.id.btnIntegral, false);

                View viewMatchBg = helper.getView(R.id.view_match_bg);
                View viewMatchValue = helper.getView(R.id.view_match_progress);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewMatchValue.getLayoutParams();
                layoutParams.width = (int) ((float) viewMatchBg.getLayoutParams().width * mMatchValue / 100.0);
                viewMatchValue.setLayoutParams(layoutParams);
                helper.setVisible(R.id.view_match_progress, mMatchValue > 0);
                helper.setText(R.id.tvMatchValue, "匹配度 " + mMatchValue + "%");

                helper.setVisible(R.id.tvUserOnline, mIsOnline);
            }

            String location = "bulu星球";
            if (userInfo.getCity() != null && !userInfo.getCity().isEmpty()) {
                location = userInfo.getCity();
            }
            helper.setText(R.id.tvLocationLabel, location);
            // helper.addOnClickListener(R.id.tvMyVoice);
            helper.addOnClickListener(R.id.anim_circle);

            TextView tvConstellationLabel = helper.getView(R.id.tvConstellationLabel);
            if (userInfo.getBirthday() == null || userInfo.getBirthday().length() == 0) {
                tvConstellationLabel.setVisibility(View.GONE);
            } else {
                tvConstellationLabel.setVisibility(View.VISIBLE);
                tvConstellationLabel.setText(UtilsCode.INSTANCE.getZodiac(userInfo.getBirthdayDate()));
            }
        } else if (item.getTrendType() == UserTrendBean.TrendType.TREND_EMPTY) {
            helper.setGone(R.id.btn_make_trend, mIsMe);
            helper.addOnClickListener(R.id.btn_make_trend);
        } else {
            helper.addOnClickListener(R.id.ivComment);
            helper.addOnClickListener(R.id.ivShare);
            helper.addOnClickListener(R.id.ivPraise);

            helper.addOnClickListener(R.id.ivDelete);
            helper.setVisible(R.id.ivDelete, mIsMe);

            ConstraintLayout layout = helper.getView(R.id.layoutItemBg);
            if (mData.size() == 2) {
                layout.setBackgroundResource(R.drawable.mine_list_item_bg_corner_18);
            } else if (mData.indexOf(item) == 1) {
                layout.setBackgroundResource(R.drawable.mine_list_item_bg_top);
            } else if (mData.indexOf(item) == mData.size() - 1) {
                layout.setBackgroundResource(R.drawable.mine_list_item_bg_bottom);
            } else {
                layout.setBackgroundColor(Color.parseColor("#37393A"));
            }
            helper.setVisible(R.id.viewTopLine, mData.indexOf(item) != 1);

            setTitleText(helper.getView(R.id.tvTitle), item);
            helper.setText(R.id.tvPublishTime, UtilsCode.INSTANCE.getFriendlyTimeSpanByNow(item.getTimeMillis()));
            helper.setImageResource(R.id.ivPraise, item.getLiked() == 1 ? R.drawable.icon_praised : R.drawable.icon_praise);
            if (item.getCommentsCount() > 0) {
                helper.setText(R.id.tvCommentCount, "" + item.getCommentsCount());
            } else {
                helper.setText(R.id.tvCommentCount, "");
            }

            if (item.getTrendType() == UserTrendBean.TrendType.TREND_IMAGE_QUESTION) {
                ImageLoader.showImage(helper.getView(R.id.ivOptionOne), item.getOptionOne());
                ImageLoader.showImage(helper.getView(R.id.ivOptionTwo), item.getOptionTwo());

                showAnswer(helper, !item.getPicked().isEmpty());
                helper.addOnClickListener(R.id.ivOptionOne);
                helper.addOnClickListener(R.id.ivOptionTwo);
            } else if (item.getTrendType() == UserTrendBean.TrendType.TREND_TEXT_QUESTION) {
                SetTextQuestionBgColor(item, helper.getView(R.id.layoutContentOne), helper.getView(R.id.layoutContentTwo), helper.getView(R.id.tvOptionOne), helper.getView(R.id.tvOptionTwo));

                helper.setText(R.id.tvOptionOne, item.getOptionOne());
                helper.setText(R.id.tvOptionTwo, item.getOptionTwo());

                TextView tvOption1 = helper.getView(R.id.tvOptionOne);
                tvOption1.setTextSize(item.getOptionOne().length() > 3 ? 20 : 30);
                TextView tvOption2 = helper.getView(R.id.tvOptionTwo);
                tvOption2.setTextSize(item.getOptionOne().length() > 3 ? 20 : 30);

                showAnswer(helper, !item.getPicked().isEmpty());
                helper.addOnClickListener(R.id.viewOptionOneBg);
                helper.addOnClickListener(R.id.viewOptionTwoBg);

            } else if (item.getTrendType() == UserTrendBean.TrendType.TREND_USER_PUBLISH) {
                setUserPictureList(helper, item);
            }

            if (!item.getPicked().isEmpty()) {
                helper.setGone(R.id.ivAnswerOneCheck, item.getPicked().equalsIgnoreCase(UserTrendBean.ANSWER_ONE));
                helper.setGone(R.id.ivAnswerTwoCheck, item.getPicked().equalsIgnoreCase(UserTrendBean.ANSWER_TWO));

                View viewAnswerOnePercent = helper.getView(R.id.viewAnswerOnePercent);
                viewAnswerOnePercent.setBackgroundColor(item.toQuestionModel().getAnswerModel().isAnswerOne() ? Color.parseColor("#E5FFE43F") : Color.parseColor("#CCE0E0E0"));
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewAnswerOnePercent.getLayoutParams();
                layoutParams.matchConstraintPercentHeight = ((float) getMiniPercent(item.getOptionOnePer()) / (float) 100.0);
                viewAnswerOnePercent.setLayoutParams(layoutParams);

                View viewAnswerTwoPercent = helper.getView(R.id.viewAnswerTwoPercent);
                viewAnswerTwoPercent.setBackgroundColor(item.toQuestionModel().getAnswerModel().isAnswerOne() ? Color.parseColor("#CCE0E0E0") : Color.parseColor("#E5FFE43F"));
                layoutParams = (ConstraintLayout.LayoutParams) viewAnswerTwoPercent.getLayoutParams();
                layoutParams.matchConstraintPercentHeight = ((float) getMiniPercent(item.getOptionTwoPer()) / (float) 100.0);
                viewAnswerTwoPercent.setLayoutParams(layoutParams);

                helper.setText(R.id.tvAnswerOnePercent, item.getOptionOnePer() + "%");
                helper.setText(R.id.tvAnswerTwoPercent, item.getOptionTwoPer() + "%");
            }
        }
    }

    private void showAnswer(BaseViewHolder helper, boolean show) {
        helper.setVisible(R.id.viewAnswerOnePercent, show);
        helper.setVisible(R.id.viewAnswerTwoPercent, show);
    }

    private void setUserPictureList(BaseViewHolder helper, UserTrendBean item) {

        RecyclerView rvUserImageList = helper.getView(R.id.rv_image);
        if (item.getPicIds() == null) {
            rvUserImageList.setVisibility(View.GONE);
        } else {
            PersonImageAdapter adapter = new PersonImageAdapter(item.getPicIds());
            adapter.bindToRecyclerView(rvUserImageList);
            rvUserImageList.setAdapter(adapter);

            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 12);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (item.getPicIds().size()) {
                        case 1:
                            return 12;
                        case 2:
                        case 4:
                            return 6;
                        case 3:
                        case 6:
                        case 9:
                            return 4;
                        case 5: {
                            if (position < 2) {
                                return 6;
                            } else {
                                return 4;
                            }
                        }
                        case 7: {
                            if (position < 3) {
                                return 4;
                            } else {
                                return 3;
                            }
                        }
                        case 8:
                            return 3;
                    }

                    return 4;
                }
            });

            rvUserImageList.setLayoutManager(layoutManager);

            if (hotListItemClickListener != null) {
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ArrayList viewList = new ArrayList();
                        for (int i = 0; i < adapter.getData().size(); i++) {
                            View imageView = adapter.getViewByPosition(i, R.id.iv_card_image);
                            if (imageView != null && imageView instanceof ImageView) {
                                viewList.add(imageView);
                            }
                        }

                        if (hotListItemClickListener != null) {
                            hotListItemClickListener.onClickImage(viewList, item.getPicIds(), position);
                        }
                    }
                });
            }
        }
    }

    public interface onUserImageClickListener {
        void onClickImage(List viewList, List dataList, int position);
    }

    private void SetTextQuestionBgColor(UserTrendBean model, View left, View right, TextView leftTextView, TextView rightTextView) {
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

    private void setTitleText(RichTextView textView, UserTrendBean bean) {
        if (bean == null) return;
        if (bean.getText() == null) {
            textView.setContentText("");
            return;
        } else {
            String content = bean.getText();
            if (bean.getTopics() != null) {
                for (TopicItemBean topic : bean.getTopics()) {
                    if (topic.getName().isEmpty()) {
                        continue;
                    }
                    content += "#" + topic.getName() + "#";
                }
            }

            textView.setContentText(content);
            textView.addRichTextStyle(new TopicPovideStyleData());
            textView.setRichTextStyle();
            textView.showText();
            textView.setOnTagContentClickListenter(new BaseRichTextStyle.OnTagContentClickListenter() {
                @Override
                public void onClick(int style, String text) {
                    if (bean == null || bean.getTopics() == null) return;
                    for (TopicItemBean topic : bean.getTopics()) {
                        if (topic.getName().isEmpty()) {
                            continue;
                        }
                        String topicName = "#" + topic.getName() + "#";
                        if (topicName.equalsIgnoreCase(text)) {
                            if (hotListItemClickListener != null) {
                                hotListItemClickListener.onClickTopic(topic);
                            }
                            break;
                        }
                    }
                }
            });
        }
    }
}
