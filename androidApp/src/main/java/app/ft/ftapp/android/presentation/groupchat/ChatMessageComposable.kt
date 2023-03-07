package app.ft.ftapp.android.presentation.groupchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.namesColor
import kotlin.random.Random

/**
 * Composable method to draw a Chat message element.
 */
@Composable
fun ChatMessageComponent(modifier: Modifier = Modifier, text: String, myMessage: Boolean) {
    val dpFormat = 8.dp
    val previousIsMine = true

    val bottomEnd: Dp //My message
    val bottomStart: Dp //Someone's

    if (myMessage) {
        bottomEnd = 0.dp
        bottomStart = dpFormat
    } else {
        bottomEnd = dpFormat
        bottomStart = 0.dp
    }
    val shape = RoundedCornerShape(
        topStart = dpFormat,
        topEnd = dpFormat,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )

    Row(
        Modifier
            .wrapContentSize()
            .then(modifier)
    ) {
        if (!myMessage) {
            Icon(
                modifier = Modifier.align(Alignment.Bottom),
                imageVector = Icons.Rounded.Person,
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(shape)
                .background(Color.Blue)
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
                fontWeight = FontWeight.SemiBold,
                text = "Егор Дмитриев",
                fontFamily = Montserrat,
                fontSize = 12.sp,
                color = namesColor[(namesColor.indices).random()]
            )
            Row {
                SelectionContainer { //TODO
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 8.dp)
                            .weight(1f, false),
                        text = text,
                        fontFamily = Montserrat,
                        color = Color.White,
                    )
                }

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Bottom)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 4.dp, bottom = 2.dp),
                        text = "12:22",
                        fontFamily = Montserrat,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}