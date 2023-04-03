package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * From taxi API response.
 */
@Serializable
data class TaxiResponse(
    val currency: String,
    val distance: Double,
    val options: TaxiOptions,
    val price: Double,
    val time: Double,
    val time_text: String
)