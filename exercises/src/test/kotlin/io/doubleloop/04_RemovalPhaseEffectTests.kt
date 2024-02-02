package io.doubleloop

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.none
import arrow.core.some
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class RemovalPhaseTests {

    data class Item(val qty: Int) {
        fun checkIn(qty: Int): Item =
            copy(qty = this.qty + qty)

        fun checkOut(qty: Int): Option<Item> =
            if (this.qty - qty < 0) none()
            else copy(qty = this.qty - qty).some()
    }

    fun parseItem(qty: String): Option<Item> =
        if (qty.matches(Regex("^[0-9]+$"))) Item(qty.toInt()).some()
        else none()

    @Test
    fun `creation and removal`() {
        val item = parseItem("100")

        val result = item
            .getOrElse { Item(0) }

        // TODO 2: change expected value
        expectThat(result).isEqualTo(TODO())
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid creation and removal`(input: String) {
        val item = parseItem(input)

        val result = item
            .getOrElse { Item(0) }

        // TODO 3: change expected value
        expectThat(result).isEqualTo(TODO())
    }

    @Test
    fun `creation, checkIn, checkOut and removal`() {
        val item = parseItem("100")

        val result = item
            .map { it.checkIn(10) }
            .flatMap { it.checkOut(20) }
            .getOrElse { Item(0) }

        // TODO 4: change expected value
        expectThat(result).isEqualTo(TODO())
    }

    @Test
    fun `creation and removal (change result type)`() {
        val item = parseItem("100")

        val result = item
            .fold({ "alternative" }, { it.qty.toString() })

        // TODO 5: change expected value
        expectThat(result).isEqualTo(TODO())
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid creation and removal (change result type)`(input: String) {
        val item = parseItem(input)

        val result = item
            .fold({ "alternative" }, { it.qty.toString() })

        // TODO 5: change expected value
        expectThat(result).isEqualTo(TODO())
    }
}