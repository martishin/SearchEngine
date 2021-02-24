package com.ttymonkey.search.text.tokenizer

import mu.KotlinLogging
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

private val log = KotlinLogging.logger {}

object FileTokenizer {
    fun tokenize(file: File): List<String> {
        val tokens: MutableList<String> = mutableListOf()
        val reader: BufferedReader
        try {
            reader = BufferedReader(FileReader(file))
            var line = reader.readLine()

            while (line != null) {
                tokens += SpaceTokenizer.tokenize(line)
                line = reader.readLine()
            }

            reader.close()
        } catch (e: IOException) {
            log.error("Error happened when reading from file {}", file.path, e)
        }

        return tokens
    }
}
