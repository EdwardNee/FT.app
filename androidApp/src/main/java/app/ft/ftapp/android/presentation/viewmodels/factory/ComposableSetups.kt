package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Constructing [ViewModel] for Android [Composable] screen.
 */
@Composable
inline fun <reified T : ViewModel> setupViewModel(factory: ViewModelProvider.Factory = NoArgsViewModelFactory()): T {
    return viewModel<T>(factory = factory)
}