package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.*

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
     * Starts the given travel by [travelId].
     */
    suspend fun startTravel(travelId: Long): ServerResult<Announce>

    /**
     * POSTs new user to become a traveler.
     */
    suspend fun becomeTraveler(travelerUser: TravelerUser): ServerResult<Announce>

    /**
     * GETs announce for user by a given [email].
     */
    suspend fun getAnnounceByEmail(email: String): ServerResult<Announce>

    /**
     * User leaves the travel
     */
    suspend fun getOutOfTravel(data: TravelerUser): ServerResult<Announce>

    /**
     * Registers user in the system.
     */
    suspend fun registerUser(user: RegisterUser): ServerResult<RegisterUser>
}