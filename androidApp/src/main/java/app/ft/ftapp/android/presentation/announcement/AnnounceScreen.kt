package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun AnnounceScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Доступные объявления",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp),
            fontSize = 24.sp
        )

        LazyColumn {
            items(5) {
                Box(modifier = Modifier.padding(bottom = 30.dp)) {
                    AnnounceCard()
                }
            }
        }
    }
}