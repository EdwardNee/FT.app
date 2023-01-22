package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IAnnouncementRepository

class ServerAnnouncementRepository constructor(private val api: Api): IAnnouncementRepository {
    override suspend fun getAvailableAnnouncements() {
//        return api.getAnnouncements()
    }
}