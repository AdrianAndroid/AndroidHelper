package com.joyy.escapekt

import java.io.Writer

/**
 * Time:2021/9/11 11:33
 * Author: flannery
 * Description: 具体的操作类
 */
class AggregateTranslator(private val translators: List<CharSequenceTranslator>) :
    CharSequenceTranslator() {

    /**
     * The first translatore to consume codepoints from the iput is the 'winner'
     * Executiion stops with the number of consumed codepoints being returned.
     */
    override fun translate(input: CharSequence, index: Int, writer: Writer): Int {
        for (translator in translators) {
            val consumed = translator.translate(input, index, writer)
            if (consumed != 0) return consumed
        }
        return 0
    }
}