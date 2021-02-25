package com.ttymonkey.search.index

import com.ttymonkey.search.text.TokenizerResult
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals


class InvertedIndexDumperTest {
    private val file = File("./file1")
    private val index = InvertedIndex()

    @ExperimentalSerializationApi
    @Test
    fun testDumpAndLoad() {
        index.addTokens(file, listOf(TokenizerResult(listOf("Hello", "world"), listOf(1, 10))))

        val file = File.createTempFile("index.dump", null)
        file.deleteOnExit()
        InvertedIndexDumper.dump(file, index)
        val loadedIndex = InvertedIndexDumper.load(file)

        assertEquals(1, loadedIndex.getPositions(listOf("Hello")).size)
    }
}
