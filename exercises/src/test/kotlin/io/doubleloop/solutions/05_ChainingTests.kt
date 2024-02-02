package io.doubleloop.solutions

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.option
import org.junit.jupiter.api.Test

class ChainingTests {

    data class ItemId(val id: Int)
    data class Item(val id: ItemId, val qty: Int) {
        fun checkIn(qty: Int): Item =
            copy(qty = this.qty + qty)
    }

    @Test
    fun `chaining with Option`() {
        fun load(id: ItemId): Option<Item> =
            Item(id, 100).some()

        fun save(item: Item): Option<Unit> =
            Unit.some()

        val program: Option<Unit> =
            load(ItemId(1))
                .map { it.checkIn(10) }
                .flatMap { save(it) }
    }

    @Test
    fun `chaining with Either`() {
        fun load(id: ItemId): Either<String, Item> =
            Item(id, 100).right()

        fun save(item: Item): Either<String, Unit> =
            Unit.right()

        val program: Either<String, Unit> =
            load(ItemId(1))
                .map { it.checkIn(10) }
                .flatMap { save(it) }
    }

    @Test
    fun `chaining with Option (syntax)`() {
        fun load(id: ItemId): Option<Item> =
            Item(id, 100).some()

        fun save(item: Item): Option<Unit> =
            Unit.some()

        val program: Option<Unit> = option {
            val item = load(ItemId(1)).bind()
            val updatedItem = item.checkIn(10)
            save(updatedItem).bind()
        }
    }

    @Test
    fun `chaining with Either (syntax)`() {
        fun load(id: ItemId): Either<String, Item> =
            Item(id, 100).right()

        fun save(item: Item): Either<String, Unit> =
            Unit.right()

        val program: Either<String, Unit> = either {
            val item = load(ItemId(1)).bind()
            val updatedItem = item.checkIn(10)
            save(updatedItem).bind()
        }
    }

}