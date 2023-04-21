package app.ft.ftapp.data.converters

import io.ktor.client.statement.*

/**
 * Converts [HttpResponse] to errorString.
 */
object ResponseToString {
    fun getErrorString(response: HttpResponse?): String {
        var errorString = CodeResponse.INTERNAL_SERVER_ERROR
        response?.let {
            errorString = when (it.status.value) {
                CodeResponse.ERROR_500 -> CodeResponse.INTERNAL_SERVER_ERROR
                CodeResponse.ERROR_400 -> CodeResponse.INTERNAL_SERVER_ERROR
                CodeResponse.ERROR_405 -> CodeResponse.INTERNAL_SERVER_ERROR
                CodeResponse.ERROR_404 -> CodeResponse.NOT_FOUND
                CodeResponse.ERROR_403 -> CodeResponse.SERVER_UNREACHABLE
                else -> {
                    it.status.description
                }
            }
        }
        return errorString
    }
}