package cn.kuwo.pp.ui.world.adapter;

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
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import cn.kuwo.pp.manager.UserInfoManager;
import cn.kuwo.pp.ui.world.util.TopicPovideStyleData;
import cn.kuwo.pp.util.HeaderRoundImageHelper;

/**
 * HotListFragment
 */
public class HotListAdapter extends BaseMultiItemQuickAdapter<UserTrendBean, BaseViewHolder> {
    public onHotListItemClickListener hotListItemClickListener;

    private Context mContext;

    public HotListAdapter(Context context, List dataList) {
        super(dataList);
        mContext = context;

        // 有问答题的样式
        addItemType(UserTrendBean.TrendType.TREND_IMAGE_QUESTION.ordinal(), R.layout.item_hot_trend_image_question);
        // 有文字的样式
        addItemType(UserTrendBean.TrendType.TREND_TEXT_QUESTION.ordinal(), R.layout.item_hot_trend_text_question);
        // 分享心情的样式
        addItemType(UserTrendBean.TrendType.TREND_USER_PUBLISH.ordinal(), R.layout.item_hot_trend_user_publish);
        // 防止没有值的情况，这个应该再创建的时候就应该舍弃
        addItemType(UserTrendBean.TrendType.TREND_NULL.ordinal(), R.layout.item_hot_trend_user_publish);        //加上这个以防异常
    }

    @Override
    protected void convert(BaseViewHolder helper, UserTrendBean item) {
        try {
            UserInfo user = item.getUser();
            if (user != null) {
                helper.setText(R.id.tvUserName, user.getName());
                HeaderRoundImageHelper.INSTANCE.showCircleImg(helper.getView(R.id.ivUserHeader), user.getHeadImg(), user.getNewDefaultHeadImage(), user.isInviteCodeUser());

                if (UserInfoManager.INSTANCE.isLogin() && UserInfoManager.INSTANCE.getUid().equals(user.getUid())) {
                    helper.setImageResource(R.id.ivFavor, R.drawable.trend_user_isme);
                } else if (FriendListManager.getInstance().isFollowedUser(user.getUid())) {
                    helper.setImageResource(R.id.ivFavor, R.drawable.trend_favored_user);
                } else {
                    helper.setImageResource(R.id.ivFavor, R.drawable.trend_favor_user);
                }

                helper.addOnClickListener(R.id.ivFavor);
                helper.addOnClickListener(R.id.ivUserHeader);
                helper.addOnClickListener(R.id.tvUserName);
            }

            setTitleText(helper.getView(R.id.tvTitle), item);
            helper.setText(R.id.tvPublishTime, UtilsCode.INSTANCE.getFriendlyTimeSpanByNow(item.getTimeMillis()));
            helper.setImageResource(R.id.ivPraise, item.getLiked() == 1 ? R.drawable.icon_praised : R.drawable.icon_praise);
            if (item.getCommentsCount() > 0) {
                helper.setText(R.id.tvCommentCount, "" + item.getCommentsCount());
            } else {
                helper.setText(R.id.tvCommentCount, "");
            }

            helper.addOnClickListener(R.id.ivComment);
            helper.addOnClickListener(R.id.ivShare);
            helper.addOnClickListener(R.id.ivPraise);

            if (item.getTrendType() == UserTrendBean.TrendType.TREND_IMAGE_QUESTION) {
                // 图片投票
                ImageLoader.showImage(helper.getView(R.id.ivOptionOne), item.getOptionOne());
                ImageLoader.showImage(helper.getView(R.id.ivOptionTwo), item.getOptionTwo());

                showAnswer(helper, !item.getPicked().isEmpty());

                helper.addOnClickListener(R.id.ivOptionOne);
                helper.addOnClickListener(R.id.ivOptionTwo);
            } else if (item.getTrendType() == UserTrendBean.TrendType.TREND_TEXT_QUESTION) {
                // 文字投票
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
                // 分享心情
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
            } else if (item.getTrendType() != UserTrendBean.TrendType.TREND_USER_PUBLISH) {
                helper.setGone(R.id.ivAnswerOneCheck, item.getPicked().equalsIgnoreCase(UserTrendBean.ANSWER_ONE));
                helper.setGone(R.id.ivAnswerTwoCheck, item.getPicked().equalsIgnoreCase(UserTrendBean.ANSWER_TWO));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTitleText(RichTextView textView, UserTrendBean bean) {
        if (bean.getText() == null) {
            textView.setContentText("");
            return;
        } else {
            String content = bean.getText();
            StringBuilder sb = new StringBuilder();
            sb.append(content).append("\n");
            if (bean != null && bean.getTopics() != null) {
                for (TopicItemBean topic : bean.getTopics()) {
                    if (topic.getName().isEmpty()) {
                        continue;
                    }
                    sb.append("#").append(topic.getName()).append("#");
                }
            }
            content = sb.toString();

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
                            return 12; // 占了所有
                        case 2:
                        case 4:
                            return 6; // 分开了
                        case 3:
                        case 6:
                        case 9:
                            return 4; // 三列
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

    public interface onHotListItemClickListener {
        void onClickTopic(TopicItemBean topic);

        void onClickImage(List viewList, List dataList, int position);
    }

    private int getMiniPercent(int percent) {
        if (percent < 30) {
            percent = 30;
        }

        return percent;
    }
}
