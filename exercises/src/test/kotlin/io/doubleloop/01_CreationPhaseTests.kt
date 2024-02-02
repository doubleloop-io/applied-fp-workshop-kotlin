package io.doubleloop

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class CreationPhaseTests {

    data class Item(val qty: Int)

    // TODO 2: complete the sum type definition
    sealed class OptionalItem {
    }

    // TODO 3: use OptionalItem as return type and remove throw
    fun parseItem(qty: String): Item =
        if (qty.matches(Regex("^[0-9]+$"))) Item(qty.toInt())
        else throw IllegalArgumentException("Invalid item") // or return null

    @Test
    fun `item creation`() {
        val result = parseItem("10")

        // TODO 4: change expected value
        // expectThat(result).isEqualTo(Item(10))
    }

    @ParameterizedTest
    @ValueSource(strings = ["asd", "1 0 0", ""])
    fun `invalid item creation`(input: String) {
        val result = parseItem(input)

        // TODO 5: change expected value
        // expectThat(result).isEqualTo(TODO())
    }
}