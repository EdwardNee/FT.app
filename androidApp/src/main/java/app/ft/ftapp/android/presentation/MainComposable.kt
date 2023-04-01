package app.ft.ftapp.android.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.ft.ftapp.android.BottomSheetApp
import app.ft.ftapp.android.presentation.announce_details.AnnouncementDetails
import app.ft.ftapp.android.presentation.announcement.AnnounceScreen
import app.ft.ftapp.android.presentation.auth.AuthScreen
import app.ft.ftapp.android.presentation.creation.AnnounceCreationScreen
import app.ft.ftapp.android.presentation.models.BottomNavItems
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.navigation.AppDestination
import app.ft.ftapp.android.ui.navigation.CustomNavigation
import app.ft.ftapp.android.ui.navigation.NavigationIntent
import app.ft.ftapp.android.ui.navigation.composable
import app.ft.ftapp.android.ui.theme.MyApplicationTheme
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.bottomNavColor
import app.ft.ftapp.android.utils.SingletonHelper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Main composable entry.
 */
@Composable
fun MainComposable() {
    val navController = rememberNavController()
    SingletonHelper.appNavigator.navController = navController

    NavigationEffects(
        navigationChannel = SingletonHelper.appNavigator.navigationChannel,
        navHostController = navController
    )

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = appBackground,//MaterialTheme.colors.background
        ) {
            Scaffold(bottomBar = { BottomNavs() }) {
                NavGraph(navController, Modifier.padding(it))
            }
        }
    }
}

/**
 * Bottom navigation composable entry.
 */
@Composable
fun BottomNavs() {
    val items = listOf(
        BottomNavItems(Icons.Filled.Home, ScreenValues.ANNOUNCES_LIST),
        BottomNavItems(Icons.Filled.Add, ScreenValues.CREATION),
        BottomNavItems(Icons.Filled.Search, ScreenValues.ANNOUNCE_DETAIL),
    )

    val backStackEntry by SingletonHelper.appNavigator.navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    BottomNavigation(backgroundColor = Color.White) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.imageVector,
                        item.description,
                        modifier = Modifier.size(30.dp)
                    )
                },
                selectedContentColor = bottomNavColor,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.description,
                interactionSource = NoRippleInteractionSource(),
                onClick = {
                    SingletonHelper.appNavigator.tryNavigateTo(item.description)
                }
            )
        }
    }
}

/**
 * Navigation graph construction.
 */
@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    CustomNavigation(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = AppDestination.AuthScreen
    ) {
        composable(destination = AppDestination.AuthScreen) {
            BackHandler(true) {} //при выходе из профиля отключаю кнопку назад
            AuthScreen()
        }
        composable(destination = AppDestination.ListAnnounces) {
//            AnnounceScreen()
            BottomSheetApp(
                pageContent = { AnnounceScreen(it) },
                sheetContent = { AnnouncementDetails() },
            )
        }
        composable(destination = AppDestination.Announce) {}
        composable(destination = AppDestination.Creation) {
            AnnounceCreationScreen()
        }
        composable(destination = AppDestination.Chatting) {}
    }
}

/**
 * Navigation process in composable.
 */
@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect() { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }

            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) {
                                inclusive = intent.inclusive
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}