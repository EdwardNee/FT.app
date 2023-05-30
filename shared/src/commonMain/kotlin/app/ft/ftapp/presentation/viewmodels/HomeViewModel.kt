package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.data.converters.CodeResponse.NOT_FOUND
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import app.ft.ftapp.domain.usecase.server.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

class HomeViewModel : BaseViewModel() {
    //region DI
    private val getAnnounceByEmail: GetAnnounceByEmailUseCase by kodein.instance()
    private val deleteTravel: DeleteAnnounceUseCase by kodein.instance()
    private val getOutOfTravel: GetOutOfTravelUseCase by kodein.instance()
    private val startTravel: StartTravelUseCase by kodein.instance()
    private val stopTravel: StopTravelUseCase by kodein.instance()
    //endregion

    private val _assignedAnnounce = MutableStateFlow<Announce?>(null)
    val assignedAnnounce: StateFlow<Announce?>
        get() = _assignedAnnounce.asStateFlow()

    private val _uiState = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val uiState: StateFlow<ModelsState>
        get() = _uiState.asStateFlow()

    val isDialogShowing = MutableStateFlow(false)
    val isDialogStopShowing = MutableStateFlow(false)

    init {
        onEvent(HomeEvent.GetAnnounceByEmail(EMAIL))
    }

    /**
     * Event call for HomeScreen.
     */
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAnnounceByEmail -> {
                getAnnounceByEmailCall(event.email)
            }
            is HomeEvent.DeleteAnnounce -> {
                deleteAnnounceById(event.travelId)
            }
            is HomeEvent.LeaveAnnounce -> {
                leaveAnnounce(event.travelId)
            }
            is HomeEvent.ShowDialogStart -> {
                changeDialogValue(event.flag)
            }
            is HomeEvent.ShowDialogStop -> {
                changeDialogStopValue(event.flag)
            }
            is HomeEvent.StartAnnounce -> {
                startTravelCall()
            }
            HomeEvent.StopAnnounce -> {
                stopTravelCall()
            }
        }
    }


    /**
     * Changes flag to show dialog.
     */
    private fun changeDialogValue(flag: Boolean) {
        isDialogShowing.value = flag
    }

    /**
     * Changes flag to show dialog.
     */
    private fun changeDialogStopValue(flag: Boolean) {
        isDialogStopShowing.value = flag
    }

    /**
     * Calls startTravel usecase to start the travel.
     */
    private fun startTravelCall() {
        viewModelScope.launch {
            val result = startTravel(assignedAnnounce.value?.id?.toLong() ?: return@launch)

            when (result) {
                is ServerResult.ResultException -> {}
                is ServerResult.SuccessfulResult -> {}
                is ServerResult.UnsuccessfulResult -> {}
            }

            getAnnounceByEmailCall(EMAIL)
        }
    }

    /**
     * Calls stopTravel usecase to start the travel.
     */
    private fun stopTravelCall() {
        viewModelScope.launch {
            val result = stopTravel(assignedAnnounce.value?.id?.toLong() ?: return@launch)

            when (result) {
                is ServerResult.ResultException -> {}
                is ServerResult.SuccessfulResult -> {}
                is ServerResult.UnsuccessfulResult -> {}
            }

            getAnnounceByEmailCall(EMAIL)
        }
    }

    /**
     * Gets announce from the server by a particular user.
     */
    private fun getAnnounceByEmailCall(email: String) {

        showProgress()
        if (_uiState.value !is ModelsState.Success<*>) {
            _uiState.value = ModelsState.Loading
        }

        viewModelScope.launch {
//            delay(500L)
//            _uiState.value = HomeModelState.Success(Announce())

            val result = getAnnounceByEmail(email)

            when (result) {
                is ServerResult.SuccessfulResult -> {
                    _assignedAnnounce.value = result.model
                    _uiState.value = ModelsState.Success(result.model)
                }
                is ServerResult.UnsuccessfulResult -> {
                    _uiState.value = if (result.error == NOT_FOUND) {
                        ModelsState.NoData
                    } else {
                        ModelsState.Error(result.error)
                    }
                }
                is ServerResult.ResultException -> {
                    _uiState.value = ModelsState.Error(result.error ?: "Ошибка")
                }
            }
            hideProgress()
        }
    }

    /**
     * Deletes given announce by its [travelId]
     */
    private fun deleteAnnounceById(travelId: Long) {
        showProgress()
        viewModelScope.launch {
            println("TAG_OF_DELETE ${deleteTravel(travelId)}")
            getAnnounceByEmailCall(EMAIL)
        }
    }

    /**
     * Method calls getOutOfTravel usecase to leave the travel.
     */
    private fun leaveAnnounce(travelId: Long) {
        showProgress()
        viewModelScope.launch {
            println("TAG_OF_LEAVE ${getOutOfTravel(TravelerUser(travelId, EMAIL))}")
            getAnnounceByEmailCall(EMAIL)
        }
    }
}

/**
 * HomeScreen events class.
 */
sealed class HomeEvent {
    class GetAnnounceByEmail(val email: String) : HomeEvent()
    class DeleteAnnounce(val travelId: Long) : HomeEvent()
    class LeaveAnnounce(val travelId: Long) : HomeEvent()
    object StartAnnounce : HomeEvent()
    object StopAnnounce : HomeEvent()
    class ShowDialogStart(val flag: Boolean) : HomeEvent()
    class ShowDialogStop(val flag: Boolean) : HomeEvent()
}