package cn.kuwo.pp.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import cn.kuwo.common.dialog.BaseBottomDialog
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.common.view.ItemOffsetDecoration
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.elbbbird.android.share.ShareItem
import com.elbbbird.android.socialsdk.Constants
import com.elbbbird.android.socialsdk.SocialSDK
import com.elbbbird.android.socialsdk.model.SocialShareScene
import kotlinx.android.synthetic.main.dialog_share.*

class ShareDialog: BaseBottomDialog() {
    private val APP_NAME = "BuluBulu"
    private val DEFAULT_DESC = "BuluBulu，附近好声音交友，live young,love forever."

    lateinit var  mTargetItem:VoiceInfo

    companion object {
        fun newInstance(info: VoiceInfo): ShareDialog {
            val args = Bundle()
            args.putParcelable("item", info)
            val fragment = ShareDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTargetItem = arguments?.getParcelable("item")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_share, container, false)
        return view
    }

    private val itemClickListener: BaseQuickAdapter.OnItemClickListener =
        BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
//            view.alpha = (0.5f)
            val item: ShareItem = adapter.getItem(position) as ShareItem
            val shareUrl = "http://bulubulu.kuwo.cn/m/bulubulu/index.html?uid=" + mTargetItem.uid
            val scene: SocialShareScene
            when(item.id){
                1 -> { //朋友圈
                    scene = getSocialShareScene(
                        shareUrl,
                        SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE
                    )
                    SocialSDK.getInstance().shareToWeChatTimeline(
                        activity,
                        Constants.Weixin.APP_ID,
                        scene
                    )
                }
                2 -> { //微信
                    scene = getSocialShareScene(shareUrl, SocialShareScene.SHARE_TYPE_WECHAT)
                    SocialSDK.getInstance().shareToWeChat(activity, Constants.Weixin.APP_ID, scene)
                }
                3 -> { //QQ好友
                    scene = getSocialShareScene(shareUrl, SocialShareScene.SHARE_TYPE_QQ)
                    SocialSDK.getInstance().shareToQQ(activity, Constants.QQ.APP_ID, scene)
                }
                4 -> { //QQ空间
                    scene = getSocialShareScene(shareUrl, SocialShareScene.SHARE_TYPE_QZONE)
                    SocialSDK.getInstance().shareToQZone(activity, Constants.QQ.APP_ID, scene)
                }
                5 -> { //微博
                    scene = getSocialShareScene(shareUrl, SocialShareScene.SHARE_TYPE_WEIBO)
                    SocialSDK.getInstance().shareToWeibo(activity, Constants.Weibo.APP_KEY, scene)
                }
            }
            dismiss()
        }

    private fun getSocialShareScene(shareUrl: String, shareType: Int): SocialShareScene {
        val title:String
        if("2".equals(mTargetItem.sex)){
            title  = "发现附近${mTargetItem.card}小姐姐"
        }else{
            title  = "发现附近${mTargetItem.card}小哥哥"
        }
        val scene: SocialShareScene
        scene = SocialShareScene(
            mTargetItem.uid,
            shareType,
            title,
            DEFAULT_DESC,
            mTargetItem.headImg,
            shareUrl
        )
        return scene
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_cancel.setOnClickListener {
            dismiss()
        }
        rv_grids.layoutManager =
            GridLayoutManager(context, 4)
        rv_grids.addItemDecoration(ItemOffsetDecoration(UtilsCode.dp2px(10f)))
        val adapter = ShareAdapter(buildShareList())
        adapter.setOnItemClickListener(itemClickListener)
        rv_grids.adapter = adapter
    }

    fun buildShareList():List<ShareItem>{
        val list = ArrayList<ShareItem>()
        val item1 = ShareItem(1, R.drawable.layer_share_moments, "朋友圈")
        val item2 = ShareItem(2, R.drawable.layer_share_wechat, "微信")
        val item3 = ShareItem(3, R.drawable.layer_share_qq, "QQ好友")
        val item4 = ShareItem(4, R.drawable.share_qzone, "QQ空间")
//        val item5 = ShareItem(5,R.drawable.share_sina,"新浪")
        list.add(item1)
        list.add(item2)
        list.add(item3)
        list.add(item4)
//        list.add(item5)
        return list
    }
}