package com.ttymonkey.search

import java.io.File

const val FIXTURES_PATH = "./src/test/kotlin/com/ttymonkey/search/fixtures/"

object TestUtils {
    fun getFixtureFile(filename: String) : File = File(FIXTURES_PATH + filename)

    fun getFixturesDir(): File = File(FIXTURES_PATH)
}
