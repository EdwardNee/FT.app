package app.ft.ftapp.presentation.viewmodels

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreationViewModel : BaseViewModel() {
    fun createAnnounce() {
        showProgress()
        viewModelScope.launch {
            delay(8000L)
            hideProgress()
        }
    }
}