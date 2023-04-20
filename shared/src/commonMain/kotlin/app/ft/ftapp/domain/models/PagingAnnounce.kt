package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Paging announce result from the server.
 */
@Serializable
data class PagingAnnounce(
    val totalPages: Int = 0,
    val totalElements: Int = 0,
    val size: Int = 0,
    val content: List<Announce> = emptyList(),
    val number: Int = 0,
    val sort: Sort = Sort(),
    val pageable: Pageable = Pageable(),
    val first: Boolean = false,
    val last: Boolean = false,
    val numberOfElements: Int = 0,
    val empty: Boolean = false,
)

/**
 * Pageable params given from the server.
 */
@Serializable
data class Pageable(
    val offset: Int = 0,
    val sort: Sort = Sort(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val unpaged: Boolean = false,
    val paged: Boolean = false
)

/**
 * Pageable sort param given from the server.
 */
@Serializable
data class Sort(
    val empty: Boolean = false,
    val sorted: Boolean = false,
    val unsorted: Boolean = false
)

/**
 * Converts [PagingAnnounce] to [List] of [Announce].
 */
fun PagingAnnounce.toAnnounce(): List<Announce> {
    return this.content
}