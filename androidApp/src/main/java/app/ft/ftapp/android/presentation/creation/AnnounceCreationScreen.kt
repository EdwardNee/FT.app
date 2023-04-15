package app.ft.ftapp.android.presentation.creation

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.presentation.viewmodels.CreationEvent
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.FocusPosition
import app.ft.ftapp.presentation.viewmodels.ModelsState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.launch
import java.time.LocalTime

/**
 * Composable method to draw announcement creation screen.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnounceCreationScreen(onAction: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel = setupViewModel<CreationViewModel>()
    val progress by viewModel.isShowProgress.collectAsState()

    val loadResult by viewModel.loadResult.collectAsState()

    val source by viewModel.sourceDestination.collectAsState()
    val end by viewModel.endDestination.collectAsState()
    val countOfParticipants by viewModel.countOfParticipants.collectAsState()
    val price by viewModel.price.collectAsState()
    val comment by viewModel.comment.collectAsState()

    val locations by viewModel.triple.collectAsState()
    val searchState by viewModel.editTextTap.collectAsState()

    val boxTimePickerColor = listOf(Color.Transparent, Color.Black)
    var borderColor by rememberSaveable { mutableStateOf(0) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val clockState = rememberSheetState()

    ClockDialog(state = clockState,
        config = ClockConfig(is24HourFormat = true),
        selection = ClockSelection.HoursMinutes { h, m ->
            selectedTime.value = LocalTime.of(h, m)
        })

    if (!clockState.visible) {
        borderColor = 0
    }

    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.loadResult.collect {
                when (it) {
                    ModelsState.Loading -> {}
                    is ModelsState.Error -> {
                        Toast.makeText(DIFactory.initCtx, it.message, Toast.LENGTH_LONG).show()
                    }
                    ModelsState.Success -> {
                        onAction()
                    }
                }
            }
        }
    }

//    when (loadResult) {
//        ModelsState.Loading -> {}
//        is ModelsState.Error -> {
//            Toast.makeText(
//                LocalContext.current, (loadResult as ModelsState.Error).message, Toast.LENGTH_LONG
//            ).show()
//        }
//        ModelsState.Success -> {
//            onAction()
//        }
//    }

    Spacer(modifier = Modifier.padding(40.dp))
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
                    .padding(horizontal = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(backgroundEditTextBG)
                        .padding(horizontal = 3.dp)
                ) {
                    FromToComposable(source, end, viewModel)
                    if (searchState != FocusPosition.None) {
                        Box(
                            Modifier
                                .height(210.dp)
                                .padding(top = 3.dp),
                            contentAlignment = if (locations.isNotEmpty()) Alignment.TopCenter else Alignment.CenterEnd
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
//                                    .background(editTextBackground)
                            ) {
                                if (locations.isNotEmpty()) {
                                    items(locations) {
                                        SearchLocationItem(it.name, it.address) {
                                            viewModel.onEvent(
                                                CreationEvent.Action.OnAddressClicked(
                                                    it.address, it.latLng
                                                )
                                            )
                                        }
                                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                                    }
                                } else {
                                    item {
                                        Text(
                                            text = "Адреса не найдены",
                                            fontSize = 22.sp,
                                            fontFamily = Montserrat,
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextValues(
                    price,
                    stringResource(R.string.price),
                    stringResource(R.string.currency_rub),
                ) {
                    viewModel.onEvent(CreationEvent.FieldEdit.ParticipantsCountEdit(it))
                }
                TextValues(3, stringResource(R.string.places_count)) {}
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Начало поездки", fontFamily = Montserrat)
                Spacer(Modifier.weight(1f))
                Card(
                    Modifier
//                        .wrapContentSize()
                        .width(180.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = editTextBackground)
                        .clickable(
                            interactionSource = NoRippleInteractionSource(), indication = null
                        ) {
                            borderColor = 1
                            clockState.show()
                        }, border = BorderStroke(1.dp, boxTimePickerColor[borderColor])
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(color = editTextBackground)
                    ) {
                        Text(
                            if (selectedTime.value == null) "Время начала" else selectedTime.value.toString(),
                            fontFamily = Montserrat,
                            color = placeholderColor,
                            fontSize = (if (selectedTime.value == null) 15 else 20).sp,
                            fontWeight = if (selectedTime.value == null) FontWeight.Normal else FontWeight.SemiBold
                        )
                    }
                }
            }

            AdditionalNotes(comment) {
                viewModel.onEvent(CreationEvent.FieldEdit.CommentEdit(it))
            }
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
fun AdditionalNotes(comment: String, onChange: (String) -> Unit) {

    TextField(
        textStyle = TextStyle(fontSize = 16.sp),
        value = comment,
        onValueChange = onChange,
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
            text,
            fontFamily = Montserrat,
            modifier = Modifier
                .padding(end = 4.dp)
                .focusable(true, interactionSource)
        )
        Card(modifier = Modifier.wrapContentSize()) {
            BasicTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle.Default.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
                value = numeric.toString(),
                singleLine = true,
                enabled = currency.isEmpty(),
                onValueChange = { onEventCall(it.toInt()) },
                modifier = Modifier
                    .width(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = editTextBackground)
                    .padding(vertical = 12.dp)
                    .padding(horizontal = 6.dp)
            )
        }

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