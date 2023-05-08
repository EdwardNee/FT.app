@file:OptIn(ExperimentalMaterialApi::class)

package app.ft.ftapp.android.presentation.home.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import app.ft.ftapp.android.presentation.home.travelers.ListTravelers
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import kotlinx.coroutines.launch

/**
 * Composable for List of history [Announce].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen() {

    val viewModelScreen = setupViewModel<HistoryScreenViewModel>()
    val historyList = viewModelScreen.pagerHistory.collectAsLazyPagingItems()

    var isChosen = rememberSaveable { mutableStateOf(true) }

    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

    ModalBottomSheetLayout(
        sheetContent = {
            if (modalBottomSheetState.isVisible) {

                if (isChosen.value) {
                    HistoryDetails(isChosen)
                } else {
                    ListOfTravelers(
                        Announce(
                            participants = listOf(
                                Participant(
                                    username = "asdad",
                                    email = "adad"
                                ),
                                Participant(username = "asdad", email = "adad"),
                                Participant(username = "asdad", email = "adad")
                            )
                        ),
                        isChosen
                    )
                }

            } else {
                Surface(
                    Modifier
                        .size(0.2.dp)
                        .background(Color.Transparent)
                ) {
                    Text("", fontSize = 0.sp)
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = appBackground,
        // scrimColor = ,  //Color for the fade background when open/close the drawer
    ) {
        HistoryList(modalBottomSheetState)
    }
}

/**
 * Composable shows list of travelers.
 */
@Composable
fun ListOfTravelers(announce: Announce, isChosen: MutableState<Boolean>) {
    Column(Modifier.fillMaxWidth()) {
        IconButton(onClick = { isChosen.value = true }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        ListTravelers(announce)
    }
}

/**
 * Composable to show list of history [Announce].
 */
@Composable
fun HistoryList(modalBottomSheetState: ModalBottomSheetState) {
    val scope = rememberCoroutineScope()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding()
    ) {
        items(/*historyList*/ 5) {
            HistoryAnnounceItem(Announce()) {
                scope.launch {
                    modalBottomSheetState.show()
                }
            }
        }
    }
}