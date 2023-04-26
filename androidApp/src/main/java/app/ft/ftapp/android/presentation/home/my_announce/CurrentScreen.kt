package app.ft.ftapp.android.presentation.home.my_announce

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.EMAIL
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.LoadingView
import app.ft.ftapp.android.presentation.announce_details.FromToText
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.android.utils.TimeUtil
import app.ft.ftapp.android.utils.toDate
import app.ft.ftapp.presentation.viewmodels.HomeEvent
import app.ft.ftapp.presentation.viewmodels.HomeViewModel
import java.time.ZoneId

/**
 * Current screen view.
 */
@Composable
fun CurrentScreen() {
    val viewModel = setupViewModel<HomeViewModel>()
    val assignedAnnounce by viewModel.assignedAnnounce.collectAsState()
    val isLoading by viewModel.isShowProgress.collectAsState()


    var announceTime by remember {
        mutableStateOf(
            TimeUtil.getMinutesLeft(
                until = assignedAnnounce?.startTime?.toDate()?.atZone(
                    ZoneId.systemDefault()
                )?.toInstant()?.toEpochMilli() ?: 0
            )
        )
    }

    DisposableEffect(announceTime) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                announceTime = TimeUtil.getMinutesLeft(until = assignedAnnounce?.startTime)
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
//    LazyColumn(Modifier.fillMaxWidth()
//        .fillMaxHeight().background(appBackground)) {

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(appBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                        .background(if (announceTime <= 10) bottomNavColor else yellowColor)
                ) {
                    Text(
                        "через $announceTime мин",
                        fontFamily = Montserrat,
                        modifier = Modifier.padding(vertical = 1.dp, horizontal = 3.dp),
                        color = if (announceTime <= 10) Color.White else Color.Black
                    )
                }

                Text(
                    TimeUtil.toStringDateParser(assignedAnnounce?.startTime),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            FromToText(assignedAnnounce?.placeFrom ?: "", assignedAnnounce?.placeTo ?: "")
            Divider(color = Color.Black, thickness = 0.4.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(id = R.string.car_price),
                    fontFamily = Montserrat,
                    fontSize = 16.sp
                )
                Text(
                    "560 P",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
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
                    (4 - (assignedAnnounce?.countOfParticipants ?: 0)).toString(),
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

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(6.dp))
                    .padding(bottom = 50.dp),
                onClick = {
                    if (assignedAnnounce?.authorEmail != EMAIL) {
                        viewModel.onEvent(
                            HomeEvent.LeaveAnnounce((assignedAnnounce?.id ?: 0).toLong())
                        )
                    } else {
                        viewModel.onEvent(
                            HomeEvent.DeleteAnnounce((assignedAnnounce?.id ?: 0).toLong())
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = chipTimeColor),
                enabled = !isLoading || assignedAnnounce?.authorEmail == EMAIL
            ) {

                if (assignedAnnounce?.authorEmail != EMAIL) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontFamily = Montserrat,
                        text = "Покинуть поездку",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontFamily = Montserrat,
                        text = "Удалить поездку",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }


            }
//            }
        }
    }

    if (isLoading) {
        LoadingView()
    }
}