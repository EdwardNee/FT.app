package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Usecase to retrieve announce by email user.
 */
class GetAnnounceByEmailUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(email: String): ServerResult<Announce> {
        return announcementRepository.getAnnounceByEmail(email)
    }
}