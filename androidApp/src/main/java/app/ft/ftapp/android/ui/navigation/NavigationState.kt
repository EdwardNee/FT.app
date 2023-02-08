package app.ft.ftapp.android.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.icerock.moko.parcelize.Parcelable

class NavigationState(val navHostController: NavHostController) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun NavController.navigateTo(route: String) {
    dummyNavigate(route)
}

fun NavController.navigateTo(route: String, argument: Pair<String, Parcelable>) {
    val stateHandle = currentBackStackEntry?.savedStateHandle
    stateHandle?.set(argument.first, argument.second)

    dummyNavigate(route)
}

fun NavController.dummyNavigate(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}