package io.doubleloop.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Console {

    const val GREEN = "\u001B[32m"
    const val RED = "\u001B[31m"
    const val RESET = "\u001B[0m"

    suspend fun ask(question: String): String {
        puts(question)
        return reads()
    }

    suspend fun logInfo(message: String) {
        puts(green("[OK] $message"))
    }

    suspend fun logError(message: String) {
        puts(red("[ERROR] $message"))
    }

    private suspend fun puts(message: String) = withContext(Dispatchers.IO) {
        println(message)
    }

    private suspend fun reads(): String = withContext(Dispatchers.IO) {
        readLine() ?: ""
    }

    private fun green(message: String): String =
        "$GREEN$message$RESET"

    private fun red(message: String): String =
        "$RED$message$RESET"
}