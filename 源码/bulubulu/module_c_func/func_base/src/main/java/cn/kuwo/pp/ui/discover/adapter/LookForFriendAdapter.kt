package cn.kuwo.pp.ui.discover.adapter

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.pp.util.WaterImageView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class LookForFriendAdapter(data: MutableList<VoiceInfo>?) :
    BaseQuickAdapter<VoiceInfo, BaseViewHolder>(R.layout.item_look_for_friend, data) {

    val blueColor = Color.parseColor("#55C0FF")
    val greenColor = Color.parseColor("#38DBB7")
    val redColor = Color.parseColor("#FF555C")

    override fun convert(helper: BaseViewHolder?, item: VoiceInfo?) {
        if ("2".equals(item?.sex)) {
            ImageLoader.showImage(helper?.getView(R.id.acv), item?.headImg, R.drawable.default_header_pic_famale_round)
        } else {
            ImageLoader.showImage(helper?.getView(R.id.acv), item?.headImg, R.drawable.default_header_pic_male_round)
        }
        val animView: LottieAnimationView? = helper?.getView(R.id.anim_circle)
        animView?.let {
            var filter: SimpleColorFilter? = null
            when (item?.card) {
                "阳光音", "初恋音", "软甜音", "傲娇音" -> {  //绿色
                    filter = SimpleColorFilter(greenColor)
                }
                "总裁音", "公子音", "仙姬音", "诱惑音" -> {
                    filter = SimpleColorFilter(redColor)
                }
                "深情音", "儒雅音", "知性音", "幽韵音" -> {
                    filter = SimpleColorFilter(blueColor)
                }
            }
            filter?.let {
                val keyPath = KeyPath("**")
                val callback = LottieValueCallback<ColorFilter>(filter)
                animView.clearColorFilter()
                animView.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
            }
        }
        helper?.addOnClickListener(R.id.acv)

    }

    fun getLastItem(): VoiceInfo? {
        if (itemCount > 0) {
            return getItem(itemCount - 1)
        }
        return null
    }

    fun getFirstItem(): VoiceInfo? {
        if (itemCount > 0) {
            return getItem(0)
        }
        return null
    }

    fun getDataCount():Int{
        if(mData!=null){
            return mData.size
        }
        return 0
    }

    fun clearAll() {
        data?.clear()
    }

    fun getFirstItemDrawable(): Drawable? {
        val waterView: WaterImageView? = getViewByPosition(0, R.id.acv) as WaterImageView?
        return waterView?.drawable
    }

    fun updatePlayState(played: Boolean) {
        val view = getViewByPosition(0, R.id.item_look_for_play_state)
        val waterView: WaterImageView? = getViewByPosition(0, R.id.acv) as WaterImageView?
        waterView?.markColor = mContext?.resources?.getColor(R.color.colorMask)!!
        waterView?.isHasMask = !played

        view.let {
            if (played) {
                it?.visibility = View.GONE
            } else {
                it?.visibility = View.VISIBLE
            }
        }
    }
}