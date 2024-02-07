package io.doubleloop.version4

/*
    ## V4 - Focus on infrastructure (compose I/O operations)

    Extend the pure way of work also to the infrastructural layer
    - Read planet data from file (size and obstacles)
    - Read rover data from file (position and orientation)
    - Ask commands from the console
    - Implement an entrypoint that:
      - run the whole app
      - log final rover output to the console
      - log any unhandled error to the console
 */

import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import kotlin.coroutines.suspendCoroutine

// TODO 1: load file contest as pair (see utils.File.loadPair) and then parse in a planet
// HINT: combination phase effect (Monad)
// HINT: use either syntax
suspend fun loadPlanet(fileName: String): Planet = either {
    raise(TODO())
}.toSuspend()

// TODO 2: load file contest as pair (see utils.File.loadPair) and then parse in a rover
// HINT: combination phase effect (Monad)
// HINT: use either syntax
suspend fun loadRover(fileName: String): Rover = either {
    raise(TODO())
}.toSuspend()

// TODO 3: ask for commands string (see utils.Console.ask) and then parse in a list of commands
// HINT: combination phase effect (Monad)
// HINT: use either syntax
suspend fun loadCommands(): List<Command> = either {
    raise(TODO())
}.toSuspend()

// TODO 4: load planet, rover and commands and then execute all
suspend fun runMission(planetFile: String, roverFile: String): Either<ObstacleDetected, Rover> {
    val planet = TODO()
    val rover = TODO()
    val commands = TODO()
    return executeAll(planet, rover, commands)
}

// TODO 5: render the rover and log it as info (see utils.Console.logInfo)
suspend fun writeSequenceCompleted(rover: Rover) {
    TODO()
}

// TODO 6: render the rover and log it as info (see utils.Console.logInfo)
suspend fun writeObstacleDetected(rover: ObstacleDetected) {
    TODO()
}

// TODO 7: log message as error (see utils.Console.logError)
suspend fun writeError(error: Throwable) {
    val message = error.message ?: "Unknown error"
    TODO()
}

// TODO 8: runMission, then fold in writeSequenceCompleted or writeObstacleDetected
// HINT: combine phase normal and then removal phase
// TODO 9: catch any unhandled error and then writeError
suspend fun runApp(planetFile: String, roverFile: String) {
    catch({
        val result = runMission(planetFile, roverFile)
        TODO()
    }) { TODO() }
}

suspend fun <R> Either<ParseError, R>.toSuspend(): R = suspendCoroutine { continuation ->
    fold(
        { error -> continuation.resumeWith(Result.failure(RuntimeException(renderError(error)))) },
        { value -> continuation.resumeWith(Result.success(value)) }
    )
}