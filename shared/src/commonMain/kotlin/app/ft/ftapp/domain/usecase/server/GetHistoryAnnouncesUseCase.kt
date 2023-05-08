package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult

/**
 * UseCase to get the list of history Announces.
 */
class GetHistoryAnnouncesUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(
        offset: Int,
        limit: Int,
        authorMail: String
    ): ServerResult<PagingAnnounce> {
        return announcementRepository.getHistoryAnnouncements(offset, limit, authorMail)
    }
}