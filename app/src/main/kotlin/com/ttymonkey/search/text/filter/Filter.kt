package com.ttymonkey.search.text.filter

interface Filter {
    fun process(tokens : List<String>): List<String>
}
