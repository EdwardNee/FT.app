package app.ft.ftapp.android.presentation.announcement

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.toAnnounce
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import org.kodein.di.instance

/**
 * Paging source to load [Announce].
 */
class AnnouncePageSource : PagingSource<Int, Announce>() {
    private val kodein = DIFactory.di
    private val getAnnouncements: GetAnnouncementsUseCase by kodein.instance()

    override fun getRefreshKey(state: PagingState<Int, Announce>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Announce> {
        val page = (params.key ?: 0) // if null we are at the init pos
        val pageSize = params.loadSize.coerceAtMost(PAGE_SIZE)

        val response = getAnnouncements(page, PAGE_SIZE) /*page, 10*/

        return when (response) {
            is ServerResult.SuccessfulResult -> {
                val prevKey = if (page == 0) null else page - 1

                val nextKey =
                    if (response.model.size <= pageSize) null
                    else page + 1
                LoadResult.Page(response.model.toAnnounce(), prevKey = prevKey, nextKey = nextKey)
            }

            is ServerResult.UnsuccessfulResult -> {
                LoadResult.Error(IllegalArgumentException(response.error))
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}