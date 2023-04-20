package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.usecase.db.GetAllAnnouncesFromDb
import app.ft.ftapp.domain.usecase.db.GetAnnounceFromDbUseCase
import app.ft.ftapp.utils.PreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * ViewModel for AnnouncementDetails screen.
 */
class DetailsViewModel(private val preferencesHelper: PreferencesHelper) : BaseViewModel() {
    private val getAnnounceFromDb: GetAnnounceFromDbUseCase by kodein.instance()
    private val getAllFromDb: GetAllAnnouncesFromDb by kodein.instance()

    private var announceId: String? = null

    private val _announceDb = MutableStateFlow<Announce?>(null)
    val announceDb: StateFlow<Announce?>
        get() = _announceDb.asStateFlow()

    /**
     * Event UI calls.
     */
    fun onEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.ClearData -> clearData()
            DetailsEvent.OnGetPref -> {
                viewModelScope.launch {
                    getFromPrefs()
                    _announceDb.value = getAnnounceFromDb(announceId?.toInt() ?: return@launch)
                }
            }
        }
    }

    /**
     * Gets announce id from Preferences.
     */
    private suspend fun getFromPrefs() {
        announceId = preferencesHelper.chosenDetailId
        println("TAG_OF_REF $announceId;;;; ${getAllFromDb.invoke()}")
    }

    /**
     * Clears all the data in the bottomsheet.
     */
    private fun clearData() {
        announceId = null
        preferencesHelper.chosenDetailId = null
    }
}

/**
 * Event class for AnnouncementDetails screen.
 */
sealed class DetailsEvent {
    object OnGetPref : DetailsEvent()
    object ClearData : DetailsEvent()
}