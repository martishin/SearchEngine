package com.ttymonkey.search.text.filter

import kotlin.test.Test
import kotlin.test.assertEquals

class StemmingFilterTest {
    private val filter = StemmingFilter()

    @Test
    fun testStemming() {
        assertEquals(listOf("hello", "world", "world"), filter.process(listOf("hello", "worlds", "world")))
    }
}
