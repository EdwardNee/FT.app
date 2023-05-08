package app.ft.ftapp.domain.models

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Announce class to get data from server.
 */
@Serializable
data class Announce(
    val id: Int = 0,
    val chatId: Int? = 0,
    @Transient var timeRemained: Long = 0,
    val authorEmail: String? = "",
    val createTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString(),
    val startTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString(),
    val placeFrom: String = "placeFrom",
    val placeTo: String = "placeTo",
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 0,
    val comment: String = ""
)
