package app.ft.ftapp.android.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.common.HeaderText
import app.ft.ftapp.android.presentation.creation.AdditionalNotes
import app.ft.ftapp.android.presentation.creation.CreationScreenViewModel
import app.ft.ftapp.android.presentation.creation.SearchLocationItem
import app.ft.ftapp.android.presentation.creation.TextValues
import app.ft.ftapp.android.presentation.creation.components.FromToComposable
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.viewmodels.factory.ArgsViewModelFactory
import app.ft.ftapp.android.presentation.viewmodels.factory.FactoryArgs
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.android.utils.SingletonHelper
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.presentation.viewmodels.CreationEvent
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.FocusPosition
import app.ft.ftapp.presentation.viewmodels.ModelsState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.launch
import org.kodein.di.instance
import java.time.LocalTime

@Composable
fun EditAnnounceScreen(isHome: MutableState<Boolean>, announce: Announce) {
    val kodein = DIFactory.di
    val viewModel: CreationViewModel by kodein.instance(tag = "announce_cr")

    viewModel.disableUpdateState()
    viewModel.onEvent(CreationEvent.Action.UpdateEditAnnounce(announce))
    val scope = rememberCoroutineScope()

    val viewModelScreen: CreationScreenViewModel = setupViewModel<CreationScreenViewModel>(
        ArgsViewModelFactory(FactoryArgs(viewModel))
    )


    val progress by viewModel.isShowProgress.collectAsState()

    val updateResult by viewModel.updateResult.collectAsState()

    val source by viewModel.sourceDestination.collectAsState()
    val endDest by viewModel.endDestination.collectAsState()
    val countOfParticipants by viewModel.countOfParticipants.collectAsState()
    val price by viewModel.price.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val startTime by viewModel.startTime.collectAsState()

    val locations by viewModelScreen.sourceDestCombine.collectAsState()
    val searchState by viewModel.editTextTap.collectAsState()

    val boxTimePickerColor = listOf(Color.Transparent, Color.Black)
    var borderColor by rememberSaveable { mutableStateOf(0) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val clockState = rememberSheetState()


    val snackbarState = remember {
        SnackbarHostState()
    }

    ClockDialog(state = clockState,
        config = ClockConfig(is24HourFormat = true),
        selection = ClockSelection.HoursMinutes { h, m ->
            viewModel.onEvent(CreationEvent.FieldEdit.StartTimeEdit(h, m))
            selectedTime.value = LocalTime.of(h, m)
        })

    if (!clockState.visible) {
        borderColor = 0
    }


    val snackbarType = remember { mutableStateOf(false) }
    //snackbarState.showSnackbar("У вас уже есть поездка")

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.updateResult.collect {
                when (it) {
                    ModelsState.Loading -> {}
                    is ModelsState.Error -> {
                        snackbarType.value = false
//                        Toast.makeText(DIFactory.initCtx, it.message, Toast.LENGTH_LONG).show()
                        snackbarState.showSnackbar(it.message)
                    }
                    ModelsState.NoData -> {}
                    is ModelsState.Success<*> -> {
                        snackbarType.value = true
                        snackbarState.showSnackbar(
                            "Изменения успешно сохранены.",
                            duration = SnackbarDuration.Short
                        )
                        isHome.value = true
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


    val query = "дубки вшэ"
//    viewModelScreen.mapSearch.searchByQuery(query)
    Spacer(modifier = Modifier.padding(40.dp))
    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            item {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 12.dp)
                ) {
                    val (icon, text) = createRefs()
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .constrainAs(icon) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { isHome.value = true },
                                Modifier.size(48.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                            }
                            HeaderText(text = stringResource(id = R.string.edit_announce))
                        }

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
                                FromToComposable(source, endDest, viewModelScreen)
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
                                                    Divider(
                                                        color = Color.LightGray,
                                                        thickness = 0.5.dp
                                                    )
                                                }
                                            } else {
                                                item {
                                                    Text(
                                                        text = stringResource(id = R.string.no_address),
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
                                price.toString(),
                                stringResource(R.string.price),
                                stringResource(R.string.currency_rub),
                            ) {}
                            TextValues(countOfParticipants, stringResource(R.string.places_count)) {
                                viewModel.onEvent(CreationEvent.FieldEdit.ParticipantsCountEdit(it))
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                stringResource(id = R.string.travel_beginning),
                                fontFamily = Montserrat
                            )
                            Spacer(Modifier.weight(1f))
                            Card(
                                Modifier
//                        .wrapContentSize()
                                    .width(180.dp)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color = editTextBackground)
                                    .clickable(
                                        interactionSource = NoRippleInteractionSource(),
                                        indication = null
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

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .constrainAs(text) {
                                top.linkTo(icon.bottom)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }) {
                        Button(
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .fillMaxWidth()
                                .align(Alignment.End)
                                .clip(RoundedCornerShape(20.dp)),
                            onClick = { viewModel.onEvent(CreationEvent.Action.SaveEditResult) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = greenColor),
                            enabled = true
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                fontFamily = Montserrat,
                                text = stringResource(R.string.save_edit),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }


        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter), hostState = snackbarState
        ) { snackbarData: SnackbarData ->
            if (snackbarType.value) {
                AlertSnackbar(
                    snackbarData.message,
                    tintColor = greenColor,
                    painterSource = app.ft.ftapp.android.R.drawable.success_icon
                )
            } else {
                AlertSnackbar(snackbarData.message)
            }

        }
    }

    if (progress) {
        LoadingView()
    }
}