package cn.kuwo.pp.ui.friend.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import cn.kuwo.common.app.App
import cn.kuwo.common.util.DateFormatUtils
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.common.util.L
import cn.kuwo.pp.BuildConfig
import cn.kuwo.pp.R
import cn.kuwo.pp.manager.FriendList.FriendListItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_find_friend.view.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView
import java.text.SimpleDateFormat
import java.util.*


class FriendListAdapter(data: List<FriendListItem>?) :
    BaseQuickAdapter<FriendListItem, BaseViewHolder>(R.layout.fragment_friend_list_item, data) {
    override fun convert(helper: BaseViewHolder, item: FriendListItem) {

        if (item.systemMessage) {
            // 系统消息
            helper.setTextColor(R.id.tvUserName, Color.parseColor("#FFE43F"))
            helper.setText(R.id.tvUserName, "布鲁小助手")
            helper.setVisible(R.id.ivSystemIcon, true)
            val ivUserImage = helper.getView<ImageView>(R.id.ivUserImage)
            ivUserImage.setImageResource(R.drawable.system_header)
        } else {
            // 普通用户
            helper.setTextColor(R.id.tvUserName, Color.parseColor("#FFFFFF"))
            helper.setText(R.id.tvUserName, item.user.name)
            helper.setGone(R.id.ivSystemIcon, false)
            ImageLoader.showCircleImage(
                helper.getView<ImageView>(R.id.ivUserImage),
                item.user.headImg,
                getDefaultImage(item)
            )
        }

        helper.addOnClickListener(R.id.voice_play_btn)

        if (App.DEBUG) L.m(
            javaClass,
            "lastMessage>>",
            item.lastMessage,
            item.lastMessageTime,
            item.user.name
        )
        // 信息和时间分开显示
        if (item.lastMessage.isNullOrEmpty()) {
            helper.setVisible(R.id.tvLastMsg, false)
        } else {
            helper.setVisible(R.id.tvLastMsg, true)
            helper.setText(R.id.tvLastMsg, item.lastMessage)
        }
        // 时间 1608024590
        if (item.lastMessageTime > 1600000000) {
            helper.setVisible(R.id.tvMsgTime, true)
            helper.setText(R.id.tvMsgTime, getTimeText(item.lastMessageTime))
        } else {
            helper.setVisible(R.id.tvMsgTime, false)
        }

        if (item.unReadMsgNum > 0) {
            var badge = getBadge(helper)
            if (item.unReadMsgNum < 100) {
                badge.setBadgeNumber(item.unReadMsgNum.toInt())
            } else {
                badge.setBadgeText("99+")
            }
            helper.setVisible(R.id.tvUnReadNum, true)
        } else {
            helper.setVisible(R.id.tvUnReadNum, false)
            var badge = getBadge(helper)
            badge.hide(false)
        }

    }

    private fun getBadge(helper: BaseViewHolder): Badge {
        var badge: Badge
        val view = helper.getView<View>(R.id.tvUnReadNum)
        if (view.tag != null) {
            badge = view.tag as Badge
        } else {
            badge = QBadgeView(this.mContext).bindTarget(view)
            badge.badgeBackgroundColor = 0xffffdf1f.toInt()
            badge.badgeTextColor = 0xff000000.toInt()
            view.setTag(badge)
        }
        return badge
    }


    private fun getDefaultImage(item: FriendListItem): Int {
        if (item.user.sex == "2") {
            return R.drawable.default_header_girl
        } else {
            return R.drawable.default_header_boy
        }
    }


//    companion object {
//        private val SECONDS = 1
//        private val MINUTES = 60 * SECONDS
//        private val HOURS = 60 * MINUTES
//        private val DAYS = 24 * HOURS
//        private val WEEKS = 7 * DAYS
//        private val MONTHS = 4 * WEEKS
//        private val YEARS = 12 * MONTHS
//
//        private val fHM = SimpleDateFormat("HH:mm")
//        private val fMD = SimpleDateFormat("MM月dd日")
//        private val fYMD = SimpleDateFormat("yyyy年MM月dd日")
//
//        init {
//            fHM.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//            fMD.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//            fYMD.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        }
//    }

    private fun getTimeText(time: Long): String {
        val nowSeconds = (Calendar.getInstance().getTimeInMillis() / 1000) as Long
        val timeDifference = nowSeconds - time
        //val dt = Date(time * 1000)
        if (timeDifference < DateFormatUtils.DAYS) {
            return DateFormatUtils.formatHHmm(time * 1000)//  fHM.format(dt)
        } else if (timeDifference < DateFormatUtils.DAYS * 2) {
            return "昨天"
        } else if (timeDifference < DateFormatUtils.DAYS * 3) {
            return "前天"
        } else if (timeDifference < DateFormatUtils.YEARS) {
            return DateFormatUtils.formatMM_dd(time * 1000) //fMD.format(dt)
        } else {
            return DateFormatUtils.formatyyyy_MM_dd(time * 1000) // fYMD.format(dt)
        }
    }
}