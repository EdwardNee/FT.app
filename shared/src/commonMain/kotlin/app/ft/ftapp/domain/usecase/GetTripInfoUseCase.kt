package app.ft.ftapp.domain.usecase

import app.ft.ftapp.data.repository.ITaxiRepository
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiResponse

/**
 * Usecase to get trip info with ya taxi API.
 */
class GetTripInfoUseCase(private val taxiRepository: ITaxiRepository) {
    suspend fun invoke(params: TaxiParams): ServerResult<TaxiResponse> {
        return taxiRepository.getTripInfo(params)
    }
}