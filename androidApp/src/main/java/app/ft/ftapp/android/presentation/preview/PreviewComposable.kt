package app.ft.ftapp.android.presentation.preview

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.utils.SingletonHelper
import kotlinx.coroutines.launch

@Composable
@Preview
fun PreviewComposable() {
    val scope = rememberCoroutineScope()
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (false) {
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            "FT.app",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp,
            color = Color.White
        )
        Text("Поиск попутчиков", fontFamily = Montserrat, fontSize = 12.sp, color = Color.LightGray)
        LaunchedEffect(Unit) {
            scope.launch {
                SingletonHelper.appNavigator.navigateTo(ScreenValues.SECOND_APP)
            }
        }
        if (false) {
            Spacer(Modifier.padding(130.dp))
            Spacer(modifier = Modifier.weight(1f))
            CircularProgressIndicator(
                color = Color.Magenta,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}