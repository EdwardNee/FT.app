package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.data.converters.CodeResponse.NOT_FOUND
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import app.ft.ftapp.domain.usecase.server.DeleteAnnounceUseCase
import app.ft.ftapp.domain.usecase.server.GetAnnounceByEmailUseCase
import app.ft.ftapp.domain.usecase.server.GetOutOfTravelUseCase
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
    //endregion

    private val _assignedAnnounce = MutableStateFlow<Announce?>(null)
    val assignedAnnounce: StateFlow<Announce?>
        get() = _assignedAnnounce.asStateFlow()

    private val _uiState = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val uiState: StateFlow<ModelsState>
        get() = _uiState.asStateFlow()

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

    private fun deleteAnnounceById(travelId: Long) {
        viewModelScope.launch {
            println("TAG_OF_DELETE ${deleteTravel(travelId)}")
            getAnnounceByEmailCall(EMAIL)
        }
    }

    private fun leaveAnnounce(travelId: Long) {
        viewModelScope.launch {
            println("TAG_OF_LEAVE ${getOutOfTravel(TravelerUser(travelId, EMAIL))}")
            getAnnounceByEmailCall(EMAIL)
        }
    }
}

//sealed interface ModelsState {
//    object Loading : ModelsState
//    object NoData : ModelsState
//    data class Success<T>(val dataResult: T) : ModelsState
//    data class Error(val message: String) : ModelsState
//}

/**
 * HomeScreen events class.
 */
sealed class HomeEvent {
    class GetAnnounceByEmail(val email: String) : HomeEvent()
    class DeleteAnnounce(val travelId: Long) : HomeEvent()
    class LeaveAnnounce(val travelId: Long) : HomeEvent()
}