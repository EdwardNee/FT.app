package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Usecase for creating announcement.
 */
class CreateAnnouncementUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(announce: Announce): ServerResult<Announce> {
        return announcementRepository.createAnnounce(announce)
    }
}