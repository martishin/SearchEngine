package com.ttymonkey.search.text.filter

import kotlin.test.Test
import kotlin.test.assertEquals

class LowerCaseFilterTest {
    private val filter = LowerCaseFilter()

    @Test
    fun testToLowerCase() {
        assertEquals(listOf("hello", "world"), filter.process(listOf("Hello", "World")))
    }
}
