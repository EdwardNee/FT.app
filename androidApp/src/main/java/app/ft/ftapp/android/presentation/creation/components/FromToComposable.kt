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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.presentation.creation.CreationScreenViewModel
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.blueCircle
import app.ft.ftapp.android.ui.theme.editTextBackground
import app.ft.ftapp.android.ui.theme.redCircle
import app.ft.ftapp.presentation.viewmodels.CreationEvent
import app.ft.ftapp.presentation.viewmodels.FocusPosition

/**
 * Composable component to show from to UI.
 */
@Composable
fun FromToComposable(
    source: String,
    end: String,
    viewModel: CreationScreenViewModel,
    size: MutableState<IntSize> = mutableStateOf(IntSize.Zero)
) {
    val animVal = remember { androidx.compose.animation.core.Animatable(1f) }
//    val stateA = remember { mutableStateOf("") }
//    val stateB = remember { mutableStateOf("") }

//    size =
    LaunchedEffect(source.isNotEmpty() && end.isNotEmpty()) {
        animVal.animateTo(
            targetValue = if (source.isNotEmpty() && end.isNotEmpty()) 5.36f else 1f,
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
            .onGloballyPositioned { coordinates ->
                size.value = coordinates.size
            }

    ) {
        Canvas(modifier = Modifier) {
            drawLine(
                strokeWidth = 1f,
                color = Color.Black,
                start = Offset(x = 15f, y = 41f), //3 * 18 + 14
                end = Offset(x = 15f, y = 42f * animVal.value)
            )
        }

        DestinationComponent(
            stringResource(R.string.pointA),
            stringResource(R.string.from),
            redCircle,
            source,
            dismissFocus = {
                viewModel.viewModel.onEvent(CreationEvent.FieldEdit.ChangeFocus(FocusPosition.None))
            },
            onFocusState = {
                viewModel.viewModel.onEvent(CreationEvent.FieldEdit.ChangeFocus(FocusPosition.SourceField))
            }
        ) {
            viewModel.viewModel.onEvent(CreationEvent.FieldEdit.SourceEdit(it))
        }

        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 8.dp), color = Color.Black, thickness = 0.4.dp
        )

        DestinationComponent(
            stringResource(R.string.pointB),
            stringResource(R.string.to),
            blueCircle,
            end,
            dismissFocus = {
                viewModel.viewModel.onEvent(CreationEvent.FieldEdit.ChangeFocus(FocusPosition.None))
            },
            onFocusState = {
                viewModel.viewModel.onEvent(CreationEvent.FieldEdit.ChangeFocus(FocusPosition.EndField))
            }
        ) {
            viewModel.viewModel.onEvent(CreationEvent.FieldEdit.EndEdit(it))
        }
    }
}

/**
 * Destination component. EditText view that animates drawn circle.
 */
@Composable
fun DestinationComponent(
    liter: String,
    helpText: String,
    color: Color,
    destination: String,
    onFocusState: () -> Unit,
    dismissFocus: () -> Unit,
    onEventCall: (String) -> Unit
//    stateVal: MutableState<String>
) {
//    var state by remember { mutableStateOf("") }
    val setColor = if (destination.isEmpty()) Color.White else color

    val colorState = remember { Animatable(Color.White) }

    // animate to green/red based on "button click"
    LaunchedEffect(destination.isNotEmpty()) {
        colorState.animateTo(
            if (destination.isNotEmpty()) color else Color.White,
            animationSpec = tween(400)
        )
    }
    Box(contentAlignment = Alignment.CenterStart) {
        Text(text = liter, fontSize = 16.sp, modifier = Modifier.drawBehind {
            drawCircle(color = Color.Black, radius = 32f)
            drawCircle(color = colorState.value, radius = 30f)
        })

        BasicTextField(
            textStyle = TextStyle.Default.copy(fontSize = 16.sp, fontFamily = Montserrat),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocusState()
                    } else {
                        dismissFocus()
                    }
                }
                .padding(vertical = 16.dp),
            value = destination, onValueChange = { /*stateVal.value = it*/ onEventCall(it) },
            maxLines = 1
        ) {
            if (destination.trim().isEmpty()) {
                PlaceHolderText(helpText)
            }
            it()
        }
    }
}