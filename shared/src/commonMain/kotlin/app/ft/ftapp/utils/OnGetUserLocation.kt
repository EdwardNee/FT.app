package app.ft.ftapp.utils

/**
 * Listener to ask for location permission grant.
 */
interface OnGetUserLocation {
    /**
     * Listener to ask for location permission grant.
     */
    fun getPermissionForLocation()

    /**
     * Listener that calls intent to auth in hse.
     */
    fun processHseAuth()
}