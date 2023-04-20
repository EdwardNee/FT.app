package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.di.DIFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.*

/**
 * Base [ViewModel].
 */
abstract class BaseViewModel : ViewModel() {
    /**
     * DI object instance.
     */
    protected val kodein = DIFactory.di


    /**
     * Private progress showing indicator.
     */
    private val isShowProgressMutable = MutableStateFlow<Boolean>(false)

    /**
     * Public progress showing indicator.
     */
    val isShowProgress: StateFlow<Boolean>
        get() = isShowProgressMutable.asStateFlow()

    /**
     * Emits value to flow to show progress loading.
     */
    fun showProgress() {
        isShowProgressMutable.value = true
    }

    /**
     * Emits value to flow to stop showing progress loading.
     */
    fun hideProgress() {
        isShowProgressMutable.value = false
    }
}