package app.ft.ftapp.android.presentation.home.travelers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.ft.ftapp.android.ui.theme.appBackground

@Composable
fun ListTravelers() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(appBackground)
            .padding(horizontal = 8.dp)
    ) {
        LazyColumn(Modifier.fillMaxHeight().padding(top = 8.dp)) {
            items(5) {
                TravelerItem()
            }
        }
    }
}