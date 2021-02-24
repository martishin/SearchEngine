package com.ttymonkey.search.text

import com.ttymonkey.search.text.filter.LowerCaseFilter
import com.ttymonkey.search.text.filter.StemmingFilter

object TextProcessor {
    private val filters = listOf(LowerCaseFilter(), StemmingFilter())

    fun process(string: String): List<String> {
        val tokens = SpaceTokenizer.tokenize(string)

        return applyFilters(tokens)
    }

    private fun applyFilters(tokens: List<String>): List<String> {
        return filters.fold(tokens) { tokensAfterFilters, filter ->
            filter.process(tokensAfterFilters)
        }
    }
}
