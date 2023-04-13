package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Announce class to get data from server.
 */
@Serializable
data class Announce(
    @Transient val id: Int = 0,
    val author: String = "",
    val email: String? = "",
    val placeFrom: String = "",
    val placeTo: String = "",
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 0,
    val comment: String = ""
)
