package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser

/**
 * Become traveler usecase.
 */
class BecomeTravelerUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(travelerUser: TravelerUser): ServerResult<Announce> {
        return announcementRepository.becomeTraveler(travelerUser)
    }
}