package io.doubleloop.version3

/*
    ## V3 - More domain logic (handle obstacles w/ effects)

    Extend the domain to handle the obstacle detection:
    - Implement obstacle detection before move to a new position.
    - If the rover encounters an obstacle, rest in the same position and aborts the sequence.
    - Render the final result as string:
      - sequence completed: "positionX:positionY:orientation"
      - obstacle detected: "O:positionX:positionY:orientation"
 */

import arrow.core.Either
import arrow.core.right
import io.doubleloop.version3.Orientation.*

// TODO 7: check version3/Parsing.kt for more TODOs

// TODO 6: fix the implementation in order to propagate the Either<ObstacleDetected, Rover>
// HINT: lift initial value
// HINT: combine previous execute result with the current one
// HINT: combination phase effect (Monad)
fun executeAll(planet: Planet, rover: Rover, commands: List<Command>): Either<ObstacleDetected, Rover> {
    val initial: Either<ObstacleDetected, Rover> = rover.right()
    return commands.fold(initial) { prev, cmd ->
        // cmd.execute(planet, prev)
        TODO()
    }
}

sealed class Command {
    data object MoveForward : Command()
    data object MoveBackward : Command()
    data object TurnRight : Command()
    data object TurnLeft : Command()

    // TODO 5: fix the implementation in order to propagate Either<ObstacleDetected, Rover>
    // HINT: lift pure values to align return types
    fun execute(planet: Planet, rover: Rover): Rover =
        when (this) {
            is TurnRight -> rover.turnRight()
            is TurnLeft -> rover.turnLeft()
            is MoveForward -> rover.moveForward(planet)
            is MoveBackward -> rover.moveBackward(planet)
        }
}

data class Rover(val position: Position, val orientation: Orientation) {

    fun turnRight(): Rover =
        orientation.turnRight()
            .let { copy(orientation = it) }

    fun turnLeft(): Rover =
        orientation.turnLeft()
            .let { copy(orientation = it) }

    // TODO 4: fix the implementation in order to propagate Either<ObstacleDetected, Rover>
    // HINT: combination phase normal (Functor)
    fun moveForward(planet: Planet): Rover =
        delta(orientation)
            .let { next(planet, it) }
            .let { copy(position = it) }

    // TODO 3: fix the implementation in order to propagate Either<ObstacleDetected, Rover>
    // HINT: combination phase normal (Functor)
    fun moveBackward(planet: Planet): Rover =
        delta(orientation.opposite())
            .let { next(planet, it) }
            .let { copy(position = it) }

    // TODO 2: Remove this old next function
    private fun next(planet: Planet, delta: Delta): Position {
        return planet.wrap(position.shift(delta))
    }

    // TODO 1: Uncomment the new next function and get familiar with the code
    //  private fun next(planet: Planet, delta: Delta): Either<ObstacleDetected, Position> {
    //      val candidate = planet.wrap(position.shift(delta))
    //      val hitObstacle = planet.obstacles.any { Position(it.x, it.y) == candidate }
    //      return if (hitObstacle) obstacleDetected().left() else candidate.right()
    //  }

    private fun obstacleDetected(): ObstacleDetected = this

    private fun delta(orientation: Orientation): Delta =
        when (orientation) {
            is N -> Delta(0, 1)
            is S -> Delta(0, -1)
            is E -> Delta(1, 0)
            is W -> Delta(-1, 0)
        }
}
typealias ObstacleDetected = Rover

data class Delta(val x: Int, val y: Int)

sealed class Orientation {
    data object N : Orientation()
    data object E : Orientation()
    data object W : Orientation()
    data object S : Orientation()

    fun turnRight(): Orientation =
        when (this) {
            is N -> E
            is E -> S
            is S -> W
            is W -> N
        }

    fun turnLeft(): Orientation =
        when (this) {
            is N -> W
            is W -> S
            is S -> E
            is E -> N
        }

    fun opposite(): Orientation =
        when (this) {
            is N -> S
            is S -> N
            is E -> W
            is W -> E
        }
}

data class Obstacle(val x: Int, val y: Int)
data class Planet(val size: Size, val obstacles: List<Obstacle>) {
    fun wrap(candidate: Position): Position =
        candidate.copy(
            x = wrap(candidate.x, size.width),
            y = wrap(candidate.y, size.height)
        )

    private fun wrap(value: Int, limit: Int): Int =
        ((value % limit) + limit) % limit
}

data class Size(val width: Int, val height: Int)
data class Position(val x: Int, val y: Int) {

    fun shift(delta: Delta): Position {
        return copy(
            x = x + delta.x,
            y = y + delta.y
        )
    }
}