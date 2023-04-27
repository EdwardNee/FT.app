package app.ft.ftapp.domain.usecase.db

import app.ft.ftapp.data.repository.IAnnounceSQRepository
import app.ft.ftapp.domain.models.Announce

/**
 * Insert [Announce] to db usecase.
 */
class InsertAnnounceToDbUseCase(private val announceSQRepository: IAnnounceSQRepository) {
    suspend operator fun invoke(announce: Announce) {
        announceSQRepository.insertAnnounceToDb(announce)
    }
}