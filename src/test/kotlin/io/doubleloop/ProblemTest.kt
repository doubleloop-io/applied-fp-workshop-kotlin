package io.doubleloop

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ProblemTest {

    @Test
    fun `il bello di kotlin sono le stringhe nei nomi`() {
        val problem = Problem("ciao")
        expectThat(problem.msg).isEqualTo("ciao")
    }

    @Test
    fun `somma ok`() {
        assertEquals(2, 1 + 1)
    }

    @Test
    fun `somma ko`() {
//        assertEquals(2, 1 + 1 + 1)
        assertEquals(3, 1 + 1 + 1)
    }
}