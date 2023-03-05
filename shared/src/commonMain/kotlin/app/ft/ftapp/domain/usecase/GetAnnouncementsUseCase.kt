package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

class GetAnnouncementsUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(): ServerResult<List<Announce>> {
        return announcementRepository.getAvailableAnnouncements()
    }
}