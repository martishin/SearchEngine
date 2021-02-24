package com.ttymonkey.search.text

import kotlin.test.Test
import kotlin.test.assertEquals

class TextProcessorTest {
    @Test fun testProcessText() {
        assertEquals(listOf("hello", "world"), TextProcessor.process("Hello  Worlds"))
    }
}
