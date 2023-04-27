package app.ft.ftapp.android.presentation.groupchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.presentation.viewmodels.ChatEvent
import app.ft.ftapp.presentation.viewmodels.ChatViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class ChatScreenViewModel(private val viewModel: ChatViewModel): ViewModel() {
    private lateinit var timer: Timer
    private var scheduled: Boolean = false

    private val kodein = DIFactory.di
//    private val getChatMessagesUseCase: GetChatMessagesUseCase by kodein.instance()

    init {
        viewModelScope.launch {
            viewModel.chatLoadState.collectLatest {
                when(it) {
                    is ModelsState.Error -> {
                        stopListening()
                    }
                    ModelsState.Loading -> {}
                    ModelsState.NoData -> {}
                    is ModelsState.Success<*> -> {}
                }
            }
        }
    }

    fun startListening(chatId : Long) {
        if (!scheduled) {
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    // TODO: Implement message listening logic here
                    viewModelScope.launch {
                        println("TAG_OF_CHAT in vm")
                        viewModel.onEvent(ChatEvent.GetMessages)
                    }
                }
            }, 0, 1000) // listen every second

            scheduled = true
        }
    }

    fun stopListening() {
        if (this::timer.isInitialized) {
            timer.cancel()
            scheduled = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}