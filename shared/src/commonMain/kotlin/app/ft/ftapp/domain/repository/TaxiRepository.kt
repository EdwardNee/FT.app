package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.TaxiApi
import app.ft.ftapp.data.repository.ITaxiRepository
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiResponse

class TaxiRepository(private val taxiApi: TaxiApi): ITaxiRepository {
    override suspend fun getTripInfo(params: TaxiParams): ServerResult<TaxiResponse> {
        return taxiApi.tripInfo(params).await()
    }
}