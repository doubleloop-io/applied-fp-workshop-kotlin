package io.doubleloop

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun createApp(dependencies: Dependencies): HttpHandler = routes(
    "/hello" bind WebappRoutes.helloRoutes,
    "/rover" bind WebappRoutes.roverRoutes(dependencies.runAppHandler)
)

fun main() {
    println("[WEB] Hello World!")

    val env = Env()
    val dependencies = Dependencies.create(env)
    val app = createApp(dependencies)
    val server = PrintRequest()
        .then(app)
        .asServer(Jetty(9000))
        .start()

    println("Server started on " + server.port())
}