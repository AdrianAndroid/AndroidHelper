package cn.kuwo.pp.ui.discover.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import cn.kuwo.common.app.App
import cn.kuwo.common.util.ImageLoader
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.common.view.UseHeadImageLayout
import cn.kuwo.func_base.utils.PlayBgMusic
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.QuestionModel
import cn.kuwo.pp.http.bean.comment.CommentItem
import cn.kuwo.pp.http.bean.user.UserInfo
import cn.kuwo.pp.http.bean.voice.VoiceInfo
import cn.kuwo.pp.ui.discover.FriendTestFragment
import cn.kuwo.pp.util.danmuku.DanMuView
import cn.kuwo.pp.util.danmuku.model.DanMuModel
import cn.kuwo.pp.view.OptionView
import cn.kuwo.pp.view.ParallelPKView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*
import kotlin.collections.ArrayList

/**
 * 主页选择逻辑
 */
class FriendTestListAdapter2(val fragment: FriendTestFragment, data: MutableList<QuestionModel>?) :
    BaseQuickAdapter<QuestionModel, BaseViewHolder>(R.layout.item_friend_test_2, data) {

    // 有一个随机背景

    val random = Random();

    override fun convert(helper: BaseViewHolder?, item: QuestionModel?) {
        // 显示必要的逻辑
        if (item == null || helper == null) return
        // 标题
        helper.setText(R.id.tvTitle, item.question)

        setOptions(
            helper.getView<OptionView>(R.id.optionOne),
            helper.getView<OptionView>(R.id.optionTwo),
            item
        )
        // 点击事件
        helper.addOnClickListener(R.id.optionOne)
        helper.addOnClickListener(R.id.optionTwo)


        // 最底部的那个View
        setQuestionInfo(helper, item)
        // PK View
        setParallelPKView(
            helper.getView<ParallelPKView>(R.id.mParallelPKView),
            item
        ) // PKView 要是显示的话

        removeDanmuIfExist(helper) // 先移除以前复用的

        // 跳过
        helper.addOnClickListener(R.id.ivSkip) // 跳过
    }

    private fun setParallelPKView(mParallelPKView: ParallelPKView, item: QuestionModel) {
        if (item.answerModel == null) {
            mParallelPKView.visibility = View.INVISIBLE
            return;
        } else {
            mParallelPKView.visibility = View.VISIBLE // 先显示出来
            getUserHeaderUrls(item.answerModel.optionOneUser)
            mParallelPKView.setResult(
                "${item.answerModel.scoreOne}人",
                getUserHeaderUrls(item.answerModel.optionOneUser),
                UseHeadImageLayout.ImageLayoutClickListener { isMore, index ->
                    if (!isMore) {
                        showUserInfo(null, item.answerModel.optionOneUser[index])
                    }
                },
                "${item.answerModel.scoreTwo}人",
                getUserHeaderUrls(item.answerModel.optionTwoUser),
                UseHeadImageLayout.ImageLayoutClickListener { isMore, index ->
                    if (!isMore) {
                        showUserInfo(null, item.answerModel.optionTwoUser[index])
                    }
                }
            )
        }


    }

    // 得到最多4个值
    private fun getUserHeaderUrls(optionUser: List<UserInfo>?): ArrayList<String> {
        val arrayListOf = ArrayList<String>(4)
        if (optionUser == null) return arrayListOf
        for (element in optionUser) {
            val element = element.headImg
            arrayListOf.add(element)
            if (arrayListOf.size == 3) break // 只能存放4个值
        }
        return arrayListOf;
    }


    private fun setQuestionInfo(helper: BaseViewHolder, item: QuestionModel) {
        // 头像
        ImageLoader.showCircleImage(
            helper.getView<ImageView>(R.id.ivUserHeader),
            item.user.headImg,
            item.user.getNewDefaultHeadImage()
        )
        helper.addOnClickListener(R.id.ivUserHeader)


        // 用户名称
        helper.setText(R.id.tvUserName, item.user.name)
        helper.addOnClickListener(R.id.tvUserName)


        // 分享
        helper.addOnClickListener(R.id.ivShare)
        // 评论
        helper.addOnClickListener(R.id.ivComment);
        // 点赞
        helper.addOnClickListener(R.id.ivPraise);


        // 播放音乐
        // 是否显示播放音乐
        helper.setImageResource(
            R.id.ivMusic,
            if (PlayBgMusic.isPlaying()) R.drawable.question_bg_music_close else R.drawable.question_bg_music
        )
        helper.addOnClickListener(R.id.ivMusic)

    }


    // 设置答题选项
    private fun setOptions(optionOne: OptionView, optionTwo: OptionView, item: QuestionModel) {
        // 给第一个选项设置标题
        // 给第二个选项设置标题

        // 先设置默认值
        optionOne.setDefault()
        optionTwo.setDefault()

        if (item.isPicQuestion) {
            // 显示图片
            optionOne.setPicQuestion(item.optionOne)
            optionTwo.setPicQuestion(item.optionTwo)
        } else {
            if (item.colorIndex < 0) {
                item.colorIndex = random.nextInt(3);
            }
            // 显示文字
            optionOne.setTextQuestion(item.optionOne, item.colorIndex, true)
            optionTwo.setTextQuestion(item.optionTwo, item.colorIndex, false)
        }


        val display = item.answerModel != null

        optionOne.setResultDisplay(display)
        optionTwo.setResultDisplay(display)
        if (display) {
            // 还是要显示结果的
            val oneWin = item.answerModel.isAnswerOne
            optionOne.setResult(oneWin, item.answerModel.optionOnePer)
            optionTwo.setResult(!oneWin, item.answerModel.optionTwoPer)
        }
    }


    private fun removeDanmuIfExist(helper: BaseViewHolder) {
        // 显示弹幕 -- 有评论的时候
        // showDanmu();
        val cardView = helper.getView<ViewGroup>(R.id.layoutCardContent)
        // 去掉已经添加上的弹幕
        // 去掉已经添加上的弹幕
        val view_danmu = cardView.findViewById<View>(R.id.comment_dan_parent)
        if (view_danmu != null) {
            cardView.removeView(view_danmu)
        }
    }

    /**
     * 获取评论后，从外部调用
     * @param item 必须是comment赋值之后的
     */
    fun showDanmu(position: Int, item: QuestionModel) {
        // 找到这个容器

        val viewByPosition = getViewByPosition(position, R.id.layoutCardContent)

        if (viewByPosition !is ViewGroup) {
            return// 不要搞崩溃了
        }
        val layoutCardContent: ViewGroup = viewByPosition as ViewGroup


        // 动态添加弹幕
        if (
            item.commentBean != null
            && item.commentBean.comments != null
            && item.commentBean.comments.size != 0
            && !item.isHadShowDanMu
            && item.isShowComment
        ) {
            // 创建View
            val parentMuView =
                mLayoutInflater.inflate(R.layout.view_danmu, layoutCardContent, false)
            layoutCardContent.addView(parentMuView);
            // 下面是match_parent & match_parent
            val danMuView = parentMuView.findViewById<DanMuView>(R.id.comment_dan_mu);

            item.isHadShowDanMu = true
            danMuView.visibility = View.VISIBLE
            danMuView.prepare();
            // 显示所有评论
            for (comment in item.commentBean.comments) {
                if (comment == null) continue
                danMuView.add(getDanmuModel(comment));
            }

        }

    }

    private fun getDanmuModel(comment: CommentItem): DanMuModel {
        val danMuView = DanMuModel();
        danMuView.setDisplayType(DanMuModel.RIGHT_TO_LEFT)
        danMuView.setPriority(DanMuModel.NORMAL)
        danMuView.marginLeft = UtilsCode.dp2px(30f)

        try {
            danMuView.text = URLDecoder.decode(comment.getContent(), "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            danMuView.text = "未知评论"
        }

        showAvatar(danMuView, comment)

        danMuView.avatarWidth = UtilsCode.dp2px(25f)
        danMuView.avatarHeight = UtilsCode.dp2px(25f)
        danMuView.avatarStrokes = false

        danMuView.textSize = UtilsCode.sp2px(14f).toFloat()
        danMuView.textColor = ContextCompat.getColor(mContext, R.color.white)
        danMuView.textMarginLeft = UtilsCode.dp2px(10f)

        danMuView.textBackground =
            ContextCompat.getDrawable(mContext, R.drawable.rect_corners_20dp_black_alpha40)
        danMuView.textBackgroundMarginLeft = UtilsCode.dp2px(30f)
        danMuView.textBackgroundPaddingTop = UtilsCode.dp2px(6f)
        danMuView.textBackgroundPaddingBottom = UtilsCode.dp2px(6f)
        danMuView.textBackgroundPaddingRight = UtilsCode.dp2px(15f)

        // 每个弹幕的点击事件

        // 每个弹幕的点击事件
        danMuView.enableTouch(true)
        danMuView.setOnTouchCallBackListener {
            showUserInfo(null, comment.userInfo)
        }

        return danMuView;
    }


    // 加载bitmap头像
    private fun showAvatar(danMuView: DanMuModel, item: CommentItem) {
        // 先设置一个默认的图片
        danMuView.avatar =
            UtilsCode.toRound(UtilsCode.getBitmap(R.drawable.voice_edit_header_default))
        Glide.with(App.getInstance())
            .asBitmap()
            .placeholder(R.drawable.voice_edit_header_default)
            .load(item.userInfo.headImg)
            .into(object : CustomTarget<Bitmap>(100, 100) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    danMuView.avatar = UtilsCode.toRound(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    // 显示动画
    fun showPkResultAnimator(position: Int, item: QuestionModel) {
        // 需要设置文字
        // 找到PkView
        // 找到 optionView
        val optionOne = getViewByPosition(position, R.id.optionOne) as? OptionView
        val optionTwo = getViewByPosition(position, R.id.optionTwo) as? OptionView
        val mParallelPKView = getViewByPosition(position, R.id.mParallelPKView) as? ParallelPKView

        if (optionOne != null && optionTwo != null && mParallelPKView != null) {
            // 显示正确的数据
            setOptions(optionOne, optionTwo, item)
            setParallelPKView(mParallelPKView, item)

            optionOne.setResultDisplay(true)
            optionTwo.setResultDisplay(true)

            // 设置动画
            val optionOneAnimator = optionOne.getResultAnimator()
            val optionTwoAnimator = optionTwo.getResultAnimator()
            val pkImageAnimator = mParallelPKView.getPKImageAnimator()
            val userAnimator = mParallelPKView.getUserAnimator()

            val oneTime = Math.max(optionOneAnimator.duration, optionTwoAnimator.duration)
            // 在结果页之后
            pkImageAnimator.startDelay = oneTime
            userAnimator.startDelay = pkImageAnimator.duration + oneTime


            val set = AnimatorSet();
            set.playTogether(optionOneAnimator, optionTwoAnimator, pkImageAnimator, userAnimator)
            set.start()
        }

    }


    // 整体的跳转提示, 直接外部调用把 , 提示的作用
    fun playSkipAnimation(position: Int) { // 超时时候的提示
        val view = getViewByPosition(position, R.id.layoutCardContent) as ViewGroup
//        if (BuildConfig.DEBUG) {
//            view.translationX = -80F
//        } else {
        val offset1 = ObjectAnimator.ofFloat(view, "translationX", 0f, -80f).setDuration(500)
        val offset2 = ObjectAnimator.ofFloat(view, "translationX", -80f, 0f).setDuration(100)
        val offset3 = ObjectAnimator.ofFloat(view, "translationX", 0f, -30f).setDuration(100)
        val offset4 = ObjectAnimator.ofFloat(view, "translationX", -30f, 0f).setDuration(50)
        offset2.startDelay = 500
        offset3.startDelay = (500 + 100).toLong()
        offset4.startDelay = (500 + 100 + 100).toLong()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(offset1, offset2, offset3, offset4)
        animatorSet.start()
//        }
    }

    // 改变点赞的状态
    fun changePraise(position: Int, model: QuestionModel) {
        // 播放音乐
        val ivPraise = getViewByPosition(position, R.id.ivPraise) as? ImageView
        // 是否点赞
        ivPraise?.setImageResource(if (model.liked == 0) R.drawable.question_prise else R.drawable.icon_praised)
    }

    // 改变声音的状态
    fun changeMusic(position: Int) {
        // 播放音乐
        val ivMusic = getViewByPosition(position, R.id.ivMusic) as? ImageView
        // 是否显示播放音乐
        ivMusic?.setImageResource(if (PlayBgMusic.isPlaying()) R.drawable.question_bg_music_close else R.drawable.question_bg_music)
    }

    // 回调回来
    private fun showUserInfo(voiceInfo: VoiceInfo?, userInfo: UserInfo) {
        // 跳转回来
        fragment.showUserInfo(voiceInfo, userInfo)
    }
}