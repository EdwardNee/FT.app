package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.Announce
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Api(private val client: HttpClient) {
    suspend fun getAnnouncements(): HttpResponse {
        return client.get("")
    }

    suspend fun createAnnounce(announce: Announce) {
        client.post("/create") {
            setBody(announce)
        }
    }
    suspend fun updateAnnounce(announce: Announce) {
        client.post("/update") {
            setBody(announce)
        }
    }

    suspend fun deleteAnnounce(announce: Announce) {
        client.post("/delete") {
            setBody(announce)
        }
    }
}