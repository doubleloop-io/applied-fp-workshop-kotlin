package io.doubleloop

interface MissionSource {
    suspend fun readPlanet(): Planet
    suspend fun readRover(): Rover
}