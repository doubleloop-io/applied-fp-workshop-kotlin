package io.doubleloop

import arrow.core.Either
import arrow.core.Option
import arrow.core.right
import arrow.core.some
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

// TODO 1: remove the disabled annotation and make all tests green
@Disabled
class ChainingTests {

    data class ItemId(val id: Int)
    data class Item(val id: ItemId, val qty: Int) {
        fun checkIn(qty: Int): Item =
            copy(qty = this.qty + qty)
    }

    @Test
    fun `chaining with Option`() {
        // NOTE: stub implementations
        //  just to make the compiler happy
        fun load(id: ItemId): Option<Item> =
            Item(id, 100).some()

        fun save(item: Item): Option<Unit> =
            Unit.some()

        // TODO 1: write a program that:
        //  load item id 123,
        //  check in 10 and
        //  finally save the item
        val program: Option<Unit> = TODO()
    }

    @Test
    fun `chaining with Either`() {
        // NOTE: stub implementations
        //  just to make the compiler happy
        fun load(id: ItemId): Either<String, Item> =
            Item(id, 100).right()

        fun save(item: Item): Either<String, Unit> =
            Unit.right()

        // TODO 2: write a program that:
        //  load item id 123,
        //  check in 10 and
        //  finally save the item
        val program: Either<String, Unit> = TODO()
    }
}