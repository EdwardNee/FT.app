package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Get announces usecase.
 */
class GetAnnouncementsUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(offset: Int, limit: Int): ServerResult<PagingAnnounce> {
        return announcementRepository.getAvailableAnnouncements(offset, limit)
    }
}