package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.ServerResult
import kotlinx.serialization.json.JsonObject

/**
 * Repository to process endpoints of chat.
 */
interface IServerChatRepository {
    /**
     * Returns all messages from chat by [chatId].
     */
    suspend fun getChatMessages(chatId: Long): ServerResult<List<ChatSenderMessage>>

    /**
     * Sends new [message] to chat.
     */
    suspend fun sendChatMessage(message: ChatSenderMessage): ServerResult<JsonObject>
}