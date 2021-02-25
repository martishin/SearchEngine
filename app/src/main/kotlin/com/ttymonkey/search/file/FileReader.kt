package com.ttymonkey.search.file

import com.ttymonkey.search.text.TextProcessor
import com.ttymonkey.search.text.TokenizerResult
import mu.KotlinLogging
import java.io.File
import java.io.IOException

private val log = KotlinLogging.logger {}

object FileReader {
    fun read(file: File): List<TokenizerResult> {
        val tokens = mutableListOf<TokenizerResult>()
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
