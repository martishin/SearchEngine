package com.ttymonkey.search

import com.ttymonkey.search.file.FileLoader
import com.ttymonkey.search.index.InvertedIndex
import com.ttymonkey.search.index.InvertedIndexDumper
import com.ttymonkey.search.query.QueryProcessor
import com.ttymonkey.search.utils.AppArgs
import com.ttymonkey.search.utils.ConsoleClient
import com.ttymonkey.search.utils.RunMode
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import mu.KotlinLogging
import java.io.File
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

class App (private val args: Array<String>){
    @ExperimentalSerializationApi
    fun run() = mainBody {
        ArgParser(args).parseInto(::AppArgs).run {
            val indexFile = File(index)
            val searchDirectory = File(directory)

            if (indexFile.isDirectory) {
                log.error("index filepath cannot be a directory")
                exitProcess(1)
            }

            if (searchDirectory.isFile) {
                log.error("search directory cannot be a file")
                exitProcess(1)
            }

            if (mode == RunMode.INDEX) {
                val index = InvertedIndex()
                runBlocking {
                    FileLoader(searchDirectory, index).load().join()
                }
                InvertedIndexDumper.dump(indexFile, index)
            } else {
                val index = InvertedIndexDumper.load(indexFile)
                val queryProcessor = QueryProcessor(index)

                ConsoleClient(10, queryProcessor).run()
            }
        }
    }
}
