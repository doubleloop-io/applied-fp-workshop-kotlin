package io.doubleloop

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNPROCESSABLE_ENTITY
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AppTests {

    val app = createApp(Dependencies.create(Env()))

    @Test
    fun `get ping`() {
        val request = Request(GET, "/hello/ping")
        val response = app(request)
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("pong")
    }

    @Test
    fun `post commands`() {
        val request = Request(POST, "/rover/FF")
        val response = app(request)
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("0:2:N")
    }

    @Test
    fun `post commands (hit obstacle)`() {
        val request = Request(POST, "/rover/RFF")
        val response = app(request)
        expectThat(response).status.isEqualTo(UNPROCESSABLE_ENTITY)
        expectThat(response).bodyString.isEqualTo("O:1:0:E")
    }

    @Test
    fun `post invalid commands`() {
        val request = Request(POST, "/rover/FFX")
        val response = app(request)
        expectThat(response).status.isEqualTo(BAD_REQUEST)
        expectThat(response).bodyString.isEqualTo("Command parsing: invalid command: X")
    }
}