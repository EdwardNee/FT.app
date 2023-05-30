package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.NAME
import app.ft.ftapp.clid
import app.ft.ftapp.data.converters.CodeResponse.BAD_REQUEST
import app.ft.ftapp.domain.models.*
import app.ft.ftapp.domain.usecase.server.CreateAnnouncementUseCase
import app.ft.ftapp.domain.usecase.server.GetAnnounceByEmailUseCase
import app.ft.ftapp.domain.usecase.server.UpdateAnnounceUseCase
import app.ft.ftapp.domain.usecase.taxi.GetTripInfoUseCase
import app.ft.ftapp.key_yandex
import app.ft.ftapp.utils.TimeUtil
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kodein.di.instance
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

/**
 * [BaseViewModel] inherited. ViewModel for creating announcements screen.
 */
class CreationViewModel : BaseViewModel() {
    //region di
    private val createAnnounce: CreateAnnouncementUseCase by kodein.instance()
    private val updateAnnounce: UpdateAnnounceUseCase by kodein.instance()
    private val getTripByEmail: GetAnnounceByEmailUseCase by kodein.instance()
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
    val countOfParticipants = MutableStateFlow<String>("0").cMutableStateFlow()
    val startTime = MutableStateFlow<String>("").cMutableStateFlow()

    val editTextTap = MutableStateFlow<FocusPosition>(FocusPosition.None)

