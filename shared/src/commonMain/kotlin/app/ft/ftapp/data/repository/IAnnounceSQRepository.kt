package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Announce

/**
 * Announcements Db repository.
 */
interface IAnnounceSQRepository {
    /**
     * Inserts [Announce] to db.
     */
    suspend fun insertAnnounceToDb(announce: Announce)

    /**
     * Returns all [Announce]'s from db.
     */
    suspend fun getAllAnnouncesFromDb(): List<Announce>

    /**
     * Returns all [Announce] by a given [id].
     */
    suspend fun getAnnounceByIdFromDb(id: Int): Announce

    /**
     * Deletes all [Announce] by a given [id].
     */
    suspend fun deleteAnnounceById(id: Int)

    /**
     * Returns all [Announce].
     */
    suspend fun deleteAllAnnouncesFromDb()
}