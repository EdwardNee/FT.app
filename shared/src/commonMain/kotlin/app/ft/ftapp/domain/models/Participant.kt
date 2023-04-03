package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Companion data class.
 */
@Serializable
data class Participant(val username: String, val email: String)