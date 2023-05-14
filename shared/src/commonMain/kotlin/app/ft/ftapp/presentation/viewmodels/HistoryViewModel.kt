package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import app.ft.ftapp.domain.usecase.db.GetParticipantsByAnnounceIdUseCase
import app.ft.ftapp.domain.usecase.db.InsertParticipantToDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * History screen viewmodel.
 */
class HistoryViewModel : BaseViewModel() {

    val getParticipantByIdFromDb: GetParticipantsByAnnounceIdUseCase by kodein.instance()
    val insertToDbUseCase: InsertParticipantToDbUseCase by kodein.instance()


    private val _historyList = MutableStateFlow(emptyList<Announce>())
    val historyList: StateFlow<List<Announce>>
        get() = _historyList.asStateFlow()


    init {
        viewModelScope.launch {
            historyList.collectLatest {
                it.forEach { announce ->
                    onEvent(HistoryEvent.InsertToDb(announce.participants ?: emptyList()))
                }

            }
        }
    }

    /**
     * Method to call events.
     */
    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.InsertToDb -> {
                event.participants.forEach { participant ->
                    insertToDbCall(participant)
                }
            }
            is HistoryEvent.GetParticipantsId -> {
                getParticipantsById(event.id)
            }
        }
    }

    fun setList(list: List<Announce>) {
        val lst = mutableListOf<Announce>()
        lst.addAll(list)
        _historyList.value = lst
    }

    /**
     * Inserts given [participant] to DB.
     */
    private fun insertToDbCall(participant: Participant) {
        viewModelScope.launch {
            insertToDbUseCase(participant)
        }
    }

    /**
     * Returns participants in a given [announceId].
     */
    private fun getParticipantsById(announceId: Long) {
        viewModelScope.launch {
            val result = getParticipantByIdFromDb(announceId.toInt())
        }
    }
}

/**
 * Types of Actions for History screen.
 */
open class HistoryEvent {
    class InsertToDb(val participants: List<Participant>) : HistoryEvent()
    class GetParticipantsId(val id: Long) : HistoryEvent()
}