package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import app.ft.ftapp.domain.models.toAnnounce
import app.ft.ftapp.domain.usecase.BecomeTravelerUseCase
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import app.ft.ftapp.domain.usecase.db.GetAllAnnouncesFromDb
import app.ft.ftapp.domain.usecase.db.InsertAnnounceToDbUseCase
import app.ft.ftapp.utils.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * ViewModel for Announces screen list.
 */
class AnnouncesViewModel(private val preferencesHelper: PreferencesHelper) : BaseViewModel() {
    //region DI
    private val getAnnouncements: GetAnnouncementsUseCase by kodein.instance()
    private val getAnnouncementsDb: GetAllAnnouncesFromDb by kodein.instance()
    private val insertAnnounceToDb: InsertAnnounceToDbUseCase by kodein.instance()
    private val becomeTraveler: BecomeTravelerUseCase by kodein.instance()
    //endregion

    private val _announcesList = MutableStateFlow(emptyList<Announce>())
    val announcesList: StateFlow<List<Announce>>
        get() = _announcesList.asStateFlow()

    init {
        viewModelScope.launch {
//            val result = getAnnouncementsDb()
//
//            if (result.isEmpty()) {
//                onEvent(AnnounceListEvent.GetAnnounces)
//            } else {
//                _announcesList.value = result
//            }

            announcesList.collectLatest {
                onEvent(AnnounceListEvent.InsertToDb(it))
            }
        }
    }

    fun setList(list: List<Announce>) {
        val lst = mutableListOf<Announce>()
        lst.addAll(list)
        _announcesList.value = lst
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
//                event.onAction()
            }
            is AnnounceListEvent.InsertToDb -> {
                insertAnnouncesToDbCall(event.announces)
            }
            is AnnounceListEvent.OnBecomeTraveler -> {
                becomeTravelerCall(event.travelId)
            }
        }
    }

    /**
     * Gets list of announces from the server application.
     */
    private fun getAnnounces() {
        showProgress()
        viewModelScope.launch {
            val result = getAnnouncements(0, 10)
            result.let {
                when (it) {
                    is ServerResult.SuccessfulResult -> {
                        _announcesList.value = it.model.toAnnounce()
                    }
                    is ServerResult.UnsuccessfulResult -> {
                        println("TAG_OF_RES, ${it.error}")
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

    private fun becomeTravelerCall(travelId: Long) {
        viewModelScope.launch {
            val result = becomeTraveler(TravelerUser(travelId, EMAIL))

            when(result) {

                is ServerResult.SuccessfulResult -> {
                    println("TAG_OF_RES ${result.model}")
                }
                is ServerResult.UnsuccessfulResult -> {
                    println("TAG_OF_RES ${result.error}")
                }
            }
        }
    }
}

/**
 * Actions event on the announcement list screen.
 */
sealed class AnnounceListEvent {
    object GetAnnounces : AnnounceListEvent()
    class OnBecomeTraveler(val travelId: Long) : AnnounceListEvent()
    class InsertToDb(val announces: List<Announce>) : AnnounceListEvent()
    class OnDetails(val announceId: String, val onAction: () -> Unit) : AnnounceListEvent()
}