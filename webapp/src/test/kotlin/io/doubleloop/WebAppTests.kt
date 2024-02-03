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

class WebAppTests {

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
        val request = Request(POST, "/rover/mission").body("{ \"commands\": \"FF\" }")
        val response = app(request)
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("{\"positionX\": 0, \"positionY\": 2, \"direction\": \"N\", \"hitObstacle\": false}")
    }

    @Test
    fun `post commands (hit obstacle)`() {
        val request = Request(POST, "/rover/mission").body("{ \"commands\": \"RFF\" }")
        val response = app(request)
        expectThat(response).status.isEqualTo(UNPROCESSABLE_ENTITY)
        expectThat(response).bodyString.isEqualTo("{\"positionX\": 1, \"positionY\": 0, \"direction\": \"E\", \"hitObstacle\": true}")
    }

    @Test
    fun `post invalid commands`() {
        val request = Request(POST, "/rover/mission").body("{ \"commands\": \"FFX\" }")
        val response = app(request)
        expectThat(response).status.isEqualTo(BAD_REQUEST)
        expectThat(response).bodyString.isEqualTo("Command parsing: invalid command: X")
    }

    @Test
    fun `convert request and response from and to json`() {
        val jsonRequest = JRunAppHttpRequest.fromJson("{ \"commands\": \"FF\" }").orThrow()
        expectThat(jsonRequest).isEqualTo(RunAppHttpRequest("FF"))

        val jsonResponse = JRunAppHttpResponse.toJson(RunAppHttpResponse(1, 2, "N", false))
        expectThat(jsonResponse).isEqualTo("{\"positionX\": 1, \"positionY\": 2, \"direction\": \"N\", \"hitObstacle\": false}")
    }
}