package app.ft.ftapp.data.converters

/**
 * Constants to map response code with error message.
 */
object CodeResponse {
    internal const val ERROR_500 = 500
    internal const val ERROR_400 = 400
    internal const val ERROR_404 = 404
    internal const val ERROR_405 = 405
    internal const val ERROR_403 = 403
    internal const val SUCCESS_200 = 200
    internal const val SERVER_UNREACHABLE = "Server Unreachable"
    internal const val NOT_FOUND = "Not Found"
    internal const val INTERNAL_SERVER_ERROR = "Internal server error"
}