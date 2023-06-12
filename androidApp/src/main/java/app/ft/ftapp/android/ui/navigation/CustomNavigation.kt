package app.ft.ftapp.android.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import app.ft.ftapp.android.utils.SingletonHelper
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

/**
 * Compose navigation entry.
 */
@Composable
fun CustomNavigation(
    navController: NavHostController,
    startDestination: AppDestination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
        builder = builder
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomNavigationAnimated(
    navController: NavHostController,
    startDestination: AppDestination,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {

    val enter = SingletonHelper.appNavigator.enterTransition.collectAsState()
    val exit = SingletonHelper.appNavigator.exitTransition.collectAsState()
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
        enterTransition = enter.value ?: { scaleIn(animationSpec = tween(300)) },
        exitTransition = exit.value ?: { scaleOut(animationSpec = tween(durationMillis = 300)) },
        builder = builder
    )
}

/**
 * Extended NavGraphBuilder composable to create custom destinations.
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composable(
    destination: AppDestination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.fullRoute,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}