package app.ft.ftapp.android.presentation.creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.R
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.greenColor

/**
 * Successfully created announcement composable view.
 */
@Composable
@Preview
fun SuccessView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(appBackground) //infoBottomBackground
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Объявление опубликовано!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontFamily = Montserrat,
            fontSize = 40.sp,
            color = greenColor,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(horizontal = 16.dp),
            painter = painterResource(id = R.drawable.suc_create),
            contentDescription = ""
        )
    }
}