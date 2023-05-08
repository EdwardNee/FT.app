package app.ft.ftapp.android.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import app.ft.ftapp.EMAIL
import app.ft.ftapp.android.presentation.LoadingView
import app.ft.ftapp.android.presentation.common.ErrorView
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.NoDataView
import app.ft.ftapp.android.presentation.home.history.HistoryScreen
import app.ft.ftapp.android.presentation.home.my_announce.CurrentScreen
import app.ft.ftapp.android.presentation.home.travelers.ListTravelers
import app.ft.ftapp.android.presentation.models.BottomNavItems
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.utils.SingletonHelper
import app.ft.ftapp.presentation.viewmodels.HomeEvent
import app.ft.ftapp.presentation.viewmodels.HomeViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

/**
 * Home screen view.
 */
@Composable
fun HomeScreen() {
    val viewModel = setupViewModel<HomeViewModel>()
    val items = listOf(
        BottomNavItems(ScreenValues.CURRENT), BottomNavItems(ScreenValues.HISTORY)
    )

    val backStackEntry by SingletonHelper.appNavigator.navControllerApp.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var isChosen by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(appBackground)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            HeaderText(text = "Текущие",
                color = if (isChosen) Color.Black else Color.Gray,
                fontSize = (if (isChosen) 40 else 28).sp,
                modifier = Modifier
                    .clickable(
                        interactionSource = NoRippleInteractionSource(), indication = null
                    ) { isChosen = true }
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 200, easing = FastOutLinearInEasing
                        )
                    ))
            Spacer(Modifier.padding(10.dp))
            HeaderText(text = "История",
                color = if (isChosen) Color.Gray else Color.Black,
                fontSize = (if (isChosen) 28 else 40).sp,
                modifier = Modifier
                    .clickable(
                        interactionSource = NoRippleInteractionSource(), indication = null
                    ) { isChosen = false }
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 200, easing = FastOutLinearInEasing
                        )
                    ))
        }
        if (isChosen) {
            TabComposable(viewModel)
        } else {
            HistoryScreen()
        }

    }
}

/**
 * TabContent with tabs and tabviews.
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TabComposable(viewModel: HomeViewModel) {
    val assignedAnnounce by viewModel.assignedAnnounce.collectAsState()
    val scope = rememberCoroutineScope()

    var isLoad by remember { mutableStateOf(true) }
    val isLoading by viewModel.isShowProgress.collectAsState()
    val stateRefresh =
        rememberPullRefreshState(
            isLoading,
            { viewModel.onEvent(HomeEvent.GetAnnounceByEmail(EMAIL)) })
    val uiState = remember { mutableStateOf<ModelsState>(ModelsState.Loading) }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.uiState.collect {
                uiState.value = it
            }
        }
    }

    val items = listOf(
        BottomNavItems(
            ScreenValues.MY_ANNOUNCES,
            tabName = "Мои поездки"
        ) { CurrentScreen() },
        BottomNavItems(
            ScreenValues.GROUP_CHAT,
            tabName = "Попутчики"
        ) { ListTravelers(assignedAnnounce) }
    )
    val pagerState = rememberPagerState(pageCount = items.size)

    Box(Modifier.pullRefresh(stateRefresh)) {
        Scaffold(topBar = { }) {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {

                when (uiState.value) {
                    is ModelsState.Error -> {
                        isLoad = false
                        ErrorView {
                            viewModel.onEvent(HomeEvent.GetAnnounceByEmail(EMAIL))
                        }
                    }
                    ModelsState.Loading -> {
                        isLoad = true
                        LoadingView()
                    }
                    is ModelsState.Success<*> -> {
                        isLoad = false
//                    NoDataView(viewModel)
                        Tabs(tabs = items, pagerState = pagerState)
                        TabsContent(tabs = items, pagerState = pagerState)
                    }
                    ModelsState.NoData -> {
                        NoDataView()
                    }
                }
            }

        }
        PullRefreshIndicator(isLoading, stateRefresh, modifier = Modifier.align(TopCenter))
    }
}

/**
 * List of tabs to show.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<BottomNavItems>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .fillMaxWidth()
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .background(Color.Black)
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                icon = { },
                text = { Text(tab.tabName, color = Color.Black, fontFamily = Montserrat) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<BottomNavItems>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground),
        verticalAlignment = Alignment.Top
    ) { page ->
        tabs[page].content?.invoke()
    }
}