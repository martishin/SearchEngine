package com.ttymonkey.search.text

import com.ttymonkey.search.TestUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class TextProcessorTest {
    @Test fun testProcessText() {
        assertEquals(listOf("hello", "world"), TextProcessor.process("Hello  Worlds"))
    }

    @Test fun testProcessFile() {
        assertEquals(listOf("hello", "world", "hello", "world", "hello"), TextProcessor.processFile(TestUtils.getFixtureFile("test_file.txt")))
    }
}
