package app.ft.ftapp.domain.usecase.chat

import app.ft.ftapp.data.repository.IServerChatRepository
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.ServerResult
import kotlinx.serialization.json.JsonObject

/**
 * Usecase to send message to chat.
 */
class SendChatMessageUseCase(private val serverChatRepository: IServerChatRepository) {
    suspend operator fun invoke(message: ChatSenderMessage): ServerResult<JsonObject> {
        return serverChatRepository.sendChatMessage(message)
    }
}