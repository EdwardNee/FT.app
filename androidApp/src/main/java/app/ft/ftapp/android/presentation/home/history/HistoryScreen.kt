@file:OptIn(ExperimentalMaterialApi::class)

package app.ft.ftapp.android.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import app.ft.ftapp.android.presentation.common.ErrorView
import app.ft.ftapp.android.presentation.common.NoDataView
import app.ft.ftapp.android.presentation.home.travelers.ListTravelers
import app.ft.ftapp.android.presentation.viewmodels.factory.ArgsViewModelFactory
import app.ft.ftapp.android.presentation.viewmodels.factory.FactoryArgs
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import app.ft.ftapp.presentation.viewmodels.HistoryViewModel
import kotlinx.coroutines.launch

/**
 * Composable for List of history [Announce].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen() {

    val chosenAnnounce = remember { mutableStateOf(Announce()) }
    val isChosen = rememberSaveable { mutableStateOf(true) }

    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

    ModalBottomSheetLayout(
        sheetContent = {
            if (modalBottomSheetState.isVisible) {

                if (isChosen.value) {
                    HistoryDetails(isChosen, chosenAnnounce)
                } else {
                    ListOfTravelers(
                        chosenAnnounce,
                        isChosen
                    )
                }

            } else {
                Surface(
                    Modifier
                        .size(0.2.dp)
                        .background(Color.Transparent)
                ) {
                    Text("", fontSize = 0.sp)
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = appBackground,
        // scrimColor = ,  //Color for the fade background when open/close the drawer
    ) {
        HistoryList(modalBottomSheetState, chosenAnnounce)
    }
}

/**
 * Composable shows list of travelers.
 */
@Composable
fun ListOfTravelers(announce: MutableState<Announce>, isChosen: MutableState<Boolean>) {
    Column(Modifier.fillMaxWidth()) {
        IconButton(onClick = { isChosen.value = true }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        ListTravelers(announce.value)
    }
}

/**
 * Composable to show list of history [Announce].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryList(
    modalBottomSheetState: ModalBottomSheetState,
    chosenAnnounce: MutableState<Announce>
) {
    val viewModel = setupViewModel<HistoryViewModel>()
    val viewModelScreen =
        setupViewModel<HistoryScreenViewModel>(ArgsViewModelFactory(FactoryArgs(viewModel)))
    val historyList = viewModelScreen.pagerHistory.collectAsLazyPagingItems()
    viewModel.setList(historyList.itemSnapshotList.items.reversed())

    val scope = rememberCoroutineScope()
    var isLoad by remember { mutableStateOf(true) }
    var isError: Boolean? by remember { mutableStateOf(null) }
    val stateRefresh = rememberPullRefreshState(isLoad, { historyList.refresh() })
    Column(
        modifier = Modifier
//            .pullRefresh(stateRefresh)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(appBackground)
            .padding(horizontal = 8.dp)
    ) {
        Box(
            Modifier
                .pullRefresh(stateRefresh)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                when (historyList.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        isLoad = false
                        isError = null
                    }
                    LoadState.Loading -> {
                        isLoad = true
                        isError = null
                    }
                    is LoadState.Error -> {
                        isLoad = false
                        if (isError == null) {
                            isError = true

                            scope.launch {
//                            snackbarState.showSnackbar("")
                            }
                        }
                    }
                    else -> {
//                        viewModel.showProgress()
                    }
                }

                items(historyList.itemCount) { item ->
                    HistoryAnnounceItem(historyList[item] ?: Announce()) {
                        chosenAnnounce.value = it
                        scope.launch {
                            modalBottomSheetState.show()
                        }
                    }
                }
            }

            if (!isLoad && isError == null && historyList.itemCount <= 0) {
                NoDataView(text = "История поездок пуста.")
            }

            if (isError == true) {
                ErrorView {
                    isLoad = true
                    historyList.refresh()
                }
            }

            PullRefreshIndicator(
                isLoad,
                stateRefresh,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}