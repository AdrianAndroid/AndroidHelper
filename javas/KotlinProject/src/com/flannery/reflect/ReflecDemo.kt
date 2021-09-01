package com.flannery.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction0
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

class ReflecDemo {
}

var y = 1

class A(val p: Int)

class Foo

fun function(factory: () -> Foo) {
    val x: Foo = factory()
}

data class Data(var open: MutableList<String> = mutableListOf(), var close: MutableList<String> = mutableListOf())

fun getInstance(param: KClass<*>): Any {
    val pa: MutableList<String> = mutableListOf()
    pa.add("open data")
    val pua = mutableListOf<String>()
    pa.add("close data")

    val allData: HashMap<String, Any> = hashMapOf()
    allData["close"] = pua
    allData["open"] = pa

    // 创建对象
    val instance = param.createInstance()
    val pro = param.declaredMemberProperties
    pro.forEach {
        // 设置为可修改类型
        val kmp = it as KMutableProperty1<Any, Any?>
        kmp.set(instance, allData[it.name])
    }
    return instance
}

open class CallBack {
    open fun sayHi() {
        println("Hi~")
    }
}

class MyCallBack : CallBack() {
    override fun sayHi() {
        println("Hello~")
    }
}

fun getInstance2(param: KClass<*>): Any {
    val createInstance = param.createInstance()

    return createInstance
}


fun main() {
    ::y.set(2)
    println(y)
    var strs = listOf("a", "bc", "def")
    println(strs.map(String::length))

    println(A::p.javaGetter)
    println(A::p.javaField)
    val factory: KFunction0<Foo> = ::Foo
    function(factory)


    val data = getInstance(Data::class) as Data
    println(data)

    val myCallBack = getInstance2(MyCallBack::class) as CallBack
    myCallBack.sayHi()
    println(myCallBack)
}