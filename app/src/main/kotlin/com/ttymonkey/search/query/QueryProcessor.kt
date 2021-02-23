package com.ttymonkey.search.query

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TextProcessor
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class QueryProcessor(private val index: InvertedIndex) {
    suspend fun process(query: String): List<SearchResult> = coroutineScope {
        log.info("Started processing query: {}", query)
        val searchResults: MutableList<SearchResult> = mutableListOf()

        val tokens = async { TextProcessor.process(query) }
        val documentsPositions = async { index.getPositions(tokens.await()) }

        documentsPositions.await().forEach { (document, positions) ->
            searchResults.add(SearchResult(document, positions))
        }

        log.info("Finished processing query: {}", query)

        searchResults
    }
}
