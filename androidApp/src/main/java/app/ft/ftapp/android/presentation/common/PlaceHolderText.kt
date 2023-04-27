package app.ft.ftapp.android.presentation.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.placeholderColor

@Composable
fun PlaceHolderText(helpText: String, fontSize: TextUnit = 16.sp, modifier: Modifier = Modifier) {
    Text(
        text = helpText,
        color = placeholderColor,
        fontSize = fontSize,
        fontFamily = Montserrat,
        modifier = modifier
    )
}