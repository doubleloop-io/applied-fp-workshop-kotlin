package io.doubleloop.solutions.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

object File {
    suspend fun loadPair(fileName: String): Pair<String, String> {
        val lines = loadLines(fileName)
        return when {
            lines.size == 2 -> Pair(lines[0], lines[1])
            else -> throw RuntimeException("Invalid file content: $fileName")
        }
    }

    private suspend fun loadLines(fileName: String): List<String> = withContext(Dispatchers.IO) {
        val resourceStream = File::class.java.classLoader.getResourceAsStream(fileName)

        resourceStream?.use { stream -> InputStreamReader(stream).readLines() }
            ?: throw IllegalArgumentException("File not found: $fileName")
    }
}