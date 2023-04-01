package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Constructing [ViewModel] for Android [Composable] screen.
 */
@Composable
inline fun <reified T : ViewModel> setupViewModel(): T {
    return viewModel<T>(factory = NoArgsViewModelFactory())
}