    val triples = combine(
        _locations,
        sourceDestination,
        endDestination,
        editTextTap
    ) { loc, start, end, sTap ->
        if (start.isBlank() && end.isBlank() && sTap !is FocusPosition.None) {
            loc
        } else {
            loc.filter {
                println("TAG_OF_RES FILTER")
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

    val triple = sourceDestination
        .debounce(2000L)
        .onEach { println("TAG_OF_RES on each") }
        .combine(
            editTextTap
        ) { start, sTap ->
            val end = "a"
            if (start.isBlank() && end.isBlank() && sTap !is FocusPosition.None) {
                _locations.value
            } else {
                val result = _locations.value.filter {
                    println("TAG_OF_RES FILTER")
                    val whatToContain = when (sTap) {
                        is FocusPosition.SourceField -> start
                        is FocusPosition.EndField -> end
                        is FocusPosition.None -> NON_EXISTING_WORD
                    }.trim().lowercase()
                    it.name.lowercase().contains(whatToContain) or it.address.lowercase()
                        .contains(whatToContain)
                }

                if (result.isEmpty()) {

                }
                result
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())


    private val _loadResult = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val loadResult: MutableStateFlow<ModelsState>
        get() = _loadResult

    val loadResultAnimate = MutableStateFlow(false)

    private val _updateResult = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val updateResult: MutableStateFlow<ModelsState>
        get() = _updateResult

    val createdAnnounce = MutableStateFlow<Announce?>(null)

    var shouldFind = MutableStateFlow(false)

    fun resetFlows() {
        loadResultAnimate.value = false
        shouldFind.value = false
        isInTravel.value = false
        _loadResult.value = ModelsState.Loading
    }

    /**
     * Event calls on CreationScreen.
     */
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
                println("TAG_ROUT $route")
                val announce = Announce(
                    authorEmail = author.value,
                    placeFrom = sourceDestination.value,
                    placeTo = endDestination.value,
                    countOfParticipants =
                    if (countOfParticipants.value.isNotEmpty())
                        countOfParticipants.value.toInt()
                    else
                        0,
                    comment = comment.value,
                    startTime = startTime.value,
                    placeFromCoords = route.first.toStrLL(),
                    placeToCoords = route.second.toStrLL()
                )
                createAnnounceCall(announce = announce)
            }

            is CreationEvent.Action.OnAddressClicked -> {
                fillByClickedAddress(event.address, event.coordinates)
            }
            is CreationEvent.FieldEdit.CommentEdit -> {
                comment.value = event.comment
            }
            is CreationEvent.FieldEdit.StartTimeEdit -> {

//                startTime.value = LocalTime(event.hour, event.minute).toString()
                startTime.value = TimeUtil.dateFormatProcess(event.hour, event.minute)
            }
            is CreationEvent.FieldEdit.ChangeFocus -> {
                shouldFind.value = true
                editTextTap.value = event.focus
                println("ChangeFocus $shouldFind")
            }
            CreationEvent.Action.SaveEditResult -> {
                val announce = Announce(
                    authorEmail = author.value,
                    placeFrom = sourceDestination.value,
                    placeTo = endDestination.value,
                    countOfParticipants =
                    if (countOfParticipants.value.isNotEmpty())
                        countOfParticipants.value.toInt()
                    else
                        0,
                    comment = comment.value,
                    startTime = startTime.value,
                    placeFromCoords = route.first.toStrLL(),
                    placeToCoords = route.second.toStrLL()
                )
                updateAnnounceCall(announce)
            }
            is CreationEvent.Action.UpdateEditAnnounce -> {
                initializeUpdatingParams(event.announce)
            }
        }
    }

    /**
     * Fills the edit text values by clicked addresses tabs.
     */
    private fun fillByClickedAddress(address: String, coordinates: LatLng) {
        shouldFind.value = false
        when (editTextTap.value) {
            is FocusPosition.SourceField -> {
                sourceDestination.value = address
                route = route.copy(first = coordinates)
                println("TAG_ROUT 1 $route, $coordinates")
            }

            is FocusPosition.EndField -> {
                endDestination.value = address
                route = route.copy(second = coordinates)
                println("TAG_ROUT 2 $route, $coordinates")
            }

            is FocusPosition.None -> {
                //shouldFind = false

            }
        }

        if (route.first.lat != 0.0 && route.second.lat != 0.0) {
//            getTripInfoCall(route)
            price.value = 560
        }
    }


    val isInTravel = MutableStateFlow(false)

    fun disableInTravel() {
        isInTravel.value = false
    }

    fun disableUpdateState() {
        _updateResult.value = ModelsState.Loading
    }


    private fun updateAnnounceCall(announce: Announce) {
//        showProgress()
        disableUpdateState()
        viewModelScope.launch {
//            delay(1500L)
            _updateResult.value = ModelsState.Success(announce)
//            return@launch
            val result = updateAnnounce(announce)
            when (result) {
                is ServerResult.SuccessfulResult -> {
                    _updateResult.value = ModelsState.Success(result.model)
                }

                is ServerResult.UnsuccessfulResult -> {
                    _updateResult.value = if (result.error == BAD_REQUEST) {
                        ModelsState.Error("Неправильно введены значения.")
                    } else {
                        ModelsState.Error(result.error)
                    }

                }
                is ServerResult.ResultException -> {
                    _updateResult.value = ModelsState.Error(result.error ?: "Ошибка")
                }
            }

            hideProgress()
        }
    }

    /**
     * Sets the initial values to parameters.
     */
    private fun initializeUpdatingParams(announce: Announce) {
        val date = TimeUtil.fromStrToDate(announce.startTime ?: "").hour

        sourceDestination.value = announce.placeFrom
        endDestination.value = announce.placeTo
        countOfParticipants.value = announce.countOfParticipants.toString()
        comment.value = announce.comment
        startTime.value = "${date.hours}:${date.minutes}"

    }

    /**
     * Method that processes request for creating announcement on the server.
     */
    private fun createAnnounceCall(announce: Announce) {
        showProgress()

        viewModelScope.launch {
//            delay(2000L)
//            _loadResult.value = ModelsState.Success(announce)

            val res = getTripByEmail(EMAIL)
            println("TAG_OF_ISIN $res")
            if (res is ServerResult.SuccessfulResult) {
                isInTravel.value = true
                println("TAG_OF_ISIN")
                hideProgress()
                return@launch
            }
            println("TAG_OF_ISIN out")

            isInTravel.value = false
            val result = createAnnounce(announce)
            when (result) {
                is ServerResult.SuccessfulResult -> {
                    createdAnnounce.value = result.model
                    _loadResult.value = ModelsState.Success(result.model)
                }

                is ServerResult.UnsuccessfulResult -> {
                    _loadResult.value = if (result.error == BAD_REQUEST) {
                        ModelsState.Error("Неправильно введены значения.")
                    } else {
                        ModelsState.Error(result.error)
                    }

                }
                is ServerResult.ResultException -> {
                    _loadResult.value = ModelsState.Error(result.error ?: "Ошибка")
                }
            }
            hideProgress()
        }
    }

    /**
     * Gets info about trip with taxi API/
     */
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
                is ServerResult.ResultException -> {

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
        object SaveEditResult : FieldEdit()
        class UpdateEditAnnounce(val announce: Announce) : FieldEdit()
        class OnAddressClicked(val address: String, val coordinates: LatLng) : Action()
    }

    /**
     * Values in edittext changed.
     */
    sealed class FieldEdit : CreationEvent() {
        data class SourceEdit(val source: String) : FieldEdit()
        data class ChangeFocus(val focus: FocusPosition) : FieldEdit()
        data class EndEdit(val end: String) : FieldEdit()
        data class ParticipantsCountEdit(val count: String) : FieldEdit()
        data class CommentEdit(val comment: String) : FieldEdit()
        data class StartTimeEdit(val hour: Int, val minute: Int) : FieldEdit()

    }
}

sealed class FocusPosition {
    object SourceField : FocusPosition()
    object EndField : FocusPosition()
    object None : FocusPosition()
}