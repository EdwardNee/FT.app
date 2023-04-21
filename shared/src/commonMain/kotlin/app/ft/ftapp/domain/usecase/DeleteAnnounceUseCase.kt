package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.ServerResult

/**
 * Usecase to delete announce by id.
 */
class DeleteAnnounceUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(id: Long): ServerResult<Int> {
        return announcementRepository.deleteAnnounce(id)
    }
}