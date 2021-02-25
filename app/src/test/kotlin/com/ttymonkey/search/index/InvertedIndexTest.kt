package com.ttymonkey.search.index

import com.ttymonkey.search.text.TokenizerResult
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class InvertedIndexTest {
    private val index = InvertedIndex()
    private val file1 = File("./file1")
    private val file2 = File("./file2")

    @Test
    fun testIndex() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world"), listOf(1, 10))))

        assertEquals(mapOf(file1.path to listOf(Position(1, 1, 1))), index.getPositions(listOf("hello")))
    }

    @Test
    fun testMultipleFiles() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world"), listOf(1, 10))))
        index.addTokens(file2, listOf(TokenizerResult(listOf("world"), listOf(1))))

        assertEquals(mapOf(file1.path to listOf(Position(2, 1, 10)), file2.path to listOf(Position(1, 1, 1))), index.getPositions(listOf("world")))
    }

    @Test
    fun testMultipleOccurrences() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "hello"), listOf(1, 10))))

        assertEquals(mapOf(file1.path to listOf(Position(1, 1, 1), Position(2, 1, 10))), index.getPositions(listOf("hello")))
    }

    @Test
    fun testEmpty() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "hello"), listOf(1, 10))))

        assertEquals(mapOf(), index.getPositions(listOf("app")))
    }

    @Test
    fun testMultipleRows() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world"), listOf(1, 10)), TokenizerResult(listOf("world", "hello"), listOf(1, 10))))

        assertEquals(mapOf(file1.path to listOf(Position(1, 1, 1), Position(4, 2, 10))), index.getPositions(listOf("hello")))
    }

    @Test
    fun testMultipleTokens() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world", "hello", "world"), listOf(1, 10, 100, 1000))))

        assertEquals(mapOf(file1.path to listOf(Position(1, 1, 1), Position(3, 1, 100))), index.getPositions(listOf("hello", "world")))
    }

    @Test
    fun testMultipleTokensMultipleFiles() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "world", "hello", "world"), listOf(1, 10, 100, 1000))))
        index.addTokens(file2, listOf(TokenizerResult(listOf("hello", "world"), listOf(1, 10))))

        assertEquals(mapOf(file1.path to listOf(Position(1, 1, 1), Position(3, 1, 100)), file2.path to listOf(Position(1, 1, 1))), index.getPositions(listOf("hello", "world")))
    }

    @Test
    fun testMultipleTokensNoMatch() {
        index.addTokens(file1, listOf(TokenizerResult(listOf("hello", "hello"), listOf(1, 10))))

        assertEquals(mapOf(), index.getPositions(listOf("hello", "world")))
    }
}
