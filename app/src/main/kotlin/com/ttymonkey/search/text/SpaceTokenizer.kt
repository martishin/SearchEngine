package com.ttymonkey.search.text

import java.lang.StringBuilder

data class TokenizerResult(var tokens: List<String>, val positions: List<Int>)

object SpaceTokenizer {
    fun tokenize(string: String): TokenizerResult {
        val builder = StringBuilder()

        var startIdx = 1
        val tokens = mutableListOf<String>()
        val positions = mutableListOf<Int>()

        string.forEachIndexed{ idx, char ->
            if (char.isLetter()) {
                if (builder.isEmpty()) {
                    startIdx = idx + 1
                }
                builder.append(char)
            } else {
                if (builder.isNotEmpty()) {
                    tokens.add(builder.toString())
                    positions.add(startIdx)
                    builder.setLength(0)
                }
            }
        }

        if (builder.isNotEmpty()) {
            tokens.add(builder.toString())
            positions.add(startIdx)
        }

        return TokenizerResult(tokens, positions)
    }
}
