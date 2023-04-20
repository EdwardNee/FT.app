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
    @Transient val timeRemained: Long = 0,
    val author: String? = "",
    val createTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
    val startTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
    val placeFrom: String = "",
    val placeTo: String = "",
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 0,
    val comment: String = ""
)
