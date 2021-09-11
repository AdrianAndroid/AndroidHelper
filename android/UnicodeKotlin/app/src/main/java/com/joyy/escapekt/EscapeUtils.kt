package com.joyy.escapekt

import java.util.Collections
import kotlin.collections.HashMap


/**
 * Time:2021/9/11 14:34
 * Author: flannery
 * Description:
 */
object EscapeUtils {

    val JAVA_CTRL_CHARS_ESCAPE by lazy {
        Collections.unmodifiableMap(HashMap<CharSequence, CharSequence>().apply {
            put("\b", "\\b")
            put("\n", "\\n")
            put("\t", "\\t")
            //put("\f", "\\f")
            put("\r", "\\r")
        })

    }

    val ESCAPE_JAVA by lazy {
        val escapeJavaMap = HashMap<CharSequence, CharSequence>().apply {
            put("\"", "\\\"")
            put("\\", "\\\\")
        }

        AggregateTranslator(
            listOf<CharSequenceTranslator>(
                LookupTranslator(Collections.unmodifiableMap(escapeJavaMap)),
                LookupTranslator(JAVA_CTRL_CHARS_ESCAPE),
                JavaUnicodeEscaper.outsideOf(32, 0x7f)
            )
        )
    }


    fun escapeJava(str: String): String {
        return ESCAPE_JAVA.translate(str)
    }

    fun unEscapeJava(str: String): String {
        return ""
    }

}