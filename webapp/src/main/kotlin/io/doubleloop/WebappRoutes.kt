package io.doubleloop

import arrow.core.raise.catch
import kotlinx.coroutines.runBlocking
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

object WebappRoutes {

    val helloRoutes = routes(
        "/ping" bind Method.GET to {
            Response(Status.OK).body("pong")
        }
    )

    fun roverRoutes(handler: RunAppHandler) = routes(
        "/{commands}" bind Method.POST to {
//            // create adapters
//            val fileMissionSource = FileMissionSource("planet.txt", "rover.txt")
//            // create use case handler
//            val handler = RunAppHandler(fileMissionSource)

            // run use case
            runBlocking {
                catch({
                    handler.handle(it.toRunAppRequest()).toResponse()
                }) { it.toResponse() }
            }
        }
    )

    private fun Request.toRunAppRequest(): RunAppRequest =
        RunAppRequest(this.path("commands") ?: "")

    private fun RunAppResponse.toResponse(): Response =
        this.result.fold(
            { Response(Status.UNPROCESSABLE_ENTITY).body(renderObstacle(it)) },
            { Response(Status.OK).body(renderComplete(it)) }
        )

    private fun Throwable.toResponse() =
        Response(Status.BAD_REQUEST).body(renderError(this))
}
