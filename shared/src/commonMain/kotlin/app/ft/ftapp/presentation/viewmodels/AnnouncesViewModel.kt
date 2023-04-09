package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import app.ft.ftapp.domain.usecase.db.GetAllAnnouncesFromDb
import app.ft.ftapp.domain.usecase.db.InsertAnnounceToDbUseCase
import app.ft.ftapp.utils.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * ViewModel for Announces screen list.
 */
class AnnouncesViewModel(private val preferencesHelper: PreferencesHelper) : BaseViewModel() {
    private val getAnnouncements: GetAnnouncementsUseCase by kodein.instance()
    private val getAnnouncementsDb: GetAllAnnouncesFromDb by kodein.instance()
    private val insertAnnounceToDb: InsertAnnounceToDbUseCase by kodein.instance()

    private val _announcesList = MutableStateFlow(emptyList<Announce>())
    val announcesList: StateFlow<List<Announce>>
        get() = _announcesList.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getAnnouncementsDb()

            if (result.isEmpty()) {
                onEvent(AnnounceListEvent.GetAnnounces)
            } else {
                _announcesList.value = result
            }
        }
    }

    /**
     * AnnounceScreen OnEvent calls.
     */
    fun onEvent(event: AnnounceListEvent) {
        when (event) {
            AnnounceListEvent.GetAnnounces -> {
                getAnnounces()
            }
            is AnnounceListEvent.OnDetails -> {
                preferencesHelper.chosenDetailId = event.announceId
                event.onAction()
            }
            is AnnounceListEvent.InsertToDb -> {
                insertAnnouncesToDbCall(event.announces)
            }
        }
    }

    /**
     * Gets list of announces from the server application.
     */
    private fun getAnnounces() {
        showProgress()
        viewModelScope.launch {
            val result = getAnnouncements()
            result.let {
                when (it) {
                    is ServerResult.SuccessfulResult -> {
                        _announcesList.value = it.model
                    }
                    is ServerResult.UnsuccessfulResult -> {
                        println("TAG_ERROR, ${it.error}")
                    }
                }
            }

            hideProgress()
        }
    }

    /**
     * Inserts to db list of [announces].
     */
    private fun insertAnnouncesToDbCall(announces: List<Announce>) {
        viewModelScope.launch {
            announces.forEach { announce ->
                insertAnnounceToDb(announce)
            }
        }
    }
}

/**
 * Actions event on the announcement list screen.
 */
sealed class AnnounceListEvent {
    object GetAnnounces : AnnounceListEvent()
    class InsertToDb(val announces: List<Announce>) : AnnounceListEvent()
    class OnDetails(val announceId: String, val onAction: () -> Unit) : AnnounceListEvent()
}