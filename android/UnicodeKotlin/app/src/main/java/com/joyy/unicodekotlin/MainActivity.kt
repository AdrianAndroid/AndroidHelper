package com.joyy.unicodekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.joyy.escapekt.EscapeUtils
import com.joyy.stringescape.StringEscapeUtils

class MainActivity : AppCompatActivity() {
    val txt =
        "<p>FIFTY-SIXTY CURRENT AFFAIRS TEST SERIES -2021\\f\\f\\f</p><p><br></p><p><br></p><p><br></p><p>\tஇந்திய குடிமைப் பணித் தேர்வில் எனப்படும் தற்கால நிகழ்வுகள் இந்திய மற்றும் சர்வதேச அளவில் முக்கியத்துவம் வாய்ந்த செய்திகள் வினாக்களாக கேட்கப்படுகின்றன இவற்றில் வரலாறு புவியியல் பொருளாதாரம் சுற்றுப்புறச் சூழல் அறிவியல் மற்றும் தொழில்நுட்பம் புவியியல் சம்பந்தப்பட்ட வினாக்கள் கேட்கப்படுகின்றன இந்த தற்கால நிகழ்வுகளுக்கான வினாக்கள் அதிகாரபூர்வமான செய்திகளிலிருந்து பெறப்படுகின்றன இதனடிப்படையில் இந்த தற்கால நிகழ்வுகளுக்கான மாதிரி வினா தேர்வு வினாத் தேர்வு தொடர் அமைக்கப்பட்டுள்ளது இந்த வினா தேர்வு தொடரில் இந்தியா இயர் புக், வேலைவாய்ப்பு செய்திகள், பொருளாதார மதிப்பீடு, அரசாங்க அமைச்சரவைகளின் அதிகாரபூர்வமான ஆண்டு மலர், இந்தியா பத்திரிகை செய்தித் தொடர்புகளின் செய்தி தொகுப்பு ,யோஜனா, குருஷேத்ரா அகில இந்திய வானொலி செய்தி தொகுப்பு, இந்திய அரசாங்கத்தின் வலைத்தளம் மற்றும் இன்ன பிற அதிகாரப்பூர்வ வெளியீடுகளிலிருந்து வினாக்கள் தொகுக்கப்பட்டுள்ளன. அனைத்து வினாக்களும் அரசாங்க அதிகாரப்பூர்வ வெளியீடுகளில் இருந்து பெறப்பட்டவை இவற்றைப் பற்றிய புரிதல் மாணாக்கர்களுக்கு அதிகமாகின்றன. அனைத்து வினாக்களும் விடைகளும் அதற்கான தெளிவான விளக்கங்களும் மின்னணு முறையில் பதிவிறக்கம் செய்து கொள்ளலாம்.இவற்றின் மூலம் மாணாக்கர்களுக்கு தெரியாத வினாக்களுக்கான விடைகளை மாணவர்கள் தெரிந்து கொள்ளலாம் .</p><p><br></p><p><br></p><p><br></p><p>ஒரு மதிப்பெண் கூட மாணவரின் வெற்றி தோல்வியை தீர்மானிப்பதாக அமைந்துள்ளது, நேரம் மேம்பாடு நேர மேலாண்மை மற்றும் கருதுகோள் விடைகளை சரியான முறையில் தேர்ந்தெடுக்கும் வகையில் பயிற்சி படுத்தப்படுகிறார்கள். தற்காலத்திய நிகழ்வுகள் பற்றிய �"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.helloworld)
        tv.text = txt

        findViewById<View>(R.id.str).setOnClickListener {
            tv.text = txt
        }

        findViewById<View>(R.id.org).setOnClickListener {
            tv.text = StringEscapeUtils.escapeJava(txt)
        }

        findViewById<View>(R.id.kt).setOnClickListener {
            tv.text = EscapeUtils.escapeJava(txt)
        }

        findViewById<View>(R.id.kt_java).setOnClickListener {
            val d = StringEscapeUtils.escapeJava(txt) == EscapeUtils.escapeJava(txt)
            Toast.makeText(this, "是否相同->$d", Toast.LENGTH_SHORT).show()
        }

    }
}