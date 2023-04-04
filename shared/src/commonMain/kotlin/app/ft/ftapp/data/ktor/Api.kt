package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.Announce
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Api(private val client: HttpClient) {
    suspend fun getAnnouncements(): HttpResponse {
        return client.get("/api/travel/getAllTravels")
    }

    suspend fun createAnnounce(announce: Announce): HttpResponse {
        return client.post("/api/travel/createTravel") {
            contentType(ContentType.Application.Json)
            setBody(announce)

            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }
    suspend fun updateAnnounce(announce: Announce) {
        client.post("/api/travel/updateTravel") {
            setBody(announce)
        }
    }

    suspend fun deleteAnnounce(announce: Announce) {
        client.post("/api/travel/deleteTravel") {
            setBody(announce)
        }
    }
}