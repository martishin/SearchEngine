package com.ttymonkey.search.index

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlinx.serialization.*
import java.io.File

typealias Position = Int
typealias Document = String

@Serializable
class InvertedIndex {
    private var index: MutableMap<String, MutableMap<Document, MutableList<Position>>> = hashMapOf()

    @Transient
    private val lock = ReentrantReadWriteLock()

    fun addTokens(file: File, tokens: List<String>) = lock.write {
        tokens.forEachIndexed { idx, token ->
            index.getOrPut(token, { hashMapOf() })
                    .getOrPut(file.path, { mutableListOf() })
                    .add(idx + 1)
        }
    }

    fun getPositions(tokens: List<String>): Map<Document, List<Position>> = lock.read {
        if (tokens.isEmpty()) {
            return mapOf()
        }

        var previousTokenToDocuments = getTokenPositions(tokens[0])
        for (idx in 1 until tokens.size) {
            val currentTokenToDocuments = getTokenPositions(tokens[idx])
            val continuousTokenPositions : MutableMap<Document, MutableList<Position>> = hashMapOf()

            val documentsIntersection = previousTokenToDocuments.keys.intersect(currentTokenToDocuments.keys)

            documentsIntersection.forEach { document ->
                val previousPositions = previousTokenToDocuments.getValue(document)
                val currentPositions = currentTokenToDocuments.getValue(document)

                var ppPointer = 0
                var cpPointer = 0

                while (ppPointer < previousPositions.size && cpPointer < currentPositions.size) {
                    when {
                        currentPositions[cpPointer] == previousPositions[ppPointer] + 1 -> {
                            if (!continuousTokenPositions.containsKey(document)) {
                                continuousTokenPositions[document] = mutableListOf()
                            }

                            continuousTokenPositions[document]?.add(currentPositions[cpPointer])
                            ++ppPointer
                            ++cpPointer
                        }
                        currentPositions[cpPointer] < previousPositions[ppPointer] + 1 -> {
                            ++cpPointer
                        }
                        else -> {
                            ++ppPointer
                        }
                    }
                }
            }

            previousTokenToDocuments = continuousTokenPositions
        }

        return previousTokenToDocuments.mapValues { positions ->
            positions.value.map{ it - tokens.size + 1  }
        }
    }

    private fun getTokenPositions(token: String): Map<Document, List<Position>> = lock.read {
        return index[token] ?: hashMapOf()
    }
}
