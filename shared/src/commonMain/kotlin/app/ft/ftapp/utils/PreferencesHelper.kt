package app.ft.ftapp.utils

/**
 * Shared preferences shared class.
 */
class PreferencesHelper(private val context: KMMContext) {
    var tokenExpirationDate: Long?
        get() = getExpirationDate()
        set(value) = setExpiration(value)

    var cookies: String?
        get() = getCookie()
        set(value) = setCookie(value)

    var userName: String?
        get() = getName()
        set(value) = setName(value)

    var userCity: String?
        get() = getCity()
        set(value) = setCity(value)

    var modelListIsEmpty: Boolean?
        get() = getModelList()
        set(value) = setModelList(value)

    var downloadInBackground: Boolean?
        get() = getSettingDownloadInBackground()
        set(value) = setSettingDownloadInBackground(value)

    fun eraseUserData() {
        userName = null
        userCity = null
        cookies = null
        tokenExpirationDate = null
    }

    private fun getExpirationDate(): Long {
        return context.getLong(SESSION_EXPIRATION, -1L)
    }

    private fun setExpiration(tokenExpirationDate: Long?) {
        context.putLong(SESSION_EXPIRATION,  tokenExpirationDate ?: -1L)
    }

    private fun getCookie(): String? {
        return context.getString(KEY_COOKIE, null)
    }

    private fun setCookie(cookie: String?) {
        context.putString(KEY_COOKIE, cookie)
    }

    private fun getName(): String? {
        return context.getString(KEY_USER_NAME, null)
    }

    private fun setName(name: String?) {
        context.putString(KEY_USER_NAME, name)
    }

    private fun getCity(): String? {
        return context.getString(KEY_USER_CITY, null)
    }

    private fun setCity(city: String?) {
        context.putString(KEY_USER_CITY, city)
    }

    private fun saveLocation(latitude: Double, longitude: Double) {
        context.putString(KEY_USER_LOCATION_LATITUDE, latitude.toString())
        context.putString(KEY_USER_LOCATION_LONGITUDE, longitude.toString())
    }

    private fun getModelList(): Boolean {
        return context.getBoolean(KEY_MODEL_LIST_EMPTY, true)
    }

    private fun setModelList(value: Boolean?) {
        context.putBoolean(KEY_MODEL_LIST_EMPTY, value ?: true)
    }

    private fun getSettingDownloadInBackground(): Boolean {
        return context.getBoolean(KEY_SETTINGS_DOWNLOAD_BACKGROUND, false)
    }

    private fun setSettingDownloadInBackground(value: Boolean?) {
        context.putBoolean(KEY_SETTINGS_DOWNLOAD_BACKGROUND, value ?: false)
    }

    companion object {
        private const val SESSION_EXPIRATION = "SESSION_EXPIRATION"
        private const val KEY_COOKIE = "KEY_COOKIE"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_USER_CITY = "KEY_USER_CITY"
        private const val KEY_USER_LOCATION_LATITUDE = "KEY_USER_LOCATION_LATITUDE"
        private const val KEY_USER_LOCATION_LONGITUDE = "KEY_USER_LOCATION_LONGITUDE"
        private const val KEY_MODEL_LIST_EMPTY = "KEY_MODEL_LIST_EMPTY"
        private const val KEY_SETTINGS_DOWNLOAD_BACKGROUND = "KEY_SETTINGS_DOWNLOAD_BACKGROUND"
    }
}