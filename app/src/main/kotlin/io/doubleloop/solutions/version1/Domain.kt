package io.doubleloop.solutions.version1

import io.doubleloop.solutions.version1.Command.*
import io.doubleloop.solutions.version1.Orientation.*

data class Planet(val size: Size)
data class Size(val width: Int, val height: Int)
data class Position(val x: Int, val y: Int)
data class Rover(val position: Position, val orientation: Orientation)
data class Delta(val x: Int, val y: Int)

sealed class Orientation {
    data object N : Orientation()
    data object E : Orientation()
    data object W : Orientation()
    data object S : Orientation()
}

sealed class Command {
    data object MoveForward : Command()
    data object MoveBackward : Command()
    data object TurnRight : Command()
    data object TurnLeft : Command()
}

fun executeAll(planet: Planet, rover: Rover, commands: List<Command>): Rover =
    commands.fold(rover) { prev, cmd -> execute(planet, prev, cmd) }

fun execute(planet: Planet, rover: Rover, command: Command): Rover =
    when (command) {
        is TurnRight -> turnRight(rover)
        is TurnLeft -> turnLeft(rover)
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

fun moveForward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(rover.orientation)))

fun moveBackward(planet: Planet, rover: Rover): Rover =
    rover.copy(position = next(planet, rover, delta(opposite(rover.orientation))))

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

fun next(planet: Planet, rover: Rover, delta: Delta): Position =
    rover.position.copy(
        x = wrap(rover.position.x + delta.x, planet.size.width),
        y = wrap(rover.position.y + delta.y, planet.size.height)
    )

fun wrap(value: Int, limit: Int): Int =
    ((value % limit) + limit) % limit