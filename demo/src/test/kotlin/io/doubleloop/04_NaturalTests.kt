package io.doubleloop

import arrow.core.*
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class NaturalTransformationTests {

    @Test
    fun `from Option to Either (valid)`() {
        val source = 5.some()
        val result = source.toEither { "error" }

        expectThat(result).isEqualTo(5.right())
    }

    @Test
    fun `from Option to Either (invalid)`() {
        val source = none<Int>()
        val result = source.toEither { "error" }

        expectThat(result).isEqualTo("error".left())
    }

    @Test
    fun `from Either to Option (valid)`() {
        val source = 5.right()
        val result = source.getOrNone()

        expectThat(result).isEqualTo(5.some())
    }

    @Test
    fun `from Either to Option (invalid)`() {
        val source = "error".left()
        val result = source.getOrNone()

        expectThat(result).isEqualTo(none())
    }
}