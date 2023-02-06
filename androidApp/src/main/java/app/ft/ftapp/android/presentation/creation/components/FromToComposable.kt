package app.ft.ftapp.android.presentation.creation.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.ui.theme.blueCircle
import app.ft.ftapp.android.ui.theme.editTextBackground
import app.ft.ftapp.android.ui.theme.redCircle

@Composable
fun FromToComposable() {
    val animVal = remember { androidx.compose.animation.core.Animatable(1f) }
    val stateA = remember { mutableStateOf("") }
    val stateB = remember { mutableStateOf("") }

    LaunchedEffect(stateA.value.isNotEmpty() && stateB.value.isNotEmpty()) {
        animVal.animateTo(
            targetValue = if (stateA.value.isNotEmpty() && stateB.value.isNotEmpty()) 5.36f else 1f,
            animationSpec = tween(durationMillis = 400, easing = LinearEasing)
        )
    }

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
                end = Offset(x = 15f, y = 42f * animVal.value)
            )
        }

        DestinationComponent("A", "Откуда едем?", redCircle, stateA)

        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 8.dp), color = Color.Black, thickness = 0.4.dp
        )

        DestinationComponent("B", "Куда едем?", blueCircle, stateB)
    }
}

@Composable
fun DestinationComponent(
    liter: String,
    helpText: String,
    color: Color,
    stateVal: MutableState<String>
) {
//    var state by remember { mutableStateOf("") }
    val setColor = if (stateVal.value.isEmpty()) Color.White else color

    val colorState = remember { Animatable(Color.White) }

    // animate to green/red based on "button click"
    LaunchedEffect(stateVal.value.isNotEmpty()) {
        colorState.animateTo(
            if (stateVal.value.isNotEmpty()) color else Color.White,
            animationSpec = tween(400)
        )
    }
    Box(contentAlignment = Alignment.CenterStart) {
        Text(text = liter, fontSize = 16.sp, modifier = Modifier.drawBehind {
            drawCircle(color = Color.Black, radius = 32f)
            drawCircle(color = colorState.value, radius = 30f)
        })

        BasicTextField(
            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
                .padding(vertical = 16.dp),
            value = stateVal.value, onValueChange = { stateVal.value = it }
        ) {
            if (stateVal.value.trim().isEmpty()) {
                PlaceHolderText(helpText)
            }
            it()
        }
    }
}