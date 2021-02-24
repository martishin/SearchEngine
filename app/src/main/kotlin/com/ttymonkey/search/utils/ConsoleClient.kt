package com.ttymonkey.search.utils

import com.ttymonkey.search.query.QueryProcessor
import kotlinx.coroutines.*

class ConsoleClient(private val numberOfWorkers: Int, private val queryProcessor: QueryProcessor) {
    fun run() = runBlocking {
        repeat(numberOfWorkers) {
            launch(Dispatchers.IO) {
                while (true) {
                    val query = readLine()
                    if (query != null) {
                        val response = queryProcessor.process(query)
                        println("$query: " + response)
                    }
                }
            }
        }
    }
}
