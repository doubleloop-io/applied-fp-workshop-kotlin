package io.doubleloop.version5

class FileMissionSource(private val planetFile: String, private val roverFile: String) : MissionSource {
    // TODO 3: call load planet function
    override suspend fun readPlanet(): Planet =
        TODO()

    // TODO 4: call load rover function
    override suspend fun readRover(): Rover =
        TODO()
}

class ConsoleCommandsChannel : CommandsChannel {
    // TODO 5: call load commands function
    override suspend fun receive(): List<Command> =
        TODO()
}

class ConsoleMissionReport : MissionReport {
    // TODO 6: call write sequence completed function
    override suspend fun sequenceCompleted(rover: Rover) =
        TODO()

    // TODO 7: call write obstacle detected function
    override suspend fun obstacleDetected(rover: ObstacleDetected) =
        TODO()

    // TODO 8: call write error function
    override suspend fun error(error: Throwable) =
        TODO()
}