package app.ft.ftapp.domain.models

/**
 * Map polygon to describe the bounding box of the area.
 */
class MapPolygon(val left: Double, val bottom: Double, val right: Double, val top: Double) {
    fun getFull(): FullPolygon {
        return FullPolygon(
            topLeft = (left to top).toLatLng(),
            topRight = (right to top).toLatLng(),
            bottomLeft = (left to bottom).toLatLng(),
            bottomRight = (left to bottom).toLatLng()
        )
    }
}

/**
 * Converts [Pair] to [LatLng] readable in latitude longitude format.
 */
fun Pair<Double, Double>.toLatLng(): LatLng {
    return LatLng(this.first, this.second)
}

/**
 * data class that contains full points of the bounding box.
 */
data class FullPolygon(
    val topLeft: LatLng,
    val topRight: LatLng,
    val bottomLeft: LatLng,
    val bottomRight: LatLng
)