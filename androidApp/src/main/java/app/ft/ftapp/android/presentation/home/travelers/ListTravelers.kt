package app.ft.ftapp.android.presentation.home.travelers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.presentation.viewmodels.HomeViewModel

@Composable
fun ListTravelers(assignedAnnounce: Announce?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .background(appBackground)
            .padding(horizontal = 8.dp)
    ) {
        LazyColumn(
            Modifier
                .fillMaxHeight()
                .padding(top = 8.dp)
        ) {
            items(assignedAnnounce?.participants ?: emptyList()) {
                TravelerItem(it)
            }
        }
    }
}