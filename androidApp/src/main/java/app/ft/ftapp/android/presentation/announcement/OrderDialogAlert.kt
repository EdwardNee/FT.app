package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.ft.ftapp.android.R
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.grayCircle
import app.ft.ftapp.android.ui.theme.yandexYellow

/**
 * Composable Ordering taxi alert dialog creating.
 */
@Composable
fun OrderDialogAlert(onYesClicked: () -> Unit, onNoClicked: () -> Unit) {
    Dialog(onDismissRequest = {
        onNoClicked()
    }) {
        OrderingAlert(onYesClicked = onYesClicked, onNoClicked = onNoClicked)
    }
}

/**
 * View of composable alert dialog creation.
 */
@Composable
fun OrderingAlert(onYesClicked: () -> Unit, onNoClicked: () -> Unit) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp),
                text = stringResource(id = R.string.alert_text_start),
                fontFamily = Montserrat,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            OrderingAlertButton(stringResource(id = R.string.continue_str), yandexYellow) {
                onNoClicked()
                onYesClicked()
            }
            OrderingAlertButton(stringResource(id = R.string.cancel_str), grayCircle) {
                onNoClicked()
            }
        }
    }
}

/**
 * Composable for alert buttons.
 */
@Composable
fun OrderingAlertButton(text: String, buttonColor: Color, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(15.dp)),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
    ) {
        Text(text, modifier = Modifier.padding(vertical = 2.dp), fontSize = 14.sp)
    }
}