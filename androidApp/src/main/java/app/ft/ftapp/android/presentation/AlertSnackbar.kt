package app.ft.ftapp.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.ft.ftapp.android.R
import app.ft.ftapp.android.ui.theme.Montserrat

/**
 * Alert error snackbar.
 */
@Composable
fun AlertSnackbar(text: String = "Проверьте подключение к Интернету") {
    Snackbar(
        elevation = 0.dp,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .fillMaxWidth()
    ) {
        Box(
            Modifier

        ) {
            Row(
                Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.error_info),
                    contentDescription = "",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text, fontFamily = Montserrat)
            }

        }

    }
}