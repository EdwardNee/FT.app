package app.ft.ftapp.android.presentation.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.creation.components.FromToComposable
import app.ft.ftapp.android.ui.theme.editTextBackground

@Composable
@Preview
fun AnnounceCreationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 12.dp)
    ) {
        HeaderText(text = "Создать объявление")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            FromToComposable()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Цена:")
                BasicTextField(
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                    value = "560P",
                    onValueChange = {},
                    modifier = Modifier
                        .width(60.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = editTextBackground)
                        .padding(vertical = 6.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Количество мест:")
                BasicTextField(
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                    value = "560P",
                    onValueChange = {},
                    modifier = Modifier
                        .width(60.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = editTextBackground)
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}


fun Modifier.drawColoredShadow(
    color: Color = Color.Red,
    alpha: Float = 1f,
    borderRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 6.dp,
    spread: Dp = 0.dp,
    enabled: Boolean = true,
) = if (enabled) {
    this.drawBehind {
        val transparentColor = color.copy(alpha = 0.0f).toArgb()
        val shadowColor = color.copy(alpha = alpha).toArgb()
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                blurRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.save()

            if (spread.value > 0) {
                fun calcSpreadScale(spread: Float, childSize: Float): Float {
                    return 1f + ((spread / childSize) * 2f)
                }

                it.scale(
                    calcSpreadScale(spread.toPx(), this.size.width),
                    calcSpreadScale(spread.toPx(), this.size.height),
                    this.center.x,
                    this.center.y
                )
            }

            it.drawRoundRect(
                0f,
                0f,
                this.size.width - 20,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
            it.restore()
        }
    }
} else {
    this
}