package cn.kuwo.pp.ui.system.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.qcloud.uikit.common.component.face.FaceManager;
import com.tencent.qcloud.uikit.common.utils.DateTimeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.systemmessage.SystemMessageBean;

/**
 * bulubulu小助手
 */
public class SystemMessageAdapter extends BaseMultiItemQuickAdapter<SystemMessageBean, BaseViewHolder> {

    public SystemMessageAdapter(Context context, List dataList){
        super(dataList);

        addItemType(SystemMessageBean.SystemMessageCategory.CONTENT_ACTION.ordinal(), R.layout.item_system_message_user_trend); //评论了我的瞬间
        addItemType(SystemMessageBean.SystemMessageCategory.RELATION_SHIP.ordinal(), R.layout.item_system_message_user_favor); //用户喜欢了我
        addItemType(SystemMessageBean.SystemMessageCategory.SYSTEM_PUSH_TEXT.ordinal(), R.layout.item_system_message_push_text); // 字面意思：推送
        addItemType(SystemMessageBean.SystemMessageCategory.SYSTEM_PUSH_IMAGE.ordinal(), R.layout.item_system_message_push_image); // 推送的图片
        addItemType(SystemMessageBean.SystemMessageCategory.USER_SEND_TEXT.ordinal(), R.layout.item_system_message_user_send_text); // 我发送的文字
        addItemType(SystemMessageBean.SystemMessageCategory.USER_SEND_IMAGE.ordinal(), R.layout.item_system_message_user_send_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessageBean item){
        int position = getData().indexOf(item);
        if(position > 1){
            SystemMessageBean lastBean = getItem(position - 1);
            if(lastBean != null){
                if(item.getTimemil() - lastBean.getTimemil() > 5 * 60 * 1000){
                    helper.setText(R.id.message_time, DateTimeUtil.getTimeFormatText(new Date(item.getTimemil())));
                }else{
                    helper.setGone(R.id.message_time, false);
                }
            }
        }else{
            helper.setText(R.id.message_time, DateTimeUtil.getTimeFormatText(new Date(item.getTimemil())));
        }

        if(item.getItemType() == SystemMessageBean.SystemMessageCategory.CONTENT_ACTION.ordinal()){
            showContentAction(helper, item);
        }else if(item.getItemType() == SystemMessageBean.SystemMessageCategory.RELATION_SHIP.ordinal()){
            if(item.getFrom() != null){
                ImageLoader.showCircleImage(helper.getView(R.id.ivFromUserHeader), item.getFrom().getHeadImg(), item.getFrom().getDefaultHeadImage());
                helper.setText(R.id.tvFromUserName, item.getFrom().getName());
            }
        }else if(item.getItemType() == SystemMessageBean.SystemMessageCategory.SYSTEM_PUSH_TEXT.ordinal()){
            helper.addOnClickListener(R.id.ivUserHeader);
            showPushText(helper, item);
        }else if(item.getItemType() == SystemMessageBean.SystemMessageCategory.SYSTEM_PUSH_IMAGE.ordinal()){
            helper.addOnClickListener(R.id.ivUserHeader);
            if(item.getMessageSource() != null && item.getMessageSource().getPic() != null){
                ImageLoader.showRoundedCornersImage(helper.getView(R.id.ivImageContent), item.getMessageSource().getPic(), R.drawable.image_placeholder, 6);
            }else{
                helper.setImageResource(R.id.ivImageContent, R.drawable.image_placeholder);
            }
        }else if(item.getItemType() == SystemMessageBean.SystemMessageCategory.USER_SEND_TEXT.ordinal()) {
            if(item.getFrom() != null){
                ImageLoader.showCircleImage(helper.getView(R.id.ivUserHeader), item.getFrom().getHeadImg(), item.getFrom().getDefaultHeadImage());
            }

            showUserSendText(helper,item);
        }
        else  if(item.getItemType() == SystemMessageBean.SystemMessageCategory.USER_SEND_IMAGE.ordinal()){
            if(item.getFrom() != null){
                ImageLoader.showCircleImage(helper.getView(R.id.ivUserHeader), item.getFrom().getHeadImg(), item.getFrom().getDefaultHeadImage());
            }
            if(item.getUserMessageSource() != null && item.getUserMessageSource().getPic() != null){
                ImageLoader.showRoundedCornersImage(helper.getView(R.id.ivImageContent), item.getUserMessageSource().getPic(), R.drawable.image_placeholder, 6);
            }else{
                helper.setImageResource(R.id.ivImageContent, R.drawable.image_placeholder);
            }
        }
    }

    private void showUserSendText(BaseViewHolder helper, SystemMessageBean item){
        if(item.getUserMessageSource() == null){
            return;
        }

        helper.setTextColor(R.id.tvMessageContent, Color.parseColor("#cc333333"));
        if(item.getUserMessageSource().getType() == 1){
            FaceManager.handlerEmojiText(helper.getView(R.id.tvMessageContent), item.getUserMessageSource().getText());
        }else if(item.getUserMessageSource().getType() == 3){
            helper.setTextColor(R.id.tvMessageContent, Color.parseColor("#FFDF1F"));
            helper.setText(R.id.tvMessageContent, item.getUserMessageSource().getUrl());
        }else{
            TextView textView = helper.getView(R.id.tvMessageContent);
            textView.setText(item.getUserMessageSource().getText());
            SpannableString urlText = new SpannableString(item.getUserMessageSource().getUrl());
            urlText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setColor(Color.parseColor("#FFDF1F"));
                }
            }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.append(urlText);
        }
    }

    private void showPushText(BaseViewHolder helper, SystemMessageBean item){
        if(item.getMessageSource() == null){
            return;
        }

        helper.setTextColor(R.id.tvMessageContent, Color.parseColor("#cc333333"));

        String content = "";
        if(item.getMessageSource().getTitle() != null && !item.getMessageSource().getTitle().isEmpty()){
            content += item.getMessageSource().getTitle()+"\n";
        }
        if(item.getMessageSource().getType() == 2){
            content += item.getMessageSource().getMessage();
            FaceManager.handlerEmojiText(helper.getView(R.id.tvMessageContent), content);
        }else if(item.getMessageSource().getType() == 3){
            TextView textView = helper.getView(R.id.tvMessageContent);
            textView.setText(content);
            SpannableString urlText = new SpannableString(item.getMessageSource().getUrl());
            urlText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setColor(Color.parseColor("#FFDF1F"));
                }
            }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.append(urlText);
        }else if(item.getMessageSource().getType() == 4){
            content += item.getMessageSource().getMessage();
            TextView textView = helper.getView(R.id.tvMessageContent);
            textView.setText(content);
            SpannableString urlText = new SpannableString(item.getMessageSource().getUrl());
            urlText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setColor(Color.parseColor("#FFDF1F"));
                }
            }, 0, urlText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.append(urlText);
        }
    }

    private void showContentAction(BaseViewHolder helper, SystemMessageBean item){
        helper.setVisible(R.id.groupContent, !item.isOffline());
        helper.setVisible(R.id.tvOfflineTip, item.isOffline());

        if(item.isOffline()){
            helper.setText(R.id.tvOfflineTip, "Sorry，您的瞬间因为不可抗力被屏蔽了");
            if(item.getType() == SystemMessageBean.SystemMessageType.COMMENT_OFFLINE.ordinal()){
                helper.setText(R.id.tvOfflineTip, "Sorry，您的评论因为不可抗力被屏蔽了");
            }else if(item.getSource() != null){
                if(item.getSource().getTrendType() != UserTrendBean.TrendType.TREND_USER_PUBLISH){
                    helper.setText(R.id.tvOfflineTip, "Sorry，您的PK因为不可抗力被屏蔽了");
                }
            }
        }

        if(item.getFrom() != null){
            ImageLoader.showCircleImage(helper.getView(R.id.ivFromUserHeader), item.getFrom().getHeadImg(), item.getFrom().getDefaultHeadImage());
            helper.setText(R.id.tvFromUserName, item.getFrom().getName());
        }

        if(item.getType() == SystemMessageBean.SystemMessageType.TREND_COMMENT.ordinal()){
            helper.setText(R.id.tvUserAction, "评论");

            if(item.getComment() != null){
                helper.setVisible(R.id.tvCommentText, true);
                try {
                    helper.setText(R.id.tvCommentText, URLDecoder.decode(item.getComment().getContent(), "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    helper.setText(R.id.tvContent, "");
                }
            }
        }else if(item.getType() == SystemMessageBean.SystemMessageType.PK_VOTE.ordinal()){
            helper.setText(R.id.tvUserAction, "参与");
            helper.setGone(R.id.tvCommentText, false);
        }else{
            helper.setText(R.id.tvUserAction, "喜欢");
            helper.setGone(R.id.tvCommentText, false);
        }

        if(item.getType() == SystemMessageBean.SystemMessageType.TREND_COMMENT.ordinal() || item.getType() == SystemMessageBean.SystemMessageType.TREND_PRISE.ordinal()){
            helper.setText(R.id.tvMyContent, "瞬间");
            if(item.getSource() != null){
                if(item.getSource().getTrendType() != UserTrendBean.TrendType.TREND_USER_PUBLISH){
                    helper.setText(R.id.tvMyContent, "PK");
                }
            }
        }else if(item.getType() == SystemMessageBean.SystemMessageType.COMMENT_PRISE.ordinal()){
            helper.setText(R.id.tvMyContent, "评论");

            if(item.getComment() != null){
                helper.setVisible(R.id.tvCommentText, true);
                try {
                    helper.setText(R.id.tvCommentText, URLDecoder.decode(item.getComment().getContent(), "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    helper.setText(R.id.tvContent, "");
                }
            }
        }else if(item.getType() == SystemMessageBean.SystemMessageType.PK_VOTE.ordinal()){
            helper.setText(R.id.tvMyContent, "PK");
        }

        if(item.getSource() != null){
            helper.setText(R.id.tvTrendText, "\""+item.getSource().getText()+"\"");

            if(item.getSource().getPicIds() != null && item.getSource().getPicIds().size() > 0){
                helper.setVisible(R.id.ivTrendImage, true);
                helper.setVisible(R.id.tvImageCount, true);

                ImageLoader.showRoundedCornersImage(helper.getView(R.id.ivTrendImage), item.getSource().getPicIds().get(0), 0, 6);
                helper.setText(R.id.tvImageCount, "1/"+item.getSource().getPicIds().size());
            }else{
                helper.setGone(R.id.ivTrendImage, false);
                helper.setGone(R.id.tvImageCount, false);
            }
        }
    }
}
