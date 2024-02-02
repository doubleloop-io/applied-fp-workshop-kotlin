package io.doubleloop

import arrow.core.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class CombinationPhaseNormalTests {

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

        // TODO 2: use 'map' to 'checkIn(10)'
        //   and observe the result
        val result = item

        expectThat(result).isEqualTo(Some(Item(20)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid creation and checkIn`(input: String) {
        val item = parseItem(input)

        // TODO 3: use 'map' to 'checkIn(10)'
        //   and observe the result
        val result = item

        expectThat(result).isEqualTo(None)
    }
}