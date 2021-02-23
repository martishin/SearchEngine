package com.ttymonkey.search.file

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TextProcessor
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File

private val log = KotlinLogging.logger {}

val FileLoaderScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

class FileLoader(baseDirectory: File, private val index: InvertedIndex) {
    private val fileLister = FileLister(baseDirectory)


    suspend fun load() = coroutineScope {
        val files = fileLister.list()

        log.info("Will parse files {}", files)

        files.forEach { file ->
            FileLoaderScope.launch {
                log.info("Started parsing file {}", file)
                val tokens = async { TextProcessor.processFile(file) }
                index.addTokens(file, tokens.await())
                log.info("Finished parsing file {}", file)
            }
        }
    }

    fun cancel() {
        FileLoaderScope.cancel()
    }
}
