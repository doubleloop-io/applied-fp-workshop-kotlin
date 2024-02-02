package io.doubleloop.version5

/*
    ## V5 - Testability via injection (Port/Adapter architectural style)

    Apply Dependency Inversion Principle (DIP) and Dependency Injection (DI) to our application
    - Look to the Ports for read planet, rover and commands
    - Implement port adapters
    - Define injectable application
    - Use test doubles in test suite
 */

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import io.doubleloop.utils.Console.ask
import io.doubleloop.utils.Console.logError
import io.doubleloop.utils.Console.logInfo
import io.doubleloop.utils.File.loadPair
import kotlin.coroutines.suspendCoroutine

// TODO 1: go to Ports.kt and get familiar with the interfaces

// TODO 2: go to Adapters.kt and solve the TODOs

// TODO 9: instantiate adapters
suspend fun runApp(planetFile: String, roverFile: String) {
    val fileMissionSource = TODO()
    val consoleCommandsChannel = TODO()
    val consoleMissionReport = TODO()
    runApp(fileMissionSource, consoleCommandsChannel, consoleMissionReport)
}

// TODO 10: replace write* function calls with missionReport methods
suspend fun runApp(
    missionSource: MissionSource,
    commandsChannel: CommandsChannel,
    missionReport: MissionReport
) {
    catch({
        runMission(missionSource, commandsChannel)
            .fold(
                { writeObstacleDetected(it) },
                { writeSequenceCompleted(it) }
            )
    }) { writeError(it) }
}

// TODO 11: use missionSource and commandsChannel to get data
suspend fun runMission(
    missionSource: MissionSource,
    commandsChannel: CommandsChannel
): Either<ObstacleDetected, Rover> {
    val planet = TODO()
    val rover = TODO()
    val commands = TODO()
    return executeAll(planet, rover, commands)
}

suspend fun loadPlanet(fileName: String): Planet = either {
    val input = loadPair(fileName)
    parsePlanet(input).bind()
}.toSuspend()

suspend fun loadRover(fileName: String): Rover = either {
    val input = loadPair(fileName)
    parseRover(input).bind()
}.toSuspend()

suspend fun loadCommands(): List<Command> = either {
    val input = ask("Waiting commands...")
    parseCommands(input).bind()
}.toSuspend()

suspend fun writeSequenceCompleted(rover: Rover) {
    logInfo(renderComplete(rover))
}

suspend fun writeObstacleDetected(rover: ObstacleDetected) {
    logInfo(renderObstacle(rover))
}

suspend fun writeError(error: Throwable) {
    logError(error.message ?: "Unknown error")
}

suspend fun <R> Either<ParseError, R>.toSuspend(): R = suspendCoroutine { continuation ->
    fold(
        { error -> continuation.resumeWith(Result.failure(RuntimeException(renderError(error)))) },
        { value -> continuation.resumeWith(Result.success(value)) }
    )
}