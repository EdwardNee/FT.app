package app.ft.ftapp.android.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Composable infinite progress loading view.
 */
@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .clickable(
                onClick = {},
                indication = null,
                interactionSource = MutableInteractionSource()
            )
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.6f)),
    ) {
        CircularProgressIndicator(
            color = Color.Magenta,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.Center)
        )
    }

}