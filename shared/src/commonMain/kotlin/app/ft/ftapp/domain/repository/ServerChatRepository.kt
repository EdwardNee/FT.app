package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IServerChatRepository
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.ServerResult
import io.ktor.client.plugins.*
import io.ktor.util.network.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject

class ServerChatRepository(private val api: Api) : IServerChatRepository {
    override suspend fun getChatMessages(chatId: Long): ServerResult<List<ChatSenderMessage>> {
        var result: ServerResult<List<ChatSenderMessage>>
        try {
            val response = api.getMessagesByChat(chatId)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException,
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

    override suspend fun sendChatMessage(message: ChatSenderMessage): ServerResult<JsonObject> {
        var result: ServerResult<JsonObject>
        try {
            val response = api.sendChatMessage(message)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException,
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