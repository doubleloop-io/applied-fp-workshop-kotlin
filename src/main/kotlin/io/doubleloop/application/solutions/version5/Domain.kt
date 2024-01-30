package io.doubleloop.application.solutions.version5

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import io.doubleloop.application.solutions.version5.Command.*
import io.doubleloop.application.solutions.version5.Orientation.*

data class Obstacle(val x: Int, val y: Int)
data class Planet(val size: Size, val obstacles: List<Obstacle>)
data class Size(val width: Int, val height: Int)
data class Position(val x: Int, val y: Int)
data class Rover(val position: Position, val orientation: Orientation)
data class Delta(val x: Int, val y: Int)
typealias ObstacleDetected = Rover

sealed class Command {
    data object MoveForward : Command()
    data object MoveBackward : Command()
    data object TurnRight : Command()
    data object TurnLeft : Command()
}

sealed class Orientation {
    data object N : Orientation()
    data object E : Orientation()
    data object W : Orientation()
    data object S : Orientation()
}

fun executeAll(planet: Planet, rover: Rover, commands: List<Command>): Either<ObstacleDetected, Rover> {
    val initial: Either<ObstacleDetected, Rover> = rover.right()
    return commands.fold(initial) { prev, cmd ->
        prev.flatMap { execute(planet, it, cmd) }
    }
}

fun execute(planet: Planet, rover: Rover, command: Command): Either<ObstacleDetected, Rover> =
    when (command) {
        is TurnRight -> turnRight(rover).right()
        is TurnLeft -> turnLeft(rover).right()
        is MoveForward -> moveForward(planet, rover)
        is MoveBackward -> moveBackward(planet, rover)
    }

fun turnRight(rover: Rover): Rover =
    rover.copy(
        orientation = when (rover.orientation) {
            is N -> E
            is E -> S
            is S -> W
            is W -> N
        }
    )

fun turnLeft(rover: Rover): Rover =
    rover.copy(
        orientation = when (rover.orientation) {
            is N -> W
            is W -> S
            is S -> E
            is E -> N
        }
    )

fun moveForward(planet: Planet, rover: Rover): Either<ObstacleDetected, Rover> =
    next(planet, rover, delta(rover.orientation))
        .map { x -> rover.copy(position = x) }

fun moveBackward(planet: Planet, rover: Rover): Either<ObstacleDetected, Rover> =
    next(planet, rover, delta(opposite(rover.orientation)))
        .map { x -> rover.copy(position = x) }

fun opposite(orientation: Orientation): Orientation =
    when (orientation) {
        is N -> S
        is S -> N
        is E -> W
        is W -> E
    }

fun delta(orientation: Orientation): Delta =
    when (orientation) {
        is N -> Delta(0, 1)
        is S -> Delta(0, -1)
        is E -> Delta(1, 0)
        is W -> Delta(-1, 0)
    }

fun next(planet: Planet, rover: Rover, delta: Delta): Either<ObstacleDetected, Position> {
    val candidate = rover.position.copy(
        x = wrap(rover.position.x + delta.x, planet.size.width),
        y = wrap(rover.position.y + delta.y, planet.size.height)
    )
    val hitObstacle = planet.obstacles.any { Position(it.x, it.y) == candidate }
    return if (hitObstacle) rover.left() else candidate.right()
}

fun wrap(value: Int, limit: Int): Int =
    ((value % limit) + limit) % limit