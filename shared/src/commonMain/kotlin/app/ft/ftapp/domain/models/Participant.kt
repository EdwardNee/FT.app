package app.ft.ftapp.domain.models

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Companion data class.
 */
@Serializable
@Parcelize
data class Participant(val username: String = "", val email: String = "", val id: Long = 0) :
    Parcelable