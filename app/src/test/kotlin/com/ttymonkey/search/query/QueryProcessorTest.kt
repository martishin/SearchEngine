package com.ttymonkey.search.query

import com.ttymonkey.search.index.InvertedIndex
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
        index.addTokens(file1, listOf("hello", "world"))

        val searchResults = runBlocking { queryProcessor.process("Hello") }

        assertEquals(1, searchResults.size)
        assertEquals(file1.path, searchResults[0].document)
        assertEquals(listOf(1), searchResults[0].positions)
    }
}
