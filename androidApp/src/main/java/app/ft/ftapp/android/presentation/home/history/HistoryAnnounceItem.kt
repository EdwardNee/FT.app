package app.ft.ftapp.android.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announcement.AnnounceButton
import app.ft.ftapp.android.presentation.announcement.AnnounceParams
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.android.utils.TimeUtil
import app.ft.ftapp.domain.models.Announce

/**
 * Composable for [Announce] item in history list.
 */
@Composable
fun HistoryAnnounceItem(announce: Announce, onClickInfo: (Announce) -> Unit) {
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
                    text = announce.placeTo,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1
                )

                Text(
                    text = TimeUtil.toStringDateParser(announce.startTime),
                    color = textGray,
                    fontSize = 14.sp
                ) //"сегодня в 12:17"
            }

            AnnounceParams(stringResource(id = R.string.history_price), "560 ₽")
            AnnounceParams(
                stringResource(id = R.string.state_announce), "Завершена"
            )


            AnnounceButton(
                Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Информация",
            ) {
                onClickInfo(announce)
            }
        }
    }
}