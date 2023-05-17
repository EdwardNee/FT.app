package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.JwtAuthData

/**
 * Helper class that parses jwt token of hse authorization.
 */
expect class CustomJwtParser() {
    fun parse(jwtString: String): JwtAuthData
}