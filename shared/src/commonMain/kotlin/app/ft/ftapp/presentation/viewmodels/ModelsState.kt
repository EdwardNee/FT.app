package app.ft.ftapp.presentation.viewmodels

/**
 * State of loading in viewModels.
 */
sealed class ModelsState {
    object Loading: ModelsState()
    class Error(val message: String): ModelsState()
    object Success: ModelsState()
}