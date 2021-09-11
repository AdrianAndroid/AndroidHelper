package com.joyy.escapekt

import java.io.Writer

/**
 * Time:2021/9/11 13:36
 * Author: flannery
 * Description:
 */
class LookupTranslator(lookupMap: Map<CharSequence, CharSequence>) : CharSequenceTranslator() {

    /** The mapping to be used in translation */
    val _lookupMap: Map<String, String>

    /** The first character of each key in the lookupMap. */
    val _prefixSet: HashSet<Char>

    /** The length of the shortest key in the lookupMap. */
    val _shortest: Int

    /** The length of the longest key in the lookupMap. */
    val _longest: Int

    init {
        _lookupMap = HashMap()
        _prefixSet = HashSet()

        var currentShortest: Int = Int.MAX_VALUE
        var currentLongest: Int = 0

        for (pair: Map.Entry<CharSequence, CharSequence> in lookupMap.entries) {
            val key = pair.key as String
            _lookupMap[key] = pair.value as String
            _prefixSet.add(key[0])
            val sz = pair.key.length
            if (sz < currentShortest) currentShortest = sz
            if (sz > currentLongest) currentLongest = sz
        }
        _shortest = currentShortest
        _longest = currentLongest
    }


    override fun translate(input: CharSequence, index: Int, writer: Writer): Int {
        // check if translation exists for the input at position index
        if (_prefixSet.contains(input[index])) {
            var max = _longest
            if (index + _longest > input.length) max = input.length - index

            // implement greedy algorithm by tring maximum match first
            for (i in max downTo _shortest) {
                val subSeq = input.subSequence(index, index + 1)
                val result = _lookupMap["$subSeq"]

                if (result != null) {
                    writer.write(result)
                    return i
                }
            }
        }
        return 0
    }
}