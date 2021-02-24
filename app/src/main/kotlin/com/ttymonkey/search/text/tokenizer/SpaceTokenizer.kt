package com.ttymonkey.search.text.tokenizer

object SpaceTokenizer {
    private val PunctuationMarksRe = Regex("""\p{Punct}+""")
    private val SpaceRe = Regex("""\s+""")

    fun tokenize(string: String): List<String> = string
            .replace(PunctuationMarksRe, " ")
            .split(SpaceRe)
            .filter { !it.isEmpty() }
            .toList()
}
