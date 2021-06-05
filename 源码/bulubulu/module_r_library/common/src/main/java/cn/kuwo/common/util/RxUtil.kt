package cn.kuwo.common.util

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

//主线程执行
fun runOnUIThread(runnable: Runnable?): Disposable {
    return Observable.just("")
        .observeOn(AndroidSchedulers.mainThread())//执行结果在主线程运行
        .subscribe { _ ->
            runnable?.run()
        }
}

/**
 * 延时主线程执行主线程执行
 * @param delay 延时时间，单位ms
 */
fun runOnUIThread(runnable: Runnable?, delay: Long): Disposable {
    return Observable.just("")
        .delay(delay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())//执行结果在主线程运行
        .subscribe { _ ->
            runnable?.run()
        }
}

//子线程执行
fun runOnIOThread(runnable: Runnable?) {
    Observable.just("")
        .observeOn(Schedulers.io())
        .subscribe { _ ->
            runnable?.run()
        }
}

//异步执行，主线程回调
fun asyncRun(runnable: Runnable?, callback: Runnable?) {
    Observable.create(ObservableOnSubscribe<String> {
        runnable?.run()
        it.onNext("")
    }).subscribeOn(Schedulers.io())//异步任务在IO线程执行
        .observeOn(AndroidSchedulers.mainThread())//执行结果在主线程运行
        .subscribe { _ ->
            callback?.run()
        }
}

//异步执行，主线程回调
fun <T> asyncRun(callable: Callable<T>, onNext: Consumer<T>) {
    Observable.create(ObservableOnSubscribe<T> {
        it.onNext(callable.call())
    }).subscribeOn(Schedulers.io())//异步任务在IO线程执行
        .observeOn(AndroidSchedulers.mainThread())//执行结果在主线程运行
        .subscribe(onNext)
}

//异步执行
fun asyncRun(runnable: Runnable?) {
    asyncRun(runnable, null)
}
