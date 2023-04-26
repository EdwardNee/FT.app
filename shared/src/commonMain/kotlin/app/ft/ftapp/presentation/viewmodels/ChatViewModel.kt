package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.EMAIL
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.chat.GetChatMessagesUseCase
import app.ft.ftapp.domain.usecase.chat.SendChatMessageUseCase
import app.ft.ftapp.domain.usecase.server.GetAnnounceByEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * ViewModel for groupchat screen.
 */
class ChatViewModel : BaseViewModel() {
    //region DI
    private val getChatMessagesUseCase: GetChatMessagesUseCase by kodein.instance()
    private val sendChatMessageUseCase: SendChatMessageUseCase by kodein.instance()
    private val getAnnounceByEmailUseCase: GetAnnounceByEmailUseCase by kodein.instance()
    //endregion

    val chatId = MutableStateFlow(0L)
    val chatMessages = MutableStateFlow(emptyList<ChatSenderMessage>())

    init {
        onEvent(ChatEvent.GetAnnounce)
        viewModelScope.launch {
            chatId.collectLatest {
//                onEvent(ChatEvent.GetMessages)
            }
        }
    }

    /**
     * Event calls for chat screen.
     */
    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.GetMessages -> {
                getChatMessages()
            }
            is ChatEvent.SendMessage -> {
                sendMessage(event.message)
            }
            ChatEvent.GetAnnounce -> {
                getAnnounceByEmail()
            }
        }
    }

    /**
     * Returns Annouces by given email.
     */
    private fun getAnnounceByEmail() {
        showProgress()
        viewModelScope.launch {
            val result = getAnnounceByEmailUseCase(EMAIL)

            when (result) {
                is ServerResult.ResultException -> {

                }
                is ServerResult.SuccessfulResult -> {
                    chatId.value = result.model.chatId?.toLong() ?: 0
                }
                is ServerResult.UnsuccessfulResult -> {}
            }
        }
    }

    /**
     * Returns chat messages by given chatId.
     */
    private fun getChatMessages() {
//        showProgress()
        viewModelScope.launch {
            val result = getChatMessagesUseCase(chatId.value)

            when (result) {
                is ServerResult.ResultException -> {}
                is ServerResult.SuccessfulResult -> {
                    chatMessages.value = result.model
                    hideProgress()
                }
                is ServerResult.UnsuccessfulResult -> {}
            }

        }
    }

    /**
     * Sends message to chat by chatId.
     */
    private fun sendMessage(message: String) {
//        showProgress()
        viewModelScope.launch {
            val chatMessage = ChatSenderMessage(chatId.value, EMAIL, message)
            val result = sendChatMessageUseCase(chatMessage)

            when (result) {
                is ServerResult.ResultException -> { getChatMessages() }
                is ServerResult.SuccessfulResult -> {}
                is ServerResult.UnsuccessfulResult -> {}
            }
        }
    }
}

/**
 * Type events for chat.
 */
sealed class ChatEvent {
    object GetMessages : ChatEvent()
    class SendMessage(val message: String) : ChatEvent()
    object GetAnnounce : ChatEvent()
}