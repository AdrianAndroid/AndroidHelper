package com.joyy.kotlinlib

import kotlinx.coroutines.*

/**
 * Time:2021/8/27 14:00
 * Author: flannery
 * Description:
 */
class RunBlockingDemo

fun main() {
//    runBlocking {
//        test1()
//        delay(100000)
//    }
    test2()

//    while (true){}
    runBlocking { delay(1000 * 10) }
}

fun test2() {
//    CoroutineScope()
//    GlobalScope.launch {
//        withContext(Dispatchers.Main) { fetchDocs() }
//    }
}

suspend fun fetchDocs() {
    val result = get("hello")
    println("show ${Thread.currentThread().name}")
}

suspend fun get(url: String) = withContext(Dispatchers.IO) {
    println("withContext Dispatchers.IO ${Thread.currentThread().name}")
}

// 协程作用域有三种
// 1。
// 2。
// 3。

suspend fun test1() {
//    runBlocking {
//    }
    val scope = CoroutineScope(SupervisorJob())
    val job1: Job
    val job2: Job
    val job3: Job
    val job4: Job

//    for (i in 0..100) {
//        scope.launch {
//            delay(600)
//            println("for(i=${i}) ${Thread.currentThread().name}")
//        }
//    }
    for (i in 0..100) {
        GlobalScope.launch {
            delay(600)
            println("GlobalScope for(i=${i}) ${Thread.currentThread().name}")
        }
    }

    job1 = scope.launch {
        println("job1 ${Thread.currentThread().name}")
    }
    job2 = scope.launch {
        println("job2 ${Thread.currentThread().name}")
    }
    supervisorScope {
        job3 = launch {
            println("job3 ${Thread.currentThread().name}")
        }
        job4 = launch {
            println("job4 ${Thread.currentThread().name}")
        }
    }
    GlobalScope.launch {
        println("GlobalScope ${Thread.currentThread().name}")
    }
    scope.launch {
        println("job5 ${Thread.currentThread().name}")
    }
}