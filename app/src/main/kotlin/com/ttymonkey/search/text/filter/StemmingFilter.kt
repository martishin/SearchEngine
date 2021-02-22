package com.ttymonkey.search.text.filter
import opennlp.tools.stemmer.PorterStemmer

class StemmingFilter : Filter {
    override fun process(tokens: List<String>): List<String> {
        val porterStemmer = PorterStemmer()
        return tokens.map(porterStemmer::stem)
    }
}
