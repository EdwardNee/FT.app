package app.ft.ftapp.android.presentation.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.presentation.creation.components.FromToComposable
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.buttonColors
import app.ft.ftapp.android.ui.theme.editTextBackground

/**
 * Composable method to draw announcement creation screen.
 */
@Composable
@Preview
fun AnnounceCreationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {
            HeaderText(text = "Создать объявление")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                FromToComposable()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextValues("Цена:", "₽")
                TextValues("Количество мест:")
            }

            AdditionalNotes()
        }

        Column(Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(6.dp))
                    .padding(bottom = 50.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColors)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = Montserrat,
                    text = "Опубликовать",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * TextField composable to add notes for an announce.
 */
@Composable
fun AdditionalNotes() {
    var value by remember { mutableStateOf("") }
    TextField(
        textStyle = TextStyle(fontSize = 16.sp),
        value = value, onValueChange = { value = it },
        placeholder = { PlaceHolderText("Дополнительные комментарии...") },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor = editTextBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
//        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = onDoneOption),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 50.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

/**
 * Text values parameters composable.
 */
@Composable
fun TextValues(text: String, currency: String = "") {
    var value by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text,fontFamily = Montserrat, modifier = Modifier.padding(end = 4.dp))
        BasicTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle.Default.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
            value = value,
            singleLine = true,
            onValueChange = { value = it },
            modifier = Modifier
                .width(80.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(color = editTextBackground)
                .padding(vertical = 12.dp)
                .padding(horizontal = 6.dp)
        )
        if (currency.isNotEmpty()) {
            Text(
                currency,
                fontFamily = Montserrat,
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        }
    }
}