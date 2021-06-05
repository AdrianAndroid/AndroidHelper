package cn.kuwo.pp.ui.record;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import cn.kuwo.pp.R;
import java.util.List;

import cn.kuwo.pp.http.bean.GuideBean;

public class RecordGuideAdapter extends BaseQuickAdapter<GuideBean,BaseViewHolder> {
    public RecordGuideAdapter(@Nullable List<GuideBean> data) {
        super(R.layout.item_record_guide,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuideBean item) {
        helper.setText(R.id.item_guide_title,item.getTitle());
        helper.setText(R.id.item_guide_content,item.getText());
    }
}
