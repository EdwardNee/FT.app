package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

interface IAnnouncementRepository {
    suspend fun getAvailableAnnouncements(): ServerResult<List<Announce>>
}