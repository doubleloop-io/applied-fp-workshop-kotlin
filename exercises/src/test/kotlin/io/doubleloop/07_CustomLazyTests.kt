package io.doubleloop

import arrow.core.identity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class CustomLazyTests {

    private var logs = ""

    @BeforeEach
    fun setUp() {
        logs = ""
    }

    fun increment(x: Int): Int {
        logs += "increment"
        return x + 1
    }

    fun string(x: Int): Lazy<String> {
        logs += "string"
        return Lazy.of { x.toString() }
    }

    @Test
    fun `creation phase`() {
        // TODO 2: implement 'of' function
        val result = Lazy.of { 10 }

        expectThat(logs).isEmpty()
        expectThat(result.run()).isEqualTo(10)
    }

    @Test
    fun `combination phase - normal`() {
        // TODO 3: implement 'map' function
        val result = Lazy.of { 10 }
            .map { increment(it) }

        expectThat(logs).isEmpty()
        expectThat(result.run()).isEqualTo(11)
    }

    @Test
    fun `combination phase - effect`() {
        // TODO 4: implement 'flatMap' function
        val result = Lazy.of { 10 }
            .flatMap { string(it) }

        expectThat(logs).isEmpty()
        expectThat(result.run()).isEqualTo("10")
    }

    @Test
    fun `removal phase`() {
        // TODO 5: implement 'run' function
        val result = Lazy.of { 10 }

        expectThat(result.run()).isEqualTo(10)
    }

    // TODO 6: check if functor laws holds
    @Test
    fun `functor laws - identity`() {
        val result = Lazy.of { 10 }
            .map { identity(it) }
            .run()
        val expected = Lazy.of { identity(10) }
            .run()
        expectThat(result).isEqualTo(expected)
    }

    @Test
    fun `functor laws - composition`() {
        val result = Lazy.of { 10 }
            .map { increment(it) }
            .map { increment(it) }
            .run()
        val expected = Lazy.of { 10 }
            .map { increment(increment(it)) }
            .run()
        expectThat(result).isEqualTo(expected)
    }

    // TODO 7: check if monad laws holds
    @Test
    fun `monad laws - left identity`() {
        val result = Lazy.of { 10 }
            .flatMap { string(it) }
            .run()
        val expected = string(10)
            .run()
        expectThat(result).isEqualTo(expected)
    }

    @Test
    fun `monad laws - right identity`() {
        val result = Lazy.of { 10 }
            .flatMap { Lazy.of { it } }
            .run()
        val expected = Lazy.of { 10 }
            .run()
        expectThat(result).isEqualTo(expected)
    }

    @Test
    fun `monad laws - associativity`() {
        val result = Lazy.of { 10 }
            .flatMap { Lazy.of { it } }
            .flatMap { string(it) }
            .run()
        val expected = Lazy.of { 10 }
            .flatMap { x -> Lazy.of { x }.flatMap { string(it) } }
            .run()
        expectThat(result).isEqualTo(expected)
    }

    data class Lazy<out A>(private val value: () -> A) {

        companion object {
            fun <A> of(value: () -> A): Lazy<A> =
                TODO()
        }

        fun <B> map(f: (A) -> B): Lazy<B> =
            TODO()

        fun <B> flatMap(f: (A) -> Lazy<B>): Lazy<B> =
            TODO()

        fun run(): A =
            TODO()
    }
}