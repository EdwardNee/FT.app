package app.ft.ftapp.android.presentation.creation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.presentation.creation.components.FromToComposable
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.buttonColors
import app.ft.ftapp.android.ui.theme.editTextBackground
import app.ft.ftapp.presentation.viewmodels.CreationEvent
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState

/**
 * Composable method to draw announcement creation screen.
 */
@Composable
fun AnnounceCreationScreen(onAction: () -> Unit) {
    val viewModel = setupViewModel<CreationViewModel>()
    val progress by viewModel.isShowProgress.collectAsState()

    val loadResult by viewModel.loadResult.collectAsState()

    val source by viewModel.sourceDestination.collectAsState()
    val end by viewModel.endDestination.collectAsState()
    val countOfParticipants by viewModel.countOfParticipants.collectAsState()


    when (loadResult) {
        ModelsState.Loading -> {}
        is ModelsState.Error -> {
            Toast.makeText(
                LocalContext.current,
                (loadResult as ModelsState.Error).message,
                Toast.LENGTH_LONG
            ).show()
        }
        ModelsState.Success -> {
            onAction()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {
            HeaderText(text = stringResource(id = R.string.create_announce))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                FromToComposable(source, end, viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextValues(
                    countOfParticipants,
                    stringResource(R.string.price),
                    stringResource(R.string.currency_rub),
                ) {
                    viewModel.onEvent(CreationEvent.FieldEdit.ParticipantsCountEdit(it))
                }
                TextValues(3, stringResource(R.string.places_count)) {}
            }

            Text(text = progress.toString())

            if (progress) {
                Text(text = "IN PROGRESS")
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
                onClick = { viewModel.onEvent(CreationEvent.Action.OnPublish) },
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColors)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = Montserrat,
                    text = stringResource(R.string.publish),
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
fun TextValues(numeric: Int, text: String, currency: String = "", onEventCall: (Int) -> Unit) {
//    var value by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text, fontFamily = Montserrat, modifier = Modifier
                .padding(end = 4.dp)
                .focusable(true, interactionSource)
        )
        BasicTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle.Default.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
            value = numeric.toString(),
            singleLine = true,
            onValueChange = { onEventCall(it.toInt()) },
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