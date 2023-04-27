package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.TaxiParams
import app.ft.ftapp.domain.models.TaxiSingleton
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Ktor for Taxi API class.
 */
class TaxiApi(private val client: HttpClient) {
    suspend fun tripInfo(params: TaxiParams): HttpResponse {
        return client.get("/taxi_info") {
            url {
                parameters.append(TaxiSingleton.Query.CLID, params.clid)
                parameters.append(TaxiSingleton.Query.APIKEY, params.apikey)
                parameters.append(TaxiSingleton.Query.CLASS, params.clss)
                parameters.append(TaxiSingleton.Query.RLL, params.rll)
                parameters.append(TaxiSingleton.Query.LANG, params.lang)
            }
        }
    }
}
