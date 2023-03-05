package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

class ServerAnnouncementRepository constructor(private val api: Api): IAnnouncementRepository {
    override suspend fun getAvailableAnnouncements(): ServerResult<List<Announce>> {
        return api.getAnnouncements().await<List<Announce>>()
    }
}