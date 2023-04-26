package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.TravelerUser
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Api(private val client: HttpClient) {
    suspend fun getAnnouncements(offset: Int, limit: Int): HttpResponse {
        return client.get("/api/travel/getAllTravels") {
            url {
                parameters.append(Api.offset, offset.toString())
                parameters.append(Api.limit, limit.toString())
            }
        }
    }

    suspend fun getTravelByUserEmail(userMail: String): HttpResponse {
        return client.get("/api/travel/getTravelByUserEmail") {
            url {
                parameters.append(userEmail, userMail)
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
    suspend fun updateAnnounce(announce: Announce): HttpResponse {
        return client.post("/api/travel/updateTravel") {
            setBody(announce)
        }
    }

    suspend fun becomeTraveler(traveler: TravelerUser): HttpResponse {
        return client.post("/api/travel/addTraveller") {
            contentType(ContentType.Application.Json)
            setBody(traveler)
        }
    }

    suspend fun deleteAnnounce(travelId: Long): HttpResponse {
        return client.post("/api/travel/deleteTravel") {
            url {
                parameters.append(Api.travelId, travelId.toString())
            }
        }
    }

    suspend fun getOutOfTravel(data: TravelerUser): HttpResponse {
        return client.post("/api/travel/reduceTravaller") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    //region chat
    suspend fun sendChatMessage(data: ChatSenderMessage): HttpResponse {
        return client.post("/api/chat/sendMessage") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    suspend fun getMessagesByChat(chatId: Long): HttpResponse {
        return client.get("/api/chat/getMessagesByChat") {
            contentType(ContentType.Application.Json)
            url {
                parameters.append(Api.chatId, chatId.toString())
            }
        }
    }

    //endregion

    companion object {
        private const val offset = "offset"
        const val limit = "limit"
        const val userEmail = "userEmail"
        const val travelId = "travelId"
        const val chatId = "chatId"
    }
}