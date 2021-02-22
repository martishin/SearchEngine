package com.ttymonkey.search.text.tokenizer

object SpaceTokenizer : Tokenizer {
    fun tokenize(string: String): List<String> = string
            .replace(Regex("""\p{Punct}+"""), " ")
            .split(Regex("""\s+"""))
            .filter { !it.isEmpty() }
            .toList()
}
