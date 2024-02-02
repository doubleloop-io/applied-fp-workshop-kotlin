package io.doubleloop.version2

/*
    ## V2 - Focus on boundaries (from primitive to domain types and vice versa)

    Our domain is declared with rich types but inputs/outputs are primitive types
    - Write a parser for input planet data (size, obstacles)
    - Write a parser for input rover data (position, orientation)
    - Write a parser for input commands
    - Render the final result as string: "positionX:positionY:orientation"
 */

import arrow.core.Either

// TODO 1: get familiar withParseError sum type
sealed class ParseError {
    data class InvalidPlanet(val message: String) : ParseError()
    data class InvalidRover(val message: String) : ParseError()
    data class InvalidCommand(val message: String) : ParseError()
}

// NOTE: utility function to split a string in a pair of numbers given a separator
// EXAMPLE USAGE:
//  parsePair("-", "1-0") == Right((1, 0))
//  parsePair("-", "10") == Left(Exception(...))
//  parsePair("-", "1, 0") == Left(Exception(...))
fun parsePair(separator: String, input: String): Either<Throwable, Pair<Int, Int>> =
    Either.catch {
        val parts = input.split(separator)
        val first = parts[0].trim().toInt()
        val second = parts[1].trim().toInt()
        Pair(first, second)
    }

// TODO 2: parse input in a pair, then in a position and set proper error
// INPUT EXAMPLE: "2,0" -> Right(Position(2, 0))
// INPUT EXAMPLE: "20" -> Left(InvalidPlanet("Invalid position"))
// HINT: combination phase normal (Functor)
fun parsePosition(input: String): Either<ParseError, Position> =
    TODO()

// TODO 3: parse input in a orientation or returns an error
// INPUT EXAMPLE: "N" -> Right(N)
// INPUT EXAMPLE: "X" -> Left(InvalidPlanet("Invalid orientation"))
// HINT: creation phase
fun parseOrientation(input: String): Either<ParseError, Orientation> =
    TODO()

// TODO 4: parse the pair in a rover and set proper error
// INPUT EXAMPLE: ("2,0", "N") -> Right(Rover(Position(2, 0), N))
// HINT: combination phase many (Applicative)
fun parseRover(input: Pair<String, String>): Either<ParseError, Rover> =
    TODO()

// TODO 5: parse input in a pair, then in a size and set proper error
// INPUT EXAMPLE: "5x4" -> Right(Size(5, 4))
// HINT: combination phase normal (Functor)
fun parseSize(input: String): Either<ParseError, Size> =
    TODO()

// TODO 6: parse input in a pair, then in an obstacle and set proper error
// INPUT EXAMPLE: "2,0" -> Right(Obstacle(2, 0))
// HINT: combination phase normal (Functor)
fun parseObstacle(input: String): Either<ParseError, Obstacle> =
    TODO()

// TODO 7: split input by space and parse each part in an obstacle and combine in one result
// INPUT EXAMPLE: "2,0 0,3" -> Right(listOf(Obstacle(2, 0), Obstacle(0, 3)))
// HINT: combination phase list (Traversal)
fun parseObstacles(input: String): Either<ParseError, List<Obstacle>> =
    TODO()

// TODO 8: parse pair in a planet and set proper error
// INPUT EXAMPLE: ("5x4", "2,0 0,3") -> Right(Planet(Size(5, 4), listOf(Obstacle(2, 0), Obstacle(0, 3))))
// HINT: combination phase many (Applicative)
fun parsePlanet(input: Pair<String, String>): Either<ParseError, Planet> =
    TODO()

// TODO 9: parse input in a command or returns an error
// INPUT EXAMPLE: "B" -> Right(MoveBackward)
// INPUT EXAMPLE: "X" -> Left(InvalidCommand("Invalid command"))
// HINT: creation phase
fun parseCommand(input: Char): Either<ParseError, Command> =
    TODO()

// TODO 10: parse each char in a command and combine in one result
// INPUT EXAMPLE: "BFLR" -> Right(listOf(MoveBackward, MoveForward, TurnLeft, TurnRight))
// INPUT EXAMPLE: "BFXLR" -> Left(InvalidCommand("Invalid command"))
// HINT: combination phase list (Traversal)
fun parseCommands(input: String): Either<ParseError, List<Command>> =
    TODO()

// TODO 11: call `domain.executeAll` with parsed planet, rover and commands
// HINT: combination phase many (Applicative)
fun runMission(
    inputPlanet: Pair<String, String>,
    inputRover: Pair<String, String>,
    inputCommands: String
): Either<ParseError, Rover> =
    TODO()

// TODO 12: convert the rover in a string
// OUTPUT EXAMPLE: Rover(Position(3, 2), N) -> "3:2:S"
fun renderComplete(rover: Rover): String =
    TODO()

// TODO 13: call `runMission` and render the result
fun runApp(
    inputPlanet: Pair<String, String>,
    inputRover: Pair<String, String>,
    inputCommands: String
): Either<ParseError, String> =
    TODO()