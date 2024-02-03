package io.doubleloop

import arrow.core.Either
import arrow.core.raise.either

data class RunAppRequest(val commands: String)

data class RunAppResponse(val result: Either<ObstacleDetected, Rover>)

class RunAppHandler(
    private val missionSource: MissionSource
) {
    suspend fun handle(
        request: RunAppRequest
    ): RunAppResponse {
        val commands = validate(request)
        val planet = missionSource.readPlanet()
        val rover = missionSource.readRover()
        return RunAppResponse(executeAll(planet, rover, commands))
    }

    private suspend fun validate(request: RunAppRequest): List<Command> = either {
        parseCommands(request.commands).bind()
    }.toSuspend()
}