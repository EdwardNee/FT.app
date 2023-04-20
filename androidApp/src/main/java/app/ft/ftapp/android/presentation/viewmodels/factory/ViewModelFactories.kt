package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import app.ft.ftapp.android.presentation.announcement.AnnounceScreenViewModel
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.presentation.viewmodels.*
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
            HomeViewModel::class.java -> HomeViewModel()
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}


/**
 * Argument factory for applications viewModels.
 */
class ArgsViewModelFactory<T>(private val args: FactoryArgs<T>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        when (modelClass) {
            AnnounceScreenViewModel::class.java -> AnnounceScreenViewModel(args.vm!! as AnnouncesViewModel)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}

data class FactoryArgs<T>(val vm: T? = null)