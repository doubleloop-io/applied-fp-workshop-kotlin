package io.doubleloop

import com.ubertob.kondor.json.JAny
import com.ubertob.kondor.json.bool
import com.ubertob.kondor.json.jsonnode.JsonNodeObject
import com.ubertob.kondor.json.num
import com.ubertob.kondor.json.str

data class RunAppHttpRequest(val commands: String)
data class RunAppHttpResponse(
    val positionX: Int,
    val positionY: Int,
    val direction: String,
    val hitObstacle: Boolean
)

object JRunAppHttpRequest : JAny<RunAppHttpRequest>() {
    val commands by str(RunAppHttpRequest::commands)

    override fun JsonNodeObject.deserializeOrThrow(): RunAppHttpRequest? =
        RunAppHttpRequest(commands = +commands)
}

object JRunAppHttpResponse : JAny<RunAppHttpResponse>() {
    val positionX by num(RunAppHttpResponse::positionX)
    val positionY by num(RunAppHttpResponse::positionY)
    val direction by str(RunAppHttpResponse::direction)
    val hitObstacle by bool(RunAppHttpResponse::hitObstacle)

    override fun JsonNodeObject.deserializeOrThrow(): RunAppHttpResponse? =
        RunAppHttpResponse(
            positionX = +positionX,
            positionY = +positionY,
            direction = +direction,
            hitObstacle = +hitObstacle,
        )
}

fun RunAppHttpRequest.toRunAppRequest(): RunAppRequest =
    RunAppRequest(this.commands)

fun Rover.renderComplete() =
    RunAppHttpResponse(
        this.position.x,
        this.position.y,
        this.orientation.toString(),
        false
    )

fun ObstacleDetected.renderObstacle() =
    RunAppHttpResponse(
        this.position.x,
        this.position.y,
        this.orientation.toString(),
        true
    )