package io.doubleloop

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class CoroutinesTests {
    // NOTE: For a complete guide to coroutines, see https://kotlinlang.org/docs/reference/coroutines-overview.html

    @Test
    fun `hello world`() {
        runBlocking {
            launch {
                delay(1000L)
                println("World!")
            }
            println("Hello")
        }
    }

    @Test
    fun `hello world (extracted fun)`() {
        suspend fun doWorld() {
            delay(1000L)
            println("World!")
        }

        runBlocking {
            launch { doWorld() }
            println("Hello")
        }
    }

    @Test
    fun `hello world (concurrently)`() {
        suspend fun doWorld() = coroutineScope { // this: CoroutineScope
            launch {
                delay(2000L)
                println("World 2")
            }
            launch {
                delay(1000L)
                println("World 1")
            }
            println("Hello")
        }

        runBlocking {
            doWorld()
            println("Done")
        }
    }

    @Test
    fun `hello world (raise)`() {
        suspend fun doWorld() {
            delay(1000L)
            throw Exception("Boom!")
        }

        try {
            runBlocking {
                launch { doWorld() }
                println("Hello")
            }
        } catch (e: Exception) {
            println("Caught: $e")
        }
    }
}