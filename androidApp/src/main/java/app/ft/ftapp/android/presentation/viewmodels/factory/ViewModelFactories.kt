package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.presentation.viewmodels.AnnouncesViewModel
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.DetailsViewModel
import app.ft.ftapp.utils.PreferencesHelper
import org.kodein.di.instance

/**
 * No-argument factory for applications viewModels.
 */
class NoArgsViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    private val kodein = DIFactory.di

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        when (modelClass) {
            CreationViewModel::class.java -> CreationViewModel()
            AnnouncesViewModel::class.java -> {
                val preferences: PreferencesHelper by kodein.instance()
                AnnouncesViewModel(preferences)
            }
            DetailsViewModel::class.java -> {
                val preferences: PreferencesHelper by kodein.instance()
                DetailsViewModel(preferences)
            }
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}
