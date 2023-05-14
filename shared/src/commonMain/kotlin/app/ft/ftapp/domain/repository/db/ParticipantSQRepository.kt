package app.ft.ftapp.domain.repository.db

import app.ft.ftapp.data.db.DatabaseDriverFactory
import app.ft.ftapp.data.repository.IParticipantSQRepository
import app.ft.ftapp.domain.models.Participant
import app.ft.ftapp.utils.ParticipantDbMapper
import db.FTAppDatabase

class ParticipantSQRepository(driverFactory: DatabaseDriverFactory) : IParticipantSQRepository {
    private val database = FTAppDatabase(driverFactory.createDriver())
    private val query = database.participantSQQueries
    private val participantDbMapper = ParticipantDbMapper()


    override suspend fun insertParticipantToDb(participant: Participant) {
        query.insertParticipant(participant.id.toInt(), participant.email, participant.username)
    }

    override suspend fun getAllParticipantsFromDb(): List<Participant> {
        val result = query.getAllParticipants().executeAsList()

        val listResult = result.map {
            participantDbMapper.fromDbToModel(it)
        }

        return listResult
    }

    override suspend fun getParticipantByIdFromDb(id: Int): List<Participant> {
        val result = query.getParticipantsToAnnounce(id).executeAsList()

        val listResult = result.map {
            participantDbMapper.fromDbToModel(it)
        }

        return listResult
    }

    override suspend fun deleteParticipantByEmail(email: String) {
        query.deleteParticipant(email)
    }
}