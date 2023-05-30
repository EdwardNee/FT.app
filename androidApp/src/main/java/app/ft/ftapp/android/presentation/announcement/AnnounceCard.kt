package app.ft.ftapp.android.presentation.announcement

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.EMAIL
import app.ft.ftapp.android.R
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.android.utils.TimeUtil
import app.ft.ftapp.android.utils.toDate
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.utils.ConstantValues
import java.time.ZoneId


/**
 * Announcement card composable method to draw.
 */
@Composable
fun AnnounceCard(
    announce: Announce,
    onClickInfo: (Announce) -> Unit,
    onClickBecome: (Long) -> Unit
) {

    var announceTime by remember {
        mutableStateOf(
            TimeUtil.getMinutesLeft(
                until = announce.startTime?.toDate()?.atZone(
                    ZoneId.systemDefault()
                )?.toInstant()?.toEpochMilli() ?: 0
            )
        )
    }

    DisposableEffect(announceTime) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                announceTime = TimeUtil.getMinutesLeft(until = announce.startTime)

                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnable)

        if (announceTime <= 0L) {
            announceTime = 0
            handler.removeCallbacks(runnable)
        }

        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

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
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (announceTime <= 10) bottomNavColor else yellowColor)
                ) {
                    Text(
                        "через $announceTime мин",
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 1.dp, horizontal = 3.dp),
                        color = if (announceTime <= 10) Color.White else Color.Black
                    )
                }

                Text(
                    text = TimeUtil.toStringDateParser(announce.startTime),
                    color = textGray,
                    fontSize = 14.sp
                ) //"сегодня в 12:17"
            }

            Text(
                text = announce.placeTo,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1
            )

            AnnounceParams(stringResource(id = R.string.car_price), "560 ₽")
            AnnounceParams(
                stringResource(id = R.string.free_places),
                (announce.countOfParticipants - (announce.participants?.size
                    ?: 0)).toString()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AnnounceButton(
                    Modifier
                        .height(IntrinsicSize.Min)
//                        .padding(end = 8.dp)
                        .weight(1f),
                    text = stringResource(id = app.ft.ftapp.android.R.string.info)
                ) {
                    onClickInfo(announce)
                }

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                AnnounceButton(
                    Modifier
                        .height(IntrinsicSize.Min)
                        .weight(1f),
                    text = "Примкнуть",
                    shouldEnable(announce)
                ) {
                    onClickBecome(announce.id.toLong())
                }
            }
        }
    }
}

fun shouldEnable(announce: Announce): Boolean {
    return announce.authorEmail != EMAIL && announce.participants?.firstOrNull { it.email == EMAIL } == null
}

/**
 * Depicts params of the announcement.
 */
@Composable
fun AnnounceParams(text: String, paramValue: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text, color = textGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        paramValue.ToStateText()
//        Text(paramValue, fontSize = 14.sp)
    }
}

/**
 * Edits Text composable depending on value.
 */
@Composable
fun String.ToStateText() {
    when (this) {
        ConstantValues.TravelStatus.CREATED -> {
            Text(
                this,
                fontSize = 18.sp,
                color = waitingStatusColor,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        }
        "Ожидание" -> {
            Text(
                this,
                fontSize = 18.sp,
                color = waitingStatusColor,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        }
        ConstantValues.TravelStatus.IN_PROGRESS -> {
            Text(
                this,
                fontSize = 18.sp,
                color = waitingStatusColor,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        }
        ConstantValues.TravelStatus.CLOSED -> {
            Text(
                this,
                fontSize = 18.sp,
                color = greenColor,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        }

        else -> {
            Text(this, fontSize = 14.sp, fontFamily = Montserrat)
        }
    }
//
}

/**
 * Button to process announcement card.
 */
@Composable
fun AnnounceButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
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
            .height(55.dp)
            .then(modifier),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = grayMore),
        contentPadding = PaddingValues()
    ) {
        if (text == "Примкнуть") {
            Image(
                modifier = Modifier,
                colorFilter = if (enabled) null else ColorFilter.tint(Color.LightGray),
                imageVector = ImageVector.vectorResource(R.drawable.become_travel),
                contentDescription = ""
            )
        } else {
            Image(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(R.drawable.info), contentDescription = ""
            )
        }
    }
}