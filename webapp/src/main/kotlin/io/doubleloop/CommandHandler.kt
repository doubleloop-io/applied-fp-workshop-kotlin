package io.doubleloop

import arrow.core.Either
import arrow.core.raise.catch

class CommandHandler(
    private val missionSource: MissionSource,
    private val commandsChannel: CommandsChannel,
    private val missionReport: MissionReport
) {
    suspend fun runApp() {
        catch({
            runMission(missionSource, commandsChannel)
                .fold(
                    { missionReport.obstacleDetected(it) },
                    { missionReport.sequenceCompleted(it) }
                )
        }) { missionReport.error(it) }
    }

    private suspend fun runMission(
        missionSource: MissionSource,
        commandsChannel: CommandsChannel
    ): Either<ObstacleDetected, Rover> {
        val planet = missionSource.readPlanet()
        val rover = missionSource.readRover()
        val commands = commandsChannel.receive()
        return executeAll(planet, rover, commands)
    }
}