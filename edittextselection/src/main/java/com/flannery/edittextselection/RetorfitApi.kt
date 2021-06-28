//package com.flannery.edittextselection
//
//import android.os.Bundle
//import android.os.PersistableBundle
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.coroutines.*
//import java.io.IOException
//import java.net.ConnectException
//import kotlin.coroutines.CoroutineContext
//
///**
// * Time:2021/6/28 15:02
// * Author:
// * Description:
// */
//interface Api {
//    @POST("user/login")
//    suspend fun login(): Call<User>
//}
//
//class RetrofitCoroutineDSL<T> {
//    val api: (Call<Result<T>>)? = null
//    internal var onSuccess: ((T) -> Unit)? = null
//        private set
//    internal var onFail: ((msg: String, errorCode: Int) -> Unit)? = null
//        private set
//    internal var onComplete: (() -> Unit)? = null
//        private set
//
//    /**
//     * 获取数据成功
//     */
//    fun onSuccess(block: (T) -> Unit) {
//        this.onSuccess = block
//    }
//
//    // 获取数据失败
//    fun onFail(block: (msg: String, errorCode: Int) -> Unit) {
//        this.onFail = block
//    }
//
//    // 询问完成
//    fun onComplete(block: () -> Unit) {
//        this.onComplete = block
//    }
//
//    internal fun clean() {
//        onSuccess = null
//        onComplete = null
//        onFail = null
//    }
//}
//
//class User {
//
//}
//
//class Call<T> {
//    fun execute() {}
//    fun cancel() {}
//}
//
//annotation class POST(val value: String)
//
//fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit) {
//    // 在主线程中开启协程
//    this.launch(Dispatchers.Main) {
//        val coroutine = RetrofitCoroutineDSL<T>().apply(dsl)
//        coroutine.api?.let { call ->
//            // async 并发执行，在IO线程中
//            val deffered = async(Dispatchers.IO) {
//                try {
//                    call.execute() // 已经在io线程中了，所以调用Retrofit的同步方法
//                } catch (e: ConnectException) {
//                    coroutine.onFail?.invoke("网络🔗出错", -1)
//                    null
//                } catch (e: IOException) {
//                    coroutine.onFail?.invoke("未知网络错误", -1)
//                    null
//                }
//            }
//            // 当协程取消的时候，取消网络请求
//            deffered.invokeOnCompletion {
//                if(deffered.isCancelled) {
//                    call.cancel()
//                    coroutine.clean()
//                }
//            }
//            // await 等待异步执行执行的结果
//            val response = deffered.await()
//            if (response == null) {
//                coroutine.onFail?.invoke("返回为空", -1)
//            } else {
////                response.let {
////                    if (response.isSuccessful) {
////                        //访问接口成功
////                        if (response.body()?.status == 1) {
////                            //判断status 为1 表示获取数据成功
////                            coroutine.onSuccess?.invoke(response.body()!!.data)
////                        } else {
////                            coroutine.onFail?.invoke(response.body()?.msg ?: "返回数据为空", response.code())
////                        }
////                    } else {
////                        coroutine.onFail?.invoke(response.errorBody().toString(), response.code())
////                    }
////                }
//            }
//            coroutine.onComplete?.invoke()
//        }
//    }
//}
//
//
//open class BBBaseActivity : AppCompatActivity(), CoroutineScope {
//
//    private lateinit var job: Job
//
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job
//
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        job = Job()
//    }
//
//    fun test() {
//        retrofit<User> {
//            // api = RetrofitCreater.create(Api::class.java).login()
//            onSuccess {
//                "success"
//            }
//            onFail { msg, _ ->
//                "error"
//            }
//            onComplete {
//                "onComplete"
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // 关闭页面后，结束所有协程任务
//        job.cancel()
//    }
//}
//
//
//
//
//
//
