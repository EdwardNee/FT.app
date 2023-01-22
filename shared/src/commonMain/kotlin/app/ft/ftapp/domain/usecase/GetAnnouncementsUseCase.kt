package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository

class GetAnnouncementsUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke() {
        return announcementRepository.getAvailableAnnouncements()
    }
}