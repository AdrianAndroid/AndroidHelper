package com.elbbbird.android.share

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.elbbbird.android.socialsdk.R

class ShareItemAdapter(data: List<ShareItem>) :
    BaseQuickAdapter<ShareItem, BaseViewHolder>(R.layout.item_share_2, data) {


    override fun convert(helper: BaseViewHolder?, item: ShareItem?) {
        if (helper == null || item == null) return;
        helper.setImageResource(R.id.item_share_icon, item.iconResId)
        helper.setText(R.id.item_share_title, item.title)
    }


}