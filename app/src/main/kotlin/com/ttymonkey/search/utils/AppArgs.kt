package com.ttymonkey.search.utils

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

enum class RunMode { INDEX, QUERY }

class AppArgs(parser: ArgParser) {
    val index by parser.storing("--idx", help = "index location")

    val mode by parser.mapping(
            "--index" to RunMode.INDEX,
            "--query" to RunMode.QUERY,
            help = "mode: index or query"
    )

    val directory by parser.storing("--dir", help = "directory for search").default("./")
}
