package app.ft.ftapp.domain.models

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Announce class to get data from server.
 */
@Serializable
data class Announce(
    @Transient val id: Int = 0,
    @Transient val timeRemained: Long = 0,
    val author: String = "",
    val email: String? = "",
    val createTime: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
    val startTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val placeFrom: String = "",
    val placeTo: String = "",
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 0,
    val comment: String = ""
)
