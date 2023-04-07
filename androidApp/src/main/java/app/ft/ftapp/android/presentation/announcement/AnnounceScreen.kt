package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announcement.shimmer.AnnounceCardShimmer
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.shimmer.ShimmerItem
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.presentation.viewmodels.AnnounceListEvent
import app.ft.ftapp.presentation.viewmodels.AnnouncesViewModel

/**
 * Composable method to show all the created announcements.
 */
@Composable
fun AnnounceScreen(onClick: () -> Unit) {
    val viewModel = setupViewModel<AnnouncesViewModel>()

    val isLoading by viewModel.isShowProgress.collectAsState()
    val announcesList by viewModel.announcesList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
    ) {
        HeaderText(text = stringResource(id = R.string.available_announces))

//        var isLoading by remember {
//            mutableStateOf(true)
//        }
//        LaunchedEffect(key1 = true) {
//            delay(1000)
//            isLoading = !isLoading
//        }
        LazyColumn {
            items(if (isLoading) 10 else announcesList.size) { idx ->
                Box(modifier = Modifier.padding(bottom = if (isLoading) 15.dp else 30.dp)) {
                    ShimmerItem(isLoading = isLoading, pattern = { AnnounceCardShimmer() }) {
                        AnnounceCard(announcesList[idx]) {
                            viewModel.onEvent(AnnounceListEvent.OnDetails(announcesList[idx].id.toString()) { })
                            onClick()
                        }
                    }
//                    AnnounceCard()
                }
            }
        }
    }
}