package com.flannery.multilanguage.confict4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.flannery.multilanguage.R

class WebViewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<WebView>(R.id.mWebView)?.run {
//            loadUrl("https://juejin.cn/post/6979575721693806629?utm_source=gold_browser_extension")
            //loadUrl("https://www.baidu.com/s?wd=%E4%B8%AD%E5%9B%BD%E7%A9%BA%E9%97%B4%E7%AB%99%E8%88%AA%E5%A4%A9%E5%91%98%E9%A6%96%E6%AC%A1%E5%87%BA%E8%88%B1&ie=utf-8&rsv_cq=%E6%90%9C%E7%8B%90%E6%96%B0%E9%97%BB&rsv_dl=0_right_fyb_pchot_20811_01&rsv_pq=fd42ad1000014dac&oq=%E6%90%9C%E7%8B%90%E6%96%B0%E9%97%BB&rsv_t=b626oYiNi0taaUPgpYg%2BTHskfJhpZATQiGZWsGz%2BbXiK4ZLHdgb0yQUpgsE&rsf=8c571180f831a14e6587dd7eb6e21a14_1_10_1&rqid=fd42ad1000014dac")
            isClickable = false // 不能点击，防止跳页
            loadUrl("https://s.weibo.com/top/summary")
//            loadUrl("http://www.748219.com/meiwen/14064.html")
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    view?.post { //异步
                        (getView() as? ViewGroup)?.let { vg ->
                            var newHeight = 0
                            repeat(vg.childCount) { index ->
                                val childAt = vg.getChildAt(index)
                                if (childAt is WrapHeightWebView) {
                                    childAt.measure(0, 0) //这样才能获取准确的高度
                                    val mH = childAt.measuredHeight
                                    val lp = layoutParams
                                    lp.height = mH
                                    layoutParams = lp
                                }
                                newHeight += childAt.layoutParams.height
                            }
                            // 更新vg的高度
                            val lp = vg.layoutParams
                            lp.height = newHeight
                            vg.layoutParams = lp
                            vg.requestLayout()
                        }
                    }
                }
            }
        }
    }

    // 更新Layout的高度
    fun updateLayoutHeight() {

    }

    // (this as? WrapHeightWebView)?.requestParentLayout()

}