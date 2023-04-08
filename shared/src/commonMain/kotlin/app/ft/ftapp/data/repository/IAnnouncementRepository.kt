package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Repository to process data from the server.
 */
interface IAnnouncementRepository {
    /**
     * Gets all the announces from the server.
     */
    suspend fun getAvailableAnnouncements(): ServerResult<List<Announce>>

    /**
     * POSTs [Announce] to the server.
     */
    suspend fun createAnnounce(announce: Announce): ServerResult<Announce>

    /**
     * UPDATEs existing [Announce].
     */
    suspend fun updateAnnounce(announce: Announce)

    /**
     * DELETEs given [Announce].
     */
    suspend fun deleteAnnounce(announce: Announce)
}