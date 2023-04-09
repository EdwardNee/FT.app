package app.ft.ftapp.domain.usecase.db

import app.ft.ftapp.data.repository.IAnnounceSQRepository
import app.ft.ftapp.domain.models.Announce

/**
 * Gets [Announce] from the db usecase.
 */
class GetAnnounceFromDbUseCase(private val announceSQRepository: IAnnounceSQRepository) {
    suspend operator fun invoke(id: Int): Announce {
        return announceSQRepository.getAnnounceByIdFromDb(id)
    }
}