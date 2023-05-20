package app.ft.ftapp.domain.models

/**
 * Data class to store parsed jwt data.
 */
data class JwtAuthData(
    val commonName: String? = "",
    val givenName: String? = "",
    val familyName: String? = "",
    val email: String? = "",
    val expirationDate: Long? = -1L,
    val issuedAt: Long? = -1L,
    val notValidBefore: Long? = -1L
)