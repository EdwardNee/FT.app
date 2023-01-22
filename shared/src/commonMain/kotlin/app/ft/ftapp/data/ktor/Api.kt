package app.ft.ftapp.data.ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Api(private val client: HttpClient) {
    suspend fun getAnnouncements(): HttpResponse {
        return client.get("")
    }
}