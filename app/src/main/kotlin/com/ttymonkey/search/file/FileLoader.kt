package com.ttymonkey.search.file

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TextProcessor
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File

private val log = KotlinLogging.logger {}

class FileLoader(baseDirectory: File, private val index: InvertedIndex) {
    private val fileLister = FileLister(baseDirectory)

    fun load() = runBlocking {
        val files = fileLister.list()

        log.info("Will parse files {}", files)

        files.forEach { file ->
            launch(Dispatchers.IO) {
                log.info("Started parsing file {}", file)
                val tokens = async(Dispatchers.IO) { TextProcessor.processFile(file) }
                index.addTokens(file, tokens.await())
                log.info("Finished parsing file {}", file)
            }
        }
    }
}
