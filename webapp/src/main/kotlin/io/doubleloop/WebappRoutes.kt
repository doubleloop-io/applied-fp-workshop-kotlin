package io.doubleloop

import arrow.core.raise.catch
import kotlinx.coroutines.runBlocking
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes

object WebappRoutes {

    val helloRoutes = routes(
        "/ping" bind Method.GET to {
            Response(Status.OK).body("pong")
        }
    )

    fun roverRoutes(handler: RunAppHandler) = routes(
        "/mission" bind Method.POST to {
            val body = it.parseJsonBody(JRunAppHttpRequest)

            runBlocking {
                catch({
                    handler.handle(body.toRunAppRequest()).toResponse()
                }) { it.toResponse() }
            }
        }
    )

    private fun RunAppResponse.toResponse(): Response =
        this.result.fold(
            { Response(Status.UNPROCESSABLE_ENTITY).bodyAsJson(JRunAppHttpResponse, it.renderObstacle()) },
            { Response(Status.OK).bodyAsJson(JRunAppHttpResponse, it.renderComplete()) }
        )

    private fun Throwable.toResponse() =
        Response(Status.BAD_REQUEST).body(this.message ?: "Unknown error")

}
