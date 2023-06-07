package app.ft.ftapp.android.presentation.home.myannounce

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.EMAIL
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.LoadingView
import app.ft.ftapp.android.presentation.announcedetails.FromToText
import app.ft.ftapp.android.presentation.announcement.OrderDialogAlert
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.android.utils.OnStartTimerNotification
import app.ft.ftapp.android.utils.TimeUtil
import app.ft.ftapp.android.utils.toDate
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.LatLng
import app.ft.ftapp.domain.models.toLatLng
import app.ft.ftapp.presentation.viewmodels.HomeEvent
import app.ft.ftapp.presentation.viewmodels.HomeViewModel
import app.ft.ftapp.utils.ConstantValues
import java.time.ZoneId

/**
 * Current screen view.
 */
@Composable
fun CurrentScreen(isHome: MutableState<Boolean>, viewModel: HomeViewModel) {
    var isLoad by remember { mutableStateOf(true) }

    //val viewModel = setupViewModel<HomeViewModel>()
    val assignedAnnounce by viewModel.assignedAnnounce.collectAsState()
    val isLoading by viewModel.isShowProgress.collectAsState()

    val isDialogShowing by viewModel.isDialogShowing.collectAsState()
    val isDialogStopShowing by viewModel.isDialogStopShowing.collectAsState()


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

    val ctx = LocalContext.current

    if (isDialogShowing) {
        OrderDialogAlert(R.string.alert_text_start, onYesClicked = {

            viewModel.onEvent(HomeEvent.StartAnnounce)
            (DIFactory.baseListener as OnStartTimerNotification).onCancelNotification()
            makeRedirect(
                ctx,
                viewModel.assignedAnnounce.value?.placeFromCoords?.toLatLng() ?: LatLng(),
                viewModel.assignedAnnounce.value?.placeToCoords?.toLatLng() ?: LatLng()
            )
        }) {
            viewModel.onEvent(HomeEvent.ShowDialogStart(false))
        }
    }

    if (isDialogStopShowing) {
        OrderDialogAlert(R.string.alert_text_stop, onYesClicked = {
            viewModel.onEvent(HomeEvent.StopAnnounce)
            (DIFactory.baseListener as OnStartTimerNotification).onCancelNotification()
        }) {
            viewModel.onEvent(HomeEvent.ShowDialogStop(false))
        }
    }

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
                    (assignedAnnounce?.participants?.size ?: 1).toString(),
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
                color = Color.Black,
                thickness = 0.4.dp,
                modifier = Modifier.padding(bottom = 46.dp)
            )


            if (assignedAnnounce?.travelStatus == ConstantValues.TravelStatus.CREATED) {
                //Редактировать
                if (assignedAnnounce?.authorEmail == EMAIL) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .fillMaxWidth()
                            .align(Alignment.End),
                        onClick = {
                            isHome.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = chatBackground),
                        enabled = !isLoading || assignedAnnounce?.authorEmail == EMAIL
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontFamily = Montserrat,
                            text = stringResource(id = R.string.edit_ann),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .fillMaxWidth()
                            .align(Alignment.End),
                        onClick = {
                            viewModel.onEvent(HomeEvent.ShowDialogStart(true))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = yandexYellow),
                        enabled = !isLoading || assignedAnnounce?.authorEmail == EMAIL
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontFamily = Montserrat,
                            text = stringResource(id = R.string.yandex_redirect),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }

                //Удалить или выйти
                Button(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .fillMaxWidth()
                        .align(Alignment.End),
                    onClick = {
                        (DIFactory.baseListener as OnStartTimerNotification).onCancelNotification()
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
                            text = stringResource(id = R.string.leave_announce),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontFamily = Montserrat,
                            text = stringResource(id = R.string.delete_announce),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            } else {
                if (assignedAnnounce?.authorEmail == EMAIL) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .fillMaxWidth()
                            .align(Alignment.End),
                        onClick = {
                            viewModel.onEvent(HomeEvent.ShowDialogStop(true))
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = chipTimeColor),
                        enabled = !isLoading || assignedAnnounce?.authorEmail == EMAIL
                    ) {
                        if (assignedAnnounce?.authorEmail == EMAIL) {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                fontFamily = Montserrat,
                                text = stringResource(id = R.string.stop_announce),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    if (isLoading) {
        LoadingView()
    }
}


fun makeRedirect(context: Context, source: LatLng, end: LatLng) {
    val redirectUrl = "https://3.redirect.appmetrica.yandex.com/route?start-lat=" +
            "${source.lat}&start-lon=${source.lon}&end-lat=${end.lat}&end-lon=${end.lon}" +
            "&level=50&ref=ftapp&appmetrica_tracking_id=1178268795219780156"
    println("REDR $source, $end $redirectUrl ")

    val uri =
        Uri.parse(
            "yandextaxi://route?" +
                    "start-lat=55.73400123907955&" +
                    "start-lon=37.588533418821726&" +
                    "end-lat=55.76776211471192&" +
                    "end-lon=37.60714921124336&" +
                    "level=50&ref=yoursiteru&appmetrica_tracking_id=1178268795219780156"
        )

//    val intent = Intent(Intent.ACTION_VIEW, uri)

//    browse.data = Uri.parse(redirectUrl)

    // problems with permissions.
//    if (intent.resolveActivity(context.packageManager) != null) {
//        context.startActivity(intent)
//    } else {
    val installationIntent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
    context.startActivity(installationIntent)
//    }

}