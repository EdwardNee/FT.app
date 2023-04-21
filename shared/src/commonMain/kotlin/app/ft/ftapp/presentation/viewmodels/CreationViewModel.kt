package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.NAME
import app.ft.ftapp.clid
import app.ft.ftapp.domain.models.*
import app.ft.ftapp.domain.usecase.CreateAnnouncementUseCase
import app.ft.ftapp.domain.usecase.taxi.GetTripInfoUseCase
import app.ft.ftapp.key_yandex
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * [BaseViewModel] inherited. ViewModel for creating announcements screen.
 */
class CreationViewModel : BaseViewModel() {
    //region di
    private val createAnnounce: CreateAnnouncementUseCase by kodein.instance()
    private val getTripInfo: GetTripInfoUseCase by kodein.instance()
    //endregion

    //Searching
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _locations = MutableStateFlow(
        listOf(
            AddressesItems(
                name = "Дубки 1 корпус",
                address = "ул. Дениса Давыдова, 1",
                latLng = LatLng(55.660409, 37.228898)
            ),
            AddressesItems(
                name = "Дубки 2 корпус",
                address = "ул. Дениса Давыдова, 3",
                latLng = LatLng(55.659668, 37.228386)
            ),
            AddressesItems(
                name = "Дубки 3 корпус",
                address = "ул. Дениса Давыдова, 9",
                latLng = LatLng(55.659622, 37.226078)
            ),
            AddressesItems(
                name = "Станция Одинцово",
                address = "Привокзальная площадь, 1",
                latLng = LatLng(55.672264, 37.281410)
            ),
        )
    )

    private var route = Pair(LatLng(0.0, 0.0), LatLng(0.0, 0.0))

    val author = MutableStateFlow<String>(EMAIL).cMutableStateFlow()
    val email = MutableStateFlow<String>(NAME).cMutableStateFlow()
    val sourceDestination = MutableStateFlow<String>("").cMutableStateFlow()
    val endDestination = MutableStateFlow<String>("").cMutableStateFlow()
    val comment = MutableStateFlow<String>("").cMutableStateFlow()
    val price = MutableStateFlow<Int>(0).cMutableStateFlow()
    val countOfParticipants = MutableStateFlow<Int>(3).cMutableStateFlow()

    val editTextTap = MutableStateFlow<FocusPosition>(FocusPosition.None)

    val triple = combine(
        _locations,
        sourceDestination,
        endDestination,
        editTextTap
    ) { loc, start, end, sTap ->
        if (start.isBlank() && end.isBlank() && sTap !is FocusPosition.None) {
            loc
        } else {
            loc.filter {
                val whatToContain = when (sTap) {
                    is FocusPosition.SourceField -> start
                    is FocusPosition.EndField -> end
                    is FocusPosition.None -> NON_EXISTING_WORD
                }.trim().lowercase()
                it.name.lowercase().contains(whatToContain) or it.address.lowercase()
                    .contains(whatToContain)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())


    private val _loadResult = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val loadResult: MutableStateFlow<ModelsState>
        get() = _loadResult

    val createdAnnounce = MutableStateFlow<Announce?>(null)

    fun onEvent(event: CreationEvent) {
        when (event) {
            is CreationEvent.FieldEdit.SourceEdit -> {
                sourceDestination.value = event.source
            }
            is CreationEvent.FieldEdit.EndEdit -> {
                endDestination.value = event.end
            }
            is CreationEvent.FieldEdit.ParticipantsCountEdit -> {
                countOfParticipants.value = event.count
            }

            is CreationEvent.Action.OnPublish -> {
                val announce = Announce(
                    authorEmail = author.value,
                    placeFrom = sourceDestination.value,
                    placeTo = endDestination.value,
                    participants = emptyList(),
                    countOfParticipants = countOfParticipants.value,
                    comment = comment.value
                )
                createAnnounceCall(announce = announce)
            }

            is CreationEvent.Action.OnAddressClicked -> {
                fillByClickedAddress(event.address, event.coordinates)
            }
            is CreationEvent.FieldEdit.CommentEdit -> {
                comment.value = event.comment.trim()
            }
        }
    }

    /**
     * Fills the edit text values by clicked addresses tabs.
     */
    private fun fillByClickedAddress(address: String, coordinates: LatLng) {
        when (editTextTap.value) {
            is FocusPosition.SourceField -> {
                sourceDestination.value = address
                route = route.copy(first = coordinates)
            }

            is FocusPosition.EndField -> {
                endDestination.value = address
                route = route.copy(second = coordinates)
            }

            is FocusPosition.None -> {

            }
        }
        editTextTap.value = FocusPosition.None

        if (route.first.lat != 0.0 && route.second.lat != 0.0) {
//            getTripInfoCall(route)
            price.value = 139
        }
    }

    /**
     * Method that processes request for creating announcement on the server.
     */
    private fun createAnnounceCall(announce: Announce) {
        showProgress()

        viewModelScope.launch {
            val result = createAnnounce(announce)
            when (result) {
                is ServerResult.SuccessfulResult -> {
                    createdAnnounce.value = result.model
                    _loadResult.value = ModelsState.Success
                }

                is ServerResult.UnsuccessfulResult -> {
                    _loadResult.value = ModelsState.Error(result.error)
                }
            }
            hideProgress()
        }
    }

    private fun getTripInfoCall(coords: Pair<LatLng, LatLng>) {
        val params = TaxiParams(
            clid = clid,
            apikey = key_yandex,
            rll = "${coords.first.lon},${coords.first.lat}~${coords.second.lon},${coords.second.lat}",
            clss = "econom",
            lang = "ru"
        )

        viewModelScope.launch {
            val result = getTripInfo(params)
            when (result) {
                is ServerResult.SuccessfulResult -> {
                    println("Succ ${result.model}")
                }
                is ServerResult.UnsuccessfulResult -> {
                    println("Unsuc ${result.error}")
                }
            }
        }

    }

    companion object {
        private const val NON_EXISTING_WORD = "NON_EXISTING_WORD"
    }
}

/**
 * Actions event on the creation screen.
 */
sealed class CreationEvent {
    /**
     * Some call actions.
     */
    sealed class Action : CreationEvent() {
        object OnPublish : Action()
        class OnAddressClicked(val address: String, val coordinates: LatLng) : Action()
    }

    /**
     * Values in edittext changed.
     */
    sealed class FieldEdit : CreationEvent() {
        data class SourceEdit(val source: String) : FieldEdit()
        data class EndEdit(val end: String) : FieldEdit()
        data class ParticipantsCountEdit(val count: Int) : FieldEdit()
        data class CommentEdit(val comment: String) : FieldEdit()
    }
}

sealed class FocusPosition {
    object SourceField : FocusPosition()
    object EndField : FocusPosition()
    object None : FocusPosition()
}