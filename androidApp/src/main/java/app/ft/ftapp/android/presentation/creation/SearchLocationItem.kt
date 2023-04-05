package app.ft.ftapp.android.presentation.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.Montserrat

/**
 * Composable item for addresses.
 */
@Composable
fun SearchLocationItem(header: String, address: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(vertical = 4.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = header,
            fontFamily = Montserrat,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = address,
            fontFamily = Montserrat,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}