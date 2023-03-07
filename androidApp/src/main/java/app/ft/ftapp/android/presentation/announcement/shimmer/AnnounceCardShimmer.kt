package app.ft.ftapp.android.presentation.announcement.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.presentation.announcement.AnnounceButton
import app.ft.ftapp.android.presentation.common.shimmer.shimmerEffect
import app.ft.ftapp.android.ui.theme.cardBody
import app.ft.ftapp.android.ui.theme.textGray

@Composable
fun AnnounceCardShimmer() {
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
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.5f)
                    .height(18.dp)
                    .shimmerEffect())
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.55f)
                    .height(16.dp)
                    .shimmerEffect())
            }

            Spacer(modifier = Modifier.padding(3.5.dp))
            //AnnounceParams
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.35f)
                    .height(16.dp)
                    .shimmerEffect())
                Spacer(modifier = Modifier
                    .height(16.dp)
                    .padding(4.dp))
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.2f)
                    .height(16.dp)
                    .shimmerEffect())
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.35f)
                    .height(16.dp)
                    .shimmerEffect())
                Spacer(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(16.dp)
                    .padding(4.dp))
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.2f)
                    .height(16.dp)
                    .shimmerEffect())
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
//                AnnounceButton(
//                    modifier = Modifier
//                        .clip(
//                            RoundedCornerShape(
//                                bottomEnd = 10.dp,
//                                bottomStart = 10.dp,
//                                topStart = 10.dp,
//                                topEnd = 10.dp
//                            )
//                        )
//                        .weight(1f)
//                        .padding(end = 8.dp)
//                )
//                AnnounceButton(
//                    modifier = Modifier
//                        .clip(
//                            RoundedCornerShape(
//                                bottomEnd = 10.dp,
//                                bottomStart = 10.dp,
//                                topStart = 10.dp,
//                                topEnd = 10.dp
//                            )
//                        )
//                        .weight(1f)
//                )
            }
        }
    }
}