package app.ft.ftapp.data.ktor

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.RegisterUser
import app.ft.ftapp.domain.models.TravelerUser
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Api(private val client: HttpClient) {
    /**
     * Gets all available [Announce] from the server.
     */
    suspend fun getAnnouncements(offset: Int, limit: Int): HttpResponse {
        return client.get("/api/travel/getAllTravels") {
            url {
                parameters.append(Api.offset, offset.toString())
                parameters.append(Api.limit, limit.toString())
            }
        }
    }
    /**
     * Gets particular [Announce] by giver [userMail].
     */
    suspend fun getTravelByUserEmail(userMail: String): HttpResponse {
        return client.get("/api/travel/getTravelByUserEmail") {
            url {
                parameters.append(userEmail, userMail)
            }
        }
    }
    /**
     * Posts [Announce] to the server.
     */
    suspend fun createAnnounce(announce: Announce): HttpResponse {
        return client.post("/api/travel/createTravel") {
            contentType(ContentType.Application.Json)
            setBody(announce)

            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }

    /**
     * Returns History list of [Announce] from the server.
     */
    suspend fun getTravelHistory(offset: Int, limit: Int, authorEmail: String): HttpResponse {
        return client.get("/api/travel/getTravelHistoryByAuthor") {
            url {
                parameters.append(Api.offset, offset.toString())
                parameters.append(Api.limit, limit.toString())
                parameters.append(Api.authorEmail, authorEmail)
            }
        }
    }

    suspend fun updateAnnounce(announce: Announce): HttpResponse {
        return client.post("/api/travel/updateTravel") {
            contentType(ContentType.Application.Json)
            setBody(announce)

            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }

    suspend fun becomeTraveler(traveler: TravelerUser): HttpResponse {
        return client.post("/api/travel/addTraveller") {
            contentType(ContentType.Application.Json)
            setBody(traveler)
        }
    }

    /**
     * Sends request to start the travel.
     */
    suspend fun startTravel(travelId: Long): HttpResponse {
        return client.post("/api/travel/startTravel") {
            url {
                parameters.append(Api.travelId, travelId.toString())
            }
        }
    }

    /**
     * Sends request to stop the travel.
     */
    suspend fun stopTravel(travelId: Long): HttpResponse {
        return client.post("/api/travel/stopTravel") {
            url {
                parameters.append(Api.travelId, travelId.toString())
            }
        }
    }

    /**
     * Deletes the [Announce] from the server by given [travelId].
     */
    suspend fun deleteAnnounce(travelId: Long): HttpResponse {
        return client.post("/api/travel/deleteTravel") {
            url {
                parameters.append(Api.travelId, travelId.toString())
            }
        }
    }

    /**
     * Removes user from the given [data] travel.
     */
    suspend fun getOutOfTravel(data: TravelerUser): HttpResponse {
        return client.post("/api/travel/reduceTravaller") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    /**
     * Registers user's email in the system.
     */
    suspend fun registerUser(user: RegisterUser): HttpResponse {
        return client.post("/api/user/addUserToSystem") {
            contentType(ContentType.Application.Json)
            setBody(user)
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
        const val authorEmail = "authorEmail"
        const val travelId = "travelId"
        const val chatId = "chatId"
    }
}