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
            isClickable = false // 不能点击，防止跳页
            loadUrl("https://s.weibo.com/top/summary")
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    //scroll(view)
                    // 直接扩大WebView即可
                    view?.run {
                        measure(0, 0) //测量webview必备
                        val lp = layoutParams
                        lp.height = measuredHeight
                        layoutParams = lp
                        requestLayout()
                    }
                }
            }
        }
    }

    // 扩展函数
    private fun WebView.scroll(view: WebView?) {
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
                if (vg.measuredHeight < newHeight) { // 只有新增高度，才重新赋值
                    val lp = vg.layoutParams
                    lp.height = newHeight
                    vg.layoutParams = lp
                }
                vg.requestLayout()
            }
        }
    }

    // (this as? WrapHeightWebView)?.requestParentLayout()

}