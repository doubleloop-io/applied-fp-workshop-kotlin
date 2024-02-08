package io.doubleloop

import com.ubertob.kondor.json.JConverter
import org.http4k.core.ContentType
import org.http4k.core.Request
import org.http4k.core.Response

private const val contentTypeHeaderName = "Content-Type"

fun <T : Any> Request.parseJsonBody(converter: JConverter<T>): T =
    converter.fromJson(bodyString()).orThrow()

fun <T : Any> Response.bodyAsJson(converter: JConverter<T>, value: T) =
    body(converter.toJson(value))
        .header(contentTypeHeaderName, ContentType.APPLICATION_JSON.toHeaderValue())
