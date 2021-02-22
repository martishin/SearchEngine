package com.ttymonkey.search.query

import com.ttymonkey.search.index.Document
import com.ttymonkey.search.index.Position

class SearchResult(val document: Document, val positions: List<Position>) {
    override fun toString(): String {
        return "SearchResult(document=${document}, positions=${positions})"
    }
}
