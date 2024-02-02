package io.doubleloop.solutions

import arrow.core.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CreationPhaseTests {

    data class Item(val qty: Int)

    private fun parseItem(qty: String): OptionalItem =
        if (qty.matches(Regex("^[0-9]+$"))) OptionalItem.Valid(Item(qty.toInt()))
        else OptionalItem.Invalid

    sealed class OptionalItem {
        data class Valid(val item: Item) : OptionalItem()
        data object Invalid : OptionalItem()
    }

    @Test
    fun `item creation`() {
        val result = parseItem("10")

        expectThat(result).isEqualTo(OptionalItem.Valid(Item(10)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid item creation`(input: String) {
        val result = parseItem(input)

        expectThat(result).isEqualTo(OptionalItem.Invalid)
    }


    data class ItemOpt(val qty: Int) {
        companion object {
            fun parseItem(qty: String): Option<Item> =
                if (qty.matches(Regex("^[0-9]+$"))) Item(qty.toInt()).some()
                else none()
        }
    }

    @Test
    fun `item creation (arrow)`() {
        val result = ItemOpt.parseItem("10")

        expectThat(result).isEqualTo(Some(Item(10)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid item creation (arrow)`(input: String) {
        val result = ItemOpt.parseItem(input)

        expectThat(result).isEqualTo(None)
    }
}