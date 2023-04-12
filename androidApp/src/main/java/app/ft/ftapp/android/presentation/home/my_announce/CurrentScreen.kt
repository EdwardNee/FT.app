package app.ft.ftapp.android.presentation.home.my_announce

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announce_details.FromToText
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.bottomNavColor

/**
 * Current screen view.
 */
@Preview
@Composable
fun CurrentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(3.dp))
                    .background(bottomNavColor)
            ) {
                Text(
                    "через 10 мин",
                    fontFamily = Montserrat,
                    modifier = Modifier.padding(vertical = 1.dp, horizontal = 3.dp),
                    color = Color.White
                )
            }

            Text(
                "сегодня в 12:43",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        FromToText("from adrees", "to address")
        Divider(color = Color.Black, thickness = 0.4.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.car_price), fontFamily = Montserrat, fontSize = 16.sp)
            Text("560 P", fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Divider(color = Color.Black, thickness = 0.4.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Посмотреть попутчиков",
                fontFamily = Montserrat,
                modifier = Modifier.weight(2f),
                maxLines = 1,
                textAlign = TextAlign.End,
                fontSize = 16.sp
            )
            Text(
                "4",
                fontFamily = Montserrat,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        Divider(color = Color.Black, thickness = 0.4.dp)
    }
}