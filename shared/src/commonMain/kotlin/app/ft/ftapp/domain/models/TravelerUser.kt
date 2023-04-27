package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * data class to become traveler.
 */
@Serializable
data class TravelerUser(val travelId: Long = 0L, val email: String = "")
