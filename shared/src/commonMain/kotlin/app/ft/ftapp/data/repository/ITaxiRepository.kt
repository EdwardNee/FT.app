package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiResponse

interface ITaxiRepository {
    suspend fun getTripInfo(params: TaxiParams): ServerResult<TaxiResponse>
}