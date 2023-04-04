package app.ft.ftapp.data.converters

import app.ft.ftapp.domain.models.ServerResult
import io.ktor.client.call.*
import io.ktor.client.statement.*

internal suspend inline fun <reified T : Any> HttpResponse.await(): ServerResult<T> {
    return if (status.description == "OK") {
        val body = body<T>()
        val headers = headers
        ServerResult.SuccessfulResult(body, headers)
    } else {
        ServerResult.UnsuccessfulResult(ResponseToString.getErrorString(this))
    }
}