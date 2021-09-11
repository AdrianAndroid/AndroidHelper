package com.joyy.escapekt

import java.io.Writer

/**
 * Time:2021/9/11 14:05
 * Author: flannery
 * Description:
 */
class JavaUnicodeEscaper(below: Int = 0, above: Int = Int.MAX_VALUE, between: Boolean = true) :
    UnicodeEscaper(below, above, between) {
    companion object {

        /**
         * below the specified value (exclusive)
         */
        fun above(codepoint: Int) = outsideOf(0, codepoint)

        /**
         * between the specified value (inclusive)
         */
        fun between(codepointLow: Int, codepointHight: Int) =
            JavaUnicodeEscaper(codepointLow, codepointHight, true)

        /**
         * outside of the specified values (exclusive)
         */
        fun outsideOf(codepointLow: Int, codepointHight: Int) =
            JavaUnicodeEscaper(codepointLow, codepointHight, false)
    }

    /**
     * Converts the given codepoint to a hex string of the form "\\uXXXX\\uXXXXX"
     */
    override fun toUtf16Escape(codepoint: Int): String {
        val surrogatePair = Character.toChars(codepoint)
        return "\\u" + hex(surrogatePair[0]) + "\\u" + hex(surrogatePair[1])
    }
}

/**
 * Translates codepoints to their Unicode escaped value.
 * Constructors a UnicodeEscaper for all characters.
 */
open class UnicodeEscaper(
    val below: Int = 0, // int value  representing the lowest codepint boundary
    val above: Int = Int.MAX_VALUE, // int value representing the highest codepoint boundary
    val between: Boolean = true // whether to escape between the boundaries or outside them.
) : CodePointTranslator() {

    override fun translate(codepoint: Int, writer: Writer): Boolean {
        if (between) {
            if (codepoint < below || codepoint > above) return false
        } else {
            if (codepoint in below..above) return false
        }

        if (codepoint > 0xffff) {
            writer.write(toUtf16Escape(codepoint))
        } else {
            writer.write("\\u")
            writer.write(HEX_DIGITS[codepoint shr 12 and 15].code)
            writer.write(HEX_DIGITS[codepoint shr 8 and 15].code)
            writer.write(HEX_DIGITS[codepoint shr 4 and 15].code)
            writer.write(HEX_DIGITS[codepoint and 15].code)
        }
        return true
    }


    open fun toUtf16Escape(codepoint: Int) = "\\u" + hex(Char(codepoint))

}


/**
 * Helper subclass to CharSequenceTranslatore to allow for translations that
 * will replace up to one character at a time
 */
abstract class CodePointTranslator : CharSequenceTranslator() {

    /**
     * Implemenation of translate that maps onto the abstract translate(int, Writer)method
     */
    override fun translate(input: CharSequence, index: Int, writer: Writer): Int {
        val codepoint = Character.codePointAt(input, index)
        val consumed = translate(codepoint, writer)
        return if (consumed) 1 else 0
    }

    /**
     * Translate tehe specified codepoint into another
     * codepoint int character input to translate
     * boolean as to whether translation occurred or not
     */
    abstract fun translate(codepoint: Int, writer: Writer): Boolean

}


