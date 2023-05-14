package app.ft.ftapp.domain.usecase.db

import app.ft.ftapp.data.repository.IParticipantSQRepository
import app.ft.ftapp.domain.models.Participant

/**
 * Usecase to insert participant to DB.
 */
class InsertParticipantToDbUseCase(private val participantsRepository: IParticipantSQRepository) {
    suspend operator fun invoke(participant: Participant) {
        participantsRepository.insertParticipantToDb(participant)
    }
}