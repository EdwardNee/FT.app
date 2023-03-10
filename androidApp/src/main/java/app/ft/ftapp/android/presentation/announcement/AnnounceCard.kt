package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.cardBody
import app.ft.ftapp.android.ui.theme.textGray

@Composable
@Preview
fun AnnounceCard() {
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBody)
                .padding(horizontal = 12.dp)
                .padding(top = 18.dp),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Объявление №15", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(text = "сегодня в 12:17", color = textGray, fontSize = 14.sp)
            }

            AnnounceParams("Цена за машину", "3")
            AnnounceParams("Свободных машин", "560 ₽")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AnnounceButton(
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                AnnounceButton(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun AnnounceParams(text: String, paramValue: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text, color = textGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(paramValue, fontSize = 14.sp)
    }
}

@Composable
fun AnnounceButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ }, modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp,
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .then(modifier)
    ) {
        Text(text = "INFO")
    }
}