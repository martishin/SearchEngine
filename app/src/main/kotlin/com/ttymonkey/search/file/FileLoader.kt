package com.ttymonkey.search.file

import com.ttymonkey.search.index.InvertedIndex
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

private val log = KotlinLogging.logger {}

class FileLoader(baseDirectory: File, private val index: InvertedIndex): CoroutineScope  {
    private val fileLister = FileLister(baseDirectory)
    private var remainingFiles = AtomicInteger(0)
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO

     fun load() : Job {
        val files = fileLister.list()
        remainingFiles.set(files.size)

        log.info("Will parse files {}, number of files: {}", files, remainingFiles.get())

        return launch {
            files.forEach { file ->
                launch(Dispatchers.IO) {
                    log.info("Started parsing file {}", file)
                    val tokens = FileReader.read(file)
                    index.addTokens(file, tokens)
                    remainingFiles.decrementAndGet()
                    log.info("Finished parsing file {}", file)
                }
            }
        }
    }

    fun getRemainingFiles(): Int {
        return remainingFiles.get()
    }

    fun cancel() {
        job.cancel()
    }
}
