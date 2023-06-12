package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Usecase to start the travel.
 */
class StartTravelUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(travelId: Long): ServerResult<Announce> {
        return announcementRepository.startTravel(travelId)
    }
}