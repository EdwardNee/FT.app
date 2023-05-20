package app.ft.ftapp.android.presentation.home.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.ft.ftapp.presentation.viewmodels.HistoryViewModel

/**
 * ViewModel in Android module for HistoryScreen.
 */
class HistoryScreenViewModel(viewModel: HistoryViewModel) : ViewModel() {
    val pagerHistory = Pager(
        PagingConfig(pageSize = HistoryPageSource.PAGE_SIZE)
    ) {
        HistoryPageSource()
    }.flow.cachedIn(viewModelScope)
}