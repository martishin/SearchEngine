package com.ttymonkey.search.index

import com.ttymonkey.search.text.TokenizerResult
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlinx.serialization.*
import java.io.File
import java.util.*

typealias Position = Triple<Int, Int, Int>
typealias Document = String

@Serializable
class InvertedIndex {
    private var index = hashMapOf<String, MutableMap<Document, MutableList<Position>>>()

    @Transient
    private val lock = ReentrantReadWriteLock()

    fun addTokens(file: File, tokens: List<TokenizerResult>) = lock.write {
        var position = 1
        tokens.forEachIndexed { row, tokenizerResult ->
            tokenizerResult.tokens.forEachIndexed { idx, token ->
                index.getOrPut(token, { hashMapOf() })
                        .getOrPut(file.path, { mutableListOf() })
                        .add(Triple(position++, row + 1, tokenizerResult.positions[idx]))
            }
        }
    }

    fun getPositions(tokens: List<String>): Map<Document, List<Position>> = lock.read {
        if (tokens.isEmpty()) {
            return mapOf()
        }

        var previousTokenToDocuments = getTokenPositions(tokens[0])
        val parentPositions = hashMapOf<Position, Position>()

        previousTokenToDocuments.forEach { (_, positions) ->
            positions.forEach {
                parentPositions[it] = it
            }
        }

        for (idx in 1 until tokens.size) {
            val currentTokenToDocuments = getTokenPositions(tokens[idx])
            val continuousTokenPositions = hashMapOf<Document, MutableList<Position>>()

            val documentsIntersection = previousTokenToDocuments.keys.intersect(currentTokenToDocuments.keys)

            documentsIntersection.forEach { document ->
                val previousPositions = previousTokenToDocuments.getValue(document)
                val currentPositions = currentTokenToDocuments.getValue(document)

                var ppPointer = 0
                var cpPointer = 0

                while (ppPointer < previousPositions.size && cpPointer < currentPositions.size) {
                    when {
                        currentPositions[cpPointer].first == previousPositions[ppPointer].first + 1 -> {
                            if (!continuousTokenPositions.containsKey(document)) {
                                continuousTokenPositions[document] = mutableListOf()
                            }

                            continuousTokenPositions[document]?.add(currentPositions[cpPointer])
                            parentPositions[previousPositions[ppPointer]]?.let { parentPositions.put(currentPositions[cpPointer], it) }

                            ++ppPointer
                            ++cpPointer
                        }
                        currentPositions[cpPointer].first < previousPositions[ppPointer].first + 1 -> {
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
            positions.value.map {
                parentPositions.getOrDefault(it, it)
            }
        }
    }

    private fun getTokenPositions(token: String): Map<Document, List<Position>> = lock.read {
        return index[token] ?: hashMapOf()
    }

    private fun findParent(tokens: List<String>, document: Document, position: Position): Position {
        var pos = position
        for (step in tokens.size - 2 downTo 0) {
            val idx = index[tokens[step]]?.get(document)?.binarySearch{
                pos.first - it.first
            }
            pos = idx?.let { index[tokens[step]]?.get(document)?.get(it) } !!
        }
        return pos
    }
}
