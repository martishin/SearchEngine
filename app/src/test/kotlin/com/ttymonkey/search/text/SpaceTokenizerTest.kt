package com.ttymonkey.search.text

import kotlin.test.Test
import kotlin.test.assertEquals

class SpaceTokenizerTest {
    @Test fun testWord() {
        val result = SpaceTokenizer.tokenize("Hello")
        assertEquals(listOf("Hello"), result.tokens)
        assertEquals(listOf(1), result.positions)
    }

    @Test fun testSpaces() {
        val result = SpaceTokenizer.tokenize("Hello  World")
        assertEquals(listOf("Hello", "World"), result.tokens)
        assertEquals(listOf(1, 8), result.positions)
    }

    @Test fun testCharacters() {
        val result = SpaceTokenizer.tokenize("Hello, World!")
        assertEquals(listOf("Hello", "World"), result.tokens)
        assertEquals(listOf(1, 8), result.positions)
    }

    @Test fun testExtraSpaces() {
        val result = SpaceTokenizer.tokenize(" Hello ")
        assertEquals(listOf("Hello"), result.tokens)
        assertEquals(listOf(2), result.positions)
    }

    @Test fun testCharactersInsideWords() {
        val result = SpaceTokenizer.tokenize("test.Hello")
        assertEquals(listOf("test", "Hello"), result.tokens)
        assertEquals(listOf(1, 6), result.positions)
    }
}
