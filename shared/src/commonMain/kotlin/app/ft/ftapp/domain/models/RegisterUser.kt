package app.ft.ftapp.domain.models

import kotlinx.serialization.Serializable

/**
 * Data class to register user.
 */
@Serializable
data class RegisterUser(val userEmail: String = "", val username: String = "")