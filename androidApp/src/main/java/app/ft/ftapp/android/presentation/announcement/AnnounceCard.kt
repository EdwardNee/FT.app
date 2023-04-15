package app.ft.ftapp.android.presentation.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.ui.theme.cardBody
import app.ft.ftapp.android.ui.theme.textGray
import app.ft.ftapp.domain.models.Announce

/**
 * Announcement card composable method to draw.
 */
@Composable
fun AnnounceCard(announce: Announce, onClickInfo: (Announce) -> Unit, onClickBecome: (Long) -> Unit) {
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
                Text(
                    text = "${announce.placeFrom}-${announce.placeTo}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(text = "сегодня в 12:17", color = textGray, fontSize = 14.sp)
            }

            AnnounceParams(stringResource(id = R.string.car_price), "560 ₽")
            AnnounceParams(
                stringResource(id = R.string.free_places),
                announce.countOfParticipants.toString()
            )

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
                ) {
                    onClickInfo(announce)
                }
                AnnounceButton(Modifier.weight(1f)) {
                    onClickBecome(announce.id.toLong())
                }
            }
        }
    }
}

/**
 * Depicts params of the announcement.
 */
@Composable
fun AnnounceParams(text: String, paramValue: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text, color = textGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(paramValue, fontSize = 14.sp)
    }
}

/**
 * Button to process announcement card.
 */
@Composable
fun AnnounceButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, modifier = Modifier
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