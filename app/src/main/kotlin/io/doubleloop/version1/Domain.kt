package io.doubleloop.version1

/*
    ## V1 - Focus on domain logic

    Develop the functions that executes commands:
    - Implement all commands logic (left, right, forward and backward).
    - Commands are sent in batch and executed sequentially, one by one.
    - The planet grid has a wrapping effect from one edge to another (pacman).
    - For now, ignore obstacle detection logic
 */


sealed class Orientation {
    data object N : Orientation()
    data object E : Orientation()
    data object W : Orientation()
    data object S : Orientation()

    // TODO 1: Change orientation
    fun turnRight(): Orientation =
        TODO()

    // TODO 2: Change orientation
    fun turnLeft(): Orientation =
        TODO()
}

data class Position(val x: Int, val y: Int)

data class Rover(val position: Position, val orientation: Orientation) {

    // TODO 3: Change rover orientation
    fun turnRight(): Rover =
        TODO()

    // TODO 4: Change rover orientation
    fun turnLeft(): Rover =
        TODO()

    // TODO 5: Change rover position
    fun moveForward(planet: Planet): Rover =
        TODO()

    // TODO 6: Change rover position
    fun moveBackward(planet: Planet): Rover =
        TODO()
}


data class Size(val width: Int, val height: Int)
data class Obstacle(val x: Int, val y: Int)
data class Planet(val size: Size, val obstacles: List<Obstacle>) {
    fun wrap(candidate: Position): Position =
        candidate.copy(
            x = wrap(candidate.x, size.width),
            y = wrap(candidate.y, size.height)
        )

    // NOTE: utility function for the pacman effect
    private fun wrap(value: Int, limit: Int): Int =
        ((value % limit) + limit) % limit
}

sealed class Command {
    data object MoveForward : Command()
    data object MoveBackward : Command()
    data object TurnRight : Command()
    data object TurnLeft : Command()

    // TODO 7: Dispatch each command to the specific function
    fun execute(planet: Planet, rover: Rover): Rover =
        TODO()
}

// TODO 8: Execute all commands and return final rover state
fun executeAll(planet: Planet, rover: Rover, commands: List<Command>): Rover =
    TODO()