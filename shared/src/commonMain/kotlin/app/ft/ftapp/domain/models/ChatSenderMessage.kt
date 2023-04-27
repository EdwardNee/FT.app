package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * data class to send chat message to the server.
 */
@Serializable
data class ChatSenderMessage(val chatId: Long = 0, val sender: String, val message: String)