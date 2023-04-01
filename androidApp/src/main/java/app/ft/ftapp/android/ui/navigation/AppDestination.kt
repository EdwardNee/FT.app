package app.ft.ftapp.android.ui.navigation

import app.ft.ftapp.android.ui.ScreenValues

/**
 * Sealed class to distinguish screens instances.
 */
sealed class AppDestination(
    protected val route: String,
    vararg params: String
) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    /**
     * Destination invoker with no arguments to screen.
     */
    sealed class NoArgumentDestination(route: String) : AppDestination(route) {
        operator fun invoke(): String = route
    }

    /**
     * AuthScreen screen destination.
     */
    object AuthScreen : NoArgumentDestination(ScreenValues.AUTH)

    /**
     * Announce screen destination.
     */
    object Announce : NoArgumentDestination(ScreenValues.ANNOUNCE_DETAIL)

    /**
     * ListAnnounces screen destination.
     */
    object ListAnnounces : NoArgumentDestination(ScreenValues.ANNOUNCES_LIST)

    /**
     * Creation screen destination.
     */
    object Creation : NoArgumentDestination(ScreenValues.CREATION)

    /**
     * Chatting screen destination.
     */
    object Chatting : NoArgumentDestination(ScreenValues.CHATTING)
}

/**
 * Params builder to pass to the destination screen.
 */
internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
