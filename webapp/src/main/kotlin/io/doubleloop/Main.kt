package io.doubleloop

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

val app: HttpHandler = routes(
    "/hello" bind WebappRoutes.helloRoutes,
    "/rover" bind WebappRoutes.roverRoutes
)

fun main() {
    println("[WEB] Hello World!")

    val server = PrintRequest()
        .then(app)
        .asServer(Jetty(9000))
        .start()

    println("Server started on " + server.port())
}