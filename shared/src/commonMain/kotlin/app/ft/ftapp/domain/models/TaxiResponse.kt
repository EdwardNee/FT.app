package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * From taxi API response.
 */
@Serializable
data class TaxiResponse(
    val options: List<TaxiOptions>,
    val currency: String,
    val distance: Double,
    val time: Double,
    val time_text: String
) {
    override fun toString(): String {
        return "$currency, $distance, $options, $time, $time_text"
    }
}