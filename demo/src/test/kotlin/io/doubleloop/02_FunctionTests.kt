package io.doubleloop

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FunctionTests {
    // # Functions
    // A function is something that accepts an input value (Domain)
    // and produces an output value (Codomain).

    // ## Single function
    // Functions are described/documented by its type definition.
    //   f:  InType => OutType
    private fun parseString(input: String): Int =
        input.toInt()

    private fun reciprocal(input: Int): Double =
        1.0 / input

    private fun asString(input: Double): String =
        input.toString()

    @Test
    fun `function application`() {
        val result = parseString("42")

        expectThat(result).isEqualTo(42)
    }

    // ## Composed functions
    // we can create a "pipeline" as a combination of different functions

    @Test
    fun `manual passing of values to functions`() {

        fun pipeline(input: String): String {
            val input2 = parseString(input)
            val input3 = reciprocal(input2)
            return asString(input3)
        }

        val result = pipeline("42")

        expectThat(result).isEqualTo("0.023809523809523808")
    }

    @Test
    fun `combine many functions (function pipeline)`() {

        fun <A, B, C> andThen(first: (A) -> B, second: (B) -> C): (A) -> C =
            { a -> second(first(a)) }

        val pipeline = andThen(andThen(::parseString, ::reciprocal), ::asString)

        val result = pipeline("42")

        expectThat(result).isEqualTo("0.023809523809523808")
    }

    @Test
    fun `combine many functions (idiomatic)`() {

        fun <A, B, C> ((A) -> B).andThen(second: (B) -> C): (A) -> C =
            { a -> second(this(a)) }

        val pipeline = (::parseString).andThen(::reciprocal).andThen(::asString)
        val result = pipeline("42")

        expectThat(result).isEqualTo("0.023809523809523808")
    }

    @Test
    fun `combine many functions (start from value)`() {

        fun <A, B> A.andThen(second: (A) -> B): B =
            second(this)

        val result = "42".andThen(::parseString).andThen(::reciprocal).andThen(::asString)

        expectThat(result).isEqualTo("0.023809523809523808")
    }

    @Test
    fun `combine many functions (the let way)`() {

        val result = parseString("42")
            .let { reciprocal(it) }
            .let { asString(it) }

        expectThat(result).isEqualTo("0.023809523809523808")
    }

    // ## Multiple Dispatch

    // Multiple dispatch (also known as multi-methods) is a feature in which
    // a function can be dispatched based on the type of one of its arguments.

    // This technique is used in conjunction with sum types.
    sealed class TrafficLight {
        data object Red : TrafficLight()
        data object Green : TrafficLight()
        data object Yellow : TrafficLight()

        // Use pattern match to dispatch different behavior.
        // The "exhaustive check" verify that there is a branch for each type case.
        fun next(): TrafficLight = when (this) {
            Red -> Green
            Green -> Yellow
            Yellow -> Red
        }
    }
}