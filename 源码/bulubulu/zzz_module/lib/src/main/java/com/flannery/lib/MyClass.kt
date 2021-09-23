package com.flannery.lib


fun main() {
    MyClass.p()
    MyClass2.pt()
}


object MyClass {
    init {
        println("MyClass")
    }

    fun p() {
        println("p()")
    }
}


object MyClass2 {

    init {
        println("MyClass2")
    }

    fun pt() {
        println("pt")
    }

}


interface Apiclient {

}


fun test() {
    arrayListOf<String>().distinctBy {

    }
}

public inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val list = ArrayList<T>()
    for (e in this) {
        val key = selector(e)
        if (set.add(key))
            list.add(e)
    }
    return list
}