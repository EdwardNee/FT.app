package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import app.ft.ftapp.domain.usecase.CreateAnnouncementUseCase
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

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
                createAnnounce(announce = announce)
            }
        }
    }

    private fun createAnnounce(announce: Announce) {
        showProgress()
        viewModelScope.launch {
            println("TAG_CREATED $announce")
            delay(8000L)
            createAnnounce(announce)
            hideProgress()
        }
    }
}

sealed class CreationEvent {
    sealed class Action : CreationEvent() {
        object OnPublish : Action()
    }

    sealed class FieldEdit : CreationEvent() {
        data class SourceEdit(val source: String) : FieldEdit()
        data class EndEdit(val end: String) : FieldEdit()
        data class ParticipantsCountEdit(val count: Int) : FieldEdit()
    }

}