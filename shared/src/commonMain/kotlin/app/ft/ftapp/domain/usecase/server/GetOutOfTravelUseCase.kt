package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser

/**
 * Usecase to remove user from the travel.
 */
class GetOutOfTravelUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(data: TravelerUser): ServerResult<Announce> {
        return announcementRepository.getOutOfTravel(data)
    }
}