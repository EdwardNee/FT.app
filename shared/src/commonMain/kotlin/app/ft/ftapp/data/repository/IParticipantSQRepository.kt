package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.Participant

/**
 * Local db participants repository.
 */
interface IParticipantSQRepository {
    /**
     * Inserts [Participant] to db.
     */
    suspend fun insertParticipantToDb(participant: Participant)

    /**
     * Returns all [Participant]'s from db.
     */
    suspend fun getAllParticipantsFromDb(): List<Participant>

    /**
     * Returns all [Participant] by a given [id].
     */
    suspend fun getParticipantByIdFromDb(id: Int): List<Participant>

    /**
     * Deletes all [Participant] by a given [email].
     */
    suspend fun deleteParticipantByEmail(email: String)
}