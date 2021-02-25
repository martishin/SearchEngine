package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class FileReaderTest {
    @Test fun testTokenize() {
        val tokenizerResults = FileReader.read(TestUtils.getFixtureFile("test_file.txt"))

        assertEquals(4, tokenizerResults.size)

        assertEquals(listOf("hello", "world"), tokenizerResults[0].tokens)
        assertEquals(listOf("hello", "world"), tokenizerResults[1].tokens)
        assertEquals(listOf(), tokenizerResults[2].tokens)
        assertEquals(listOf("hello"), tokenizerResults[3].tokens)

        assertEquals(listOf(1, 7), tokenizerResults[0].positions)
        assertEquals(listOf(1, 8), tokenizerResults[1].positions)
        assertEquals(listOf(), tokenizerResults[2].positions)
        assertEquals(listOf(2), tokenizerResults[3].positions)
    }
}
