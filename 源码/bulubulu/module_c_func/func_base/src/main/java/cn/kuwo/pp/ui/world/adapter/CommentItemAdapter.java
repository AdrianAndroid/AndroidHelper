package cn.kuwo.pp.ui.world.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.bean.comment.CommentItem;

public class CommentItemAdapter extends BaseQuickAdapter<CommentItem, BaseViewHolder> {

    public CommentItemAdapter(@Nullable List<CommentItem> data){
        super(R.layout.item_commnet,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentItem item){
        helper.addOnClickListener(R.id.ivUserHeader);
        helper.addOnClickListener(R.id.ivPraise);

        helper.setImageResource(R.id.ivPraise, item.getLiked() == 1 ? R.drawable.icon_praised : R.drawable.icon_praise);
        helper.setText(R.id.tvCommentTime, UtilsCode.INSTANCE.getFriendlyTimeSpanByNow(item.getTimes()));

        if(item.getUserInfo() != null){
            ImageLoader.showCircleImage(helper.getView(R.id.ivUserHeader), item.getUserInfo().getHeadImg(), item.getUserInfo().getNewDefaultHeadImage());
            helper.setText(R.id.tvUserName, item.getUserInfo().getName());
        }

        try {
            helper.setText(R.id.tvContent, URLDecoder.decode(item.getContent(), "UTF-8"));
        }catch (UnsupportedEncodingException e){
            helper.setText(R.id.tvContent, "");
        }
    }
}
