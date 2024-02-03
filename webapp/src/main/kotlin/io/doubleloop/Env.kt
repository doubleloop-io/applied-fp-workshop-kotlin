package io.doubleloop

import java.lang.System.getenv

private const val PLANET_FILE: String = "planet.txt"
private const val ROVER_FILE: String = "rover.txt"

data class Env(
    val fileSource: FileSource = FileSource(),
) {

    data class FileSource(
        val planet: String = getenv("PLANET_FILE") ?: PLANET_FILE,
        val rover: String = getenv("ROVER_FILE") ?: ROVER_FILE,
    )
}