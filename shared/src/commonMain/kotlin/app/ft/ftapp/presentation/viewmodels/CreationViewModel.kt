package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.CreateAnnouncementUseCase
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * [BaseViewModel] inherited. ViewModel for creating announcements screen.
 */
class CreationViewModel : BaseViewModel() {
    //region di
    private val createAnnounce: CreateAnnouncementUseCase by kodein.instance()
    //endregion

    val author = MutableStateFlow<String>("").cMutableStateFlow()
    val email = MutableStateFlow<String>("").cMutableStateFlow()
    val sourceDestination = MutableStateFlow<String>("").cMutableStateFlow()
    val endDestination = MutableStateFlow<String>("").cMutableStateFlow()

    //   val price = MutableStateFlow<Double>(0.0).cMutableStateFlow()
    val participants = MutableStateFlow<List<Participant>>(emptyList()).cMutableStateFlow()
    val countOfParticipants = MutableStateFlow<Int>(0).cMutableStateFlow()

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
                    author = "",
                    email = "",
                    placeFrom = sourceDestination.value,
                    placeTo = endDestination.value,
                    participants = emptyList(),
                    countOfParticipants = countOfParticipants.value
                )
                createAnnounceCall(announce = announce)
            }
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
    }

    /**
     * Values in edittext changed.
     */
    sealed class FieldEdit : CreationEvent() {
        data class SourceEdit(val source: String) : FieldEdit()
        data class EndEdit(val end: String) : FieldEdit()
        data class ParticipantsCountEdit(val count: Int) : FieldEdit()
    }

}