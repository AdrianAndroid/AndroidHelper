package cn.kuwo.pp.ui.share;

import androidx.annotation.Nullable;
import cn.kuwo.pp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elbbbird.android.share.ShareItem;

import java.util.List;

public class ShareAdapter extends BaseQuickAdapter<ShareItem, BaseViewHolder> {
    public ShareAdapter(@Nullable List<ShareItem> data) {
        super(R.layout.item_share,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareItem item) {
        helper.setImageResource(R.id.item_share_icon,item.getIconResId());
        helper.setText(R.id.item_share_title,item.getTitle());
    }
}
