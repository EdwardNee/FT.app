package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.utils.PreferencesHelper

/**
 * ViewModel for AnnouncementDetails screen.
 */
class DetailsViewModel(private val preferencesHelper: PreferencesHelper) : BaseViewModel() {
    private var announceId: String? = null

    /**
     * Event UI calls.
     */
    fun onEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.ClearData -> clearData()
            DetailsEvent.OnGetPref -> getFromPrefs()
        }
    }

    /**
     * Gets announce id from Preferences.
     */
    private fun getFromPrefs() {
        announceId = preferencesHelper.chosenDetailId
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