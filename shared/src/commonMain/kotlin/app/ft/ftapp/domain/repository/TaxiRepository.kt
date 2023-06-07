package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.TaxiApi
import app.ft.ftapp.data.repository.ITaxiRepository
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiResponse
import io.ktor.client.plugins.*
import io.ktor.util.network.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

class TaxiRepository(private val taxiApi: TaxiApi): ITaxiRepository {
    override suspend fun getTripInfo(params: TaxiParams): ServerResult<TaxiResponse> {
//        return taxiApi.tripInfo(params).await()

        var result: ServerResult<TaxiResponse>
        try {
            val response = taxiApi.tripInfo(params)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }
}