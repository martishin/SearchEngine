package com.ttymonkey.search.index

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class InvertedIndexTest {
    private val index = InvertedIndex()
    private val file1 = File("./file1")
    private val file2 = File("./file2")

    @Test
    fun testIndex() {
        index.addTokens(file1, listOf("hello", "world"))

        assertEquals(mapOf(file1.path to listOf(1)), index.getPositions(listOf("hello")))
    }

    @Test
    fun testMultipleFiles() {
        index.addTokens(file1, listOf("hello", "world"))
        index.addTokens(file2, listOf("world"))

        assertEquals(mapOf(file1.path to listOf(2), file2.path to listOf(1)), index.getPositions(listOf("world")))
    }

    @Test
    fun testMultipleOccurrences() {
        index.addTokens(file1, listOf("hello", "hello"))

        assertEquals(mapOf(file1.path to listOf(1, 2)), index.getPositions(listOf("hello")))
    }

    @Test
    fun testEmpty() {
        index.addTokens(file1, listOf("hello", "hello"))

        assertEquals(mapOf(), index.getPositions(listOf("app")))
    }

    @Test
    fun testMultipleTokens() {
        index.addTokens(file1, listOf("hello", "world", "hello", "world"))

        assertEquals(mapOf(file1.path to listOf(1, 3)), index.getPositions(listOf("hello", "world")))
    }

    @Test
    fun testMultipleSameTokens() {
        index.addTokens(file1, listOf("hello", "hello", "hello", "hello"))

        assertEquals(mapOf(file1.path to listOf(1, 2, 3)), index.getPositions(listOf("hello", "hello")))
    }

    @Test
    fun testMultipleTokensMultipleFiles() {
        index.addTokens(file1, listOf("hello", "world", "hello", "world"))
        index.addTokens(file2, listOf("hello", "world"))

        assertEquals(mapOf(file1.path to listOf(1, 3), file2.path to listOf(1)), index.getPositions(listOf("hello", "world")))
    }

    @Test
    fun testMultipleTokensNoMatch() {
        index.addTokens(file1, listOf("hello", "hello"))

        assertEquals(mapOf(), index.getPositions(listOf("hello", "world")))
    }
}
