package app.ft.ftapp.domain.models

/**
 * World coordinates class entity.
 */
data class LatLng(val lat: Double = 0.0, val lon: Double = 0.0) {
    override fun toString(): String {
        return "$lat;$lon"
    }
}