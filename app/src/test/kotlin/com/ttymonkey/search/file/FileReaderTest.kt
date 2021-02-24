package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class FileReaderTest {
    @Test fun testTokenize() {
        assertEquals(listOf("hello", "world", "hello", "world", "hello"), FileReader.read(TestUtils.getFixtureFile("test_file.txt")))
    }
}
