package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce

class CreateAnnouncementUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(announce: Announce) {
        announcementRepository.createAnnounce(announce)
    }
}