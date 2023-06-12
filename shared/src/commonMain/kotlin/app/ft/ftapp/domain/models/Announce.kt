package app.ft.ftapp.domain.models

import app.ft.ftapp.utils.ConstantValues
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
    val placeFromCoords: AnnounceLatLng? = AnnounceLatLng(),
    val placeToCoords: AnnounceLatLng? = AnnounceLatLng(),
    val travelStatus: String? = ConstantValues.TravelStatus.CREATED,
    val participants: List<Participant>? = emptyList(),
    val countOfParticipants: Int = 3,
    val comment: String = "",
    val price: Int? = 0
) : Parcelable

/**
 * LatLng class for [Announce] requests.
 */
@Serializable
@Parcelize
data class AnnounceLatLng(val lat: String = "", val lon: String = "") : Parcelable

/**
 * Converts [LatLng] to [AnnounceLatLng].
 */
fun LatLng.toStrLL(): AnnounceLatLng {
    return AnnounceLatLng(this.lat.toString(), this.lon.toString())
}

/**
 * Converts [AnnounceLatLng] to [LatLng].
 */
fun AnnounceLatLng.toLatLng(): LatLng {
    val lat = if (this.lat.isNotEmpty()) this.lat.toDouble() else 0.0
    val lon = if (this.lon.isNotEmpty()) this.lon.toDouble() else 0.0

    return LatLng(lat, lon)
}