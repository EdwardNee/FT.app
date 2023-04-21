package app.ft.ftapp.android.presentation.home.my_announce

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.bottomNavColor
import app.ft.ftapp.android.ui.theme.chipTimeColor
import app.ft.ftapp.presentation.viewmodels.HomeEvent
import app.ft.ftapp.presentation.viewmodels.HomeViewModel

/**
 * Current screen view.
 */
@Preview
@Composable
fun CurrentScreen() {
    val viewModel = setupViewModel<HomeViewModel>()
    val assignedAnnounce by viewModel.assignedAnnounce.collectAsState()

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
                    assignedAnnounce?.countOfParticipants.toString(),
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
                    viewModel.onEvent(
                        HomeEvent.DeleteAnnounce((assignedAnnounce?.id ?: 0).toLong())
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = chipTimeColor)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = Montserrat,
                    text = "Удалить поездку",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
//            }
        }
    }
}