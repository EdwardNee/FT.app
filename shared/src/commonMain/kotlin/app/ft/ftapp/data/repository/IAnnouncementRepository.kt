package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import kotlinx.serialization.json.JsonObject

/**
 * Repository to process data from the server.
 */
interface IAnnouncementRepository {
    /**
     * Gets all the announces from the server.
     */
    suspend fun getAvailableAnnouncements(offset: Int, limit: Int): ServerResult<PagingAnnounce>

    /**
     * Gets history announces from the server for current [authorMail].
     */
    suspend fun getHistoryAnnouncements(
        offset: Int,
        limit: Int,
        authorMail: String
    ): ServerResult<PagingAnnounce>

    /**
     * POSTs [Announce] to the server.
     */
    suspend fun createAnnounce(announce: Announce): ServerResult<Announce>

    /**
     * UPDATEs existing [Announce].
     */
    suspend fun updateAnnounce(announce: Announce): ServerResult<Announce>

    /**
     * DELETEs given [Announce].
     */
    suspend fun deleteAnnounce(travelId: Long): ServerResult<Int>

    /**
     * POSTs new user to become a traveler.
     */
    suspend fun becomeTraveler(travelerUser: TravelerUser): ServerResult<Announce>

    /**
     * GETs announce for user by a given [email].
     */
    suspend fun getAnnounceByEmail(email: String): ServerResult<Announce>
    suspend fun getOutOfTravel(data: TravelerUser): ServerResult<Announce>
}