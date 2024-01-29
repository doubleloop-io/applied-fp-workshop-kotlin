package io.doubleloop.application.solutions.version2

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right

sealed class ParseError {
    data class InvalidPlanet(val message: String) : ParseError()
    data class InvalidRover(val message: String) : ParseError()
    data class InvalidCommand(val message: String) : ParseError()
}

fun runMission(
    inputPlanet: Pair<String, String>,
    inputRover: Pair<String, String>,
    inputCommands: String
): Either<ParseError, String> = either {
    val planet = parsePlanet(inputPlanet).bind()
    val rover = parseRover(inputRover).bind()
    val commands = parseCommands(inputCommands).bind()
    val result = executeAll(planet, rover, commands)
    render(result)
}

// PARSING
fun parseCommand(input: Char): Either<ParseError, Command> =
    when (input.lowercase()) {
        "f" -> Command.MoveForward.right()
        "b" -> Command.MoveBackward.right()
        "r" -> Command.TurnRight.right()
        "l" -> Command.TurnLeft.right()
        else -> ParseError.InvalidCommand("invalid command: $input").left()
    }

fun parseCommands(input: String): Either<ParseError, List<Command>> = either {
    input
        .trim()
        .map { parseCommand(it).bind() }
}

fun parsePosition(input: String): Either<ParseError, Position> =
    parseTuple(",", input)
        .map { Position(it.first, it.second) }
        .mapLeft { ParseError.InvalidRover("invalid position: $input") }

fun parseOrientation(input: String): Either<ParseError, Orientation> =
    when (input.trim().lowercase()) {
        "n" -> Orientation.N.right()
        "w" -> Orientation.W.right()
        "e" -> Orientation.E.right()
        "s" -> Orientation.S.right()
        else -> ParseError.InvalidRover("invalid orientation: $input").left()
    }

fun parseRover(input: Pair<String, String>): Either<ParseError, Rover> = either {
    val (inputPosition, inputOrientation) = input
    val position = parsePosition(inputPosition).bind()
    val orientation = parseOrientation(inputOrientation).bind()
    Rover(position, orientation)
}

fun parseSize(input: String): Either<ParseError, Size> =
    parseTuple("x", input)
        .map { Size(it.first, it.second) }
        .mapLeft { ParseError.InvalidPlanet("invalid size: $input") }

fun parseObstacle(input: String): Either<ParseError, Obstacle> =
    parseTuple(",", input)
        .map { Obstacle(it.first, it.second) }
        .mapLeft { ParseError.InvalidPlanet("invalid obstacle: $input") }

fun parseObstacles(input: String): Either<ParseError, List<Obstacle>> = either {
    input
        .split(" ")
        .map { parseObstacle(it).bind() }
}
    .mapLeft { ParseError.InvalidPlanet("invalid obstacles: $input") }

fun parsePlanet(input: Pair<String, String>): Either<ParseError, Planet> = either {
    val (inputSize, inputObstacles) = input
    val size = parseSize(inputSize).bind()
    val obstacles = parseObstacles(inputObstacles).bind()
    Planet(size, obstacles)
}

fun parseTuple(separator: String, input: String): Either<Throwable, Pair<Int, Int>> =
    Either.catch {
        val parts = input.split(separator)
        val first = parts[0].trim().toInt()
        val second = parts[1].trim().toInt()
        Pair(first, second)
    }

// RENDERING
fun render(rover: Rover): String =
    "${rover.position.x}:${rover.position.y}:${rover.orientation}"
