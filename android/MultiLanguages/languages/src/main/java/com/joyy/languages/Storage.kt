package com.joyy.languages

import android.content.Context
import java.util.*

/**
 * Time:2021/9/16 15:33
 * Author: flannery
 * Description:
 */
interface Storage {
    fun save(ctx: Context, local: Locale)
    fun get(ctx: Context): Locale
    fun clear(ctx: Context)
}