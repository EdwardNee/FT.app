package app.ft.ftapp.utils

/**
 * Shared preferences shared class.
 */
class PreferencesHelper(private val context: KMMContext) {
    var tokenExpirationDate: Long?
        get() = getExpirationDate()
        set(value) = setExpiration(value)

    var chosenDetailId: String?
        get() = getChosenDetail()
        set(value) = setChosenDetail(value)


    var userName: String?
        get() = getName()
        set(value) = setName(value)

    var userMail: String?
        get() = getEmail()
        set(value) = setEmail(value)


    fun eraseUserData() {
        chosenDetailId = null
        userName = null
        userMail = null
        tokenExpirationDate = null
    }

    private fun setChosenDetail(value: String?) {
        context.putString(CHOSEN_DETAIL_ITEM, value)
    }

    private fun getChosenDetail(): String? {
        return context.getString(CHOSEN_DETAIL_ITEM, "")
    }

    private fun getExpirationDate(): Long {
        return context.getLong(SESSION_EXPIRATION, -1L)
    }

    private fun setExpiration(tokenExpirationDate: Long?) {
        context.putLong(SESSION_EXPIRATION, tokenExpirationDate ?: -1L)
    }

    private fun setEmail(value: String?) {
        context.putString(USER_MAIL, value)
    }

    private fun getEmail(): String? {
        return context.getString(USER_MAIL, "")
    }

    private fun getName(): String? {
        return context.getString(KEY_USER_NAME, null)
    }

    private fun setName(name: String?) {
        context.putString(KEY_USER_NAME, name)
    }

    private fun saveLocation(latitude: Double, longitude: Double) {
        context.putString(KEY_USER_LOCATION_LATITUDE, latitude.toString())
        context.putString(KEY_USER_LOCATION_LONGITUDE, longitude.toString())
    }

    companion object {
        private const val CHOSEN_DETAIL_ITEM = "CHOSEN_DETAIL_ITEM"
        private const val SESSION_EXPIRATION = "SESSION_EXPIRATION"
        private const val USER_MAIL = "USER_MAIL"
        private const val KEY_COOKIE = "KEY_COOKIE"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_USER_CITY = "KEY_USER_CITY"
        private const val KEY_USER_LOCATION_LATITUDE = "KEY_USER_LOCATION_LATITUDE"
        private const val KEY_USER_LOCATION_LONGITUDE = "KEY_USER_LOCATION_LONGITUDE"
        private const val KEY_MODEL_LIST_EMPTY = "KEY_MODEL_LIST_EMPTY"
        private const val KEY_SETTINGS_DOWNLOAD_BACKGROUND = "KEY_SETTINGS_DOWNLOAD_BACKGROUND"
    }
}