package com.ttymonkey.search.query

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TextProcessor
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class QueryProcessor(private val index: InvertedIndex) {
    fun process(query: String): List<SearchResult> {
        log.info("Started processing query: {}", query)
        val searchResults = mutableListOf<SearchResult>()

        val tokens = TextProcessor.process(query)
        val documentsPositions = index.getPositions(tokens.tokens)

        documentsPositions.forEach { (document, positions) ->
            searchResults.add(SearchResult(document, positions))
        }

        log.info("Finished processing query: {}", query)

        return searchResults
    }
}
