package app.ft.ftapp.domain.models

import io.ktor.http.*

sealed class ServerResult<out T> {
    class SuccessfulResult<T>(val model: T, val header: Headers? = null) : ServerResult<T>()
    class UnsuccessfulResult<T>(val error: String) : ServerResult<T>()
}