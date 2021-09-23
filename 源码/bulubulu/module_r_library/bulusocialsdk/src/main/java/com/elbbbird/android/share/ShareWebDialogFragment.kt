package com.elbbbird.android.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kuwo.common.dialog.BaseBottomDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import com.elbbbird.android.socialsdk.Constants
import com.elbbbird.android.socialsdk.R
import com.elbbbird.android.socialsdk.SocialSDK
import com.elbbbird.android.socialsdk.model.SocialShareScene
import com.flannery.kuwowebview.KwWebView
import kotlinx.android.synthetic.main.dialog_share_web.*

/**
 *  分享WebView
 */
class ShareWebDialogFragment(val webview: KwWebView, val shareId: String, val topicTitle: String) :
    BaseBottomDialog() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.dialog_share_web, container, false)
        setVerticalDragListener(inflate)
        return inflate
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPicRecyclerView()
        initLinkRecyclerView()
    }


    // 分享图片的
    private fun initPicRecyclerView() {
        val data = listOf<ShareItem>(
            ShareItem(SocialShareScene.SHARE_TYPE_PIC, R.drawable.layer_share_pic_, "生成图片"),
            ShareItem(
                SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE,
                R.drawable.layer_share_moments,
                "朋友圈"
            ),
            ShareItem(SocialShareScene.SHARE_TYPE_WECHAT, R.drawable.layer_share_wechat, "微信好友"),
            ShareItem(SocialShareScene.SHARE_TYPE_QQ, R.drawable.layer_share_qq, "QQ好友"),
            ShareItem(SocialShareScene.SHARE_TYPE_QZONE, R.drawable.share_qzone, "QQ空间")
        )
        rvPic.adapter = ShareItemAdapter(data).apply {
            onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                    AnalyticsUtils.onEvent(
                        view.context,
                        UmengEventId.CHANLLEGE_RESULT_PIC,
                        ShareWebDialogFragment::class.java.simpleName
                    )
                    when (val shareType = (adapter.getItem(position) as? ShareItem)?.id) {
                        SocialShareScene.SHARE_TYPE_PIC -> {
                            // 生成图片
                            ShareUtil.saveCaptureWebView(webview, true) // 生成本地图片
                        }
                        SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE -> {
                            // 朋友圈
                            SocialSDK.getInstance().shareToWeChatTimeline(
                                context,
                                Constants.Weixin.APP_ID,
                                generateSocailShareScene(
                                    shareType,
                                    ShareUtil.saveCaptureWebView(webview, false)
                                )
                            )
                        }
                        SocialShareScene.SHARE_TYPE_WECHAT -> {
                            // 微信
                            SocialSDK.getInstance().shareToWeChat(
                                context,
                                Constants.Weixin.APP_ID,
                                generateSocailShareScene(
                                    shareType,
                                    ShareUtil.saveCaptureWebView(webview, false)
                                )
                            )
                        }
                        SocialShareScene.SHARE_TYPE_QQ -> {
                            // QQ
                            SocialSDK.getInstance().shareToQQ(
                                context,
                                Constants.QQ.APP_ID,
                                generateSocailShareScene(
                                    shareType,
                                    ShareUtil.saveCaptureWebView(webview, false)
                                )
                            )
                        }
                        SocialShareScene.SHARE_TYPE_QZONE -> {
                            // QZONE
                            SocialSDK.getInstance().shareToQZone(
                                context,
                                Constants.QQ.APP_ID,
                                generateSocailShareScene(
                                    shareType,
                                    ShareUtil.saveCaptureWebView(webview, false)
                                )
                            )
                        }
                    }
                    dismiss()
                }
        }
    }

    // 分享链接的
    private fun initLinkRecyclerView() {
        val data = listOf<ShareItem>(
            ShareItem(
                SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE,
                R.drawable.layer_share_moments,
                "朋友圈"
            ),
            ShareItem(SocialShareScene.SHARE_TYPE_WECHAT, R.drawable.layer_share_wechat, "微信好友"),
            ShareItem(SocialShareScene.SHARE_TYPE_QQ, R.drawable.layer_share_qq, "QQ好友")
//            , ShareItem(SocialShareScene.SHARE_TYPE_QZONE, R.drawable.share_qzone, "QQ空间")
        )
        rvLink.adapter = ShareItemAdapter(data).apply {
            onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                    AnalyticsUtils.onEvent(
                        view.context,
                        UmengEventId.CHANLLEGE_RESULT_LINK,
                        ShareWebDialogFragment::class.java.simpleName
                    )
                    when (val shareType = (adapter.getItem(position) as? ShareItem)?.id) {
                        SocialShareScene.SHARE_TYPE_WECHAT_TIMELINE -> {
                            // 朋友圈
                            SocialSDK.getInstance().shareToWeChatTimeline(
                                context,
                                Constants.Weixin.APP_ID,
                                generateSocialShareScene(shareType)
                            )
                        }
                        SocialShareScene.SHARE_TYPE_WECHAT -> {
                            // 微信
                            SocialSDK.getInstance().shareToWeChat(
                                context,
                                Constants.Weixin.APP_ID,
                                generateSocialShareScene(shareType)
                            )
                        }
                        SocialShareScene.SHARE_TYPE_QQ -> {
                            // QQ
                            SocialSDK.getInstance().shareToQQ(
                                context,
                                Constants.QQ.APP_ID,
                                generateSocialShareScene(shareType)
                            )
                        }
                        SocialShareScene.SHARE_TYPE_QZONE -> {
                            // QZONE
                            SocialSDK.getInstance().shareToQZone(
                                context,
                                Constants.QQ.APP_ID,
                                generateSocialShareScene(shareType)
                            )
                        }
                    }
                    dismiss()
                }
        }
    }

    // 生成带链接的Sceene
    private fun generateSocialShareScene(shareType: Int): SocialShareScene {
        return SocialShareScene(
            shareId,
            shareType,
            "没想到,原来你是这种人?!",
            "#$topicTitle#入圈挑战大公开~测测你是...?",
            "",
            "https://h5app.kuwo.cn/m/meetChallenge/index.html?topicid=$shareId"
        );
    }

    private fun generateSocailShareScene(
        shareType: Int,
        shareBitmapPath: String
    ): SocialShareScene {
        return SocialShareScene(
            shareId,
            shareType,
            shareBitmapPath,
            "入圈挑战"
        )
    }


}