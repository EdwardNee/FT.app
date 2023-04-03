package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Taxi options for [TaxiParams] data class.
 */
@Serializable
data class TaxiOptions(
    val class_name: String,
    val class_level: String,
    val class_text: String,
    val min_price: Double,
    val price: Double,
    val waiting_time: Double,
    val price_text: String
)