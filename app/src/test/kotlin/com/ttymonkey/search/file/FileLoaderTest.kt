package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import com.ttymonkey.search.index.InvertedIndex
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class FileLoaderTest {
    private val index = InvertedIndex()
    private val fileLoader = FileLoader(TestUtils.getFixturesDir(), index)

    @Test
    fun testList() {
        runBlocking {
            fileLoader.load().join()
        }

        assertEquals(2, index.getPositions(listOf("hello")).size)
    }

    @Test
    fun testCancel() {
        runBlocking {
            fileLoader.load()
            fileLoader.cancel()
        }

        assertEquals(0, index.getPositions(listOf("hello")).size)
    }

    @Test
    fun testRemainingFiles() {
        runBlocking {
            val fileLoaderJob = fileLoader.load()
            assertEquals(2, fileLoader.getRemainingFiles())
            fileLoaderJob.join()
        }

        assertEquals(0, fileLoader.getRemainingFiles())
    }
}
