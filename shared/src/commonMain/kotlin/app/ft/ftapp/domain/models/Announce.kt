package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Announce class to get data from server.
 */
@Serializable
data class Announce(
    val author: String,
    val email: String,
    val placeFrom: String,
    val placeTo: String,
    val participants: List<Participant>,
    val countOfParticipants: Int
)
