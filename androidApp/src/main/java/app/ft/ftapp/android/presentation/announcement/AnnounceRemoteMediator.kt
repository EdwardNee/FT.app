package app.ft.ftapp.android.presentation.announcement

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.usecase.db.InsertAnnounceToDbUseCase
import db.AnnounceSQ
import org.kodein.di.instance



@OptIn(ExperimentalPagingApi::class)
class AnnounceRemoteMediator: RemoteMediator<Int, AnnounceSQ>() {
    private val kodein = DIFactory.di
    private val insertAnnounceToDb: InsertAnnounceToDbUseCase by kodein.instance()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnnounceSQ>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}