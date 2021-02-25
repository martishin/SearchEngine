package com.ttymonkey.search.index

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.*
import mu.KotlinLogging
import java.io.File
import java.io.IOException

private val log = KotlinLogging.logger {}

object InvertedIndexDumper {
    @ExperimentalSerializationApi
    fun dump(file: File, index: InvertedIndex) {
        val bytes = ProtoBuf.encodeToByteArray(index)
        log.info("Dumping index to file {}", file.path)
        try {
            file.writeBytes(bytes)
        } catch (e: IOException) {
            log.error("Error happened when dumping index to file {}", file.path, e)
            throw e
        }
    }

    @ExperimentalSerializationApi
    fun load(file: File): InvertedIndex {
        try {
            log.info("Loading index from file {}", file.path)
            val bytes = file.readBytes()
            return ProtoBuf.decodeFromByteArray(bytes)
        } catch (e: IOException) {
            log.error("Error happened when reading index from file {}", file.path, e)
            throw e
        }
    }
}
