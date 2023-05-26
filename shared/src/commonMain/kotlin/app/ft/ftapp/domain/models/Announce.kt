package app.ft.ftapp.domain.models

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Announce class to get data from server.
 */
@Serializable
@Parcelize
data class Announce(
    val id: Int = 0,
    val chatId: Int? = 0,
    @Transient var timeRemained: Long = 0,
    val authorEmail: String? = "",
    val createTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString(),
    val startTime: String? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString(),
    val placeFrom: String = "Дениса Давыдова 3",
    val placeTo: String = "Дубровская застава 5",
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 3,
    val comment: String = ""
) : Parcelable
