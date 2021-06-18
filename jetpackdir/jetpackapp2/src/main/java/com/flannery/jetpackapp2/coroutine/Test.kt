package com.flannery.jetpackapp2.coroutine

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureTimeMillis

//https://juejin.cn/post/6908271959381901325
object Test {

    fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")

    fun main() {
        // GlobalScope 全局作用域
        GlobalScope.launch(context = Dispatchers.IO) {
            //延迟一秒
            delay(1000)
            log("launch")
        }
        // 主动休眠两秒，防止JVM过快推出
        Thread.sleep(2000)
        log("end")
    }
    //[DefaultDispatcher-worker-1 @coroutine#1] launch
    //[main] end

    // 协程的四个基础概念
    // * suspend function。
    //     即挂起函数，delay函数就是协程库提供的一个用于实现非阻塞式延时的挂起函数。
    // * CoroutineScope。
    //     即协程作用域，GlobalScope是CoroutineScope的一个实现类，用于指定协程的作用范围，
    //     可用于管理多个协程的生命周期，所有协程都需要通过CoroutineScope来启动
    // * CoroutineContext。
    //     即协程上下文，包含多正类型的配置参数。
    //     Dispatchers.IO是CoroutineContext这个抽象概念的一种实现，用于指定协程的运行载体
    //     ，即用于指定协程要运行在哪类线程上
    // * CoroutineBuilder。
    //     即协程构建器，协程在CoroutineScope的上下文通过launch、async等协程构建起来进行声明并启动。
    //     launch、async等均被声明CoroutineScope的扩展方法

