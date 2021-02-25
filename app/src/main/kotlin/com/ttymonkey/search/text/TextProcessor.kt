package com.ttymonkey.search.text

import com.ttymonkey.search.text.filter.LowerCaseFilter
import com.ttymonkey.search.text.filter.StemmingFilter

object TextProcessor {
    private val filters = listOf(LowerCaseFilter(), StemmingFilter())

    fun process(string: String): TokenizerResult {
        val tokenizerResult = SpaceTokenizer.tokenize(string)
        tokenizerResult.tokens = filters.fold(tokenizerResult.tokens) { tokensAfterFilters, filter ->
            filter.process(tokensAfterFilters)
        }
        return tokenizerResult
    }
}
