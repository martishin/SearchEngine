package com.ttymonkey.search.file

import com.ttymonkey.search.TestUtils
import org.junit.Test
import kotlin.test.assertEquals

class FileListerTest {
    private val fileLister = FileLister(TestUtils.getFixturesDir())

    @Test
    fun testList() {
        assertEquals(2, fileLister.list().size)
    }
}
