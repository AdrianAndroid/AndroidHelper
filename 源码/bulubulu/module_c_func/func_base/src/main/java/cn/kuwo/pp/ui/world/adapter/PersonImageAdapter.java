package cn.kuwo.pp.ui.world.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.kuwo.common.util.ImageLoader;
import cn.kuwo.pp.R;

public class PersonImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PersonImageAdapter(List<String> data){
        super(R.layout.personal_card_image_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item){
        ImageLoader.showRoundedCornersImage(helper.getView(R.id.iv_card_image), item, R.drawable.image_rounded_placeholder, 2);
    }

}
