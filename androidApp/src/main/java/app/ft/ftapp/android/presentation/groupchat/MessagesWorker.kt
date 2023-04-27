package app.ft.ftapp.android.presentation.groupchat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.chat.GetChatMessagesUseCase
import app.ft.ftapp.presentation.viewmodels.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.instance


class MessagesWorker(context: Context, private val workerParams: WorkerParameters) :
    CoroutineWorker(
        context,
        workerParams
    ) {

    private val kodein = DIFactory.di
    private val getMessages: GetChatMessagesUseCase by kodein.instance()

    override suspend fun doWork(): Result {
        val chatId = workerParams.inputData.getLong(CHAT_ID, 0)
        val messages = getMessages(chatId)

        return withContext(Dispatchers.IO) {
            when (messages) {
                is ServerResult.ResultException -> {
                    Result.failure(
                        workDataOf(ERROR_MSG to (messages.error ?: MESSAGES_ERROR))
                    )
                }
                is ServerResult.UnsuccessfulResult -> {
                    Result.retry()
                }
                is ServerResult.SuccessfulResult -> {
                    Result.success(workDataOf(MNG_DATA to messages.model))
                }
            }
        }
    }

    companion object {
        const val ERROR_MSG = "ERROR_MSG"
        const val CHAT_ID = "CHAT_ID"
        const val MNG_DATA = "MNG_DATA"
        const val MESSAGES_ERROR = "Ошибка чтения сообщений"
    }
}