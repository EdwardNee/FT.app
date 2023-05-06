package app.ft.ftapp.android.presentation

//import app.ft.ftapp.android.presentation.home.HomeScreen
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import app.ft.ftapp.android.BottomSheetApp
import app.ft.ftapp.android.R
import app.ft.ftapp.android.presentation.announce_details.AnnouncementDetails
import app.ft.ftapp.android.presentation.announcement.AnnounceScreen
import app.ft.ftapp.android.presentation.auth.AuthScreen
import app.ft.ftapp.android.presentation.common.Keyboard
import app.ft.ftapp.android.presentation.common.keyboardAsState
import app.ft.ftapp.android.presentation.creation.CreationWithMap
import app.ft.ftapp.android.presentation.creation.SuccessView
import app.ft.ftapp.android.presentation.groupchat.GroupChat
import app.ft.ftapp.android.presentation.home.HomeScreen
import app.ft.ftapp.android.presentation.models.BottomNavItems
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.preview.PreviewComposable
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
    SingletonHelper.appNavigator.mainNavController = navController

    NavigationEffects(
        navigationChannel = SingletonHelper.appNavigator.rootNavigationChannel,
        navHostController = navController,
        graph = ScreenValues.ROOT
    )

    MyApplicationTheme {
        MainNavGraph(navController)
    }
}

@Composable
fun AppScreens(navController: NavHostController = rememberNavController()) {
    SingletonHelper.appNavigator.navControllerApp = navController

    NavigationEffects(
        navigationChannel = SingletonHelper.appNavigator.appNavigationChannel,
        navHostController = navController,
        graph = ScreenValues.SECOND_APP
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = appBackground,//MaterialTheme.colors.background
    ) {
        Scaffold(bottomBar = { BottomNavs() }) {
            AppNavGraph(navController, Modifier.padding(it))
//            MainNavGraph(navController, Modifier.padding(it))
        }
    }
}

/**
 * Bottom navigation composable entry.
 */
@Composable
fun BottomNavs() {
    val items = listOf(
        BottomNavItems(
            description = ScreenValues.ANNOUNCES_LIST,
            imageVector = Icons.Filled.Search
        ),
        BottomNavItems(description = ScreenValues.HOME, imageVector = Icons.Filled.Home),
        BottomNavItems(description = ScreenValues.CREATION, imageVector = Icons.Filled.Add),
        BottomNavItems(
            description = ScreenValues.CHATTING,
            painter = painterResource(id = R.drawable.message_bubble)
        ),
//        BottomNavItems(description = ScreenValues.ANNOUNCE_DETAIL, imageVector = Icons.Filled.Search),
    )

    val isKeyboardOpen by keyboardAsState()

    val backStackEntry by SingletonHelper.appNavigator.navControllerApp.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    if (isKeyboardOpen == Keyboard.Closed) {
        BottomNavigation(backgroundColor = Color.White) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        if (item.imageVector == null) {
                            Icon(
                                item.painter!!,
                                item.description,
                                modifier = Modifier.size(25.dp)
                            )
                        } else {
                            Icon(
                                item.imageVector,
                                item.description,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                    },
                    selectedContentColor = bottomNavColor,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.description,
                    interactionSource = NoRippleInteractionSource(),
                    onClick = {
                        if (currentRoute != item.description) {
                            SingletonHelper.appNavigator.tryNavigateTo(
                                item.description,
                                isSingleTop = true,
                                saveState = (item.description == ScreenValues.CREATION)
                            )
                        }
                    }
                )
            }
        }
    }

}

fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = ScreenValues.PREVIEW, route = ScreenValues.FIRST_PREVIEW) {
        composable(route = ScreenValues.PREVIEW) {
            PreviewComposable()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.appGraph(navController: NavController) {
    navigation(startDestination = ScreenValues.ANNOUNCES_LIST, route = ScreenValues.SECOND_APP) {
        composable(destination = AppDestination.AuthScreen) {
            BackHandler(true) {} //при выходе из профиля отключаю кнопку назад
            AuthScreen()
        }

        composable(destination = AppDestination.HomeScreen) {
            HomeScreen()
        }


        composable(destination = AppDestination.ListAnnounces) {
//            AnnounceScreen()
            BottomSheetApp(
                pageContent = { listener ->
                    AnnounceScreen(listener)
                },
                sheetContent = { state ->
                    AnnouncementDetails(state)
                },
            )
        }

        composable(destination = AppDestination.Announce) {}

        composable(destination = AppDestination.Creation) {
            BottomSheetApp(
                pageContent = { listener ->
//                    AnnounceCreationScreen(listener)
                    CreationWithMap()
                },
                sheetContent = { SuccessView() }
            )
        }

        composable(destination = AppDestination.Chatting) {
            GroupChat()
        }
    }
}


/**
 * Navigation graph construction.
 */
@Composable
fun MainNavGraph(navController: NavHostController) {
    CustomNavigation(
        navController = navController,
        startDestination = AppDestination.PreviewBars,
        route = ScreenValues.ROOT
    ) {
        loginGraph(navController)

        composable(route = ScreenValues.SECOND_APP) {
            AppScreens()
        }
//        appGraph(navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {

    CustomNavigation(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = AppDestination.ListAnnounces,
        route = ScreenValues.SECOND_APP
    ) {
        composable(destination = AppDestination.AuthScreen) {
            BackHandler(true) {} //при выходе из профиля отключаю кнопку назад
            AuthScreen()
        }

        composable(destination = AppDestination.HomeScreen) {
            HomeScreen()
        }


        composable(destination = AppDestination.ListAnnounces) {
//            AnnounceScreen()
            BottomSheetApp(
                pageContent = { listener ->
                    AnnounceScreen(listener)
                },
                sheetContent = { state ->
                    AnnouncementDetails(state)
                },
            )
        }

        composable(destination = AppDestination.Announce) {}

        composable(destination = AppDestination.Creation) {
            BottomSheetApp(
                pageContent = { listener ->
//                    AnnounceCreationScreen(listener)
                    CreationWithMap()
                },
                sheetContent = { SuccessView() }
            )
        }

        composable(destination = AppDestination.Chatting) {
            GroupChat()
        }
//        appGraph(navController)
    }
}

/**
 * Navigation process in composable.
 */
@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController,
    graph: String
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
//                        launchSingleTop = intent.isSingleTop
//                        intent.popUpToRoute?.let { popUpToRoute ->
                        popUpTo(navHostController.graph.findStartDestination().id) {

                            inclusive = intent.inclusive
                            saveState = intent.saveState
                        }
                        launchSingleTop = intent.isSingleTop
                        restoreState = true
//                        }
                    }
                }
            }
        }
    }
}