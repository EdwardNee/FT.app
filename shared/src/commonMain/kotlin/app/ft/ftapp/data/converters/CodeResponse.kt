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
    internal const val SERVER_UNREACHABLE = "Server Unreachable"
    internal const val MODEL_NOT_AVAILABLE = "Model not available"
    internal const val INTERNAL_SERVER_ERROR = "Internal server error"
}