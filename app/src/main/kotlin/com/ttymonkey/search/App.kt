package com.ttymonkey.search

import com.ttymonkey.search.file.FileLoader
import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.index.InvertedIndexDumper
import com.ttymonkey.search.query.QueryProcessor
import com.ttymonkey.search.utils.AppArgs
import com.ttymonkey.search.utils.RunMode
import com.xenomachina.argparser.ArgParser
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

class App (private val args: Array<String>){
    @ExperimentalSerializationApi
    fun run() = runBlocking {
        ArgParser(args).parseInto(::AppArgs).run {
            val indexFile = File(index)
            val searchDirectory = File(directory)

            if (indexFile.isDirectory) {
                log.error("index filepath cannot be a directory")
                exitProcess(1)
            }

            if (searchDirectory.isFile) {
                log.error("search directory cannot be an index")
                exitProcess(1)
            }

            if (mode == RunMode.INDEX) {
                val index = InvertedIndex()
                val fileLoader = FileLoader(searchDirectory, index)
                fileLoader.load().join()
                InvertedIndexDumper.dump(indexFile, index)
            } else {
                val index = InvertedIndexDumper.load(indexFile)
                val queryProcessor = QueryProcessor(index)

                listOf("dump", "query", "load").forEach {
                    launch {
                        val response = queryProcessor.process(it)
                        println("$it: $response")
                    }
                }
            }
        }
    }
}
