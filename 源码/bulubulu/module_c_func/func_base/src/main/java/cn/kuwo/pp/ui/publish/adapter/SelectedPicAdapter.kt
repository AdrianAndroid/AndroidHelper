package cn.kuwo.pp.ui.publish.adapter;

import cn.kuwo.common.util.ImageLoader
import cn.kuwo.pp.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class SelectedPicAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.fragment_select_picture_item, data) {

    override fun getItemCount(): Int {
        return Math.min(mData.size, MAX_SELECT_COUNT)
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        if (mData.size <= MAX_SELECT_COUNT && helper.adapterPosition == mData.size - 1) {
            helper.setImageResource(R.id.image, R.drawable.select_pic_add_icon)
            helper.setGone(R.id.iv_delete, false)
        } else {
            ImageLoader.showImage(helper.getView(R.id.image), item)
            helper.setGone(R.id.iv_delete, true)
        }
        helper.addOnClickListener(R.id.iv_delete)
    }
    companion object{
        public const val MAX_SELECT_COUNT = 9
    }
}