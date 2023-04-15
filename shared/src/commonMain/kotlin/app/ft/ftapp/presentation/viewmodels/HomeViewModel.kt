package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.usecase.GetAnnounceByEmailUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

class HomeViewModel : BaseViewModel() {
    //region DI
    private val getAnnounceByEmail: GetAnnounceByEmailUseCase by kodein.instance()
    //endregion

    private val _assignedAnnounce = MutableStateFlow<Announce?>(null)
    val assignedAnnounce: StateFlow<Announce?>
        get() = _assignedAnnounce.asStateFlow()

    private val _uiState = MutableStateFlow<HomeModelState>(HomeModelState.Loading)
    val uiState: StateFlow<HomeModelState>
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
        }
    }

    /**
     * Gets announce from the server by a particular user.
     */
    private fun getAnnounceByEmailCall(email: String) {
        viewModelScope.launch {
            delay(500L)
            _uiState.value = HomeModelState.Success(Announce())
//            val result = getAnnounceByEmail(email)
//
//            when (result) {
//                is ServerResult.SuccessfulResult -> {
//                    _assignedAnnounce.value = result.model
//                    uiState.value = HomeModelState.Success(result.model)
//                }
//                is ServerResult.UnsuccessfulResult -> {
//                    uiState.value = HomeModelState.Error(result.error)
//                }
//            }
        }
    }
}

sealed interface HomeModelState {
    object Loading : HomeModelState
    data class Success<T>(val dataResult: T) : HomeModelState
    data class Error(val message: String) : HomeModelState
}

/**
 * HomeScreen events class.
 */
sealed class HomeEvent {
    class GetAnnounceByEmail(val email: String) : HomeEvent()
}