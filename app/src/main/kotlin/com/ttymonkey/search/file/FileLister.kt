package com.ttymonkey.search.file

import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors

class FileLister(private val baseDirectory: File) {
    fun list(): List<File> = Files.walk(baseDirectory.toPath())
            .map { it.toFile() }
            .filter { it.isFile }
            .collect(Collectors.toList())
}
