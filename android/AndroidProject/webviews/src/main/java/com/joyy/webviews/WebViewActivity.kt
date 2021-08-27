package com.joyy.webviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joyy.webviews.api.IWebJS
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

//    private val webAppInterface = WebAppInterface(this)


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

//        mWebView.loadUrl("https://www.jianshu.com/p/6c796944a19f")
//        mWebView.loadUrl("file:///android_asset/web/test.html")
//        mWebView.addJavascriptInterface(webAppInterface, "app")
        WebView.setWebContentsDebuggingEnabled(true)
//        // 注入
//        JSHelper.addJavascriptInterface(mWebView) // 注入JS
        addCommands(mWebView) // 添加方法

//        findViewById<View>(R.id.button).setOnClickListener {
//            webAppInterface.showName("测试成功")
//        }
    }

    lateinit var jsInterface: JSInterface
    fun addCommands(mWebView: WebView) {
        jsInterface = JSInterface(mWebView)
        mWebView.addJavascriptInterface(jsInterface, "app")

        jsInterface.addCommand(Command("getName", object : IWebJS {
            override fun invoke(name: String?, params: String?, cbName: String?): String {
                return "<p>FIFTY-SIXTY CURRENT AFFAIRS TEST SERIES -2021</p><p><br></p><p><br></p><p><br></p><p>\t  இந்திய குடிமைப் பணித் தேர்வில் எனப்படும் தற்கால நிகழ்வுகள் இந்திய மற்றும் சர்வதேச அளவில் முக்கியத்துவம் வாய்ந்த செய்திகள் வினாக்களாக கேட்கப்படுகின்றன இவற்றில் வரலாறு புவியியல் பொருளாதாரம் சுற்றுப்புறச் சூழல் அறிவியல் மற்றும் தொழில்நுட்பம் புவியியல் சம்பந்தப்பட்ட வினாக்கள் கேட்கப்படுகின்றன இந்த தற்கால நிகழ்வுகளுக்கான வினாக்கள் அதிகாரபூர்வமான செய்திகளிலிருந்து பெறப்படுகின்றன இதனடிப்படையில் இந்த தற்கால நிகழ்வுகளுக்கான மாதிரி வினா தேர்வு வினாத் தேர்வு தொடர் அமைக்கப்பட்டுள்ளது இந்த வினா தேர்வு தொடரில் இந்தியா இயர் புக், வேலைவாய்ப்பு செய்திகள், பொருளாதார மதிப்பீடு, அரசாங்க அமைச்சரவைகளின் அதிகாரபூர்வமான ஆண்டு மலர், இந்தியா பத்திரிகை செய்தித் தொடர்புகளின் செய்தி தொகுப்பு ,யோஜனா, குருஷேத்ரா அகில இந்திய வானொலி செய்தி தொகுப்பு, இந்திய அரசாங்கத்தின் வலைத்தளம் மற்றும் இன்ன பிற அதிகாரப்பூர்வ வெளியீடுகளிலிருந்து வினாக்கள் தொகுக்கப்பட்டுள்ளன. அனைத்து வினாக்களும் அரசாங்க அதிகாரப்பூர்வ வெளியீடுகளில் இருந்து பெறப்பட்டவை இவற்றைப் பற்றிய புரிதல் மாணாக்கர்களுக்கு அதிகமாகின்றன. அனைத்து வினாக்களும் விடைகளும் அதற்கான தெளிவான விளக்கங்களும் மின்னணு முறையில் பதிவிறக்கம் செய்து கொள்ளலாம்.இவற்றின் மூலம் மாணாக்கர்களுக்கு தெரியாத வினாக்களுக்கான விடைகளை மாணவர்கள் தெரிந்து கொள்ளலாம் .</p><p><br></p><p><br></p><p><br></p><p>ஒரு மதிப்பெண் கூட மாணவரின் வெற்றி தோல்வியை தீர்மானிப்பதாக அமைந்துள்ளது, நேரம் மேம்பாடு நேர மேலாண்மை மற்றும் கருதுகோள் விடைகளை சரியான முறையில் தேர்ந்தெடுக்கும் வகையில் பயிற்சி படுத்தப்படுகிறார்கள். தற்காலத்திய நிகழ்வுகள் பற்றிய அறிதல் மற்றும் புரிதல் மேம்படுகிறது இந்த தேர்வு தொடரில் பயிற்சி மேற்கொள்ளும் பொழுது அதிக மதிப்பெண் பெறுவதற்கான சாத்தியக்கூறுகள் உள்ளன.</p><p><br></p><p><br></p><p><br></p><p>இந்தத் தேர்வு தொடரில் தற்போதைய நிலையில் 7 தேர்வுகள் உள்ளன ஒவ்வொரு தேர்விற்கும் 50 வினாக்கள் கொடுக்கப்பட்டுள்ளன. தேர்வுக்கான நேரம் 60 நிமிடங்கள்.எதிர்வரும் காலங்களில் இன்னும் மூன்று தேர்வுகள் இதில் சேர்க்கப்பட உள்ளன. எனவே மொத்தத்தில் 10 தேர்வுகள் கொண்ட ஒரு முழு தொடர் இது. </p><p><br></p><p><br></p><p><br></p><p>தேர்வுத் தொடர் சம்பந்தமான ஏதேனும் சந்தேகங்கள் இருப்பின் எங்களது வாட்ஸ்அப் மற்றும் டெலிகிராம் 8939290339 எண்ணுக்கு குறுஞ்செய்தி அனுப்பி தெரிந்து கொள்ளலாம்.</p><p><br></p><p><br></p><p><br></p><p>விருப்பமுள்ள மாணவர்கள் இணைந்து பயன் பெறலாம்</p><p><br></p>"
            }
        }))
        jsInterface.addCommand(Command("sayHello", object : IWebJS {
            override fun invoke(name: String?, params: String?, cbName: String?): String {
                runOnUiThread {
                    Toast.makeText(this@WebViewActivity, params ?: "还没点击按钮", Toast.LENGTH_SHORT)
                        .show()
                }
                return ""
            }
        }))
        jsInterface.addCommand(Command("back", object : IWebJS {
            override fun invoke(name: String?, params: String?, cbName: String?): String {
                runOnUiThread {
                    Toast.makeText(this@WebViewActivity, "点击了H5的按钮", Toast.LENGTH_SHORT).show()
                }
                return ""
            }
        }))
        jsInterface.addCommand(Command("showName", object : IWebJS {
            override fun invoke(name: String?, params: String?, cbName: String?): String {
                runOnUiThread { mWebView.loadUrl("javascript:('$name')") }
                return ""
            }
        }))

        jsInterface.loadUrl("file:///android_asset/web/test.html")
    }

    override fun onResume() {
        super.onResume()
        jsInterface.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        jsInterface.destroy()
    }
