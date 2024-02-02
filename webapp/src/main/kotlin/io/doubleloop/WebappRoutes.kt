package io.doubleloop

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

    val roverRoutes = routes(
        "/{commands}" bind Method.POST to {
            // create adapters
            val fileMissionSource = FileMissionSource("planet.txt", "rover.txt")
            val requestCommandsChannel = RequestCommandsChannel(it)
            val responseMissionReport = ResponseMissionReport()
            // create use case handler
            val handler = CommandHandler(fileMissionSource, requestCommandsChannel, responseMissionReport)

            // run use case
            runBlocking {
                handler.runMission()
            }

            // return response
            responseMissionReport.response
        }
    )
}
