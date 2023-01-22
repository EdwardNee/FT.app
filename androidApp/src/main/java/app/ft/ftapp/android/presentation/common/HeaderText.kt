package app.ft.ftapp.android.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 24.dp),
        fontSize = 30.sp
    )
}