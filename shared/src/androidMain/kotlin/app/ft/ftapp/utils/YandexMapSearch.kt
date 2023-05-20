package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.MapBoundingBoxes
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.search.*

/**
 * Implements logic for searching for address.
 */
actual class YandexMapSearch(private val listener: Session.SearchListener) {
    val searchManager: SearchManager = SearchFactory.getInstance().createSearchManager(
        SearchManagerType.COMBINED
    )

    /**
     * Searches in API by given [query].
     */
    actual fun searchByQuery(query: String) {
        val visiblePoly =
            VisibleRegionUtils.toPolygon(MapBoundingBoxes.MOSCOW_BBOX.getFull().visibleRegion())
        val session = searchManager.submit(query, visiblePoly, SearchOptions(), listener)
    }
}