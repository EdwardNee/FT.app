package app.ft.ftapp.android.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.Montserrat

/**
 * Error while loading view.
 */
@Composable
fun ErrorView(onClick: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ошибка при загрузке данных", fontFamily = Montserrat, fontSize = 18.sp)
        Button(
            border = null,
            modifier = Modifier.size(72.dp),
            elevation = null,
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Filled.Refresh,
                contentDescription = "retry",
                colorFilter = ColorFilter.tint(Color.Green)
            )
        }
    }
}