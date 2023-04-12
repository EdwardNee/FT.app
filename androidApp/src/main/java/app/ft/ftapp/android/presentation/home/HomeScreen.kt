package app.ft.ftapp.android.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.home.my_announce.CurrentScreen
import app.ft.ftapp.android.presentation.home.travelers.ListTravelers
import app.ft.ftapp.android.presentation.models.BottomNavItems
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.utils.SingletonHelper
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

/**
 * Home screen view.
 */
@Composable
fun HomeScreen() {
    val items = listOf(
        BottomNavItems(ScreenValues.CURRENT),
        BottomNavItems(ScreenValues.HISTORY)
    )

    val backStackEntry by SingletonHelper.appNavigator.navController.currentBackStackEntryAsState()
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
            HeaderText(
                text = "Текущие",
                color = if (isChosen) Color.Black else Color.Gray,
                fontSize = (if (isChosen) 40 else 28).sp,
                modifier = Modifier
                    .clickable(
                        interactionSource = NoRippleInteractionSource(),
                        indication = null
                    ) { isChosen = true }
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutLinearInEasing
                        )
                    )
            )
            Spacer(Modifier.padding(10.dp))
            HeaderText(
                text = "История",
                color = if (isChosen) Color.Gray else Color.Black,
                fontSize = (if (isChosen) 28 else 40).sp,
                modifier = Modifier
                    .clickable(
                        interactionSource = NoRippleInteractionSource(),
                        indication = null
                    ) { isChosen = false }
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutLinearInEasing
                        )
                    )
            )
        }
        TabComposable()
    }
}

/**
 * TabContent with tabs and tabviews.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabComposable() {
    val items = listOf(
        BottomNavItems(
            ScreenValues.MY_ANNOUNCES,
            tabName = "Мои поездки",
            content = { CurrentScreen() }),
        BottomNavItems(
            ScreenValues.GROUP_CHAT,
            tabName = "Попутчики",
            content = { ListTravelers() })
    )
    val pagerState = rememberPagerState(pageCount = items.size)

    Scaffold(
        topBar = { },
    ) {
        it
        Column {
            Tabs(tabs = items, pagerState = pagerState)
            TabsContent(tabs = items, pagerState = pagerState)
        }
    }
}

/**
 * List of tabs to show.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<BottomNavItems>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
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
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        tabs[page].content?.invoke()
    }
}