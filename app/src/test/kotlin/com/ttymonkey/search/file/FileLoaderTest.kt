package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import com.ttymonkey.search.index.InvertedIndex
import kotlinx.coroutines.isActive
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

        assertEquals(true, fileLoader.coroutineContext.isActive)
        assertEquals(2, index.getPositions(listOf("hello")).size)
    }

    @Test
    fun testCancel() {
        fileLoader.load()
        fileLoader.cancel()

        assertEquals(false, fileLoader.coroutineContext.isActive)
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
