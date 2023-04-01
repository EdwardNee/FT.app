package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import app.ft.ftapp.presentation.viewmodels.CreationViewModel

/**
 * No-argument factory for applications viewModels.
 */
class NoArgsViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        when (modelClass) {
            CreationViewModel::class.java -> CreationViewModel()
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}