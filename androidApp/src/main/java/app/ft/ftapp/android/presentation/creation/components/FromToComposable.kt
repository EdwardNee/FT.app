package app.ft.ftapp.android.presentation.creation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.blueCircle
import app.ft.ftapp.android.ui.theme.editTextBackground
import app.ft.ftapp.android.ui.theme.placeholderColor
import app.ft.ftapp.android.ui.theme.redCircle

@Composable
fun FromToComposable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(color = editTextBackground)
            .padding(start = 26.dp)
            .padding(vertical = 5.dp)
    ) {
        Canvas(modifier = Modifier) {
            drawLine(
                strokeWidth = 1f,
                color = Color.Black,
                start = Offset(x = 15f, y = 41f), //3 * 18 + 14
                end = Offset(x = 15f, y = 220f)
            )
        }


        DestinationComponent("A", "Откуда едем?", redCircle)

        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 8.dp), color = Color.Black, thickness = 0.4.dp
        )

        DestinationComponent("B", "Куда едем?", blueCircle)


    }
}

@Composable
fun DestinationComponent(liter: String, helpText: String, color: Color) {
    var state by remember { mutableStateOf("") }
    Box(contentAlignment = Alignment.CenterStart) {
        Text(text = liter, fontSize = 16.sp, modifier = Modifier.drawBehind {
            drawCircle(color = Color.Black, radius = 32f)
            drawCircle(color = color, radius = 30f)
        })

        BasicTextField(
            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
                .padding(vertical = 16.dp),
            value = state, onValueChange = { state = it }
        ) {
            if (state.trim().isEmpty()) {
                Text(text = helpText, color = placeholderColor, fontSize = 16.sp)
            }
            it()
        }
    }
}