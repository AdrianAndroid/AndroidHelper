package cn.kuwo.common.util

import java.util.*
import kotlin.collections.ArrayList

object ArrayUtils {


    fun <T, K> distinctBy(hashSet: HashSet<K>, list: ArrayList<T>, selector: (T) -> K) {
        hashSet.clear() //清空
        val tmpList = ArrayList<T>()
        for (t in list) {
            val key = selector(t)
            if (hashSet.add(key)) {
                tmpList.add(t)
            }
        }
        list.clear()
        list.addAll(tmpList)
    }

    fun <T, K> distinctBy(list: ArrayList<T>, selector: (T) -> K) {
        val hashSet = HashSet<K>()
        distinctBy(hashSet, list, selector)
    }

    // 创建一个限定容量的List数组
    @SafeVarargs
    fun <T> asList(vararg a: T): List<T> {
        val arrayList = ArrayList<T>(a.size)
        arrayList.addAll(a)
        return arrayList
    }

    fun <T> join(str: CharSequence, list: Iterable<T>, filter: ((T) -> CharSequence)): String {
        return list.joinToString(str, transform = filter)
    }

//    fun join(var0: CharSequence, var1: Iterable<CharSequence?>): String? {
//        Objects.requireNonNull(var0)
//        Objects.requireNonNull(var1)
//        val var2 = StringJoiner(var0)
//        val var3: Iterator<*> = var1.iterator()
//        while (var3.hasNext()) {
//            val var4 = var3.next() as CharSequence
//            var2.add(var4)
//        }
//        return var2.toString()
//    }

}