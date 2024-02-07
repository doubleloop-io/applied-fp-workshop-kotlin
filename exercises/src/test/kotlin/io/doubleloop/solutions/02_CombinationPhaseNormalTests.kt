package io.doubleloop.solutions

import arrow.core.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CombinationPhaseNormalTests {
    // Functor:
    // 1. type constructor:
    //      Option<A>
    // 2. map function:
    //      Option<A>.map(A -> B): Option<B>
    // 3. respect laws (tests)
    //      identity, composition

    data class Item(val qty: Int) {
        fun checkIn(qty: Int): Item =
            copy(qty = this.qty + qty)
    }

    fun parseItem(qty: String): Option<Item> =
        if (qty.matches(Regex("^[0-9]+$"))) Item(qty.toInt()).some()
        else none()

    @Test
    fun `creation and checkIn`() {
        val item = parseItem("10")

        val result = item
            .map { it.checkIn(10) }

        expectThat(result).isEqualTo(Some(Item(20)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid creation and checkIn`(input: String) {
        val item = parseItem(input)

        val result = item
            .map { it.checkIn(10) }

        expectThat(result).isEqualTo(None)
    }
}