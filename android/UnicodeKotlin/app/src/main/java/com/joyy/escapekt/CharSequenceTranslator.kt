package com.joyy.escapekt

import java.io.StringWriter
import java.io.Writer
import java.util.*

/**
 * Time:2021/9/11 11:06
 * Author: flannery
 * Description:
 */
abstract class CharSequenceTranslator {

    companion object {
        fun hex(codepoint: Char): String {
            return Integer.toHexString(codepoint.code).uppercase(Locale.ENGLISH);
        }
    }

    /**
     * Array containging the hexadecimal alphabet
     */
    val HEX_DIGITS: CharArray = charArrayOf(
        '0', '1', '2', '3',
        '4', '5', '6', '7',
        '8', '9', 'A', 'B',
        'C', 'D', 'E', 'F'
    )

    @Throws(Exception::class)
    abstract fun translate(input: CharSequence, index: Int, writer: Writer): Int

    // 外部真正调用
    fun translate(input: CharSequence): String {
        if (input.isBlank()) return input.toString()
        val writer = StringWriter(input.length * 2)
        translate(input, writer) // 进行翻译
        return writer.toString()
    }

    @Throws(Exception::class)
    fun translate(input: CharSequence, writer: Writer) {
        if (input.isBlank()) return
        var pos = 0
        val len = input.length
        while (pos < len) {
            val consumed = translate(input, pos, writer)
            // inlined implementation of character.toChars(Character.codePointAt(input,pos))
            // avoids allocating temp char arrays and duplicate checks
            if (consumed == 0) {
                val c1 = input[pos] // charAt(pos)
                writer.write(c1.code)
                pos++
                // 判断给定char值是Unicode高代理项代码单元(也称为高级代理项代码单元)。
                if (Character.isHighSurrogate(c1) && pos < len) {
                    val c2 = input[pos] // 此时po已经+1了
                    if (Character.isLowSurrogate(c2)) {
                        writer.write(c2.code)
                        pos++
                    }
                }
                continue
            }

            // contract with tanslators is that they have to understand codepoints
            // and they just took  care of a surrogate paire
            for (pt in 0 until consumed) {
                pos += Character.charCount(Character.codePointAt(input, pos))
            }
        }
    }

}