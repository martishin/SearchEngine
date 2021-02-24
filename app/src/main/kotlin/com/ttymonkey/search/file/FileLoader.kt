package com.ttymonkey.search.file

import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.text.TextProcessor
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

private val log = KotlinLogging.logger {}

class FileLoader(baseDirectory: File, private val index: InvertedIndex) {
    private val fileLister = FileLister(baseDirectory)
    private val scope = CoroutineScope(Dispatchers.IO)
    private var remainingFiles = AtomicInteger(0)

    fun load(): Job {
        val files = fileLister.list()
        remainingFiles.set(files.size)

        log.info("Will parse files {}, number of files: {}", files, remainingFiles.get())

        return scope.launch {
            files.forEach { file ->
                launch {
                    log.info("Started parsing file {}", file)
                    val tokens = FileReader.read(file)
                    index.addTokens(file, tokens)
                    val remainingFilesCount = remainingFiles.decrementAndGet()
                    log.info("Finished parsing file {}, remaining files: {}", file,remainingFilesCount)
                }
            }
        }
    }

    fun getRemainingFiles(): Int {
        return remainingFiles.get()
    }

    fun cancel() {
        scope.cancel()
    }
}
