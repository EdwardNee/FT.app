package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Taxi arguments to request.
 */
@Serializable
data class TaxiParams(
    val clid: String,
    val apikey: String,
    val rll: String,
    val clss: String = "econom",
    val lang: String = "ru",
    val req: String = ""
)