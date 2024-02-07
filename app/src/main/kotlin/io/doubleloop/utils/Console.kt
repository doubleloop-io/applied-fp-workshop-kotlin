package io.doubleloop.utils

import kotlinx.coroutines.coroutineScope

object Console {

    const val GREEN = "\u001B[32m"
    const val RED = "\u001B[31m"
    const val RESET = "\u001B[0m"

    suspend fun ask(): String = coroutineScope {
        puts("Waiting commands...")
        reads()
    }

    suspend fun logInfo(message: String) = coroutineScope {
        puts(green("[OK] $message"))
    }

    suspend fun logError(message: String) = coroutineScope {
        puts(red("[ERROR] $message"))
    }

    private fun puts(message: String) =
        println(message)

    private fun reads(): String =
        readlnOrNull() ?: ""

    private fun green(message: String): String =
        "$GREEN$message$RESET"

    private fun red(message: String): String =
        "$RED$message$RESET"
}