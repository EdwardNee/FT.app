package app.ft.ftapp.android.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState
import app.ft.ftapp.android.BottomSheetApp
import app.ft.ftapp.android.R
import app.ft.ftapp.android.presentation.announcedetails.AnnouncementDetails
import app.ft.ftapp.android.presentation.announcement.AnnounceScreen
import app.ft.ftapp.android.presentation.auth.AuthScreen
import app.ft.ftapp.android.presentation.common.Keyboard
import app.ft.ftapp.android.presentation.common.keyboardAsState
import app.ft.ftapp.android.presentation.creation.AnnounceCreationScreen
import app.ft.ftapp.android.presentation.creation.CreationWithMap
import app.ft.ftapp.android.presentation.creation.SuccessView
import app.ft.ftapp.android.presentation.groupchat.GroupChat
import app.ft.ftapp.android.presentation.home.HomeScreen
import app.ft.ftapp.android.presentation.models.BottomNavItems
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.preview.PreviewComposable
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.navigation.*
import app.ft.ftapp.android.ui.theme.MyApplicationTheme
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.bottomNavColor
import app.ft.ftapp.android.utils.SingletonHelper
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Main composable entry.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainComposable(startDestination: AppDestination = AppDestination.PreviewBars) {
    val navController = rememberAnimatedNavController()
    SingletonHelper.appNavigator.mainNavController = navController

    NavigationEffects(
        navigationChannel = SingletonHelper.appNavigator.rootNavigationChannel,
        navHostController = navController,
        graph = ScreenValues.ROOT
    )

    MyApplicationTheme {
        MainNavGraph(navController, startDestination)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScreens(navController: NavHostController = rememberAnimatedNavController()) {
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
        BottomNavItems(description = ScreenValues.CREATION_MAP, imageVector = Icons.Filled.Add),
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
                    modifier = Modifier.testTag(item.description),
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
                    selected = item.description.contains(currentRoute.toString()) ?: false,
                    interactionSource = NoRippleInteractionSource(),
                    onClick = {
                        if (currentRoute != item.description) {
                            if (item.description == ScreenValues.CREATION_MAP) {
                                SingletonHelper.appNavigator.tryNavigateTo(
                                    item.description,
                                    inclusive = false, isSingleTop = false,
                                    saveState = (item.description == ScreenValues.CREATION_MAP)
                                )
                            } else {
                                SingletonHelper.appNavigator.tryNavigateTo(
                                    item.description,
                                    isSingleTop = false,
                                    saveState = (item.description == ScreenValues.CREATION_MAP)
                                )
                            }

                        }
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.loginGraph() {
    navigation(startDestination = ScreenValues.PREVIEW, route = ScreenValues.FIRST_PREVIEW) {
        composable(destination = AppDestination.Preview) {
            PreviewComposable()
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
//fun NavGraphBuilder.appGraph(navController: NavController) {
//    navigation(startDestination = ScreenValues.ANNOUNCES_LIST, route = ScreenValues.SECOND_APP) {
//
//        composable(destination = AppDestination.AuthScreen) {
//            BackHandler(true) {} //при выходе из профиля отключаю кнопку назад
//            AuthScreen()
//        }
//
//        composable(destination = AppDestination.HomeScreen) {
//            HomeScreen()
//        }
//
//
//        composable(destination = AppDestination.ListAnnounces) {
////            AnnounceScreen()
//            BottomSheetApp(
//                pageContent = { listener ->
//                    AnnounceScreen(listener)
//                },
//                sheetContent = { state ->
//                    AnnouncementDetails(state)
//                },
//            )
//        }
//
//        composable(destination = AppDestination.Announce) {}
//
//        composable(destination = AppDestination.Creation) {
//            BottomSheetApp(
//                pageContent = { listener ->
////                    AnnounceCreationScreen(listener)
//                    CreationWithMap()
//                },
//                sheetContent = { SuccessView() }
//            )
//        }
//
//        composable(destination = AppDestination.Chatting) {
//            GroupChat()
//        }
//    }
//}


/**
 * Navigation graph construction.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    startDestination: AppDestination = AppDestination.PreviewBars
) {
    CustomNavigationAnimated(
        navController = navController,
        startDestination = startDestination,
        route = ScreenValues.ROOT
    ) {
        loginGraph()

        composable(destination = AppDestination.AppBars) { //ScreenValues.SECOND_APP
            AppScreens()
        }
//        appGraph(navController)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {

    val map = mapOf(
        ScreenValues.ANNOUNCES_LIST to 1,
        ScreenValues.HOME to 2,
        ScreenValues.CREATION_MAP to 3,
        ScreenValues.CHATTING to 4
    )

    LaunchedEffect(navController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (map[destination.route] == null) {
                SingletonHelper.appNavigator.enterTransition.value = null
                SingletonHelper.appNavigator.exitTransition.value = null
            } else {
                if ((map[navController.previousBackStackEntry?.destination?.route]
                        ?: 0) > (map[destination.route] ?: 0)
                ) {
                    SingletonHelper.appNavigator.enterTransition.value = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        )
                    }
                    SingletonHelper.appNavigator.exitTransition.value = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        )
                    }
                } else {
                    SingletonHelper.appNavigator.enterTransition.value = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        )
                    }
                    SingletonHelper.appNavigator.exitTransition.value = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        )
                    }
                }
            }
        }
    }

    CustomNavigationAnimated(
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

        composable(destination = AppDestination.CreationMap) {
            CreationWithMap()
        }

        composable(destination = AppDestination.Creation) {
            BottomSheetApp(
                pageContent = { listener ->
                    AnnounceCreationScreen()
//                    CreationWithMap()
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
//                        intent.popUpToRoute?.let { popUpToRoute ->
//                            popUpTo(popUpToRoute) {
//                                inclusive = intent.inclusive TODO TODO TODO TODO
//                                saveState = intent.saveState
//                            }
//                        } ?: run {
//                            popUpTo(navHostController.graph.findStartDestination().id) {
//                                inclusive = intent.inclusive
//                                saveState = intent.saveState
//                            }
//                        }
//                        popUpTo(intent.popUpToRoute ?: navHostController.graph.findStartDestination().) {
//
//                            inclusive = intent.inclusive
//                            saveState = intent.saveState
//                        }
                        launchSingleTop = intent.isSingleTop
                        restoreState = true
//                        }
                    }
                }
            }
        }
    }
}