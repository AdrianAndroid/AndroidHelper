package cn.kuwo.common.dialog

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import cn.kuwo.common.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by shihc on 2017/7/17.
 */

class DialogOptionAdapter(data: List<OptionItem>) : BaseQuickAdapter<OptionItem, BaseViewHolder>(R.layout.dialog_base_item, data) {

    override fun convert(helper: BaseViewHolder, item: OptionItem) {
        helper.setText(R.id.tv_option_title, item.title)
        val icon = helper.getView<ImageView>(R.id.iv_option_icon)
        if (item.icon > 0) {
            icon.visibility = View.VISIBLE
            icon.setImageResource(item.icon)
            if (!item.isEnable) {
                icon.setColorFilter(Color.parseColor("#9C9C9C"))
            }
        } else {
            icon.visibility = View.GONE
        }

        if (!item.isEnable) {
            helper.setTextColor(R.id.tv_option_title, Color.parseColor("#9C9C9C"))
        }
    }
}
