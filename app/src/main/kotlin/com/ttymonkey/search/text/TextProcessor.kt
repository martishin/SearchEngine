package com.ttymonkey.search.text

import com.ttymonkey.search.text.filter.LowerCaseFilter
import com.ttymonkey.search.text.filter.StemmingFilter
import com.ttymonkey.search.text.tokenizer.FileTokenizer
import com.ttymonkey.search.text.tokenizer.SpaceTokenizer
import java.io.File

object TextProcessor {
    private val filters = listOf(LowerCaseFilter(), StemmingFilter())

    fun process(string: String): List<String> {
        val tokens = SpaceTokenizer.tokenize(string)

        return applyFilters(tokens)
    }

    fun processFile(file: File): List<String> {
        val tokens = FileTokenizer.tokenize(file)

        return applyFilters(tokens)
    }

    private fun applyFilters(tokens: List<String>): List<String> {
        return filters.fold(tokens) { tokensAfterFilters, filter ->
            filter.process(tokensAfterFilters)
        }
    }
}
