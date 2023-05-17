package app.ft.ftapp.domain.models

/**
 * Sessions state checks.
 */
sealed class SessionState {
    object SessionValid : SessionState()
    object SessionExpired : SessionState()
    object SessionInvalid : SessionState()
}