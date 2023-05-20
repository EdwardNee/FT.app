package app.ft.ftapp.android.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * [AppNavigator] implementation. Wrapper of Compose navigation.
 */
class AppNavigatorImpl : AppNavigator {
    lateinit var mainNavController: NavHostController
    lateinit var navControllerApp: NavHostController

    val enterTransition =
        MutableStateFlow<(AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)?>(
            null
        )
    val exitTransition =
        MutableStateFlow<(AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)?>(
            null
        )

    override val rootNavigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    override val appNavigationChannel: Channel<NavigationIntent> = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun navigateBack(route: String?, inclusive: Boolean) {
        rootNavigationChannel.send(NavigationIntent.NavigateBack(route, inclusive))
    }

    override fun tryNavigateBack(route: String?, inclusive: Boolean) {
        appNavigationChannel.trySend(NavigationIntent.NavigateBack(route, inclusive))
    }

    override suspend fun navigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        rootNavigationChannel.send(
            NavigationIntent.NavigateTo(
                route,
                popUpToRoute,
                inclusive,
                isSingleTop
            )
        )
    }

    override fun tryNavigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean,
        saveState: Boolean
    ) {
        appNavigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route,
                popUpToRoute,
                inclusive,
                isSingleTop
            )
        )
    }
}