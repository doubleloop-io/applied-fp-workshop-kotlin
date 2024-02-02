package io.doubleloop

import arrow.core.Option
import arrow.core.none
import arrow.core.raise.option
import arrow.core.some
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class CombinationPhaseListTests {

    data class Item(val qty: Int)

    fun parseItem(qty: String): Option<Item> =
        if (qty.matches(Regex("^[0-9]+$"))) Item(qty.toInt()).some()
        else none()

    @Test
    fun `all valid - list of results`() {
        val items: List<Option<Item>> = listOf("1", "10", "100")
            .map { parseItem(it) }

        // TODO 1: change expected value
        //    pay attention to items type
        expectThat(items).isEqualTo(
            TODO()
        )
    }

    @Test
    fun `some invalid - list of results`() {
        val items: List<Option<Item>> = listOf("1", "asd", "100")
            .map { parseItem(it) }

        // TODO 2: change expected value
        //    pay attention to items type
        expectThat(items).isEqualTo(
            TODO()
        )
    }

    @Test
    fun `all valid - result of list`() {
        val items: Option<List<Item>> = option {
            listOf("1", "10", "100")
                .map { parseItem(it).bind() }
        }

        // TODO 3: change expected value
        //    pay attention to items type
        expectThat(items).isEqualTo(
            TODO()
        )
    }

    @Test
    fun `some invalid - result of list`() {
        val items: Option<List<Item>> = option {
            listOf("1", "asd", "100")
                .map { parseItem(it).bind() }
        }

        // TODO 4: change expected value
        //    pay attention to items type
        expectThat(items).isEqualTo(
            TODO()
        )
    }
}