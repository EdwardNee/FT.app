package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.TravelerUser
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Api(private val client: HttpClient) {
    suspend fun getAnnouncements(offset: Int, limit: Int): HttpResponse {
        return client.get("/api/travel/getAllTravels") {
            url {
                parameters.append("offset", offset.toString())
                parameters.append("limit", limit.toString())
            }
        }
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

    suspend fun becomeTraveler(traveler: TravelerUser): HttpResponse {
        return client.post("/api/travel/addTraveller") {
            contentType(ContentType.Application.Json)
            setBody(traveler)
        }
    }

    suspend fun deleteAnnounce(announce: Announce) {
        client.post("/api/travel/deleteTravel") {
            setBody(announce)
        }
    }
}