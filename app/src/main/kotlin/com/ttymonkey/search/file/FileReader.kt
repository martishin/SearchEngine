package com.ttymonkey.search.file

import com.ttymonkey.search.text.TextProcessor
import mu.KotlinLogging
import java.io.File
import java.io.IOException

private val log = KotlinLogging.logger {}

object FileReader {
    fun read(file: File): List<String> {
        val tokens: MutableList<String> = mutableListOf()
        try {
            file.forEachLine {
                tokens += TextProcessor.process(it)
            }
        } catch (e: IOException) {
            log.error("Error happened when reading from file {}", file.path, e)
        }

        return tokens
    }
}
