package cn.kuwo.pp.ui.mine

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.util.SP
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.pp.R
import cn.kuwo.pp.ui.login.LoginFragment2
import com.zhl.userguideview.GuideUtils
import kotlinx.android.synthetic.main.fragment_developer.*
import okhttp3.HttpUrl

class DeveloperFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return attachToSwipeBack(inflater.inflate(R.layout.fragment_developer, container, false))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val packageInfo = _mActivity.packageManager.getPackageInfo(_mActivity.packageName, 0)

        val sb = StringBuilder()
        sb.append(packageInfo.packageName).append("\n")
        sb.append(packageInfo.versionName).append("\n")
        sb.append("${packageInfo.versionCode}").append("\n")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            sb.append("${packageInfo.longVersionCode}").append("\n")
        }
        contents.text = sb.toString()


        uri.setOnClickListener {
            uriTest()
        }

        // 显示LoginFragmeng
        displayLoginFragment()

        // 测试开发用
        guideview.setOnClickListener {
            GuideUtils.showGuideAtTop(_mActivity, it) { view, type ->
                GuideUtils.dismissGuideView(view)
            }
        }

        mmkv()
        httpUrlClick()
    }

    private fun httpUrlClick() {
        httpUrl.setOnClickListener {
            val oldUrl = HttpUrl.parse("http://www.baidu.com")
            //oldUrl.newBuilder("")
        }
    }

    private fun mmkv() {
        mmkvKeys.setOnClickListener {
            // 展开，关闭
            if (layoutMMKV.childCount > 0) {
                layoutMMKV.removeAllViews()
            } else {
                // 找到MMKV的所有的Key
                SP.allKeys().forEach { key ->
                    val btn = Button(_mActivity)
                    btn.setBackgroundColor(Color.GRAY)
                    btn.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    btn.text = key
                    btn.setOnClickListener {
                        UtilsCode.showLong(SP.decodeString((it as Button).text as String))
                    }
                    btn.setOnLongClickListener { view ->
                        SP.remove((view as Button).text as String?)
                        layoutMMKV.removeView(view)
                        true
                    }
                    layoutMMKV.addView(btn)
                }
            }
        }
    }

    private fun displayLoginFragment() {
        displayLoginFragment2.setOnClickListener {

            childFragmentManager.beginTransaction()
                .replace(R.id.containerid, LoginFragment2())
                .commitNow()
        }
    }

    private fun uriTest() {
        //kwbulu://challenge/issue
        val parse = Uri.parse("kwbulu://challenge/issue")
        parse.authority
        parse.host
        parse.scheme
    }

    // 判断是否是相同的scheme
    private fun isSameScheme(uri: Uri, uriString: String): Boolean {
        val parse = Uri.parse(uriString)
        if (uri.scheme == parse.scheme
            && uri.host == parse.host
            && uri.path == parse.path
        ) {
            return true;
        }
        return false;
    }


    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}