package com.ttymonkey.search.text

import kotlin.test.Test
import kotlin.test.assertEquals

class TextProcessorTest {
    @Test fun testProcessText() {
        assertEquals(TokenizerResult(listOf("hello", "world"), listOf(1, 8)), TextProcessor.process("Hello  World"))
    }

    @Test fun testProcessTextModifiedByFilter() {
        assertEquals(TokenizerResult(listOf("hello", "world", "world"), listOf(1, 7, 14)), TextProcessor.process("Hello Worlds World"))
    }
}
