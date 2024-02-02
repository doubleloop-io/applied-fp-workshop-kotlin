package io.doubleloop

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },
    "/echo/{name}" bind POST to {
        val name = it.path("name") ?: "anonymous"
        val msg = Query.string().required("msg")(it)
        Response(OK).body("$msg $name!")
    }
)

fun main() {
    println("[WEB] Hello World!")

    val printingApp = PrintRequest().then(app)
    val server = printingApp.asServer(Jetty(9000)).start()

    println("Server started on " + server.port())
}