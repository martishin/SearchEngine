package com.ttymonkey.search.query

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TokenizerResult
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class QueryProcessorTest {
    private val index = InvertedIndex()
    private val queryProcessor = QueryProcessor(index)
    private val file1 = File("./file1")

    @Test
    fun testProcess() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world"), listOf(1, 7))))

        val searchResults = runBlocking { queryProcessor.process("Hello") }

        assertEquals(1, searchResults.size)
        assertEquals(file1.path, searchResults[0].document)
        assertEquals(listOf(Triple(1, 1, 1)), searchResults[0].positions)
    }
}
