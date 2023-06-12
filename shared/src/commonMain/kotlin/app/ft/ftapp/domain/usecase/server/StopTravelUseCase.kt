package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult

/**
 * Stops the given by id travel
 */
class StopTravelUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(travelId: Long): ServerResult<Announce> {
        return announcementRepository.stopTravel(travelId)
    }
}