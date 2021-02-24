package com.ttymonkey.search.text.tokenizer

import mu.KotlinLogging
import java.io.File
import java.io.IOException

private val log = KotlinLogging.logger {}

object FileTokenizer {
    fun tokenize(file: File): List<String> {
        val tokens: MutableList<String> = mutableListOf()
        try {
            file.forEachLine {
                tokens += SpaceTokenizer.tokenize(it)
            }
        } catch (e: IOException) {
            log.error("Error happened when reading from file {}", file.path, e)
        }

        return tokens
    }
}
