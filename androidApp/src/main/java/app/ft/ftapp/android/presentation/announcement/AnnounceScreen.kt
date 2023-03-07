package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announcement.shimmer.AnnounceCardShimmer
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.shimmer.ShimmerItem
import kotlinx.coroutines.delay

/**
 * Composable method to show all the created announcements.
 */
@Composable
@Preview
fun AnnounceScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
    ) {
        HeaderText(text = stringResource(id = R.string.create_announce))

        var isLoading by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(key1 = true) {
            delay(1000)
            isLoading = !isLoading
        }
        LazyColumn {
            items(5) {
                Box(modifier = Modifier.padding(bottom = if (isLoading) 15.dp else 30.dp)) {
                    ShimmerItem(isLoading = isLoading, pattern = { AnnounceCardShimmer() }) {
                        AnnounceCard()
                    }
//                    AnnounceCard()
                }
            }
        }
    }
}