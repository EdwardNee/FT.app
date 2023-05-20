package app.ft.ftapp.android.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import app.ft.ftapp.android.presentation.announcement.AnnounceScreenViewModel
import app.ft.ftapp.android.presentation.creation.CreationScreenViewModel
import app.ft.ftapp.android.presentation.groupchat.ChatScreenViewModel
import app.ft.ftapp.android.presentation.home.history.HistoryScreenViewModel
import app.ft.ftapp.presentation.viewmodels.MainActivityViewModel
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
            ChatViewModel::class.java -> ChatViewModel()
            MainActivityViewModel::class.java -> MainActivityViewModel()
            HistoryViewModel::class.java -> HistoryViewModel()

            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}


/**
 * Argument factory for applications viewModels.
 */
class ArgsViewModelFactory<T>(private val args: FactoryArgs<T>) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        when (modelClass) {
            AnnounceScreenViewModel::class.java -> AnnounceScreenViewModel(args.vm!! as AnnouncesViewModel)
            ChatScreenViewModel::class.java -> ChatScreenViewModel(args.vm!! as ChatViewModel)
            CreationScreenViewModel::class.java -> CreationScreenViewModel(args.vm!! as CreationViewModel)
            HistoryScreenViewModel::class.java -> HistoryScreenViewModel(args.vm!! as HistoryViewModel)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}

data class FactoryArgs<T>(val vm: T? = null)