package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Taxi options for [TaxiParams] data class.
 */
@Serializable
data class TaxiOptions(
    val price: Double,
    val min_price: Double,
    val waiting_time: Double,
    val class_name: String,
    val class_text: String,
    val class_level: String,
    val price_text: String
) {
    override fun toString(): String {
        return "$class_name, $class_level, $class_text, $min_price, $price, $waiting_time, $price_text"
    }
}