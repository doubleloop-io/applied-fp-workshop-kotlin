package io.doubleloop.solutions.version4

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import io.doubleloop.solutions.utils.Console.ask
import io.doubleloop.solutions.utils.Console.logError
import io.doubleloop.solutions.utils.Console.logInfo
import io.doubleloop.solutions.utils.File.loadPair
import kotlin.coroutines.suspendCoroutine

suspend fun createApplication(planetFile: String, roverFile: String): Unit {
    catch({
        runMission(planetFile, roverFile)
            .fold(
                { writeObstacleDetected(it) },
                { writeSequenceCompleted(it) }
            )
    }) { writeError(it) }
}

suspend fun runMission(planetFile: String, roverFile: String): Either<ObstacleDetected, Rover> {
    val planet = loadPlanet(planetFile)
    val rover = loadRover(roverFile)
    val commands = loadCommands()
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