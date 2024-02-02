package io.doubleloop

import arrow.core.Option
import arrow.core.none
import arrow.core.raise.option
import arrow.core.some
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class CombinationPhaseManyTests {

    data class Item(val name: String, val qty: Int) {
        fun checkIn(qty: Int): Item =
            copy(qty = this.qty + qty)

        fun checkOut(qty: Int): Option<Item> =
            if (this.qty - qty < 0) none()
            else copy(qty = this.qty - qty).some()
    }

    // TODO 2: create an item only if name and quantity are valid
    fun parseItem(name: String, qty: String): Option<Item> =
        TODO()

    // TODO 3: same as before but using option syntax
    fun parseItemSyntax(name: String, qty: String): Option<Item> = option {
        TODO()
    }

    fun parseName(value: String): Option<String> =
        if (value.trim().isNotEmpty()) value.some()
        else none()

    fun parseQty(qty: String): Option<Int> =
        if (qty.matches(Regex("^[0-9]+$"))) qty.toInt().some()
        else none()

    @Test
    fun `valid creation`() {
        val item = parseItem("foo", "100")

        expectThat(item).isEqualTo(Item("foo", 100).some())
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  "])
    fun `invalid creation (name)`(input: String) {
        val item = parseItem(input, "10")

        expectThat(item).isEqualTo(none())
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid creation (qty)`(input: String) {
        val item = parseItemSyntax("foo", input)

        expectThat(item).isEqualTo(none())
    }

    @Test
    fun `invalid creation (both)`() {
        val item = parseItemSyntax("", "asd")

        expectThat(item).isEqualTo(none())
    }

    @Test
    fun `creation, checkIn and checkOut`() {
        val item = parseItemSyntax("foo", "100")

        val result = item
            .map { it.checkIn(10) }
            .flatMap { it.checkOut(20) }

        expectThat(result).isEqualTo(Item("foo", 90).some())
    }
}