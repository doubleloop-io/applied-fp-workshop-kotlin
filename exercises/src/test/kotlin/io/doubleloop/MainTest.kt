package io.doubleloop

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MainTest{
    
    @Test
    fun `try me`() {
        expectThat("hello").isEqualTo("hello")
    }
}