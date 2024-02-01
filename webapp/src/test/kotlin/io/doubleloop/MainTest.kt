package io.doubleloop

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MainTest {

    @Test
    fun `Ping test`() {
        val request = Request(GET, "/ping")
        val response = app(request)
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("pong")
    }
}