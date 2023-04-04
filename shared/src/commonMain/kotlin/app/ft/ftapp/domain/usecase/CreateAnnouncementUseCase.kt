package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

class CreateAnnouncementUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(announce: Announce): ServerResult<Announce> {
        return announcementRepository.createAnnounce(announce)
    }
}