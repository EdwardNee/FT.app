package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiResponse

/**
 * Taxi api processing repository.
 */
interface ITaxiRepository {
    /**
     * Gets info about trip by given [TaxiParams].
     */
    suspend fun getTripInfo(params: TaxiParams): ServerResult<TaxiResponse>
}