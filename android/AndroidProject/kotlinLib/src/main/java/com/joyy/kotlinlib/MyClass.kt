package com.joyy.kotlinlib

class Command(val name: Any, val invokeMethod: (() -> Unit)) {
    override fun toString(): String {
        return "Command(name=$name, invokeMethod=$invokeMethod)"
    }
}

class MyClass {

    //    val list = ArrayList<Command>()
    val hashMap = hashMapOf<String, () -> String?>()


    fun add() {
//        list.add(Command("test_one") { println("test_one") })
//        list.add(Command("test_two") { println("test_two") })
//        list.add(Command("test_three") { println("test_three") })
        val key_0 = "test_one"
        val key_1 = "test_two"
        val key_2 = "test_thr"


        hashMap[key_0] = { "test_0" }
        hashMap[key_1] = { "test_1" }
        hashMap[key_2] = { "test_2" }
    }

    fun invoke() {
        hashMap.forEach { (k, v) ->
            //println("k = $k , v = $v")
            println(v())
        }
//        for (func in list) {
//            println(func)
//            func.invokeMethod()
//        }
    }

    // 1. JS调用
    // 2。JS调用取回值
    // 3。JAVA调用        X
    // 4。 JAVA调用取回值  X
}

fun main() {
    println("Hello World")
    // 想干什么
    // 1. List方法
    // 2。 打印方法名称
    // 3。调用方法
    val my = MyClass()
    my.add()
    my.invoke()
}