package app.ft.ftapp.presentation.viewmodels

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreationViewModel : BaseViewModel() {
    fun createAnnounce() {
        viewModelScope.launch {
            showProgress()
            delay(8000L)
            hideProgress()
        }
    }
}