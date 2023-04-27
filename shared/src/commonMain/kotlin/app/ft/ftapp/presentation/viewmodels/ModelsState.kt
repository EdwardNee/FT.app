package app.ft.ftapp.presentation.viewmodels

/**
 * State of loading in viewModels.
 */
sealed class ModelsState {
    object Loading: ModelsState()
    object NoData: ModelsState()
    class Error(val message: String): ModelsState()
    data class Success<T>(val dataResult: T): ModelsState()
}