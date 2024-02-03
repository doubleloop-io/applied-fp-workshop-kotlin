package io.doubleloop

class Dependencies(
    val missionSource: MissionSource,
    val runAppHandler: RunAppHandler
) {
    companion object {
        fun create(env: Env): Dependencies {
            val fileMissionSource = FileMissionSource(env.fileSource.planet, env.fileSource.rover)
            return Dependencies(fileMissionSource, RunAppHandler(fileMissionSource))
        }
    }
}