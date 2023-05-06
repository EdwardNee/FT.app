package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.FullPolygon
import app.ft.ftapp.domain.models.LatLng
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.VisibleRegion

/**
 * Maps [LatLng] to [Point].
 */
fun LatLng.toPoint(): Point {
    return Point(this.lat, this.lon)
}

/**
 * Maps [Point] to [LatLng].
 */
fun Point.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

/**
 * Maps [FullPolygon] to [VisibleRegion].
 */
fun FullPolygon.visibleRegion(): VisibleRegion {
    return VisibleRegion(
        topLeft.toPoint(),
        topRight.toPoint(),
        bottomLeft.toPoint(),
        bottomRight.toPoint(),
    )
}