package app.ft.ftapp.domain.usecase.db

import app.ft.ftapp.data.repository.IAnnounceSQRepository
import app.ft.ftapp.domain.models.Announce

/**
 * Gets all the [Announce] from db.
 */
class GetAllAnnouncesFromDb (private val announceSQRepository: IAnnounceSQRepository) {
    suspend operator fun invoke(): List<Announce> {
        return announceSQRepository.getAllAnnouncesFromDb()
    }
}