    // 四、suspend function的挂起与恢复
    // * suspend 用于暂停执行当前协程，并保存所有局部变量
    // * resume  用于让已暂停的协程从暂停处继续执行
    suspend fun fetchDocs() {                                       // Dispatchers.Main
        val result = get("https://devveloper.android.com")  // Dispatchers.IO for 'get'
        //show(result)                                      // Dispatchers.Main
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {/* ..网络请求.. */ }

    // Android平台上协程主要就用来解决两个问题
    // 1. 处理耗时任务（Long running tasks），这种任务常常会阻塞住主线程
    // 2. 保证主线程安全（Main-safety），即确保安全地从主线程调用任何suspend函数

    // 五、CoroutineScope 协程作用域
    fun test() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(300)
        }
            .cancel() //取消作用域
    }

    // CorountineScope大体上可以分为三种
    // * GlobalScope
    //    即全局协程作用域，在这个范围内启动的协程可以一直运行直到应用停止运行。
    //    GlobalScope本身不会阻塞当前线程，且启动的协程相当于守护线程，不会阻止JVM结束运行
    // * runBlocking
    //    一个顶层函数，和GlobalScope不一样，它会阻塞当前线程直到内部所有相同作用域的协程运行结束
    // * 自定义CoroutineScope
    //    可用于实现自动控制协程的生命周期范围，对于Android开发来说最大意义之一就是可以避免内存泄漏。

    // 1. GlobalScope
    fun main2() {
        log("start")
        GlobalScope.launch {
            launch {
                delay(400)
                log("launch A")
            }
            launch {
                delay(300)
                log("launch B")
            }
            log("GlobalScope")
        }
        log("end")
        Thread.sleep(500)
    }
    //[main] start
    //[main] end
    //[DefaultDispatcher-worker-1 @coroutine#1] GlobalScope
    //[DefaultDispatcher-worker-3 @coroutine#3] launch B
    //[DefaultDispatcher-worker-3 @coroutine#2] launch A


    // 2. runBlocking
    // public fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
    // runBlocking 本身带有阻塞线程的意味，但其内部运行的协程又是非阻塞的，读者需要意会这两者的区别
    fun main3() {
        log("start")
        runBlocking {
            launch {
                repeat(3) {
                    delay(100)
                    log("launchA - $it")
                }
            }
            launch {
                repeat(3) {
                    delay(100)
                    log("launchB - $it")
                }
            }
            GlobalScope.launch {
                repeat(3) {
                    log("GlobalScope - $it")
                }
            }
        }
        log("end")
    }
    //[main] start
    //[main] launchA - 0
    //[main] launchB - 0
    //[DefaultDispatcher-worker-1] GlobalScope - 0
    //[main] launchA - 1
    //[main] launchB - 1
    //[DefaultDispatcher-worker-1] GlobalScope - 1
    //[main] launchA - 2
    //[main] launchB - 2
    //[main] end

    //基于是否会阻塞线程的区别，以下代码中runBlocking会早于GlobalScope输出日志
    fun main4() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(600)
            log("GlobalScope")
        }
        runBlocking {
            delay(500)
            log("runBlocking")
        }
        //主动休眠两百毫秒， 使得和runBlocking加起来的延迟时间少于六百毫秒
        Thread.sleep(200)
        log("after sleep")
    }
    //[main] runBlocking
    //[DefaultDispatcher-worker-1] GlobalScope
    //[main] after sleep

    // 3. corountineScope 独立的协程作用域
    //     runBlocking和coroutineScope看起来很像，因为它们都需要等待其内部所有相同作用域的协程结束后才会结束自己
    //     两者主要区别在于runBlocking方法会阻塞当前线程，而coroutineScope不会阻塞线程，而是会挂起并释放底层线程以供其他协程使用。
    //     由于这个差别，runBlocking是一个普通函数，而coroutineScope是一个挂起函数

    fun main5() = runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                log("Task from ensted launch")
            }
            delay(100)
            log("Task from coroutine scope")
        }
        log("Coroutine scope is over")
    }
    //[main] Task from coroutine scope
    //[main] Task from runBlocking
    //[main] Task from nested launch
    //[main] Coroutine scope is over

    // 4. supervisorScope
    // supervisorScope函数用于创建一个使用了SupervisorJob的coroutineScope
    // ，该作用域的特点就是抛出的异常不会连锁取消同级协程和父协程
    fun main6() = runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        supervisorScope {
            launch {
                delay(500)
                log("Task throw Exception")
                throw Exception("failed")
            }
            launch {
                delay(600)
                log("Task from nested launch")
            }
        }
        log("Coroutine scope is over")
    }
    //[main @coroutine#2] Task from runBlocking
    //[main @coroutine#3] Task throw Exception
    //[main @coroutine#4] Task from nested launch
    //[main @coroutine#1] Coroutine scope is over

    // 5. 自定义CoroutineScope
    private val mainScope = MainScope();
    fun onCreate() {
        mainScope.launch {
            repeat(5) {
                delay(1000L * it)
            }
        }
    }

    fun onDestroy() {
        mainScope.cancel()
    }

    class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

        fun onCreate() {
            launch {
                repeat(5) {
                    delay(200L * it)
                    log(it)
                }
            }
            log("Activity Created")
        }

        fun onDestory() {
            cancel()
            log("Activity Destroyd")
        }

    }

    fun main7() = runBlocking {
        val activity = Activity()
        activity.onCreate()
        delay(1000)
        activity.onDestory()
        delay(1000)
    }
    //[main @coroutine#1] Activity Created
    //[DefaultDispatcher-worker-1 @coroutine#2] 0
    //[DefaultDispatcher-worker-1 @coroutine#2] 1
    //[DefaultDispatcher-worker-1 @coroutine#2] 2
    //[main @coroutine#1] Activity Destroyed

    // 六、CoroutineBuilder
    //  public fun CoroutineScope.launch(
    //      context: CoroutineContext = EmptyCoroutineContext,
    //      start: CoroutineStart = CoroutineStart.DEFAULT,
    //      block: suspend CoroutineScope.() -> Unit
    //  ): Job

    // launch 函数共包含三个参数
    //  1 context 用于指定协程的上下文
    //  2 start   用于指定协程的启动方式。默认值为CoroutineStart.DEFAULT，即协程会在声明的同时就立即进入等待调度的状态，即可以立即执行的状态。可以通过将其设置为CoroutineStart.LAZY 来实现延迟启动，即懒加载
    //  3 block   用于传递协程的执行体，即希望交由协程执行的任务

    // 可以看到launchA和launchB是并行交叉执行的
    fun main8() = runBlocking {
        val launchA = launch {
            repeat(3) {
                delay(100)
                log("launchA - $it")
            }
        }
        val launchB = launch {
            repeat(3) {
                delay(100)
                log("launchB - $it")
            }
        }
    }
    //[main] launchA - 0
    //[main] launchB - 0
    //[main] launchA - 1
    //[main] launchB - 1
    //[main] launchA - 2
    //[main] launchB - 2

    // 2. Job 是协程的句柄
    // 使用launch 和 async创建的每个协程都会返回一个Job实例
    interface B {
        //当 Job 处于活动状态时为 true
        //如果 Job 未被取消或没有失败，则均处于 active 状态
        public val isActive: Boolean

        //当 Job 正常结束或者由于异常结束，均返回 true
        public val isCompleted: Boolean

        //当 Job 被主动取消或者由于异常结束，均返回 true
        public val isCancelled: Boolean

        //启动 Job
        //如果此调用的确启动了 Job，则返回 true
        //如果 Job 调用前就已处于 started 或者是 completed 状态，则返回 false
        public fun start(): Boolean

        //用于取消 Job，可同时通过传入 Exception 来标明取消原因
        public fun cancel(cause: CancellationException? = null)

        //阻塞等待直到此 Job 结束运行
        public suspend fun join()

        //当 Job 结束运行时（不管由于什么原因）回调此方法，可用于接收可能存在的运行异常
        public fun invokeOnCompletion(handler: CompletionHandler): DisposableHandle
    }

    // State                          isActive   isCompleted   iCancelled
    // New(optional initial state)    false      false         false
    // Active(default initial state)  true       false         false
    // Completing (transient state)   true       false         false
    // Cancelling (transient)         false      false         true
    // Cancelled (final state)        false      true          true
    // Completed (final state)        false      true          false

    fun main9() {
        // 将协程设置为延迟启动
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            for (i in 0..100) {
                // 每循环一次均延迟一百毫秒
                delay(100)
            }
        }
        job.invokeOnCompletion {
            log("invokeOnCompletion:$it")
        }
        log("1. job.isActive：${job.isActive}")
        log("1. job.isCancelled：${job.isCancelled}")
        log("1. job.isCompleted：${job.isCompleted}")

        job.start()

        log("2. job.isActive：${job.isActive}")
        log("2. job.isCancelled：${job.isCancelled}")
        log("2. job.isCompleted：${job.isCompleted}")

        //休眠四百毫秒后再主动取消协程
        Thread.sleep(400)
        job.cancel(CancellationException("test"))

        //休眠四百毫秒防止JVM过快停止导致 invokeOnCompletion 来不及回调
        Thread.sleep(400)

        log("3. job.isActive：${job.isActive}")
        log("3. job.isCancelled：${job.isCancelled}")
        log("3. job.isCompleted：${job.isCompleted}")
    }

    //[main] 1. job.isActive：false
    //[main] 1. job.isCancelled：false
    //[main] 1. job.isCompleted：false
    //[main] 2. job.isActive：true
    //[main] 2. job.isCancelled：false
    //[main] 2. job.isCompleted：false
    //[DefaultDispatcher-worker-2] invokeOnCompletion：java.util.concurrent.CancellationException: test
    //[main] 3. job.isActive：false
    //[main] 3. job.isCancelled：true
    //[main] 3. job.isCompleted：true

    // 3. async
    // aysnc函数的方法签名
    //public fun <T> CoroutineScope.async(
    //    context: CoroutineContext = EmptyCoroutineContext,
    //    start: CoroutineStart = CoroutineStart.DEFAULT,
    //    block: suspend CoroutineScope.() -> T
    //): Deferred<T>
    fun main10() {
        val time = measureTimeMillis {
            runBlocking {
                val asyncA = async {
                    delay(3000)
                    1
                }
                val asyncB = async {
                    delay(4000)
                    2
                }
                log(asyncA.await() + asyncB.await())
            }
        }
        log(time)
    }
    //[main] 3
    //[main] 4070

    //4. async的错误用法
    fun main11() {
        val time = measureTimeMillis {
            runBlocking {
                val asyncA = async(start = CoroutineStart.LAZY) {
                    delay(3000)
                    1
                }
                val asyncB = async(start = CoroutineStart.LAZY) {
                    delay(4000)
                    2
                }
                log(asyncA.await() + asyncB.await())
            }
        }
        log(time)
    }
    //[main] 3
    //[main] 7077

    //第一个实现
    //  asyncA.start()
    //  asyncB.start()
    //  log(asyncA.await() + asyncB.await())


    // 5. async 进行分解
    suspend fun fetchTwoDocs() = coroutineScope {
        val deferredOne = async { fetchDocs() }
        val deferredTwo = async { fetchDocs() }
        deferredOne.await()
        deferredTwo.await()
    }

    // 还可以对集合使用awaitAll
    suspend fun fetchTwoDocs2() =        // called on any Dispatcher (any thread, possibly Main)
        coroutineScope {
            val deferreds = listOf(     // fetch two docs at the same time
                async { fetchDocs() },  // async returns a result for the first doc
                async { fetchDocs() }   // async returns a result for the second doc
            )
            deferreds.awaitAll()        // use awaitAll to wait for both network requests
        }


    // 6 Deferred

    // 七 CoroutineContext 使用以下元素集定义协程的行为：
    //    Job: 控制协程的生命周期
    //    CoroutineDispatcher: 将工作分派到适当的线程
    //    CoroutineName: 协程的名称，可用于调试
    //    CoroutineExceptionHandler: 处理未捕获的异常
    // 1. Job
    fun main12() = runBlocking {
        val job = launch {
            log("My job is ${coroutineContext[Job]}")
        }
        log("My job is $job")
    }
    //[main @coroutine#1] My job is "coroutine#2":StandaloneCoroutine{Active}@75a1cd57
    //[main @coroutine#2] My job is "coroutine#2":StandaloneCoroutine{Active}@75a1cd57

    public val CoroutineScope.isActive: Boolean
        get() = coroutineContext[Job]?.isActive ?: true

    // 2.CoroutineDispatcher
    // Dispatchers.Main - 使用此调度程序可在 Android 主线程上运行协程。此调度程序只能用于与界面交互和执行快速工作。示例包括调用 suspend 函数、运行 Android 界面框架操作，以及更新 LiveData 对象
    // Dispatchers.IO - 此调度程序经过了专门优化，适合在主线程之外执行磁盘或网络 I/O。示例包括使用 Room 组件、从文件中读取数据或向文件中写入数据，以及运行任何网络操作
    // Dispatchers.Default - 此调度程序经过了专门优化，适合在主线程之外执行占用大量 CPU 资源的工作。用例示例包括对列表排序和解析 JSON
    fun main13() = runBlocking<Unit> {
        launch {
            log("main runBlocking")
        }
        launch(Dispatchers.Default) {
            log("Default")
        }
        launch(Dispatchers.IO) {
            log("IO")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
        }
    }
    //[DefaultDispatcher-worker-1 @coroutine#3] Default
    //[DefaultDispatcher-worker-2 @coroutine#4] IO
    //[MyOwnThread @coroutine#5] newSingleThreadContext
    //[main @coroutine#2] main runBlocking

    // 3 withContext
    suspend fun fetchDocs3() {
        val result = get("developer.android.com");
//        show(result)
    }

    // 4 CoroutineName
    fun main14() = runBlocking<Unit>(CoroutineName("RunBlocking")) {
        log("start")
        launch(CoroutineName("MainCoroutine")) {
            launch(CoroutineName("Coroutine#A")) {
                delay(400)
                log("launch A")
            }
            launch(CoroutineName("Coroutine#B")) {
                delay(300)
                log("launch B")
            }
        }
    }
    //[main @RunBlocking#1] start
    //[main @Coroutine#B#4] launch B
    //[main @Coroutine#A#3] launch A

    //5. CoroutineExceptionHandler

    //6. 组合上下文元素
    fun main15() = runBlocking<Unit> {
        launch(Dispatchers.Default + CoroutineName("test")) {
            log("Hello World!")
        }
    }
    //[DefaultDispatcher-worker-1 @test#2] Hello World

    // 八、取消协程
    //job.cancel()就用于取消协程，job.join()用于阻塞等待协程运行结束。
    fun main16() = runBlocking {
        val job = launch {
            repeat(1000) { i ->
                log("job: I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L)
        log("main: I'm tired of waiting!")
        job.cancel()
        job.join()
        log("main: Now I can quit.")
    }
    //[main] job: I'm sleeping 0 ...
    //[main] job: I'm sleeping 1 ...
    //[main] job: I'm sleeping 2 ...
    //[main] main: I'm tired of waiting!
    //[main] main: Now I can quit.

    // 1。 协程可能无法取消
    fun main18() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    log("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        log("main: I'm tired of waiting!")
        job.cancelAndJoin()
        log("main: Now I can quit.")
    }
    //[DefaultDispatcher-worker-1] job: I'm sleeping 0 ...
    //[DefaultDispatcher-worker-1] job: I'm sleeping 1 ...
    //[DefaultDispatcher-worker-1] job: I'm sleeping 2 ...
    //[main] main: I'm tired of waiting!
    //[DefaultDispatcher-worker-1] job: I'm sleeping 3 ...
    //[DefaultDispatcher-worker-1] job: I'm sleeping 4 ...
    //[main] main: Now I can quit.

    //isActive 判断是否还处于可运行状态
    fun main19() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        log("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                } else {
                    return@launch
                }
            }
        }
        delay(1300L)
        log("main: I'm tired of waiting!")
        job.cancelAndJoin()
        log("main: Now I can quit.")
    }

    // 2. 用finally 释放资源
    fun main20() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    log("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } catch (e: Throwable) {
                log(e.message)
            } finally {
                log("job: I'm running finally")
            }
        }

        delay(1300L)
        log("main: I'm tired of waiting")
        job.cancelAndJoin()
        log("main: Now I can quit")
    }
    //[main] job: I'm sleeping 0 ...
    //[main] job: I'm sleeping 1 ...
    //[main] job: I'm sleeping 2 ...
    //[main] main: I'm tired of waiting!
    //[main] StandaloneCoroutine was cancelled
    //[main] job: I'm running finally
    //[main] main: Now I can quit.

    // 3. NonCancellable
    fun main21() = runBlocking {
        log("start")
        val launchA = launch {
            try {
                repeat(5) {
                    delay(50)
                    log("launchA-$it")
                }
            } finally {
                delay(50)
                log("launchA isCompleted")
            }
        }
        val launchB = launch {
            try {
                repeat(5) {
                    delay(50)
                    log("launchB-$it")
                }
            } finally {
                withContext(NonCancellable) {
                    delay(50)
                    log("launchB isCompleted")
                }
            }
        }
        // 延时一百毫秒，保证两个协程都已经被启动了
        delay(200)
        launchA.cancel()
        launchB.cancel()
        log("end")
    }
    //[main] start
    //[main] launchA-0
    //[main] launchB-0
    //[main] launchA-1
    //[main] launchB-1
    //[main] launchA-2
    //[main] launchB-2
    //[main] end
    //[main] launchB isCompleted

    // 4。 父协程和子协程
    fun main22() = runBlocking {
        val parentJob = launch {
            repeat(3) { i ->
                launch {
                    delay((i + 1) * 200L)
                    log("Coroutine $i is done")
                }
                log("request: I'm done and I don't explicitly join my children that are still active")
            }
        }
    }
    //[main @coroutine#2] request: I'm done and I don't explicitly join my children that are still active
    //[main @coroutine#3] Coroutine 0 is done
    //[main @coroutine#4] Coroutine 1 is done
    //[main @coroutine#5] Coroutine 2 is done

    // 5. 传播取消操作
    fun main23() = runBlocking {
        val request = launch {
            val job1 = launch {
                repeat(10) {
                    delay(300)
                    log("job1: $it")
                    if (it == 2) {
                        log("job1 canceled")
                        cancel()
                    }
                }
            }
            val job2 = launch {
                repeat(10) {
                    delay(300)
                    log("job2: $it")
                }
            }
        }
        delay(1600)
        log("parent job canceled")
        request.cancel()
        delay(1000)
    }
    //[main @coroutine#3] job1: 0
    //[main @coroutine#4] job2: 0
    //[main @coroutine#3] job1: 1
    //[main @coroutine#4] job2: 1
    //[main @coroutine#3] job1: 2
    //[main @coroutine#3] job1 canceled
    //[main @coroutine#4] job2: 2
    //[main @coroutine#4] job2: 3
    //[main @coroutine#4] job2: 4
    //[main @coroutine#1] parent job canceled

    // 6. withTimeout
    fun main25() = runBlocking {
        log("start")
        val result = withTimeout(300) {
            repeat(5) {
                delay(100)
            }
            200
        }
        log(result)
        log("end")
    }
    //[main] start
    //Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 300 ms
    //	at kotlinx.coroutines.TimeoutKt.TimeoutCancellationException(Timeout.kt:186)
    //	at kotlinx.coroutines.TimeoutCoroutine.run(Timeout.kt:156)
    //	at kotlinx.coroutines.EventLoopImplBase$DelayedRunnableTask.run(EventLoop.common.kt:497)
    //	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:274)
    //	at kotlinx.coroutines.DefaultExecutor.run(DefaultExecutor.kt:69)
    //	at java.lang.Thread.run(Thread.java:748)

    // 九、 异常处理
    //    取消它自己的子级
    //    取消它自己
    //    将异常传播并传递给它的父级

    fun main27() = runBlocking {
        val launchA = launch {
            delay(1000)
            1 / 0
        }
        val launchB = launch {
            try {
                delay(1300)
                log("launchB")
            } catch (e: CancellationException) {
                e.printStackTrace()
            }
        }
        launchA.join()
        launchB.join()
    }
    //kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=BlockingCoroutine{Cancelling}@5eb5c224
    //Caused by: java.lang.ArithmeticException: / by zero
    //	at coroutines.CoroutinesMainKt$main$1$launchA$1.invokeSuspend(CoroutinesMain.kt:11)
    //	···
    //Exception in thread "main" java.lang.ArithmeticException: / by zero
    //	at coroutines.CoroutinesMainKt$main$1$launchA$1.invokeSuspend(CoroutinesMain.kt:11)
    //	···

    // 1. CoroutineExceptionHandler
    fun main28() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) {
            throw ArithmeticException()
        }
        joinAll(job, deferred)
    }
    //[DefaultDispatcher-worker-2] Caught java.lang.AssertionError


    // 2. SupervisorJob
    fun main29() = runBlocking {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                log("First child is failing")
                throw AssertionError("First child is cancelled")
            }
            val secondChild = launch {
                firstChild.join()
                log("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    log("Second child is cancelled because supervisor is cancelled")
                }
            }
            firstChild.join()
            log("Cancelling supervisor")
            //取消所有协程
            supervisor.cancel()
            secondChild.join()
        }
    }
    //[main] First child is failing
    //[main] First child is cancelled: true, but second one is still active
    //[main] Cancelling supervisor
    //[main] Second child is cancelled because supervisor is cancelled



}
