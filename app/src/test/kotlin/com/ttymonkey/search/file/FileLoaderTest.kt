package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import com.ttymonkey.search.index.InvertedIndex
import org.junit.Test
import kotlin.test.assertEquals

class FileLoaderTest {
    private val index = InvertedIndex()
    private val fileLoader = FileLoader(TestUtils.getFixturesDir(), index)

    @Test
    fun testList() {
        fileLoader.load()

        assertEquals(2, index.getPositions(listOf("hello")).size)
    }
}
