package io.doubleloop.application.solutions.version5

interface MissionSource {
    suspend fun readPlanet(): Planet
    suspend fun readRover(): Rover
}

interface CommandsChannel {
    suspend fun receive(): List<Command>
}

interface MissionReport {
    suspend fun sequenceCompleted(rover: Rover)
    suspend fun obstacleDetected(rover: ObstacleDetected)
    suspend fun error(error: Throwable)
}