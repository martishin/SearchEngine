package com.ttymonkey.search.text.tokenizer

import com.ttymonkey.search.TestUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class FileTokenizerTest {
    @Test fun testTokenize() {
        assertEquals(listOf("Hello", "World", "hello", "worlds", "Hello"), FileTokenizer.tokenize(TestUtils.getFixtureFile("test_file.txt")))
    }
}
