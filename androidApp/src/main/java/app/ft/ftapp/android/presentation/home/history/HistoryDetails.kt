package app.ft.ftapp.android.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.announcedetails.FromToText
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.infoBottomBackground
import app.ft.ftapp.android.utils.TimeUtil
import app.ft.ftapp.domain.models.Announce

/**
 * Composable to show history details.
 */
@Composable
fun HistoryDetails(isChosen: MutableState<Boolean>) {
    val announce = Announce()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(infoBottomBackground)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = TimeUtil.toStringDateParser(announce?.startTime),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = Montserrat
        )

        FromToText(announce?.placeFrom ?: "", announce?.placeTo ?: "")
        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 8.dp),
            color = Color.Black,
            thickness = 0.4.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.car_price),
                fontFamily = Montserrat,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(text = "560 ₽", fontFamily = Montserrat, fontWeight = FontWeight.Bold)
        }

        Divider(
            modifier = Modifier,
            color = Color.Black,
            thickness = 0.4.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isChosen.value = false
                }
                .padding(vertical = 16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Посмотреть попутчиков",
                fontFamily = Montserrat,
                modifier = Modifier.weight(2f),
                maxLines = 1,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                (announce.participants?.size ?: 1).toString(),
                fontFamily = Montserrat,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Divider(
            modifier = Modifier,
            color = Color.Black,
            thickness = 0.4.dp
        )

        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = announce.comment
                ?: "",
            fontFamily = Montserrat,
            fontSize = 16.sp
        )
    }
}