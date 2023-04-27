package app.ft.ftapp.android.presentation.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import app.ft.ftapp.android.presentation.announcement.AnnouncePageSource.Companion.PAGE_SIZE
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.presentation.viewmodels.AnnouncesViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Screen for Announces android viewmodel.
 */
class AnnounceScreenViewModel(private val viewModel: AnnouncesViewModel) : ViewModel() {
    val pagerAnnounces = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        AnnouncePageSource()
    }.flow/*stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        PagingData.empty()
    ).flow*/.cachedIn(viewModelScope)//

    init {
        viewModelScope.launch {
//            pagerAnnounces
//                .collect {
//                val list = mutableListOf<Announce>()
//                it.map { an ->
//                    list.add(an)
//                }
//                viewModel.setList(list)
//            }
        }
    }
}