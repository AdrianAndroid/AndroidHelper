package com.joyy.androidproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joyy.stringescape.StringEscapeUtils
import com.joyy.webviews.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val url =
        "<p>FIFTY-SIXTY CURRENT AFFAIRS TEST SERIES -2021</p><p><br></p><p><br></p><p><br></p><p>\tஇந்திய குடிமைப் பணித் தேர்வில் எனப்படும் தற்கால நிகழ்வுகள் இந்திய மற்றும் சர்வதேச அளவில் முக்கியத்துவம் வாய்ந்த செய்திகள் வினாக்களாக கேட்கப்படுகின்றன இவற்றில் வரலாறு புவியியல் பொருளாதாரம் சுற்றுப்புறச் சூழல் அறிவியல் மற்றும் தொழில்நுட்பம் புவியியல் சம்பந்தப்பட்ட வினாக்கள் கேட்கப்படுகின்றன இந்த தற்கால நிகழ்வுகளுக்கான வினாக்கள் அதிகாரபூர்வமான செய்திகளிலிருந்து பெறப்படுகின்றன இதனடிப்படையில் இந்த தற்கால நிகழ்வுகளுக்கான மாதிரி வினா தேர்வு வினாத் தேர்வு தொடர் அமைக்கப்பட்டுள்ளது இந்த வினா தேர்வு தொடரில் இந்தியா இயர் புக், வேலைவாய்ப்பு செய்திகள், பொருளாதார மதிப்பீடு, அரசாங்க அமைச்சரவைகளின் அதிகாரபூர்வமான ஆண்டு மலர், இந்தியா பத்திரிகை செய்தித் தொடர்புகளின் செய்தி தொகுப்பு ,யோஜனா, குருஷேத்ரா அகில இந்திய வானொலி செய்தி தொகுப்பு, இந்திய அரசாங்கத்தின் வலைத்தளம் மற்றும் இன்ன பிற அதிகாரப்பூர்வ வெளியீடுகளிலிருந்து வினாக்கள் தொகுக்கப்பட்டுள்ளன. அனைத்து வினாக்களும் அரசாங்க அதிகாரப்பூர்வ வெளியீடுகளில் இருந்து பெறப்பட்டவை இவற்றைப் பற்றிய புரிதல் மாணாக்கர்களுக்கு அதிகமாகின்றன. அனைத்து வினாக்களும் விடைகளும் அதற்கான தெளிவான விளக்கங்களும் மின்னணு முறையில் பதிவிறக்கம் செய்து கொள்ளலாம்.இவற்றின் மூலம் மாணாக்கர்களுக்கு தெரியாத வினாக்களுக்கான விடைகளை மாணவர்கள் தெரிந்து கொள்ளலாம் .</p><p><br></p><p><br></p><p><br></p><p>ஒரு மதிப்பெண் கூட மாணவரின் வெற்றி தோல்வியை தீர்மானிப்பதாக அமைந்துள்ளது, நேரம் மேம்பாடு நேர மேலாண்மை மற்றும் கருதுகோள் விடைகளை சரியான முறையில் தேர்ந்தெடுக்கும் வகையில் பயிற்சி படுத்தப்படுகிறார்கள். தற்காலத்திய நிகழ்வுகள் பற்றிய அறிதல் மற்றும் புரிதல் மேம்படுகிறது இந்த தேர்வு தொடரில் பயிற்சி மேற்கொள்ளும் பொழுது அதிக மதிப்பெண் பெறுவதற்கான சாத்தியக்கூறுகள் உள்ளன.</p><p><br></p><p><br></p><p><br></p><p>இந்தத் தேர்வு தொடரில் தற்போதைய நிலையில் 7 தேர்வுகள் உள்ளன ஒவ்வொரு தேர்விற்கும் 50 வினாக்கள் கொடுக்கப்பட்டுள்ளன. தேர்வுக்கான நேரம் 60 நிமிடங்கள்.எதிர்வரும் காலங்களில் இன்னும் மூன்று தேர்வுகள் இதில் சேர்க்கப்பட உள்ளன. எனவே மொத்தத்தில் 10 தேர்வுகள் கொண்ட ஒரு முழு தொடர் இது. </p><p><br></p><p><br></p><p><br></p><p>தேர்வுத் தொடர் சம்பந்தமான ஏதேனும் சந்தேகங்கள் இருப்பின் எங்களது வாட்ஸ்அப் மற்றும் டெலிகிராம் 8939290339 எண்ணுக்கு குறுஞ்செய்தி அனுப்பி தெரிந்து கொள்ளலாம்.</p><p><br></p><p><br></p><p><br></p><p>விருப்பமுள்ள மாணவர்கள் இணைந்து பயன் பெறலாம்</p><p><br></p>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnActive = findViewById<Button>(R.id.btnActive).also {
            it.setOnClickListener {
                Toast.makeText(this, "Activew : ${it.isActivated}", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<RadioGroup>(R.id.rg).setOnCheckedChangeListener { rg, id ->
            if (id == R.id.rb1) {
                btnActive.isActivated = true
            } else if (id == R.id.rb2) {
                btnActive.isActivated = false
            }
        }

        findViewById<View>(R.id.webview).setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }


        // 1. 先打印字符串测试
        // 2。抽离StringEscapeUtils代码
        // 3。 WebView写JS
        // 4。
        findViewById<TextView>(R.id.textview).text = StringEscapeUtils.escapeJava(url)
    }



    suspend fun one() {
//        delay()
    }
}