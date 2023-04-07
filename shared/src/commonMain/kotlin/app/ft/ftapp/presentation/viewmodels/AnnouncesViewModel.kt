package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import app.ft.ftapp.utils.PreferencesHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * ViewModel for Announces screen list.
 */
class AnnouncesViewModel(private val preferencesHelper: PreferencesHelper) : BaseViewModel() {
    private val getAnnouncements: GetAnnouncementsUseCase by kodein.instance()

    private val _announcesList = MutableStateFlow(emptyList<Announce>())
    val announcesList: StateFlow<List<Announce>>
        get() = _announcesList.asStateFlow()

    init {
        onEvent(AnnounceListEvent.GetAnnounces)
    }

    fun onEvent(event: AnnounceListEvent) {
        when (event) {
            AnnounceListEvent.GetAnnounces -> {
                getAnnounces()
            }
            is AnnounceListEvent.OnDetails -> {
                preferencesHelper.chosenDetailId = event.announceId
                event.onAction()
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
}

/**
 * Actions event on the announcement list screen.
 */
sealed class AnnounceListEvent {
    object GetAnnounces : AnnounceListEvent()
    class OnDetails(val announceId: String, val onAction: () -> Unit) : AnnounceListEvent()
}