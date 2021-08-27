package com.joyy.androidproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joyy.stringescape.StringEscapeUtils
import com.joyy.webviews.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

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


        //test()
//        foreach(0) //launch 会复用线程池
        //test4()
//        test5()
        //test6()
        //test7() // 优先主线， 协程才做
        //test8() //创建多个协程, 两个 async 交叉运行
//        test9() // coroutineScope会一个新的作用域
//        test10()
//        test11()
        //test12()//launch中的this只是当前launch
        //test13()
        //test14() //验证cancelchild
        //test15() // 先取消上一个任务，在进行下一个任务
        //test16()
        test17()
    }

    fun test18() {
        GlobalScope.launch(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }) {

            this.coroutineContext.fold(this) { _, _ ->
                this.coroutineContext
            }

        }
    }

    fun test17() {
        // 任务一个一个排队执行
//        val controlledRunner = ControlledRunner<String>() // 放到公共区
        val controlledRunner = ControlledRunner<String>() // 排队

        MainScope().launch {
            var num: Int = 0
            repeat(10) {
                num++
                controlledRunner.joinPreviousOrRun {
                    for (i in 0..10) {
                        log("$num controlledRunner.cancelPreviousThenRun  $i")
                        delay(300)
                    }
                    ""
                }
                delay(600)
            }
        }
    }

    fun test16() {
        // 任务一个一个排队执行
//        val controlledRunner = ControlledRunner<String>() // 放到公共区
        val singleRunner = SingleRunner() // 排队

        MainScope().launch {
            repeat(10) {
                singleRunner.afterPrevious {
                    for (i in 0..10) {
                        log("controlledRunner.cancelPreviousThenRun  $i")
                        delay(300)
                    }
                }
            }
        }
    }


    fun test15() {
        // 下一个任务， 先取消前一个任务
        val controlledRunner = ControlledRunner<String>() // 放到公共区

        MainScope().launch {
            repeat(10) {
                controlledRunner.cancelPreviousThenRun {
                    for (i in 0..1000) {
                        log("controlledRunner.cancelPreviousThenRun  $i")
                        delay(300)
                    }
                    ""
                }
            }
        }
    }


    fun test14() { // 验证cancelchild
        vm.viewModelScope.launch {
            val child1 = launch {
                for (i in 0..1000) {
                    log("launch $i")
                    delay(800)
                }
            }
            val child2 = async {
                for (i in 0..1000) {
                    log("async $i")
                    delay(600)
                }
            }


            for (i in 0..100000) {
                delay(200)
                log("viewModelScope $i")
                if (i == 10) {
                    this.coroutineContext.job.cancelChildren()
//                    child1.cancel()
//                    child2.cancel()
                    break
                }
            }

        }.invokeOnCompletion {
            log("invokeOnCompletion $it")
        }
    }

    fun test13() { // 验证取消job，子协程是否能取消
        // 结论：子协程也被取消
        vm.viewModelScope.launch {
            launch {
                for (i in 0..1000) {
                    log("launch $i")
                    delay(800)
                }
            }.invokeOnCompletion {
                log("launch $it")
            }
            async {
                for (i in 0..1000) {
                    log("async $i")
                    delay(600)
                }
            }

            for (i in 0..1000) {
                log("viewModelScope $i")
                delay(600)
                if (i == 10) this.cancel()
            }
        }.invokeOnCompletion {
            log("invokeOnCompletion $it")
        }
    }

    fun test12() { // 取消正在运行的协程
        // launch中的this只是当前launch
        val job: Job = vm.viewModelScope.launch {
            for (i in 0..1000) {
                log("viewModelScope $i")
                delay(500)
                if (i == 10) this.cancel()
            }
        }
        vm.viewModelScope.launch {
            for (i in 0..1000) {
                log("****  $i")
                delay(800)
            }
        }
    }

    fun test11() {
        vm.viewModelScope.launch {
            for (i in 0..10) {
                delay(6)
                log("viewModelScope ")
            }

        }
        for (i in 0..10) {
            log("test11 $i")
            Thread.sleep(6000)
        }
    }


    fun test10() { //验证在corouteinScope中，任意一个子失败，就会停止
        log("test10 start")
        MainScope().launch(CoroutineExceptionHandler { c: CoroutineContext, t: Throwable ->
//            log("$t")
            log("$c")
        }) {
            log("MainScope start")
            coroutineScope {
                launch {
                    log("luanch start")
                    delay(2000)
                    throw IllegalArgumentException("")
                }
                async {
                    log("async start")
                    for (i in 0..100) {
                        delay(500)
                        log("async 001 - $i")
                    }
                    log("async end")
                }
            }
            log("MainScope end")
        }
        log("test10 end")
    }


    fun test9() {
        MainScope().launch {
            coroutineScope {
                launch {
                    for (i in 0..100) {
                        delay(1000)
                        log("launch 001 - $i")
                    }
                }
                async {
                    for (i in 0..100) {
                        delay(500)
                        log("async 001 - $i")
                    }
                }
            }
        }
    }

    fun test8() {
        // 揭露， async两个交叉运行
        log("test8 start")
        CoroutineScope(Dispatchers.Main).launch { // 作用域
            log("CoroutineScope start")
            async {
                for (i in 0..100) {
                    delay(1000)
                    log("async 001 - $i")
                }
            }
            async {
                for (i in 0..100) {
                    delay(500)
                    log("async 002 - $i")
                }
            }
//            a1.await()
            log("CoroutineScope end")
        }
        log("test8 end")
    }


    fun test7() { // 测试异步会不会影响协程
        for (i in 0..50) {
            log("$i start -> ")
            Thread.sleep(100)
            if (i == 26) Thread.sleep(5000)
        }
        for (i in 0..100) {
            CoroutineScope(Dispatchers.Main).launch {
                delay((200 * i).toLong())
                log("$i")
            }
        }

        Thread {
            Thread.sleep(5000) //休眠5秒
            runOnUiThread {
                // 干别的事情会不会被影响到
                for (i in 0..50) {
                    log("$i end -> ")
                    Thread.sleep(100)
                    if (i == 26) Thread.sleep(5000)
                }
            }
        }.start()


    }

    fun test6() { // 测试在main中创建多个协程
        // 结论，协程不会阻塞主线程的工作
        // 干别的事情会不会被影响到
        for (i in 0..50) {
            log("$i start -> ")
            Thread.sleep(100)
            if (i == 26) Thread.sleep(5000)
        }
        for (i in 0..100) {
            CoroutineScope(Dispatchers.Main).launch {
                delay((200 * i).toLong())
                log("$i")
            }
        }

        // 干别的事情会不会被影响到
        for (i in 0..50) {
            log("$i end -> ")
            Thread.sleep(100)
            if (i == 26) Thread.sleep(5000)
        }
    }

    fun test5() { // 测试在main中创建多个协程
        for (i in 0..100) {
            CoroutineScope(Dispatchers.Main).launch {
                log("$i")
                delay(800)
            }
        }

        // 干别的事情会不会被影响到
        for (i in 0..100) {
            log("$i other -> ")
            Thread.sleep(200)
        }
    }

    fun test4() {
        // 结论： Main中是在main里面
        // 其他： 单独创建单独的线程
        runBlocking {
            log("runBlocking")
        }
        GlobalScope.launch {
            log("GlobalScope.launch")
        }
        val launch: Job = CoroutineScope(SupervisorJob()).launch {
            log("CoroutineScope(SupervisorJob()).launch")
        }
        CoroutineScope(Job()).launch {
            log("CoroutineScope(Job()).launch")
        }
        CoroutineScope(Dispatchers.IO).launch {
            log("CoroutineScope(Dispatchers.IO).launch")
        }
        CoroutineScope(Dispatchers.Main).launch {
            log("CoroutineScope(Dispatchers.Main).launch")
        }
        CoroutineScope(Dispatchers.Default).launch {
            log("CoroutineScope(Dispatchers.Default).launch")
        }
        vm.viewModelScope.launch {
            log("vm.viewModelScope.launch")
        }
        vm.viewModelScope.launch(Dispatchers.IO) {
            log("vm.viewModelScope.launch(Dispatchers.IO)")
        }
    }

    val vm: VM by viewModels()
    fun log(msg: String) {
        Log.e("SCOPE", "$msg - ${Thread.currentThread().name} \t ${System.currentTimeMillis()}")
    }

    val map = hashMapOf<String, Int>()
    fun HashMap<String, Int>.put2(key: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val num = map.getOrDefault(key, 0) + 1
            map[key] = num
        }
    }

    fun HashMap<String, Int>.pritln() {
        val currentTimeMillis = System.currentTimeMillis()
        map.forEach { (key, num) ->
            log("$currentTimeMillis \t\t $num \t $key")
        }
    }

    fun foreach(i: Int) {
        // 验证 ： launch也是会复用线程池
        if (i > 10) return
        GlobalScope.launch {
            foreach(i + 1)
            map.put2(Thread.currentThread().name)
            log("$i GlobalScope")
            delay(800)

            map.pritln()
        }
        // 统计个数
    }

    fun test() {
        log("test()")
        GlobalScope.launch {
            log("1. GlobalScope")
            GlobalScope.launch {
                log("2. GlobalScope")
                GlobalScope.launch {
                    log("3. GlobalScope")
                    GlobalScope.launch {
                        log("4. GlobalScope")
                        GlobalScope.launch {
                            log("5. GlobalScope")
                            GlobalScope.launch {
                                log("6. GlobalScope")
                            }
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                log("withContext Main")
            }
            withContext(Dispatchers.IO) {
                log("withContext IO")
            }
        }
        vm.viewModelScope.launch {
            log("viewModelScope")
        }
    }
}

class VM : ViewModel()