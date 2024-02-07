package io.doubleloop.solutions.version2

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import io.doubleloop.solutions.version2.Command.*
import io.doubleloop.solutions.version2.Orientation.*
import io.doubleloop.solutions.version2.ParseError.*

sealed class ParseError {
    data class InvalidPlanet(val message: String) : ParseError()
    data class InvalidRover(val message: String) : ParseError()
    data class InvalidCommand(val message: String) : ParseError()
}

fun runApp(
    inputPlanet: Pair<String, String>,
    inputRover: Pair<String, String>,
    inputCommands: String
): Either<ParseError, String> =
    either {
        val planet = parsePlanet(inputPlanet).bind()
        val rover = parseRover(inputRover).bind()
        val commands = parseCommands(inputCommands).bind()
        // val (planet, rover, commands) = parseAll(input1, ....)
        val it = executeAll(planet, rover, commands)
        renderComplete(it)
    }

fun parseCommand(input: Char): Either<ParseError, Command> =
    when (input.lowercase()) {
        "f" -> MoveForward.right()
        "b" -> MoveBackward.right()
        "r" -> TurnRight.right()
        "l" -> TurnLeft.right()
        else -> InvalidCommand("invalid command: $input").left()
    }

fun parseCommands(input: String): Either<ParseError, List<Command>> = either {
    input
        .trim()
        .map { parseCommand(it).bind() }
}

fun parsePosition(input: String): Either<ParseError, Position> =
    parsePair(",", input)
        .map { Position(it.first, it.second) }
        .mapLeft { InvalidRover("invalid position: $input") }

fun parseOrientation(input: String): Either<ParseError, Orientation> =
    when (input.trim().lowercase()) {
        "n" -> N.right()
        "w" -> W.right()
        "e" -> E.right()
        "s" -> S.right()
        else -> InvalidRover("invalid orientation: $input").left()
    }

fun parseRover(input: Pair<String, String>): Either<ParseError, Rover> = either {
    val (inputPosition, inputOrientation) = input
    val position = parsePosition(inputPosition).bind()
    val orientation = parseOrientation(inputOrientation).bind()
    Rover(position, orientation)
}

fun parseSize(input: String): Either<ParseError, Size> =
    parsePair("x", input)
        .map { Size(it.first, it.second) }
        .mapLeft { InvalidPlanet("invalid size: $input") }

fun parseObstacle(input: String): Either<ParseError, Obstacle> =
    parsePair(",", input)
        .map { Obstacle(it.first, it.second) }
        .mapLeft { InvalidPlanet("invalid obstacle: $input") }

fun parseObstacles(input: String): Either<ParseError, List<Obstacle>> = either {
    input
        .split(" ")
        .map { parseObstacle(it).bind() }
}

fun parsePlanet(input: Pair<String, String>): Either<ParseError, Planet> = either {
    val (inputSize, inputObstacles) = input
    val size = parseSize(inputSize).bind()
    val obstacles = parseObstacles(inputObstacles).bind()
    Planet(size, obstacles)
}

fun parsePair(separator: String, input: String): Either<Throwable, Pair<Int, Int>> =
    Either.catch {
        val parts = input.split(separator)
        val first = parts[0].trim().toInt()
        val second = parts[1].trim().toInt()
        Pair(first, second)
    }

fun renderComplete(rover: Rover): String =
    "${rover.position.x}:${rover.position.y}:${rover.orientation}"
