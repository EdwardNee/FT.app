package app.ft.ftapp.android.ui.navigation

import kotlinx.coroutines.channels.Channel

// https://nuancesprog-ru.turbopages.org/nuancesprog.ru/s/p/16843/

/**
 * Navigation interface.
 */
interface AppNavigator {
    /**
     * Navigation flow.
     */
    val navigationChannel: Channel<NavigationIntent>

    /**
     * Navigate back to screen.
     */
    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false
    )


    /**
     * Safe back to screen.
     */
    fun tryNavigateBack(
        route: String? = null,
        inclusive: Boolean = false
    )

    /**
     * Calling navigation to specified [route] screen.
     */
    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    /**
     * Safe navigation to specified [route] screen.
     */
    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
}

//Вы можете дополнить их список, например намерением перейти по ссылке на конкретный ресурс или что-нибудь в этом роде.
/**
 * Navigation intents to show navigation actions.
 */
sealed class NavigationIntent {
    /**
     * Back intent.
     */
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false
    ) : NavigationIntent()

    /**
     * Forward intent.
     */
    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false
    ) : NavigationIntent()
}