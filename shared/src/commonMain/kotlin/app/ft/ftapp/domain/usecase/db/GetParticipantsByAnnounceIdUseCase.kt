package app.ft.ftapp.domain.usecase.db

import app.ft.ftapp.data.repository.IParticipantSQRepository

/**
 * Gets all participants of an announce by given announceId
 */
class GetParticipantsByAnnounceIdUseCase(private val participantSQRepository: IParticipantSQRepository) {
    suspend operator fun invoke(announceId: Int) {
        participantSQRepository.getParticipantByIdFromDb(announceId)
    }
}