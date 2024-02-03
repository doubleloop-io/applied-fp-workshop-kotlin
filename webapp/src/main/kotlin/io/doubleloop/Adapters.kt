package io.doubleloop

import arrow.core.Either
import arrow.core.raise.either
import io.doubleloop.File.loadPair
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import kotlin.coroutines.suspendCoroutine

class FileMissionSource(private val planetFile: String, private val roverFile: String) : MissionSource {
    override suspend fun readPlanet(): Planet = either {
        val input = loadPair(planetFile)
        parsePlanet(input).bind()
    }.toSuspend()

    override suspend fun readRover(): Rover = either {
        val input = loadPair(roverFile)
        parseRover(input).bind()
    }.toSuspend()
}

suspend fun <R> Either<ParseError, R>.toSuspend(): R = suspendCoroutine { continuation ->
    fold(
        { error -> continuation.resumeWith(Result.failure(RuntimeException(renderError(error)))) },
        { value -> continuation.resumeWith(Result.success(value)) }
    )
}