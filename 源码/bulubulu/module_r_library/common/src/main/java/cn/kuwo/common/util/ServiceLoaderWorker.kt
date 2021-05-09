package cn.kuwo.common.util

import java.util.*
import java.util.Iterator

object ServiceLoaderWorker {

    fun <S> load(service: Class<S>): S? {
        return try {
            ServiceLoader.load(service).iterator().next()
        } catch (e: Exception) {
            null
        }
    }

    fun <S> loads(service: Class<S>): MutableIterator<S>? {
        return try {
            ServiceLoader.load(service).iterator()
        } catch (e: Exception) {
            null
        }
    }

}