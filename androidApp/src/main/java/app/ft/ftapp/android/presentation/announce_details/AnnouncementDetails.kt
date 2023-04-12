package app.ft.ftapp.android.presentation.announce_details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.blueCircle
import app.ft.ftapp.android.ui.theme.infoBottomBackground
import app.ft.ftapp.android.ui.theme.redCircle
import app.ft.ftapp.presentation.viewmodels.DetailsEvent
import app.ft.ftapp.presentation.viewmodels.DetailsViewModel

/**
 * Composable method draws announcements details of the created announce.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnouncementDetails(modalBottomSheetState: ModalBottomSheetState) {
    val viewModel = setupViewModel<DetailsViewModel>()
    val announceDb by viewModel.announceDb.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { modalBottomSheetState.currentValue }
            .collect {
                if (it == ModalBottomSheetValue.Expanded) {
                    viewModel.onEvent(DetailsEvent.OnGetPref)
                } else if (it == ModalBottomSheetValue.Hidden) {
                    viewModel.onEvent(DetailsEvent.ClearData)
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(infoBottomBackground)
            .padding(horizontal = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = "Сегодня в 12:17",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = Montserrat
        )

        FromToText(announceDb?.placeFrom ?: "", announceDb?.placeTo ?: "")
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.free_places),
                fontFamily = Montserrat,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = announceDb?.countOfParticipants.toString(),
                fontFamily = Montserrat,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(
            modifier = Modifier,
            color = Color.Black,
            thickness = 0.4.dp
        )

        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = announceDb?.comment
                ?: "",//"Хочу уехать на ситимобил и сидеть спереди. Багажа нет, только рюкзак с ноутбуком. Встречаемся около Мака.",
            fontFamily = Montserrat,
            fontSize = 16.sp
        )
    }
}

/**
 * Shows from to details.
 */
@Composable
fun FromToText(fromA: String, toB: String) {
//    val stateA = remember { mutableStateOf("улица Лефортовский вал, д 7") }
//    val stateB = remember { mutableStateOf("улица Д. Давыдова, д 5") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .padding(start = 10.dp)
            .padding(vertical = 5.dp)
    ) {
        Canvas(modifier = Modifier) {
            drawLine(
                strokeWidth = 1f,
                color = Color.Black,
                start = Offset(x = 15f, y = 41f), //3 * 18 + 14
                end = Offset(x = 15f, y = 220f)
            )
        }

        DestinationComponentDetails(stringResource(id = R.string.pointA), redCircle, fromA)

        Divider(
            modifier = Modifier
                .padding(start = 40.dp)
                .padding(end = 8.dp),
            color = Color.Black,
            thickness = 0.4.dp
        )

        DestinationComponentDetails(stringResource(id = R.string.pointB), blueCircle, toB)
    }
}

/**
 * Composable draws circles for source and destination point.
 */
@Composable
fun DestinationComponentDetails(
    liter: String, color: Color, stateVal: String
) {
    Box(contentAlignment = Alignment.CenterStart) {
        Text(text = liter, fontSize = 16.sp, modifier = Modifier.drawBehind {
            drawCircle(color = Color.Black, radius = 32f)
            drawCircle(color = color, radius = 30f)
        })

        Text(
            text = stateVal,
            fontFamily = Montserrat,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
                .padding(vertical = 16.dp)
        )
    }
}