package com.ttymonkey.search.text.filter

class LowerCaseFilter : Filter {
    override fun process(tokens: List<String>): List<String> = tokens.map(String::toLowerCase)
}
