package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.usecase.db.GetParticipantsByAnnounceIdUseCase
import app.ft.ftapp.domain.usecase.db.InsertAnnounceToDbUseCase
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
    val insertToDbParticipantUseCase: InsertParticipantToDbUseCase by kodein.instance()
    val insertAnnounceToDbUseCase: InsertAnnounceToDbUseCase by kodein.instance()

    private val _historyList = MutableStateFlow(emptyList<Announce>())
    val historyList: StateFlow<List<Announce>>
        get() = _historyList.asStateFlow()


    init {
        viewModelScope.launch {
            historyList.collectLatest {
                onEvent(HistoryEvent.InsertToDb(it))
            }
        }
    }

    /**
     * Method to call events.
     */
    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.InsertToDb -> {
                insertToDbCall(event.announces)
            }
            is HistoryEvent.GetParticipantsId -> {
                getParticipantsById(event.id)
            }
        }
    }

    /**
     * Sets list of announces to the flow value.
     */
    fun setList(list: List<Announce>) {
        val lst = mutableListOf<Announce>()
        lst.addAll(list)
        _historyList.value = lst
    }

    /**
     * Inserts given [announces] to DB with participants in them.
     */
    private fun insertToDbCall(announces: List<Announce>) {
        viewModelScope.launch {
            announces.forEach { announce ->
                insertAnnounceToDbUseCase(announce)
                announce.participants?.forEach { participant ->
                    insertToDbParticipantUseCase(participant)
                }
            }
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
    class InsertToDb(val announces: List<Announce>) : HistoryEvent()
    class GetParticipantsId(val id: Long) : HistoryEvent()
}