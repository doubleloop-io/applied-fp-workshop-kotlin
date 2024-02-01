package io.doubleloop

import arrow.core.*
import org.junit.jupiter.api.Test

class EitherTests {

    @Test
    fun creation() {
        // Right constructor fix only the right type parameter
        // e1 type is Either<Nothing, Int>
        val e1 = 5.right()

        // Explicit type to fix even the left type parameter
        val e2: Either<String, Int> = 5.right()

        // Function definition helps the type inference
        fun foo(e: Either<String, Int>) = e
        // The value pass as parameter has type Either<String, Int>
        val e3 = foo(5.right())

        // Swap the left and right type parameters
        val e4: Either<Int, String> = e3.swap()
    }

    @Test
    fun mapping() {
        val e1: Either<String, Int> = 5.right()

        // Change the right type parameter's type
        val e2: Either<String, String> = e1.map { it.toString() }

        // Change either's state
        val e3 = e2.flatMap { "error".left() }

        // Change the left type parameter's type
        val e4 = e3.mapLeft { it.length }
    }

    @Test
    fun `resolve errors`() {
        val e1: Either<String, Int> = "unknown".left()

        // The Left<String> value is converted to a Right<Int>
        val e2 = e1.getOrElse { it.hashCode() }.right()

        // Same conversions as before
        val e3 = e1.recover { a -> a.hashCode().right().bind() }
    }
}