//
//    inner class WebAppInterface(val context: Context) {
//
//        // 获取Android中的值
//        @JavascriptInterface
//        fun getName(): String {
//            return "<p>FIFTY-SIXTY CURRENT AFFAIRS TEST SERIES -2021</p><p><br></p><p><br></p><p><br></p><p>\t  இந்திய குடிமைப் பணித் தேர்வில் எனப்படும் தற்கால நிகழ்வுகள் இந்திய மற்றும் சர்வதேச அளவில் முக்கியத்துவம் வாய்ந்த செய்திகள் வினாக்களாக கேட்கப்படுகின்றன இவற்றில் வரலாறு புவியியல் பொருளாதாரம் சுற்றுப்புறச் சூழல் அறிவியல் மற்றும் தொழில்நுட்பம் புவியியல் சம்பந்தப்பட்ட வினாக்கள் கேட்கப்படுகின்றன இந்த தற்கால நிகழ்வுகளுக்கான வினாக்கள் அதிகாரபூர்வமான செய்திகளிலிருந்து பெறப்படுகின்றன இதனடிப்படையில் இந்த தற்கால நிகழ்வுகளுக்கான மாதிரி வினா தேர்வு வினாத் தேர்வு தொடர் அமைக்கப்பட்டுள்ளது இந்த வினா தேர்வு தொடரில் இந்தியா இயர் புக், வேலைவாய்ப்பு செய்திகள், பொருளாதார மதிப்பீடு, அரசாங்க அமைச்சரவைகளின் அதிகாரபூர்வமான ஆண்டு மலர், இந்தியா பத்திரிகை செய்தித் தொடர்புகளின் செய்தி தொகுப்பு ,யோஜனா, குருஷேத்ரா அகில இந்திய வானொலி செய்தி தொகுப்பு, இந்திய அரசாங்கத்தின் வலைத்தளம் மற்றும் இன்ன பிற அதிகாரப்பூர்வ வெளியீடுகளிலிருந்து வினாக்கள் தொகுக்கப்பட்டுள்ளன. அனைத்து வினாக்களும் அரசாங்க அதிகாரப்பூர்வ வெளியீடுகளில் இருந்து பெறப்பட்டவை இவற்றைப் பற்றிய புரிதல் மாணாக்கர்களுக்கு அதிகமாகின்றன. அனைத்து வினாக்களும் விடைகளும் அதற்கான தெளிவான விளக்கங்களும் மின்னணு முறையில் பதிவிறக்கம் செய்து கொள்ளலாம்.இவற்றின் மூலம் மாணாக்கர்களுக்கு தெரியாத வினாக்களுக்கான விடைகளை மாணவர்கள் தெரிந்து கொள்ளலாம் .</p><p><br></p><p><br></p><p><br></p><p>ஒரு மதிப்பெண் கூட மாணவரின் வெற்றி தோல்வியை தீர்மானிப்பதாக அமைந்துள்ளது, நேரம் மேம்பாடு நேர மேலாண்மை மற்றும் கருதுகோள் விடைகளை சரியான முறையில் தேர்ந்தெடுக்கும் வகையில் பயிற்சி படுத்தப்படுகிறார்கள். தற்காலத்திய நிகழ்வுகள் பற்றிய அறிதல் மற்றும் புரிதல் மேம்படுகிறது இந்த தேர்வு தொடரில் பயிற்சி மேற்கொள்ளும் பொழுது அதிக மதிப்பெண் பெறுவதற்கான சாத்தியக்கூறுகள் உள்ளன.</p><p><br></p><p><br></p><p><br></p><p>இந்தத் தேர்வு தொடரில் தற்போதைய நிலையில் 7 தேர்வுகள் உள்ளன ஒவ்வொரு தேர்விற்கும் 50 வினாக்கள் கொடுக்கப்பட்டுள்ளன. தேர்வுக்கான நேரம் 60 நிமிடங்கள்.எதிர்வரும் காலங்களில் இன்னும் மூன்று தேர்வுகள் இதில் சேர்க்கப்பட உள்ளன. எனவே மொத்தத்தில் 10 தேர்வுகள் கொண்ட ஒரு முழு தொடர் இது. </p><p><br></p><p><br></p><p><br></p><p>தேர்வுத் தொடர் சம்பந்தமான ஏதேனும் சந்தேகங்கள் இருப்பின் எங்களது வாட்ஸ்அப் மற்றும் டெலிகிராம் 8939290339 எண்ணுக்கு குறுஞ்செய்தி அனுப்பி தெரிந்து கொள்ளலாம்.</p><p><br></p><p><br></p><p><br></p><p>விருப்பமுள்ள மாணவர்கள் இணைந்து பயன் பெறலாம்</p><p><br></p>"
//        }
//
//        // ----下面是JS调用安卓的方法
//        @JavascriptInterface
//        fun sayHello(name: String) {
//            val txt = if (name.isBlank()) "还没点本地按钮" else name
//            Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
//        }
//
//        @JavascriptInterface
//        fun back() {
//            Toast.makeText(context, "点击了H5的按钮", Toast.LENGTH_SHORT).show()
//        }
//
//        // ----下面是安卓调用JS的方法
//        @JavascriptInterface
//        fun showName(name: String) {
//            runOnUiThread { mWebView.loadUrl("javascript:showName('$name')") }
//        }
//
//    }


}

