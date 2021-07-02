package com.flannery.edittextselection

import KAnimal
import KDog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.NullPointerException
import java.util.concurrent.Executors

/**
 * 1. runBlocking:T
 * 2. launch:Job
 * 3. async
 */
class MainActivity : AppCompatActivity() {

    val pool = Executors.newFixedThreadPool(4)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        FirstFragment()
//        SecondFragment()
//        ThirdFragment()
        supportFragmentManager.commit {
            add(R.id.container, FirstFragment())
        }
        l1("MyActivity , supportFragmentMananger $supportFragmentManager")

//        pool.execute {
//            test5()
//        }
//        test4()
//        pool.execute {
//            asyncReturn()
//        }
//        pool.execute {åå
//            test7()
//        }
//        asyncConcurrent()
//        test8()
//        m(MainActivity::class.java, "Hel")
//        m(, "Hel")
        L.m3(this, "Hello", "Hello", "World!!!!")

//        test10(this, "Hello World!")
//        test10(User(), "Hello World!")
//        test10(FirstFragment(), "Hello World!")
//        test10(SecondFragment(), "Hello World!")
//        test10(ThirdFragment(), "Hello World!")
//        test12()
//        test13()

//        test17()
    }

    fun test17() {
        l1("test17 start")
        l1("lifecycleScope.launch start")
        lifecycleScope.launch {
            l1("lifecycleScope.launch")
        }
        l1("lifecycleScope.launch end")

        l1("lifecycleScope.launchWhenResumed start")
        lifecycleScope.launchWhenResumed {
            l1("lifecycleScope.launchWhenResumed")
        }
        l1("lifecycleScope.launchWhenResumed end")

        l1("test17 end")
    }

    override fun onResume() {
        super.onResume()
        l1("onResume")
    }

    fun test14() {
        var animal: List<KAnimal> = ArrayList() // out 上界通配符
        var dogs = ArrayList<KDog>()
        animal = dogs
    }

    fun test13() {
        l1("test13 start")
        GlobalScope.launch {

        }
        val runCatch: Result<String> = kotlin.runCatching {
            l1("runCatching")
            //throw Exception("throw")
            "from runCatching!"
        }.onSuccess {
            l1("onSuccess")
        }.onFailure {
            l1("onFailure")
        }
        println("runCatch.getOrNull() ${runCatch.getOrNull()}")
        println("runCatch.getOrDefault ${runCatch.getOrDefault("default string")}")
        println("runCatch.exceptionOrNull() ${runCatch.exceptionOrNull()}")

        l1("test13 end")
    }


    // kotlin中GlobalScope类提供了几个创建协程的构造函数
    ////// launch：创建协程
    ////// async：创建带返回值的协程，返回的是Deferred类
    ////// withContext：不创建新的协程，指定协程上运行代码块
    ////// runBlocking：不是globalScope的API，可以独立使用，区别runBlocking里面的delay会阻塞线程，而launch创建的不会
    private fun test12() {
        // [111111111]
        // [222222222]
        // [333333333]
        // [444444444]
        l1("[111111111] test12 start")
        val launch: Job = GlobalScope.launch(Dispatchers.Main) {
            l1("[222222222] GlobalScope.launch start")
            val str = withContext(Dispatchers.IO) {
                l1("[333333333] withContext(Dispatchers.IO")

                repeat(10) {
                    delay(500)
                    l1("[333333333] repeat $it")
                }

                "Hello GlobalScope withContedt"
            }
            l1(str)
            l1("[222222222] GlobalScope.launch end")
        }
        l1("[111111111] test12 end ${launch.isActive}")
        l1("[111111111] test12 end ${launch.isCancelled}")
        l1("[111111111] test12 end ${launch.isCompleted}")
    }


    class User {
        fun getName(): String {
            return "User::getName"
        }
    }

    fun test10(o: Any?, str: String) {
        val javaClass = javaClass
        o?.run {
            m(javaClass, str)
        }

    }

    private fun findClassStackTraceElement(aClass: Class<*>?): StackTraceElement? {
        if (BuildConfig.DEBUG)
        //Thread.currentThread().getStackTrace()[4].getClassName()
        //cn.kuwo.pp.ui.discover.FriendMatchingDialog
        //aClass.getCanonicalName()
        //cn.kuwo.pp.ui.discover.FriendMatchingDialog
            for (stackTraceElement in Thread.currentThread().stackTrace) {
                if (stackTraceElement != null && aClass != null) {
                    val className = stackTraceElement.className
                    val canonicalName = aClass.canonicalName
                    if (!TextUtils.isEmpty(className)
                        && !TextUtils.isEmpty(canonicalName)
                        && className.startsWith(canonicalName)
                    ) {
                        return stackTraceElement
                    }
                }
            }
        return null
    }

    open fun m(aClass: Class<*>?, vararg o: String?) {
        if (BuildConfig.DEBUG) {
            if (aClass == null) {
                printLogString(null, *o)
            } else {
                val classStackTraceElement = findClassStackTraceElement(aClass)
//                if (classStackTraceElement == null && TextUtils.isEmpty(otherTag)) {
//                    otherTag = aClass.name
//                }
                printLogString(classStackTraceElement, *o)
            }
        }
    }


    // 打印log
    private fun printLogString(stackTraceElement: StackTraceElement?, vararg o: String?) {
        val sb = java.lang.StringBuilder()
        var fileName: String? = "L" //防止为空
        if (stackTraceElement != null) {
            fileName = stackTraceElement.fileName
            //val className = stackTraceElement.className
            val methodName = stackTraceElement.methodName
            val lineNumber = stackTraceElement.lineNumber
            sb.append(methodName).append("(").append(fileName).append(":").append(lineNumber)
                .append(")")
        }
        sb.append(Thread.currentThread().name).append(",")
        o.forEach { i -> if (i != null) sb.append(i).append(",") }
        //Log.i(fileName, methodName+"("+fileName+":"+lineNumber+")");
        Log.i("[KLog]", sb.toString())
    }

    // Job: 协程的唯一标识，用来控制协程的生命周期(new, active, completing, completed, canceling, cnaceled)
    // CoroutineDispatcher: 指定协程运行的线程(IO, Default, Main, Unconfined)
    // CoroutinName: 指定协程的名称，默认为coroutine
    // CoroutineExceptionHandler: 指定协程的异常处理器，用来处理未捕获的异常


    private fun test8() {
        val parentJob = GlobalScope.launch {
            // childJob 是一个supervisorJob
            l1("GlobalScope.launch")
            val childJob = launch(SupervisorJob()) {
                l1("launch(SupervisorJob())")
                throw  NullPointerException()
            }
            childJob.join()
            l1("parent complete!")
        }
        Thread.sleep(1000)
    }

    private fun asyncConcurrent() {
//        val job: Job = GlobalScope.launch { "" }
//        val async: Deferred<String> = GlobalScope.async { "" }
//
//        val start = job.start()
//        job.cancel()


        // coroutineContext 的创建
        val job4 = Job()
        job4.invokeOnCompletion {
            l1("invokeOnCompletion job4")
        }
        val coroutineContext = job4 +
                Dispatchers.Main +
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    l1("CoroutineExceptionHandler  $coroutineContext  $throwable")
                } +
                CoroutineName("asyncConcurrent")

        val launch = mScope.launch(coroutineContext) {
            val job1 = async(Dispatchers.IO) {
                delay(1000)
                l1("job1-finish")
                "job1-finish"
            }
            val job2 = async(Dispatchers.IO) {
                delay(1000)
                l1("job2-finish")
                "job2-finish"
            }
            val job3 = async(Dispatchers.IO) {
                delay(1000)
                l1("job3-finish")
                "job3-finish"
            }
            l1(job1.await() + job2.await() + job3.await())
        }
        launch.invokeOnCompletion {
            l1("invokeOnCompletion")
        }
    }


    /*
E/MainActivity: -------Coroutine-Async 线程name：DefaultDispatcher-worker-1 线程id：24021
E/MainActivity: Coroutine-Async 线程name：DefaultDispatcher-worker-2 线程id：24022
E/MainActivity: -------Coroutine-Async-2 线程name：main 线程id：2
E/MainActivity: Coroutine-Async-2 线程name：DefaultDispatcher-worker-2 线程id：24022
E/MainActivity: -------Coroutine-Async-3 线程name：kotlinx.coroutines.DefaultExecutor 线程id：24025
E/MainActivity: Coroutine-Async-3 线程name：DefaultDispatcher-worker-2 线程id：24022
E/MainActivity: -------Coroutine-Async-4 线程name：DefaultDispatcher-worker-1 线程id：24021
E/MainActivity: Coroutine-Async-4 线程name：DefaultDispatcher-worker-1 线程id：24021





     */
    private fun test7() {
        GlobalScope.launch {
            // 新开一个协程去执行协程体，父协程的代码会接着往下走
            var deffered: Deferred<String>
            var data: String

            deffered = async(Dispatchers.IO) {
                delay(1000)
                l1("-------Coroutine-Async")
                "Coroutine-Async"
            }
            data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Main) {
                delay(1000)
                l1("-------Coroutine-Async-2")
                "Coroutine-Async-2"
            }
            data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Unconfined) {
                delay(1000)
                l1("-------Coroutine-Async-3")
                "Coroutine-Async-3"
            }
            data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Default) {
                delay(1000)
                l1("-------Coroutine-Async-4")
                "Coroutine-Async-4"
            }
            data = deffered.await()
            l1("$data")
        }
    }


    private fun asyncReturn() {
        mScope.launch(Dispatchers.Main) {
            // 新开一个协程去执行协程体，父协程的代码会接着往下走
            var deffered = async(Dispatchers.IO) {
                delay(1000)
                "Coroutine-Async"
            }
            var data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Main) {
                delay(1000)
                "Coroutine-Async-2"
            }
            data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Unconfined) {
                delay(1000)
                "Coroutine-Async-3"
            }
            data = deffered.await()
            l1("$data")
            deffered = async(Dispatchers.Default) {
                delay(1000)
                "Coroutine-Async-4"
            }
            data = deffered.await()
            l1("$data")
        }
    }


    private val mScope = MainScope()

    // 获取一个协程作用域用于创建协程
    private fun test6() {
        l1("test() start")
        mScope.launch(Dispatchers.IO) {
            l1("mScope.launch start")
            val res = getStringInfo()
            //获取结果后主线程提示更新
            withContext(Dispatchers.Main) {
                l1(" withContext(Dispatchers.Main)")
                Toast.makeText(this@MainActivity, "test6 $res", Toast.LENGTH_SHORT).show()
            }
            l1("mScope.launch end")
        }
        l1("test() end")
    }
    // 1. 获取一个协程作用域于创建协程
    // 2。通过协程作用域launch方法启动新的协程任务
    //    1。 启动时可以指定执行线程
    //    2。 内部通过withContext方法实现切换线程
    // 3。 在onDestroy生命周期方法之后要手动取消

    private suspend fun getStringInfo(): String {
        l1("getStringInfo()")
        return withContext(Dispatchers.IO) {
            // 在着1000毫秒应该协程所处的线程不会阻塞
            l1("getStringInfo() withContext(Dispatchers.IO)")
            delay(1000)
            "Coroutine-launch"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mScope.cancel()
    }


    private fun test5() {
        l1("test4 start")
        GlobalScope.launch {
            l1("GlobalScope.aunch")
            val result1 = GlobalScope.async {
                pool.execute {
                    GlobalScope.launch {
                        getResult1()
                    }
                }
            }
            val result2 = GlobalScope.async {
                pool.execute {
                    GlobalScope.launch {
                        getResult2()
                    }
                }
            }
            val result = result1.await();// + result2.await()
            l1("result = $result")
        }
        l1("test4 end")
    }


    private fun test4() {
        l1("test4 start")
        GlobalScope.launch {
            l1("GlobalScope.aunch")
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async {
                getResult2()
            }
            val result = result1.await() + result2.await()
            l1("result = $result")
        }
        l1("test4 end")
    }

    private suspend fun getResult1(): Int {
        l1("getResult1 start")
        delay(3000)
        l1("getResult1 end")
        return 1
    }

    private suspend fun getResult2(): Int {
        l1("getResult2 start")
        delay(4000)
        l1("getResult2 end")
        return 2
    }


    private fun test3() {
        GlobalScope.launch(Dispatchers.Main) {
            val token = getToken();
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)
        }
        repeat(8) {
            Thread.sleep(500)
            l1("主线程执行$it")
        }
    }

    private fun setUserInfo(userInfo: String) {
        l1(userInfo)
    }

    private suspend fun getToken(): String {
        l1("getToken delay start")
        delay(2000)
        l1("getToken delay end")
        return "token"
    }

    private suspend fun getUserInfo(token: String): String {
        l1("getUserInfo delay start")
        delay(2000)
        l1("getUserInfo delay end")
        return "$token - userInfo"
    }


    // 1。 协程上下文
    //      Dispatcher.Main      : 在UI线程中执行
    //      Dispatcher.IO        : 在线程池中执行
    //      Dispatcher.Default   : 在线程池中执行
    //      Dispatcher.Unconfined: 在调用的线程直接执行
    // 2。 协程启动模式
    //      DEFAULT      : 默认的模式，立即执行协程体
    //      LAZY         : 只有在需要的情况下运行
    //      ATOMIC       : 立即执行协程体，但在开始运行之前无法取消
    //      UNDISPATCHED : 立即在当前执行协程体，直到第一个suspend调用
    // 3。 协程体
    private fun test2() {
        mylog("主线程id：${mainLooper.thread.id}")
        val job = GlobalScope.launch {
            delay(6000)
            mylog("协程执行结束 -- 线程id：${Thread.currentThread().id}")
        }
        mylog("执行线程结束")
    }


    private fun test1() {
        mylog("主线程id：${mainLooper.thread.id}")
        test()
        mylog("执行线程结束")
    }


    private fun l1(msg: String) =
        mylog("$msg 线程name：${Thread.currentThread().name} 线程id：${Thread.currentThread().id}")

    private fun mylog(msg: String) = Log.e(MainActivity::class.simpleName, msg)

    private fun test() = runBlocking {
        repeat(8) {
            //Log.e(MainActivity::class.simpleName, "协程执行$it 线程id：${Thread.currentThread().id}")
            mylog("协程执行$it 线程id：${Thread.currentThread().id}")
            delay(1000)
        }
    }


}