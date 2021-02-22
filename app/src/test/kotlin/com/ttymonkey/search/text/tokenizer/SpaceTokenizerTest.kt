package com.ttymonkey.search.text.tokenizer

import kotlin.test.Test
import kotlin.test.assertEquals

class SpaceTokenizerTest {
    @Test fun testSpaces() {
        assertEquals(listOf("Hello", "World"), SpaceTokenizer.tokenize("Hello  World"))
    }

    @Test fun testCharacters() {
        assertEquals(listOf("Hello", "World"), SpaceTokenizer.tokenize("Hello, World!"))
    }

    @Test fun testExtraSpaces() {
        assertEquals(listOf("Hello"), SpaceTokenizer.tokenize(" Hello "))
    }

    @Test fun testCharactersInsideWords() {
        assertEquals(listOf("test", "Hello"), SpaceTokenizer.tokenize("test.Hello"))
    }
}
