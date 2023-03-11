package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

interface IAnnouncementRepository {
    suspend fun getAvailableAnnouncements(): ServerResult<List<Announce>>
    suspend fun createAnnounce(announce: Announce)
    suspend fun updateAnnounce(announce: Announce)
    suspend fun deleteAnnounce(announce: Announce)
}