package app.ft.ftapp.domain.usecase.chat

import app.ft.ftapp.data.repository.IServerChatRepository
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.ServerResult

/**
 * Usecase returns all messages from chat.
 */
class GetChatMessagesUseCase(private val serverChatRepository: IServerChatRepository) {
    suspend operator fun invoke(chatId: Long): ServerResult<List<ChatSenderMessage>> {
        return serverChatRepository.getChatMessages(chatId)
    }
}