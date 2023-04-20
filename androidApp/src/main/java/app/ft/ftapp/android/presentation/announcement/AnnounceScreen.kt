package app.ft.ftapp.android.presentation.announcement

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.map
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announcement.shimmer.AnnounceCardShimmer
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.shimmer.ShimmerItem
import app.ft.ftapp.android.presentation.viewmodels.factory.ArgsViewModelFactory
import app.ft.ftapp.android.presentation.viewmodels.factory.FactoryArgs
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.presentation.viewmodels.AnnounceListEvent
import app.ft.ftapp.presentation.viewmodels.AnnouncesViewModel
import app.ft.ftapp.client_ID
import kotlinx.coroutines.launch

/**
 * Composable timer with [Handler].
 */
@Composable
fun counterTimer(items: List<Announce>): List<Announce> {
    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = Runnable {
            for (item in items) {
//                item.timeRemained = TimeUtil.getMinutesLeft(item.timeRemained)
            }
        }

        handler.postDelayed(runnable, 1000L)

        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    return items
}

/**
 * Composable method to show all the created announcements.
 */
@Composable
fun AnnounceScreen(onClick: () -> Unit) {

    val isLoad = remember { mutableStateOf(true) }
    val viewModel = setupViewModel<AnnouncesViewModel>()
    val screenViewModel: AnnounceScreenViewModel = setupViewModel<AnnounceScreenViewModel>(
        ArgsViewModelFactory(FactoryArgs(viewModel))
    )
    val announcesList = screenViewModel.pagerAnnounces.collectAsLazyPagingItems()
    viewModel.setList(announcesList.itemSnapshotList.items)

    val isLoading by viewModel.isShowProgress.collectAsState()
    val announces by viewModel.announcesList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(appBackground)
            .padding(horizontal = 8.dp)
    ) {
        HeaderText(text = stringResource(id = R.string.available_announces))

//        viewModel.setList(counterTimer(announcesList))
//        var isLoading by remember {
//            mutableStateOf(true)
//        }
//        LaunchedEffect(key1 = true) {
//            delay(1000)
//            isLoading = !isLoading
//        }

        LazyColumn {
            items(if (announcesList.itemCount == 0) 3 else announcesList.itemCount) { idx ->
                when (announcesList.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        println("TAG_OF_RERE notl")
                        isLoad.value = false
                    }
                    LoadState.Loading -> {
                        println("TAG_OF_RERE l")
                        isLoad.value = true
                    }
                    is LoadState.Error -> TODO()
                    else -> {
                        println("TAG_OF_RERE a ")
                        viewModel.showProgress()
                    }
                }

                Box(modifier = Modifier.padding(bottom = if (isLoad.value) 15.dp else 30.dp)) {
                    ShimmerItem(isLoading = isLoad.value, pattern = { AnnounceCardShimmer() }) {
                        if (announcesList.itemCount > 0) {
                            val current = announcesList[idx]
                            current?.let {
                                AnnounceCard(it, onClickInfo = {
                                    println("TAG_OF_REF comp ${current} $idx")
                                    viewModel.onEvent(AnnounceListEvent.OnDetails(current.id.toString()) { })
                                    onClick()
                                }) {
                                    viewModel.onEvent(AnnounceListEvent.OnBecomeTraveler(it))
                                }
                            }
                        }
                    }
                }
            }
//            items(if (isLoading) 10 else announcesList.itemCount) { idx ->
//                Box(modifier = Modifier.padding(bottom = if (isLoading) 15.dp else 30.dp)) {
//                    ShimmerItem(isLoading = isLoading, pattern = { AnnounceCardShimmer() }) {
//                        val current = announcesList[idx]!!
//                        AnnounceCard(current, onClickInfo = {
//                            println("TAG_OF_REF comp ${current} $idx")
//                            viewModel.onEvent(AnnounceListEvent.OnDetails(current.id.toString()) { })
//                            onClick()
//                        }) {
//                            viewModel.onEvent(AnnounceListEvent.OnBecomeTraveler(it))
//                        }
//                    }
//                }
//            }
        }
    }
}