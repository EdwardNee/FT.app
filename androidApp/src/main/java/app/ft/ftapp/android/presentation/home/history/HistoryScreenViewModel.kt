package app.ft.ftapp.android.presentation.home.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

/**
 * ViewModel in Android module for HistoryScreen.
 */
class HistoryScreenViewModel : ViewModel() {
    val pagerHistory = Pager(
        PagingConfig(pageSize = HistoryPageSource.PAGE_SIZE)
    ) {
        HistoryPageSource()
    }.flow.cachedIn(viewModelScope)
}