package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

class GetAnnounceByEmailUseCase (private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(email: String): ServerResult<Announce> {
        return announcementRepository.createAnnounce(Announce())
    }
}